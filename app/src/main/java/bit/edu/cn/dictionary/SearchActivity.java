package bit.edu.cn.dictionary;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;

import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import bit.edu.cn.dictionary.bean.AWord;
import bit.edu.cn.dictionary.bean.State;
import bit.edu.cn.dictionary.db.LocalWord;
import bit.edu.cn.dictionary.db.WordSQLHelper;
import bit.edu.cn.dictionary.utils.AudioUtils;
import bit.edu.cn.dictionary.utils.NetworkUtils;

import static bit.edu.cn.dictionary.bean.State.EMPTY;
import static bit.edu.cn.dictionary.bean.State.RECENTINFO;
import static bit.edu.cn.dictionary.bean.State.WORDINFO;


public class SearchActivity extends AppCompatActivity {

    public final static String StoragePath = "/kkdir/pron/";
    public static String searchword = null;
    private final String TAG = "search";
    public WordSQLHelper wordSQLHelper;
    public SQLiteDatabase db;
    private SearchView searchView;

    private final static int WRONG = 1;
    private WordFragment wordFragment = null;
    private RecentFragment recentFragment = null;
    private EmptyFragment empty=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_word);

        wordFragment = new WordFragment();
        recentFragment = new RecentFragment();
        empty=new EmptyFragment();
        switchFragment(RECENTINFO);

        wordSQLHelper = new WordSQLHelper(this);
        db = wordSQLHelper.getWritableDatabase();
        searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchword = s;
                switchFragment(WORDINFO);
                getWordFromInternet();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                switchFragment(EMPTY);
                return false;
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
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
                    final AWord Word_Now = NetworkUtils.getInputStreamByUrl(URL_temp, searchword);
                    if (Word_Now == null)
                    {
                        Message message = new Message();
                        message.obj = WRONG;
                        //   handler.sendMessage(message);
                        //TODO message的传递
                    } else {
                        Runnable updateUIControl=new Runnable() {
                            @Override
                            public void run() {
                               wordFragment.word_info.setText(Word_Now.getKey()+"\n"+Word_Now.getPsA()+"  "+Word_Now.getPsE()+"\n"+Word_Now.getInterpret());

                            }
                            //TODO 封装音频
                            // AudioUtils.getAudio(Word_Now.getPronA(), searchword + "A");
                            //AudioUtils.getAudio(Word_Now.getPronE(), searchword + "E");

                        };
                        SearchActivity.this.runOnUiThread(updateUIControl);
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

    private void switchFragment(State state)
    {
        FragmentManager fm=getFragmentManager();
        if(fm.getBackStackEntryCount()!=0)
            fm.popBackStack();
        FragmentTransaction ft=fm.beginTransaction();
        switch (state){
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
}

