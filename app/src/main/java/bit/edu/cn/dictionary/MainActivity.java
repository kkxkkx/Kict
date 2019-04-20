package bit.edu.cn.dictionary;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static bit.edu.cn.dictionary.db.GetInfo.getInterpret;
import static bit.edu.cn.dictionary.db.GetInfo.getWord;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int REQUEST_CODE = 1;
    public static final int REQUEST_CODE_ADD=2;
    private static final String TAG="main";

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String channelID="word";
            String channelName="单词推送";
            int importance=NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelID,channelName,importance);

        }
        findViewById(R.id.find).setOnClickListener(this);
        findViewById(R.id.WordBook).setOnClickListener(this);
        findViewById(R.id.home).setOnClickListener(this);


        this.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorWhite));
        }
        setStatusBarLightMode();
    }

    //申请权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE: {

                for (int i = 0; i < 3; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) { //同意权限申请

                    } else {
                        Toast toast = Toast.makeText(this, "获取失败", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        return;
                    }
                }
                break;
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.find:
                startActivityForResult(
                        new Intent(MainActivity.this, SearchActivity.class),
                        REQUEST_CODE_ADD);
                break;
            case R.id.WordBook:
                startActivity(new Intent(this, SaveActivity.class));
                break;
            case R.id.home:
                Log.v(TAG,"home");
                startActivity(new Intent(this, HomeAcitivity.class));
                break;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance)
    {
        NotificationChannel channel=new NotificationChannel(channelId,channelName,importance);
        channel.setShowBadge(false);
        NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }

    public void sendwordMsg(View view) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String word=getWord();
        String interpret=getInterpret();
        Notification notification = new NotificationCompat.Builder(this, "chat")
                .setContentTitle("hello")
                .setContentText("n.你好")
                .setSmallIcon(R.drawable.tosave)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.tosave))
                .setAutoCancel(true)
                .setShowWhen(false)
                .setOngoing(true)
                .build();
        manager.notify(1, notification);
    }

    private void setStatusBarLightMode() {
        if (this.getWindow() != null) {
            Class clazz = this.getWindow().getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                extraFlagField.invoke(this.getWindow(), darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

