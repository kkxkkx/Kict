package bit.edu.cn.dictionary;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class HomeAcitivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView iv_theme;
    private  ImageView iv_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_home);
        iv_list=findViewById(R.id.iv_list);
        iv_theme=findViewById(R.id.iv_theme);
        iv_theme.setOnClickListener(this);
        iv_list.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_list:
                startActivity(new Intent(this, SaveActivity.class));
                break;
                
        }
    }
}
