package com.example.todo.ui.statistic;

import static android.content.Context.SENSOR_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.todo.RealtimeDatabase.EventService;
import com.example.todo.Room.Entity.Event;
import com.example.todo.Room.ViewModel.EventViewModel;
import com.example.todo.databinding.FragmentStatisticBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StatisticFragment extends Fragment {


    private FragmentStatisticBinding binding;

    private FirebaseUser user;
    private EventViewModel eventViewModel;
    private LiveData<List<Event>> eventList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentStatisticBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        user = FirebaseAuth.getInstance().getCurrentUser();
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);

        eventList = eventViewModel.getEvents();

        eventViewModel.getEvents().observe(getViewLifecycleOwner(), new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                setUpBarChart();
                setUpPieChart();
            }
        });

        return root;
    }
    private void setUpBarChart() {



        // Get a reference to the BarChart view in the XML layout.

        // Create data entries for the chart.
//        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<BarEntry> finishedEntries = new ArrayList<>();
        ArrayList<BarEntry> unfinishedEntries = new ArrayList<>();

        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


        // 存储过去一周日期的列表
        List<String> dates = new ArrayList<>();
        // 存储过去一周星期的列表
        List<String> week = new ArrayList<>();

        // 存储过去一周每天的finished列表
        int[] finished = new int[7];
        Arrays.fill(finished, 0);


        // 存储过去一周unfinished的列表
        int[] unfinished = new int[7];
        Arrays.fill(unfinished, 0);

        String[] weekdays = {"Sun", "Mon", "Tues","Wed", "Thurs", "Fri","Sat"};

        // 循环获取过去七天的日期，并将它们添加到列表中
        for (int i = 0; i < 7; i++) {
            // 将calendar对象回退一天，得到前一天的日期
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            Date date = calendar.getTime();
            String dateString = dateFormat.format(date);
            dates.add(dateString);
            // 将数字转换为对应的星期名称
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            String day = weekdays[dayOfWeek - 1];
            week.add(day);
        }


        for (Event event : eventList.getValue()) {
            String tagDate = dateFormat.format(event.getDate());
            String tagStatus = Integer.toString(event.getStatus());
            for(int i = 0; i < 7; i++){
                if(tagDate.equals(dates.get(i)) ){
                    //Finished
                    if(tagStatus.equals("1")){
                        finished[i]++;
                    }
                    //Unfinished!
                    else if(tagStatus.equals("0")){
                        unfinished[i]++;
                    }

                }

            }
        }
        // 为已完成和未完成的事件创建条目
        for (int i = 0; i < 7; i++) {
            finishedEntries.add(new BarEntry(i, finished[i]));
            unfinishedEntries.add(new BarEntry(i, unfinished[i]));
        }


        // Customize the horizontal axis.
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        List<String> xAxisValues = new ArrayList<>(week);
        XAxis xAxis = binding.barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisValues));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);

        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(7);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);



        // Create a BarDataSet object to represent the data in the chart.
        //写两个
        BarDataSet finishedDataSet = new BarDataSet(finishedEntries, "Finished");
        finishedDataSet.setColors(Color.BLUE);
        // Create a BarData object to hold the data.
        BarDataSet unfinishedDataSet = new BarDataSet(unfinishedEntries, "Unfinished");
        // Customize the appearance of the unfinished event bars.
        unfinishedDataSet.setColors(Color.RED);

        dataSets.add(finishedDataSet);
        dataSets.add(unfinishedDataSet);

        // Create a BarData object to hold the data.
        BarData barData = new BarData(dataSets);
//设置组间距占比30% 每条柱状图宽度占比 70% /barAmount  柱状图间距占比 0%
        float groupSpace = 0.3f; //柱状图组之间的间距
        float barWidth = (1f - groupSpace) / 2;
        float barSpace = 0f;
//设置柱状图宽度
        barData.setBarWidth(barWidth);
//(起始点、柱状图组间距、柱状图之间间距)
        barData.groupBars(0f, groupSpace, barSpace);


        // Set the BarData object on the chart.
        binding.barChart.setData(barData);



        // Customize the appearance of the chart.
        binding.barChart.getDescription().setText("");
        binding.barChart.animateY(1000);


        // Customize the vertical axis.
        YAxis yAxis = binding.barChart.getAxisLeft();
        yAxis.setAxisMinimum(0);
        yAxis.setGranularity(1f);
        yAxis.setDrawGridLines(false);

        binding.barChart.setDrawValueAboveBar(true);
//        binding.barChart.setDrawGridBackground(true);
        binding.barChart.setGridBackgroundColor(Color.WHITE);
        binding.barChart.setDrawBarShadow(true);


        //refresh the chart
        binding.barChart.invalidate();


    }

    private void setUpPieChart() {

        int Finished = 0;
        int Unfinished = 0;



        for(Event event : eventList.getValue()){
            // 获取最新的数据
            String tag = Integer.toString(event.getStatus());
            if(tag.equals("1")){
                Finished++;
//                System.out.println(tag);
            } else if(tag.equals("0")){
                Unfinished++;
            }
        }


        List<PieEntry> pieEntryList = new ArrayList<>();
        pieEntryList.add(new PieEntry(Finished,"Finished!"));
        pieEntryList.add(new PieEntry(Unfinished,"Unfinished!"));
        PieDataSet pieDataSet = new PieDataSet(pieEntryList,"");
        //设置饼图颜色
        pieDataSet.setColors(Color.RED, Color.BLUE);
        //设置数据文本颜色
        pieDataSet.setValueTextColor(Color.WHITE);
        //设置数据文本大小
        pieDataSet.setValueTextSize(20);
        PieData pieData = new PieData(pieDataSet);
        //设置数据百分比显示
        pieData.setValueFormatter(new PercentFormatter());
        binding.pieChart.setData(pieData);
        //设置中心圆大小
        binding.pieChart.setHoleRadius(0);
        //设置中心圆颜色大小
        binding.pieChart.setTransparentCircleRadius(0);
        //右下角英文是否显示
        binding.pieChart.getDescription().setEnabled(false);
        //图例大小
        binding.pieChart.getLegend().setFormSize(10);
        //图例文本大小
        binding.pieChart.getLegend().setTextSize(10);
        //数据描述文本颜色
        binding.pieChart.setEntryLabelColor(Color.WHITE);
        //数据描述文本大小
        binding.pieChart.setEntryLabelTextSize(20);

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}