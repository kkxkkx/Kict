package bit.edu.cn.dictionary;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView iv_theme;
    private  ImageView iv_list;
    private ImageView iv_back;
    private static final String TAG="HomeActcity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorWhite));
        }
        setStatusBarLightMode();

        setContentView(R.layout.acitivity_home);
        iv_list=findViewById(R.id.iv_list);
        iv_theme=findViewById(R.id.iv_theme);
        iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        iv_theme.setOnClickListener(this);
        iv_list.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_list:
                startActivity(new Intent(this, ListActivity.class));
                break;
            case R.id.iv_theme:
                Log.v(TAG,"switch");
                startActivity(new Intent(this, ThemeActivity.class));
                break;
            case R.id.iv_back:
                Log.v(TAG,"finish this activity");
                finish();
                break;
        }
    }
}

