package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.todo.NavigationDrawerActivity;
import com.example.todo.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;
    private Context context;

    private static final String TAG = "Register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth = FirebaseAuth.getInstance();
        context = RegisterActivity.this;

        binding.RegisterRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.RegisterEmailEdt.getText().toString();
                String password = binding.RegisterPasswordEdt.getText().toString();
                String passwordConfirm = binding.RegisterPasswordConfirmEdt.getText().toString();

                if(isValidEmail(email) && isValidPassword(password)) {

                    if(!password.equals(passwordConfirm)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Alert");
                        builder.setMessage("Two passwords must be same");
                        builder.setPositiveButton("Confirm", null);
                        builder.show();
                    } else {
                        createAccount(email, password);
                    }

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Alert");
                    if (!isValidEmail(email)) {
                        builder.setMessage("Email format should be valid.");
                    } else if (!isValidPassword(password)) {
                        builder.setMessage("The password must contain at least one special character.\n" +
                                "The password must contain at least one uppercase letter and one lowercase letter.\n" +
                                "The password must contain at least one digit.\n" +
                                "The password length must be between 6 to 16 characters.");
                    }
                    builder.setPositiveButton("Confirm", null);
                    builder.show();
//                    Toast.makeText(context, "wrong format", Toast.LENGTH_SHORT).show();
                }
//                createAccount(email, password);
            }
        });

        binding.RegisterCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context, LoginActivity.class);
                startActivity(intent);
            }
        });

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

    private void createAccount(String email, String password) {
        // [START create_user_with_email]
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            String uuid = String.valueOf(UUID.randomUUID()).substring(0, 12);
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName("TODOList_" + uuid)
//                                    .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User profile updated.");
                                            }
                                        }
                                    });

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
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END create_user_with_email]
    }

    private void updateUI(FirebaseUser user) {

        if(user != null) {
            Intent intent =new Intent(context, NavigationDrawerActivity.class);
            startActivity(intent);
        }
    }

    private void saveToken(String token) {
        // 获取SharedPreferences对象
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // 使用Editor对象添加令牌到SharedPreferences中
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();
    }
}