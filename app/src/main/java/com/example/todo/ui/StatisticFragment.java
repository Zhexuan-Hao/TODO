package com.example.todo.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.todo.R;
import com.example.todo.WelcomeActivity;
import com.example.todo.databinding.FragmentHomeBinding;
import com.example.todo.databinding.FragmentStatisticBinding;
import com.example.todo.ui.home.HomeViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
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


    private void setUpBarChart(@NonNull DataSnapshot snapshot) {

        // Get a reference to the BarChart view in the XML layout.

        // Create data entries for the chart.
        ArrayList<BarEntry> entries = new ArrayList<>();

        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

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
            String dateString = date.toString();
            dates.add(dateString);
            // 将数字转换为对应的星期名称
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            String day = weekdays[dayOfWeek - 1];
            week.add(day);
        }

        //读取对应日期的finished和unfinished
        for(DataSnapshot data : snapshot.getChildren()){
            // 获取最新的数据
            String tagDate = data.child("date").getValue().toString();
            String tagStatus = data.child("status").getValue().toString();;
            for(int i = 0; i < 7; i++){
                if(tagDate.equals(dates.get(i)) ){
                    //Finished
                    if(tagStatus.equals("0")){
                        finished[i]++;
                    }
                    //Unfinished!
                    else if(tagStatus.equals("1")){
                        unfinished[i]++;
                    }

                }
                entries.add(new BarEntry(i,finished[i]));
                entries.add(new BarEntry(i+1,unfinished[i]));

            }
        }


        // Customize the horizontal axis.
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        List<String> xAxisValues = new ArrayList<>(dates);
        XAxis xAxis = binding.barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisValues));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);



        // Create a BarDataSet object to represent the data in the chart.
        //写两个
        BarDataSet dataSet = new BarDataSet(entries, "Finished");



        // Customize the appearance of the bars.
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        // Create a BarData object to hold the data.
        BarData barData = new BarData(dataSet);

        // Set the BarData object on the chart.
        binding.barChart.setData(barData);

        // Customize the appearance of the chart.
        binding.barChart.getDescription().setText("Weekly Quantity");
        binding.barChart.animateY(1000);



        // Customize the vertical axis.
        YAxis yAxis = binding.barChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setGranularity(1f);
        yAxis.setDrawGridLines(false);

        //refresh the chart
        binding.barChart.invalidate();
    }

    private void setUpPieChart(@NonNull DataSnapshot snapshot) {

        int Finished = 0;
        int Unfinished = 0;

        for(DataSnapshot data : snapshot.getChildren()){
            // 获取最新的数据
            String tag = data.child("status").getValue().toString();
            if(tag.equals("Finished!")){
                Finished++;
                System.out.println(tag);
            } else if(tag.equals("Unfinished!")){
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


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentStatisticBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // pieChart
        // 获取 Firebase 实例
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // 获取根引用
        DatabaseReference rootRef = database.getReference();
        // 在根引用上添加路径，获取指向特定节点的引用
        DatabaseReference usersRef = rootRef.child("Event");
        usersRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // Call the setUpPieChart() method to set up and populate the chart.
                setUpPieChart(snapshot);

                // Call the setUpBarChart() method to set up and populate the chart.
                setUpBarChart(snapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 当监听器被取消时，执行该方法
            }
        });

        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}