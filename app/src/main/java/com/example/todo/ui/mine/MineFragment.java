package com.example.todo.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.todo.WelcomeActivity;
import com.example.todo.databinding.FragmentMineBinding;
import com.example.todo.databinding.FragmentSlideshowBinding;
import com.example.todo.ui.slideshow.SlideshowViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MineFragment extends Fragment {

    EditText Email, Nickname;
    Button Statistic, Logout;
    FirebaseUser user;

    private FragmentMineBinding binding;

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
