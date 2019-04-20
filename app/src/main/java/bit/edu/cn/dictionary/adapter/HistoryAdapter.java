package bit.edu.cn.dictionary.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bit.edu.cn.dictionary.R;
import bit.edu.cn.dictionary.bean.RecentWord;
import bit.edu.cn.dictionary.search.HistoryOperator;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private final HistoryOperator operator;
    private final List<RecentWord> words=new ArrayList<>();

    public HistoryAdapter(HistoryOperator operator){
        this.operator=operator;
    }

    public  void refresh(List<RecentWord> newWords){
        words.clear();
        if(newWords!=null){
            words.addAll(newWords);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_history,viewGroup,false);
        return new HistoryViewHolder(itemView,operator);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder historyViewHolder, int i) {
        historyViewHolder.bind(words.get(i));
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {

        private TextView wordText;
        private TextView interpretText;
        private final HistoryOperator operator;
        private ImageButton deleteBtn;
        private final static String TAG="Holder";

        public HistoryViewHolder(@NonNull View itemView , final HistoryOperator operator) {
            super(itemView);
            this.operator=operator;
            wordText=itemView.findViewById(R.id.tv_history_word);
            interpretText=itemView.findViewById(R.id.text_interpret);
            deleteBtn=itemView.findViewById(R.id.delete);

        }


        public void bind(final RecentWord recentWord)
        {
            wordText.setText(recentWord.getWord());
            interpretText.setText(recentWord.getInterpret());

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v(TAG,"delete");
                    operator.deleteWord(recentWord);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v(TAG,"search");
                    operator.changeFragment(recentWord.getWord());
                }
            });
        }
    }

}

