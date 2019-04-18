package bit.edu.cn.dictionary.search;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bit.edu.cn.dictionary.R;
import bit.edu.cn.dictionary.SearchActivity;
import bit.edu.cn.dictionary.bean.RecentWord;
import bit.edu.cn.dictionary.db.HistoryWord;
import bit.edu.cn.dictionary.ui.HistoryAdapter;

import static bit.edu.cn.dictionary.bean.Page.WORDINFO;

public class HistoryFragment extends Fragment {
    private View view;
    public RecyclerView recyclerView;
    public static HistoryAdapter HisAdapter;
    private final static String TAG="RECENT_FRAGMENT";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view!=null){
            ViewGroup group=(ViewGroup)view.getParent();
            if(group!=null){
                //如果不为空，先从group移去，避免出现空白页面
                group.removeView(view);
            }
            return view;
        }
        Log.v(TAG,"create");
        view=inflater.inflate(R.layout.history_fragment, container,false);
        initRecyclerView();
        return view;
    }

    private void initRecyclerView()
    {
        Log.v("init"," history recycler");
        recyclerView= view.findViewById(R.id.history_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        final HistoryWord historyWord=new HistoryWord(getActivity());

        HisAdapter=new HistoryAdapter(new HistoryOperator(){

            @Override
            public void deleteWord(RecentWord word) {
                Log.v(TAG,"delete");
                historyWord.deleteWord(word);
            }

            @Override
            public void changeFragment(String s) {
                Log.v(TAG,"search");
                SearchActivity search=(SearchActivity)getActivity();

                SearchActivity.searchword =s;
                search.getWordFromInternet();
                search.switchFragment(WORDINFO);
            }
        });

        recyclerView.setAdapter(HisAdapter);
        HisAdapter.refresh(historyWord.LoadWordsFromDatabase());
    }


}
