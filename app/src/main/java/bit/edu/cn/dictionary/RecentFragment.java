package bit.edu.cn.dictionary;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bit.edu.cn.dictionary.bean.RecentWord;
import bit.edu.cn.dictionary.db.LocalWord;
import bit.edu.cn.dictionary.ui.RecentAdapter;

public class RecentFragment extends Fragment {
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       view=inflater.inflate(R.layout.recent_fragment, container,false);
        initRecyclerView();
        return view;
    }
    private void initRecyclerView()
    {
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.recent_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

        RecentAdapter ReAdapter=new RecentAdapter(new RecentOperator() {
            @Override
            public void deleteWord(RecentWord word) {
                deleteWord(word);
            }

            @Override
            public void updateWord(RecentWord word) {
                updateWord(word);
            }
        });

        recyclerView.setAdapter(ReAdapter);
        LocalWord localWord=new LocalWord(getActivity());
        ReAdapter.refresh(localWord.LoadWordsFromDatabase());
    }
}
