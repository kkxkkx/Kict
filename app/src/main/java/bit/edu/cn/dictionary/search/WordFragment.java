package bit.edu.cn.dictionary.search;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import bit.edu.cn.dictionary.R;
import bit.edu.cn.dictionary.adapter.SentenceAdapter;
import bit.edu.cn.dictionary.db.SaveWord;

import static bit.edu.cn.dictionary.SearchActivity.Word_Now;
import static bit.edu.cn.dictionary.bean.State.NOTSAVE;
import static bit.edu.cn.dictionary.bean.State.SAVED;


public class WordFragment extends Fragment {
    private View view;

    public TextView tv_word;
    public TextView tv_pron_us;
    public TextView tv_pron_uk;
    public TextView tv_interpret;
    public ImageView iv_state;
    public RecyclerView sentence_list;
    public static SentenceAdapter sentence_adapter;

    public final static String TAG="WordFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG,"oncreat");
        view=inflater.inflate(R.layout.fragment_word, container,false);


        tv_interpret=view.findViewById(R.id.tv_interpret);
        tv_pron_uk=view.findViewById(R.id.tv_pron_uk);
        tv_pron_us=view.findViewById(R.id.tv_pron_us);
        tv_word=view.findViewById(R.id.tv_history_word);
        iv_state=view.findViewById(R.id.iv_save);


        sentence_list = view.findViewById(R.id.sentence_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        sentence_list.setLayoutManager(layoutManager);
        sentence_list.setHasFixedSize(true);
        sentence_adapter = new SentenceAdapter();
        sentence_list.setAdapter(sentence_adapter);


        final SaveWord saveWord=new SaveWord(getActivity());

        iv_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Word_Now.getState()== SAVED){
                    iv_state.setImageResource(R.drawable.tosave);
                    Word_Now.setState(NOTSAVE);
                    Log.v(TAG,"NOT SAVED");
                    saveWord.deleteWord(Word_Now.getKey());
                }
                else
                {
                    iv_state.setImageResource(R.drawable.saved);
                    Word_Now.setState(SAVED);
                    Log.v(TAG,"SAVED");
                    saveWord.SaveWord(Word_Now);
                }
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



}
