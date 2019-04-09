package bit.edu.cn.dictionary.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import bit.edu.cn.dictionary.R;
import bit.edu.cn.dictionary.RecentOperator;
import bit.edu.cn.dictionary.bean.RecentWord;


public class RecentAdapter extends RecyclerView.Adapter<RecentViewHolder> {

    private final RecentOperator operator;
    private final List<RecentWord> words=new ArrayList<>();

    public RecentAdapter(RecentOperator operator){
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
    public RecentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.word_recent,viewGroup,false);
        return new RecentViewHolder(itemView,operator);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentViewHolder recentViewHolder, int i) {
        recentViewHolder.bind(words.get(i));
    }

    @Override
    public int getItemCount() {
        return words.size();
    }
}
