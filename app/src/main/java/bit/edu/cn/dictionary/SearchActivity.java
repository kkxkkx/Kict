package bit.edu.cn.dictionary;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.xml.parsers.ParserConfigurationException;

import bit.edu.cn.dictionary.adapter.SentenceAdapter;
import bit.edu.cn.dictionary.adapter.ThemeAdapter;
import bit.edu.cn.dictionary.bean.AWord;
import bit.edu.cn.dictionary.bean.Page;
import bit.edu.cn.dictionary.db.HistoryWord;
import bit.edu.cn.dictionary.db.SaveWord;
import bit.edu.cn.dictionary.search.EmptyFragment;
import bit.edu.cn.dictionary.search.HistoryFragment;
import bit.edu.cn.dictionary.search.WordFragment;
import bit.edu.cn.dictionary.utils.NetworkUtils;

import static bit.edu.cn.dictionary.bean.Page.EMPTY;
import static bit.edu.cn.dictionary.bean.Page.HistoryInfo;
import static bit.edu.cn.dictionary.bean.Page.WORDINFO;
import static bit.edu.cn.dictionary.bean.State.NOTSAVE;
import static bit.edu.cn.dictionary.search.HistoryFragment.HisAdapter;
import static bit.edu.cn.dictionary.search.WordFragment.sentence_adapter;


public class SearchActivity extends AppCompatActivity {

    public final static String StoragePath = "/kkdir/pron/";
    public static String searchword = null;
    private final String TAG = "search";
    public SearchView searchView;
    public static AWord Word_Now=null;

    private WordFragment wordFragment = null;
    private HistoryFragment historyFragment = null;
    private EmptyFragment empty=null;

    private TextView btn_back=null;
    public  SaveWord saveWord;
    public HistoryWord historyWord;


    public RecyclerView sentence_list;

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

        setContentView(R.layout.acitivity_search);

        btn_back=findViewById(R.id.cancel);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        saveWord=new SaveWord(this);
        wordFragment = new WordFragment();
        historyFragment = new HistoryFragment();
        empty=new EmptyFragment();
        switchFragment(HistoryInfo);

        historyWord=new HistoryWord(this);

        searchView = findViewById(R.id.searchView);
        searchView.onActionViewExpanded();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchView.clearFocus();
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
                {
                    HisAdapter.refresh(historyWord.LoadWordsFromDatabase());
                    switchFragment(HistoryInfo);
                }
                return false;
            }
        });

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.v(TAG,"hasFocus"+hasFocus);
                switchFragment(HistoryInfo);
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG,"destroy");
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
            case HistoryInfo:
                ft.replace(R.id.fragment_container,historyFragment);
                ft.addToBackStack(null);
                break;
        }
        ft.commit();
    }

    //重写返回方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
            if(keyCode== KeyEvent.KEYCODE_BACK)
                finish();
            Log.v(TAG,"back");
            return true;
    }


    //对输入的单词进行搜索
    public void getWordFromInternet() {

        final String Word_temp = searchword;
        if (Word_temp == null || Word_temp.equals(""))
            return ;
        char[] array = Word_temp.toCharArray();
        if (array[0] > 256)
            return ;

        final String URL_temp = NetworkUtils.Search_Word1 + Word_temp + NetworkUtils.Search_Word2;
        Log.v(TAG,URL_temp.toString());
        Thread thread = new Thread(new Runnable() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {

                try {
                    Log.v(TAG, "start thread");
                    Word_Now = NetworkUtils.getInputStreamByUrl(URL_temp, searchword);
                    if (Word_Now == null)
                    {

                    } else {
                        Runnable updateUIControl=new Runnable() {
                            @Override
                            public void run() {

                                if(saveWord.IsSaved(searchword))
                                {
                                    Log.v(TAG,"refreshUI");
                                    wordFragment.iv_state.setImageResource(R.drawable.saved);
                                }
                                else {
                                    Log.v(TAG,"refreshUI");
                                    wordFragment.iv_state.setImageResource(R.drawable.tosave);
                                }
                                sentence_adapter.refresh(SentenceAdapter.LoadSentence(Word_Now));
                                wordFragment.tv_word.setText(Word_Now.getKey());
                                wordFragment.tv_pron_us.setText(Word_Now.getPsA());
                                wordFragment.tv_pron_uk.setText(Word_Now.getPsE());
                                wordFragment.tv_interpret.setText(Word_Now.getInterpret());

                            }
                        };
                        SearchActivity.this.runOnUiThread(updateUIControl);
                        HistoryWord history=new HistoryWord(getBaseContext());

                        if(!history.IsExistDB(Word_Now))
                            history.saveInfoDatabase(Word_Now);
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

