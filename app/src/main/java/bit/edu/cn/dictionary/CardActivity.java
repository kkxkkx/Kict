package bit.edu.cn.dictionary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import bit.edu.cn.dictionary.adapter.SampleAdapter;
import bit.edu.cn.dictionary.db.SaveWord;

public class CardActivity extends AppCompatActivity {
    public SampleAdapter sampleAdapter;
    public SaveWord cardword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        sampleAdapter=new SampleAdapter();
        recyclerView.setAdapter(sampleAdapter);
        cardword=new SaveWord(this);
        sampleAdapter.refresh(cardword.LoadFromSavedDB());
    }
}
