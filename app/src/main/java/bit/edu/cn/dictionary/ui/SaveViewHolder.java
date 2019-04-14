package bit.edu.cn.dictionary.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import bit.edu.cn.dictionary.R;
import bit.edu.cn.dictionary.bean.RecentWord;

public class SaveViewHolder extends RecyclerView.ViewHolder {

    public TextView Saved_text;

    public SaveViewHolder(@NonNull View itemView) {
        super(itemView);
        Saved_text=itemView.findViewById(R.id.saved_word);

    }

    public void bind(final RecentWord recentWord)
    {
        Saved_text.setText(recentWord.getWord());
    }
}
