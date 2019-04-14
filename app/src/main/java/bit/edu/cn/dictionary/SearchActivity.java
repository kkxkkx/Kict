package bit.edu.cn.dictionary;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import bit.edu.cn.dictionary.bean.AWord;
import bit.edu.cn.dictionary.bean.Page;
import bit.edu.cn.dictionary.db.LocalWord;
import bit.edu.cn.dictionary.db.SaveWord;
import bit.edu.cn.dictionary.search.EmptyFragment;
import bit.edu.cn.dictionary.search.RecentFragment;
import bit.edu.cn.dictionary.search.WordFragment;
import bit.edu.cn.dictionary.utils.NetworkUtils;

import static bit.edu.cn.dictionary.bean.Page.EMPTY;
import static bit.edu.cn.dictionary.bean.Page.RECENTINFO;
import static bit.edu.cn.dictionary.bean.Page.WORDINFO;
import static bit.edu.cn.dictionary.bean.State.NOTSAVE;


public class SearchActivity extends AppCompatActivity {

    public final static String StoragePath = "/kkdir/pron/";
    public static String searchword = null;
    private final String TAG = "search";
    public SearchView searchView;
    public static AWord Word_Now=null;

    private WordFragment wordFragment = null;
    private RecentFragment recentFragment = null;
    private EmptyFragment empty=null;

    private TextView btn_back=null;
    public  SaveWord saveWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        btn_back=findViewById(R.id.cancel);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchActivity.this, MainActivity.class));
            }
        });

        saveWord=new SaveWord(this);
        wordFragment = new WordFragment();
        recentFragment = new RecentFragment();
        empty=new EmptyFragment();
        switchFragment(RECENTINFO);

        searchView = findViewById(R.id.searchView);
        searchView.onActionViewExpanded();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchword = s;
                saveWord=new SaveWord(getBaseContext());
                getWordFromInternet();
                switchFragment(WORDINFO);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s.length()!=0)
                    switchFragment(EMPTY);
                else
                    switchFragment(RECENTINFO);
                return false;
            }
        });

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.v(TAG,"hasFocus"+hasFocus);
                switchFragment(RECENTINFO);
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void switchFragment(Page page)
    {
        FragmentManager fm=getFragmentManager();
        if(fm.getBackStackEntryCount()!=0)
            fm.popBackStack();
        FragmentTransaction ft=fm.beginTransaction();
        switch (page){
            case WORDINFO:
                ft.replace(R.id.fragment_container,wordFragment);
                ft.addToBackStack(null);
                break;
            case EMPTY:
                ft.replace(R.id.fragment_container,empty);
                ft.addToBackStack(null);
                break;
            case RECENTINFO:
                ft.replace(R.id.fragment_container,recentFragment);
                ft.addToBackStack(null);
                break;
        }
        ft.commit();
    }


    //对输入的单词进行搜索
    public void getWordFromInternet() {

        final String Word_temp = searchword;
        if (Word_temp == null || Word_temp.equals(""))
            return ;
        char[] array = Word_temp.toCharArray();
        //TODO 这里是中文需要进行修改
        if (array[0] > 256)
            return ;

        final String URL_temp = NetworkUtils.Search_Word1 + Word_temp + NetworkUtils.Search_Word2;

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                try {
                    Log.v(TAG, "start thread");
                    Word_Now = NetworkUtils.getInputStreamByUrl(URL_temp, searchword);
                    if (Word_Now == null)
                    {
                        Message message = new Message();
                        //   handler.sendMessage(message);
                        //TODO message的传递
                    } else {
                        Runnable updateUIControl=new Runnable() {
                            @Override
                            public void run() {

                                if(saveWord.IsSaved(searchword))
                                {
                                    Log.v(TAG,"refreshUI");
                                    wordFragment.word_state.setImageResource(R.drawable.saved);
                                }
                                else {
                                    Log.v(TAG,"refreshUI");
                                    wordFragment.word_state.setImageResource(R.drawable.tosave);

                                }
                                wordFragment.word_info.setText(Word_Now.getKey()+"\n"+Word_Now.getPsA()+"  "+Word_Now.getPsE()+"\n"+Word_Now.getInterpret());

                            }
                            //TODO 封装音频
                            // AudioUtils.getAudio(Word_Now.getPronA(), searchword + "A");
                            //AudioUtils.getAudio(Word_Now.getPronE(), searchword + "E");

                        };
                        SearchActivity.this.runOnUiThread(updateUIControl);
                        LocalWord localWord=new LocalWord(getBaseContext());

                        //判断是否在最近查询中
                        if(!localWord.IsExistDB(Word_Now))
                            localWord.saveInfoDatabase(Word_Now);
                        Word_Now.setState(NOTSAVE);
                        Log.v(TAG, String.valueOf(Word_Now.getState()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
    }
}

