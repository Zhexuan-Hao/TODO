package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.todo.NavigationDrawerActivity;
import com.example.todo.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;
    private Context context;

    private static final String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        context = LoginActivity.this;

        binding.LoginLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.LoginEmailEdt.getText().toString();
                String password = binding.LoginPasswordEdt.getText().toString();
                if(isValidEmail(email) && isValidPassword(password)) {
                    signIn(email, password);
                } else {
                    Toast.makeText(context, "wrong format", Toast.LENGTH_SHORT).show();
                }
//                signIn(email, password);
            }
        });

        binding.LoginRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent();
                intent.setClass(context, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }

        String token = getSavedToken();
        if(token != null && "".equals(token)) {
            signInWithToken(token);
        }

    }

    public static boolean isValidEmail(String email) {
        // 电子邮件地址必须符合RFC 822规范
        String pattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
        return email.matches(pattern);
    }


    public static boolean isValidPassword(String password) {
        // 密码必须包含大小写字母和至少一个特殊符号，长度在6-16个字符之间
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{6,16}$";
        return password.matches(pattern);
    }

    private void signInWithToken(String token) {
        firebaseAuth.signInWithCustomToken(token)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithToken:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithToken:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void signIn(String email, String password) {
        // [START sign_in_with_email]
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            firebaseAuth.getCurrentUser().getIdToken(true)
                                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                                            if (task.isSuccessful()) {
                                                String token = task.getResult().getToken();
                                                // 将令牌保存到本地存储中
                                                saveToken(token);
                                            } else {
                                                // 获取令牌失败
                                            }
                                        }
                                    });

                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END sign_in_with_email]
    }

    private String getSavedToken() {
        // 获取SharedPreferences对象
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // 从SharedPreferences中获取保存的令牌
        String token = sharedPreferences.getString("token", null);

        return token;
    }

    private void saveToken(String token) {
        // 获取SharedPreferences对象
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // 使用Editor对象添加令牌到SharedPreferences中
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();
    }

    private void updateUI(FirebaseUser user) {
        if(user != null) {
            Intent intent =new Intent(context, NavigationDrawerActivity.class);
            startActivity(intent);
        }
    }

    private void reload() {
        Intent intent = new Intent();
        intent.setClass(context, NavigationDrawerActivity.class);
        startActivity(intent);
    }
}