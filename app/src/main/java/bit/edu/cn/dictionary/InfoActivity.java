package bit.edu.cn.dictionary;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import bit.edu.cn.dictionary.adapter.SentenceAdapter;
import bit.edu.cn.dictionary.utils.AudioPlayer;
import bit.edu.cn.dictionary.utils.AudioUtils;
import bit.edu.cn.dictionary.utils.NetworkUtils;

import static bit.edu.cn.dictionary.ListActivity.audio;
import static bit.edu.cn.dictionary.SearchActivity.Word_Now;
import static bit.edu.cn.dictionary.utils.AudioPlayer.UK_ACCENT;
import static bit.edu.cn.dictionary.utils.AudioPlayer.US_ACCENT;

public class InfoActivity extends AppCompatActivity {

    public String word_info;
    public TextView tv_word;
    public TextView tv_interpret;
    public TextView tv_pron_us;
    public TextView tv_pron_uk;
    public Toolbar card_toolbar;
    public ImageView iv_us;
    public ImageView iv_uk;

    public RecyclerView sen_recyclerview;
    public SentenceAdapter sentence_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        tv_word=findViewById(R.id.card_history_word);
        tv_interpret=findViewById(R.id.card_interpret);
        tv_pron_uk=findViewById(R.id.card_pron_uk);
        tv_pron_us=findViewById(R.id.card_pron_us);
        card_toolbar=findViewById(R.id.card_toolbar);
        iv_uk=findViewById(R.id.card_iv_pron_uk);
        iv_us=findViewById(R.id.card_iv_pron_us);
        sen_recyclerview=findViewById(R.id.card_sentence_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        sen_recyclerview.setLayoutManager(layoutManager);
        sen_recyclerview.setHasFixedSize(true);
        sentence_adapter = new SentenceAdapter();
        sen_recyclerview.setAdapter(sentence_adapter);

        setSupportActionBar(card_toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        card_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent=getIntent();
        word_info=intent.getStringExtra("word");
        getWordFromInternet();


        iv_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audio.playAndioByWord(Word_Now.getKey(),US_ACCENT);
            }
        });

        iv_uk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audio.playAndioByWord(Word_Now.getKey(),UK_ACCENT);
            }
        });
    }
        //对输入的单词进行搜索
    public void getWordFromInternet() {
        final String Word_temp = word_info;
        if (Word_temp == null || Word_temp.equals(""))
            return ;

        final String URL_temp = NetworkUtils.Search_Word1 + Word_temp + NetworkUtils.Search_Word2;
        Thread thread = new Thread(new Runnable() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                try {
                    Word_Now = NetworkUtils.getInputStreamByUrl(URL_temp, word_info);
                 //   AudioUtils.getAudio(Word_Now.getPronA(),Word_Now.getKey()+"_us");
                   // AudioUtils.getAudio(Word_Now.getPronE(),Word_Now.getKey()+"_uk");
                    Runnable updateUIControl=new Runnable() {
                            @Override
                            public void run() {
                                refresh();
                            }
                        };
                        InfoActivity.this.runOnUiThread(updateUIControl);
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
    }

    public void refresh()
    {
        tv_word.setText(Word_Now.getKey());
        tv_pron_us.setText(Word_Now.getPsA());
        tv_pron_uk.setText(Word_Now.getPsE());
        tv_interpret.setText(Word_Now.getInterpret());
        sentence_adapter.refresh(SentenceAdapter.LoadSentence(Word_Now));
    }
}
