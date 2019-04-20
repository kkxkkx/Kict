package bit.edu.cn.dictionary.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bit.edu.cn.dictionary.R;

import bit.edu.cn.dictionary.bean.RecentWord;

public class SaveAdapter extends RecyclerView.Adapter<SaveAdapter.SaveViewHolder> {

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
                .inflate(R.layout.item_save,viewGroup,false);
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



    public class SaveViewHolder extends RecyclerView.ViewHolder {

        public TextView Saved_text;

        public SaveViewHolder(@NonNull View itemView) {
            super(itemView);
            Saved_text=itemView.findViewById(R.id.iv_theme_select);

        }

        public void bind(final RecentWord recentWord)
        {
            Saved_text.setText(recentWord.getWord());
        }
    }
}
