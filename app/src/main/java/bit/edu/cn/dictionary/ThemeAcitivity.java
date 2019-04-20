package bit.edu.cn.dictionary;

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

public class ThemeAcitivity extends AppCompatActivity {
    private ThemeAdapter adapter;
    private RecyclerView theme_list;
    private static final String TAG="ThemeActivity";
    private Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        toolbar=findViewById(R.id.theme_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
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
        adapter = new ThemeAdapter(messages);
        theme_list.setAdapter(adapter);
    }

}
