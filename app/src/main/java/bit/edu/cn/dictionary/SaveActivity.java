package bit.edu.cn.dictionary;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import bit.edu.cn.dictionary.db.SaveWord;
import bit.edu.cn.dictionary.ui.SaveAdapter;

public class SaveActivity extends AppCompatActivity {

    public static SaveAdapter saveAdapter;
    public RecyclerView saved_recycler;
    public SaveWord saveWord;
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save);

        toolbar=findViewById(R.id.wordlist);
        setSupportActionBar(toolbar);

        saved_recycler=findViewById(R.id.saved_list);
        saved_recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        saved_recycler.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        saveAdapter=new SaveAdapter();
        saved_recycler.setAdapter(saveAdapter);
        saveWord=new SaveWord(this);
        saveAdapter.refresh(saveWord.LoadFromSavedDB());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_debug:

                return true;
            case R.id.action_card:
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
