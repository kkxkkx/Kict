package bit.edu.cn.dictionary;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import bit.edu.cn.dictionary.adapter.ThemeAdapter;
import bit.edu.cn.dictionary.utils.ThemeParser;

public class ThemeActivity extends AppCompatActivity {
    private ThemeAdapter adapter;
    private RecyclerView theme_list;
    private static final String TAG="ThemeActivity";
    private Toolbar theme_toolbar;

    public static int mThemeId = -1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(savedInstanceState!=null){
            Log.v(TAG, String.valueOf(mThemeId));
            if(savedInstanceState.getInt("theme",-1)!=-1){
                mThemeId=savedInstanceState.getInt("theme");
                this.setTheme(mThemeId);
            }
        }

        setContentView(R.layout.activity_theme);


        theme_toolbar=findViewById(R.id.theme_toolbar);
        setSupportActionBar(theme_toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        theme_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Log.v(TAG,"on create");
        List<String> messages = null;
        try {
            InputStream inputStream = getAssets().open("data.xml");
            messages = ThemeParser.pull(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        theme_list = findViewById(R.id.theme_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        theme_list.setLayoutManager(layoutManager);
        theme_list.setHasFixedSize(true);
        adapter = new ThemeAdapter(messages, new ThemeOperator() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void changeTheme(String s) {
                if(s.equals("Blue")){
                    Log.v(TAG,"blue");
                    mThemeId=R.style.BlueTheme;
                    Log.v(TAG, String.valueOf(mThemeId));
                }if(s.equals("Pink")){
                    mThemeId=R.style.PinkTheme;
                }if(s.equals("Green")){
                    mThemeId=R.style.GreenTheme;
                }if(s.equals("Dark")){
                    mThemeId=R.style.DarkTheme;
                }if(s.equals("Primary")){
                   mThemeId=R.style.AppTheme;
                }
                Intent intent = new Intent(ThemeActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        theme_list.setAdapter(adapter);
    }

    public void onTheme(int itheme){
        mThemeId=itheme;
        Intent intent=getIntent();
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0,0);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("theme", mThemeId);
    }


}

