package bit.edu.cn.dictionary.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import bit.edu.cn.dictionary.R;
import bit.edu.cn.dictionary.bean.RecentWord;

public class SaveAdapter extends RecyclerView.Adapter<SaveViewHolder> {

    private static final String TAG = "SaveAdapter";
    private final List<RecentWord> words=new ArrayList<>();

    public  void refresh(List<RecentWord> newWords){
        Log.v(TAG,"refresh");
        words.clear();
        if(newWords!=null){
            words.addAll(newWords);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SaveViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.save_item,viewGroup,false);
        return new SaveViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SaveViewHolder saveViewHolder, int i) {
        saveViewHolder.bind(words.get(i));
    }

    @Override
    public int getItemCount() {
        return words.size();
    }
}
