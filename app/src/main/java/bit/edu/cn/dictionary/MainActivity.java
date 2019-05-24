package bit.edu.cn.dictionary;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.jaeger.library.StatusBarUtil;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import bit.edu.cn.dictionary.bean.SignState;
import bit.edu.cn.dictionary.db.NoticeDB;
import bit.edu.cn.dictionary.db.SaveWord;
import bit.edu.cn.dictionary.db.Sign;
import bit.edu.cn.dictionary.db.Temp;
import bit.edu.cn.dictionary.notification.SendToNoti;
import bit.edu.cn.dictionary.utils.NoticeParser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int REQUEST_CODE = 1;
    public static final int REQUEST_CODE_ADD=2;
    private static final String TAG="main";
    public TextView tv_date;
    public ImageView iv_sign;
    public TextView tv_sign;
    public TextView tv_click;
    public TextView tv_background;
    public TextView tv_dailyword;
    public Sign signhelper;
    public static SaveWord NotiWord;
    public static NoticeDB noticeDB;
    public Temp tempword;
    public LottieAnimationView animation_fly;

    public static String daily_word;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        StatusBarUtil.setTransparent(this);
        setContentView(R.layout.activity_main);


        animation_fly=findViewById(R.id.animation_fly);
        tv_background=findViewById(R.id.textView3);
        tv_click=findViewById(R.id.textView3);
        iv_sign=findViewById(R.id.iv_sign);
        tv_dailyword=findViewById(R.id.tv_daily_word);
        tv_sign=findViewById(R.id.tv_sign);
        tv_date=findViewById(R.id.tv_date);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        tv_date.setText(simpleDateFormat.format(date));


        signhelper=new Sign(this);
        tempword=new Temp(this);
        final int signdays=signhelper.LoadSignDays(String.valueOf(date));
        signhelper.LoadSignDays(String.valueOf(date));
        Log.v(TAG,"signsignsign"+signdays);
        if(signdays==0)
        {
            signInVisible(SignState.ALREADY);
        }else{
            signInVisible(SignState.NOTYET);
        }

        NotiWord=new SaveWord(this);
        noticeDB=new NoticeDB(this);

        InputStream inputStream = null;
        try {
            inputStream = getAssets().open("words.xml");
            NoticeParser.pull(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }


        tv_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signInVisible(SignState.ALREADY);
                daily_word=sendwordMsg();
                tv_dailyword.setText(daily_word);
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

    public String sendwordMsg() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        SendToNoti send=new SendToNoti();

        String word=send.getNoti_word();
        String interpret=send.getNoti_interpret();
        tempword.add(word);
        Intent intent=new Intent(MainActivity.this,SearchActivity.class);
        intent.putExtra("word",word);
        PendingIntent contentIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);

        Log.v(TAG,"notification_display");

        Notification notification = new NotificationCompat.Builder(this, "word")
                .setContentTitle(word)
                //.setContentText(interpret)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(interpret))
                .setSmallIcon(R.drawable.tosave)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.tosave))
                .setAutoCancel(false)
                .setShowWhen(false)
                .setContentIntent(contentIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setOngoing(true)  //不能删除
                .build();
        manager.notify(1, notification);

        Log.v(TAG,"notification_isplay");
        Intent appIntent=null;
        appIntent = new Intent(this,InfoActivity.class);
        appIntent.setAction(Intent.ACTION_MAIN);
        appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        appIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        return word;
 }

public void signInVisible(SignState sign)
{
    if(sign==SignState.ALREADY)
    {
        tv_date.setVisibility(View.INVISIBLE);
        iv_sign.setVisibility(View.INVISIBLE);
        tv_background.setVisibility(View.INVISIBLE);
        tv_date.setVisibility(View.INVISIBLE);
        tv_sign.setVisibility(View.INVISIBLE);
        tv_dailyword.setVisibility(View.VISIBLE);
        tv_dailyword.setText(tempword.loadFromTemp());

    }else{
        tv_date.setVisibility(View.VISIBLE);
        iv_sign.setVisibility(View.VISIBLE);
        tv_background.setVisibility(View.VISIBLE);
        tv_date.setVisibility(View.VISIBLE);
        tv_sign.setVisibility(View.VISIBLE);
        tv_dailyword.setVisibility(View.INVISIBLE);
    }

}

}

