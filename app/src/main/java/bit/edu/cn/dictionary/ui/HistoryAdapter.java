package bit.edu.cn.dictionary.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import bit.edu.cn.dictionary.R;
import bit.edu.cn.dictionary.bean.RecentWord;
import bit.edu.cn.dictionary.search.HistoryOperator;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> {

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
                .inflate(R.layout.history_item,viewGroup,false);
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

}
