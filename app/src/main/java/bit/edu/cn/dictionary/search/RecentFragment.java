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
import bit.edu.cn.dictionary.db.LocalWord;
import bit.edu.cn.dictionary.ui.RecentAdapter;

import static bit.edu.cn.dictionary.bean.Page.WORDINFO;

public class RecentFragment extends Fragment {
    private View view;
    public RecyclerView recyclerView;
    public static RecentAdapter ReAdapter;
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
        view=inflater.inflate(R.layout.recent_fragment, container,false);
        initRecyclerView();
        return view;
    }

    private void initRecyclerView()
    {
        Log.v("init"," recent recycler");
        recyclerView=(RecyclerView)view.findViewById(R.id.recent_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        final LocalWord localWord=new LocalWord(getActivity());

        ReAdapter=new RecentAdapter(new RecentOperator(){

            @Override
            public void deleteWord(RecentWord word) {
                Log.v(TAG,"delete");
                localWord.deleteWord(word);
            }

            @Override
            public void changeFragment(String s) {
                Log.v(TAG,"search");
                SearchActivity search=(SearchActivity)getActivity();
                search.searchword=s;
                search.getWordFromInternet();
                search.switchFragment(WORDINFO);
            }
        });

        recyclerView.setAdapter(ReAdapter);
        ReAdapter.refresh(localWord.LoadWordsFromDatabase());
    }


}
