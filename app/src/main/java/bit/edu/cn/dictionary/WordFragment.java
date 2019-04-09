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
import android.widget.TextView;


public class WordFragment extends Fragment {
    private View view;
    public TextView word_info;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.word_fragment, container,false);
        word_info=view.findViewById(R.id.textView);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
