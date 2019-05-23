package bit.edu.cn.dictionary;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import bit.edu.cn.dictionary.adapter.Card;
import bit.edu.cn.dictionary.adapter.CardAdapter;
import bit.edu.cn.dictionary.adapter.DetailClickListener;
import bit.edu.cn.dictionary.db.SaveWord;
import bit.edu.cn.dictionary.menu.SpaceItemDecoration;

import static bit.edu.cn.dictionary.ListActivity.ListAdapter;
import static bit.edu.cn.dictionary.ListActivity.ListWord;

public class CardActivity extends AppCompatActivity {
    public CardAdapter CardAdapter;
    public SaveWord cardword;
    public ImageView iv_card_back;
    public Button btn_random;
    public Button btn_again;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        int position=intent.getIntExtra("position",0);
        this.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorDark));
        }
       setStatusBarLightMode();

        setContentView(R.layout.activity_card);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);


        int space=20;
        recyclerView.addItemDecoration(new SpaceItemDecoration(space));

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        iv_card_back=findViewById(R.id.iv_card_back);
        btn_random=findViewById(R.id.card_btn_random);
        btn_again=findViewById(R.id.card_btn_again);



        LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
        llm.scrollToPositionWithOffset(position, 0);
        llm.setStackFromEnd(false);

        CardAdapter=new CardAdapter();
        recyclerView.setAdapter(CardAdapter);
        cardword=new SaveWord(this);
        CardAdapter.refresh(cardword.LoadFromSavedDB());

        CardAdapter.setDetailListener(new DetailClickListener() {
            @Override
            public void onClick(String word) {
                Intent intent_pass=new Intent(CardActivity.this,InfoActivity.class);
                intent_pass.putExtra("word",word);
                startActivity(intent_pass);
            }
        });

        btn_random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=(int) (Math.random() * CardAdapter.getItemCount());
                //TODO 转到指定位置
            }
        });

        btn_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
    protected void onDestroy() {
        ListAdapter.refresh(ListWord.LoadFromSavedDB());
        super.onDestroy();
    }
}
