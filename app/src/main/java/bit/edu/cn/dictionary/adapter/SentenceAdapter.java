package bit.edu.cn.dictionary.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import bit.edu.cn.dictionary.R;
import bit.edu.cn.dictionary.bean.AWord;
import bit.edu.cn.dictionary.bean.Sentence;

public class SentenceAdapter extends RecyclerView.Adapter<SentenceAdapter.SentenceViewHolder> {

    private static final String TAG ="Scentence_Adpater" ;
    private final List<Sentence> sentences=new ArrayList<>();


    public  void refresh(List<Sentence> newSentence){
        sentences.clear();
        if(newSentence!=null){
            sentences.addAll(newSentence);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SentenceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_sentence,viewGroup,false);
        SentenceAdapter.SentenceViewHolder viewHolder=new SentenceAdapter.SentenceViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SentenceViewHolder sentenceViewHolder, int i) {
        sentenceViewHolder.bind(sentences.get(i));
    }

    @Override
    public int getItemCount() {
        return sentences.size();
    }

    class SentenceViewHolder extends RecyclerView.ViewHolder{

        private TextView iv_sentence;
        private TextView iv_translation;
        public SentenceViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_sentence=(TextView) itemView.findViewById(R.id.tv_sentence);
            iv_translation=(TextView) itemView.findViewById(R.id.tv_translation);
        }

        public void bind(final Sentence s)
        {
            iv_translation.setText(s.getTrans());
            iv_sentence.setText(s.getSentence());
        }
    }

    /*
    **@function: 把查出来的例句放入list中
     */
    public static List<Sentence> LoadSentence(AWord w)
    {
        List<Sentence> list=new LinkedList<>();
        Log.v(TAG, String.valueOf(w.getSentenceNum()));
        for(int i=0;i<w.getSentenceNum();i++)
        {
            Sentence s=new Sentence();
            s.setSenten(w.getOrigs(i));
            s.setTrans(w.getTrans(i));
            list.add(s);
        }
        return list;
    }
}
