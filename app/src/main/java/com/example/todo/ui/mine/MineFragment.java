package com.example.todo.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.todo.Room.Entity.User;
import com.example.todo.WelcomeActivity;
import com.example.todo.databinding.FragmentMineBinding;
import com.example.todo.databinding.FragmentSlideshowBinding;
import com.example.todo.ui.slideshow.SlideshowViewModel;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.components.Description;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import com.github.mikephil.charting.charts.BarChart;


public class MineFragment extends Fragment {

    EditText Email, Nickname;
    Button Statistic, Logout;
    FirebaseUser user;

    private FragmentMineBinding binding;


    public int Finished;
    public int Unfinished;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentMineBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        user = FirebaseAuth.getInstance().getCurrentUser();

        Email = binding.MineEmailEdt;
        Nickname = binding.MineNicknameEdt;

        Nickname.setText(user.getDisplayName());
        Email.setText(user.getEmail());

        Statistic = binding.MineStatisticBtn;
        Logout = binding.MineLogoutBtn;

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                RemoveToken();
                Intent intent =new Intent(getActivity(), WelcomeActivity.class);
                startActivity(intent);
            }
        });


        // 获取 Firebase 实例
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // 获取根引用
        DatabaseReference rootRef = database.getReference();
        // 在根引用上添加路径，获取指向特定节点的引用
        DatabaseReference usersRef = rootRef.child("Event");
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Finished = 0;
                Unfinished = 0;
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

                List<PieEntry> pieEntryList=new ArrayList<>();
                pieEntryList.add(new PieEntry(Finished,"Finished!"));
                pieEntryList.add(new PieEntry(Unfinished,"Unfinished!"));
                PieDataSet pieDataSet=new PieDataSet(pieEntryList,"");
                //设置饼图颜色
                pieDataSet.setColors(Color.RED,Color.BLUE);
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

    public void RemoveToken() {
        // 获取SharedPreferences对象
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("token");
        editor.apply();
    }

}

