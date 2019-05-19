package bit.edu.cn.dictionary;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import bit.edu.cn.dictionary.menu.NetSpeed;
import bit.edu.cn.dictionary.menu.NetSpeedTimer;

import static android.view.View.VISIBLE;

public class DebugActivity extends  AppCompatActivity implements Callback {

    private static int REQUEST_CODE_STORAGE_PERMISSION = 1001;
    private NetSpeedTimer mNetSpeedTimer;
    private TextView InternetText;
    private Toolbar info_toolbar;
    private RelativeLayout re_speed;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        setTitle(R.string.action_debug);

        Handler handler = new Handler(this);
        //创建NetSpeedTimer实例
        mNetSpeedTimer = new NetSpeedTimer(this, new NetSpeed(), handler).setDelayTime(1000).setPeriodTime(2000);

        info_toolbar=(Toolbar) findViewById(R.id.info_toolbar);
        info_toolbar.setTitle("");
        setSupportActionBar(info_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        re_speed=findViewById(R.id.re_speed);

        info_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    finish();
            }
        });

        final Button printBtn = findViewById(R.id.btn_print_path);
        final TextView pathText = findViewById(R.id.text_path);
        printBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pathText.setVisibility(VISIBLE);
                StringBuilder sb = new StringBuilder();
                sb.append("===== Internal Private =====\n").append(getInternalPath())
                        .append("===== External Private =====\n").append(getExternalPrivatePath())
                        .append("===== External Public =====\n").append(getExternalPublicPath());
                pathText.setText(sb);
            }
        });

        final Button changeBtn=findViewById(R.id.change_net);
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });

        final Button permissionBtn = findViewById(R.id.btn_request_permission);
        permissionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int state = ActivityCompat.checkSelfPermission(DebugActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (state == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(DebugActivity.this, "already granted",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                ActivityCompat.requestPermissions(DebugActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_STORAGE_PERMISSION);
            }
        });


        //打印实时网速
        final Button fileWriteBtn = findViewById(R.id.btn_write_files);
        InternetText = findViewById(R.id.text_files);
        fileWriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNetSpeedTimer.startSpeedTimer();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions.length == 0 || grantResults.length == 0) {
            return;
        }
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION) {
            int state = grantResults[0];
            if (state == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(DebugActivity.this, "permission granted",
                        Toast.LENGTH_SHORT).show();
            } else if (state == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(DebugActivity.this, "permission denied",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getInternalPath() {
        Map<String, File> dirMap = new LinkedHashMap<>();
        dirMap.put("cacheDir", getCacheDir());
        dirMap.put("filesDir", getFilesDir());
        dirMap.put("customDir", getDir("custom", MODE_PRIVATE));
        return getCanonicalPath(dirMap);
    }

    private String getExternalPrivatePath() {
        Map<String, File> dirMap = new LinkedHashMap<>();
        dirMap.put("cacheDir", getExternalCacheDir());
        dirMap.put("filesDir", getExternalFilesDir(null));
        dirMap.put("picturesDir", getExternalFilesDir(Environment.DIRECTORY_PICTURES));
        return getCanonicalPath(dirMap);
    }

    private String getExternalPublicPath() {
        Map<String, File> dirMap = new LinkedHashMap<>();
        dirMap.put("rootDir", Environment.getExternalStorageDirectory());
        dirMap.put("picturesDir",
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
        return getCanonicalPath(dirMap);
    }

    private static String getCanonicalPath(Map<String, File> dirMap) {
        StringBuilder sb = new StringBuilder();
        try {
            for (String name : dirMap.keySet()) {
                sb.append(name)
                        .append(": ")
                        .append(dirMap.get(name).getCanonicalPath())
                        .append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }



    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case NetSpeedTimer.NET_SPEED_TIMER_DEFAULT:
                String speed = (String)msg.obj;
                //打印你所需要的网速值，单位默认为kb/s
                re_speed.setVisibility(VISIBLE);
                InternetText.setText("speed ="+speed);
                Log.i("current net speed", speed);
                break;

            default:
                break;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        if(null != mNetSpeedTimer){
            mNetSpeedTimer.stopSpeedTimer();
        }
        super.onDestroy();
    }

}

