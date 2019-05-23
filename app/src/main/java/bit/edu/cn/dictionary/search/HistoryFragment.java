package bit.edu.cn.dictionary.search;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import bit.edu.cn.dictionary.R;
import bit.edu.cn.dictionary.SearchActivity;
import bit.edu.cn.dictionary.adapter.HistoryAdapter;
import bit.edu.cn.dictionary.bean.RecentWord;
import bit.edu.cn.dictionary.db.HistoryWord;

import static bit.edu.cn.dictionary.SearchActivity.et_searchview;
import static bit.edu.cn.dictionary.SearchActivity.historyWord;
import static bit.edu.cn.dictionary.bean.Page.WORDINFO;

public class HistoryFragment extends Fragment {
    private View view;
    public RecyclerView recyclerView;
    public static HistoryAdapter HisAdapter;
    private final static String TAG="RECENT_FRAGMENT";
    private Button emptyBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG,"create");
        view=inflater.inflate(R.layout.fragment_history, container,false);
        initRecyclerView();

        emptyBtn=view.findViewById(R.id.empty);
        emptyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlertDialog();
            }
        });

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
                search.flag=false;
                search.switchFragment(WORDINFO);
                et_searchview.setText(s);
            }
        });

        recyclerView.setAdapter(HisAdapter);
        HisAdapter.refresh(historyWord.LoadWordsFromDatabase());
    }

    private void createAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete");
        builder.setMessage("Are you sure you want to delete all history words" );
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //TODO 删除数据库
                historyWord.empty();
                HisAdapter.refresh(historyWord.LoadWordsFromDatabase());
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
