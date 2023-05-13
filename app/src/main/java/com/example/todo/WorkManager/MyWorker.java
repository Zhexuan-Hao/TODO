package com.example.todo.WorkManager;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.todo.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MyWorker extends Worker {

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }


    @NonNull
    @Override
    public Result doWork() {
        displayNotification("I am your WorkManager!!","Hey, you have task(s) to do! Don't forget!!");
        long timeGetTime =new Date().getTime();
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd-HH:mm:ssE",Locale.getDefault());
        String time = sdf.format(timeGetTime);
        Log.d(TAG, "Here is WorkManager --->:"+time);
        return Result.success();
    }

    private void displayNotification(String task, String desc){
        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("simple","simple",NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),"simple")
                .setContentTitle(task)
                .setContentText(desc)
                .setSmallIcon(R.mipmap.ic_launcher);

        manager.notify(1,builder.build());
    }
}
