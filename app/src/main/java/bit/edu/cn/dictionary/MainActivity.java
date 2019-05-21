package bit.edu.cn.dictionary;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import bit.edu.cn.dictionary.db.SaveWord;
import bit.edu.cn.dictionary.db.Sign;
import bit.edu.cn.dictionary.notification.SendToNoti;

import static bit.edu.cn.dictionary.db.GetInfo.getInterpret;
import static bit.edu.cn.dictionary.db.GetInfo.getWord;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int REQUEST_CODE = 1;
    public static final int REQUEST_CODE_ADD=2;
    private static final String TAG="main";
    public TextView tv_date;
    public ImageView iv_sign;
    public TextView tv_sign;
    public TextView tv_click;
    public Sign signhelper;
    public static SaveWord NotiWord;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);



        NotiWord=new SaveWord(this);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        tv_date=findViewById(R.id.tv_date);
        tv_date.setText(simpleDateFormat.format(date));
        signhelper=new Sign(this);
        final int signdays=signhelper.LoadSignDays(String.valueOf(date));

        tv_click=findViewById(R.id.textView3);
        iv_sign=findViewById(R.id.iv_sign);
        tv_sign=findViewById(R.id.tv_sign);
        tv_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_date.setText("");
                tv_sign.setText("签到成功！");
                sendwordMsg();
                tv_date.setText("已连续签到"+signdays+"天");
                iv_sign.setImageResource(R.drawable.icon_sign_already);
            }
        });


        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String channelID="word";
            String channelName="单词推送";
            int importance=NotificationManager.IMPORTANCE_MIN;
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
                startActivity(new Intent(this, ListActivity.class));
                break;
            case R.id.home:
                Log.v(TAG,"home");
                startActivity(new Intent(this, HomeActivity.class));
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

    public void sendwordMsg() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        SendToNoti send=new SendToNoti();
        String word=send.getNoti_word();
        String interpret=send.getNoti_interpret();
        Log.v(TAG,"notification_display");
        Notification notification = new NotificationCompat.Builder(this, "word")
                .setContentTitle(word)
                //.setContentText(interpret)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(interpret))
                .setSmallIcon(R.drawable.tosave)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.tosave))
                .setAutoCancel(false)
                .setShowWhen(false)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setOngoing(true)  //不能删除
                .build();
        manager.notify(1, notification);
        Log.v(TAG,"notification_isplay");
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

