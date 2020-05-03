package com.example.fcmnotifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText cookies;
    Button notification;

    String CHANNEL_ID = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        cookies = findViewById(R.id.etCookies);
        notification = findViewById(R.id.btnSendNotification);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numberOfCookies = cookies.getText().toString().trim();
                Intent intent = new Intent(MainActivity.this, Second.class);
                intent.putExtra("cookies", numberOfCookies);
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cookies);

                //remoteview for custom notification
                RemoteViews collapse_layout = new RemoteViews(getPackageName(), R.layout.collapsed_layout);
                RemoteViews expandable_layout = new RemoteViews(getPackageName(), R.layout.expandable_layout);
                expandable_layout.setTextViewText(R.id.tvNumberOfCookies, "You have successfully bought " + numberOfCookies);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_chat)
                        .setCustomContentView(collapse_layout)
                        .setCustomBigContentView(expandable_layout)
                        .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
//                        .setContentTitle("Cookies")
//                        .setContentText("You just bought " + numberOfCookies + " cookies")
//                        .setContentIntent(pendingIntent)
//                        .setAutoCancel(true)
//                        .setLargeIcon(bitmap)
//                        .addAction(R.drawable.ic_launcher_background, "GET BONUS", pendingIntent)
//                        .addAction(R.drawable.ic_launcher_background, "OPTION 2", pendingIntent)
                        .setColor(getResources().getColor(R.color.colorPrimary))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(MainActivity.this);
                //notification ID is unique for each notification you create
                notificationManagerCompat.notify(1, builder.build());
                Toast.makeText(MainActivity.this, "You bought " + numberOfCookies + " cookies", Toast.LENGTH_SHORT).show();
                cookies.setText(" ");
            }
        });
    }

    public void createNotificationChannel() {
        //Create notification channels only for android API level 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "My Name";
            String description = "My description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            //Register the channel with the system
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
