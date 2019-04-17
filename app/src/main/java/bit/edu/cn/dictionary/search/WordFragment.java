package bit.edu.cn.dictionary.search;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import bit.edu.cn.dictionary.R;
import bit.edu.cn.dictionary.db.SaveWord;

import static bit.edu.cn.dictionary.SearchActivity.Word_Now;
import static bit.edu.cn.dictionary.bean.State.NOTSAVE;
import static bit.edu.cn.dictionary.bean.State.SAVED;


public class WordFragment extends Fragment {
    private View view;
    public TextView word_info;
    public ImageView word_state;

    public final static String TAG="WordFragment";

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

        Log.v(TAG,"oncreat");
        view=inflater.inflate(R.layout.word_fragment, container,false);
        word_info=view.findViewById(R.id.textView);
        word_state=view.findViewById(R.id.ToSave);

        final SaveWord saveWord=new SaveWord(getActivity());

        word_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Word_Now.getState()== SAVED){
                    word_state.setImageResource(R.drawable.tosave);
                    Word_Now.setState(NOTSAVE);
                    Log.v(TAG,"NOT SAVED");
                    saveWord.deleteWord(Word_Now.getKey());
                }
                else
                {
                    word_state.setImageResource(R.drawable.saved);
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
