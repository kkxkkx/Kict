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
import android.widget.LinearLayout;
import android.widget.TextView;

import bit.edu.cn.dictionary.R;
import bit.edu.cn.dictionary.adapter.SentenceAdapter;
import bit.edu.cn.dictionary.db.SaveWord;

import static bit.edu.cn.dictionary.SearchActivity.Word_Now;
import static bit.edu.cn.dictionary.SearchActivity.audioPlay;
import static bit.edu.cn.dictionary.SearchActivity.saveWord;
import static bit.edu.cn.dictionary.SearchActivity.searchword;
import static bit.edu.cn.dictionary.bean.State.NOTSAVE;
import static bit.edu.cn.dictionary.bean.State.SAVED;
import static bit.edu.cn.dictionary.db.SaveState.FORSAVE;
import static bit.edu.cn.dictionary.utils.AudioPlayer.UK_ACCENT;
import static bit.edu.cn.dictionary.utils.AudioPlayer.US_ACCENT;


public class WordFragment extends Fragment {
    private View view;

    public TextView tv_word;
    public TextView tv_pron_us;
    public TextView tv_pron_uk;
    public TextView tv_interpret;
    public ImageView iv_state;
    public ImageView iv_us;
    public ImageView iv_uk;
    public RecyclerView sentence_list;
    public  SentenceAdapter sentence_adapter;
    public LinearLayout interpret;

    public Boolean IsInterpret=true;

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
        iv_us=view.findViewById(R.id.pron_us);
        iv_uk=view.findViewById(R.id.pron_uk);
        interpret=view.findViewById(R.id.pron_interpret_divider);

        sentence_list = view.findViewById(R.id.sentence_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        sentence_list.setLayoutManager(layoutManager);
        sentence_list.setHasFixedSize(true);
        sentence_adapter = new SentenceAdapter();
        sentence_list.setAdapter(sentence_adapter);


        final SaveWord saveWord=new SaveWord(getActivity());

        interpret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IsInterpret)
                {
                    tv_interpret.setVisibility(View.GONE);
                    IsInterpret=false;
                }
                else
                {
                    tv_interpret.setVisibility(View.VISIBLE);
                    IsInterpret=true;
                }
            }
        });

        iv_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioPlay.playAndioByWord(Word_Now.getKey(),US_ACCENT);
            }
        });

        iv_uk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioPlay.playAndioByWord(Word_Now.getKey(),UK_ACCENT);
            }
        });

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


    public void refresh()
    {
        Log.v("iv_state", String.valueOf(iv_state));
        if(saveWord.IsSaved(searchword,FORSAVE))
        {
            Log.v(TAG,"refreshUI");
            iv_state.setImageResource(R.drawable.saved);
        }
        else {
            Log.v(TAG,"refreshUI");
            iv_state.setImageResource(R.drawable.tosave);
        }

        tv_word.setText(Word_Now.getKey());
        tv_pron_us.setText(Word_Now.getPsA());
        tv_pron_uk.setText(Word_Now.getPsE());
        tv_interpret.setText(Word_Now.getInterpret());
        sentence_adapter.refresh(SentenceAdapter.LoadSentence(Word_Now));
    }



}
