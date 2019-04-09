package bit.edu.cn.dictionary.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import bit.edu.cn.dictionary.R;
import bit.edu.cn.dictionary.RecentOperator;
import bit.edu.cn.dictionary.bean.RecentWord;


public class RecentViewHolder extends RecyclerView.ViewHolder {

    private TextView wordText;
    private TextView interpretText;
    private final RecentOperator operator;
    private Button deleteBtn;
    public RecentViewHolder(@NonNull View itemView , RecentOperator operator) {
        super(itemView);
        this.operator=operator;

        wordText=itemView.findViewById(R.id.text_word);
        interpretText=itemView.findViewById(R.id.text_interpret);
        deleteBtn=itemView.findViewById(R.id.btn_delete);
    }

    public void bind(final RecentWord recentWord)
    {
        wordText.setText(recentWord.getWord());
        interpretText.setText(recentWord.getInterpret());

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operator.deleteWord(recentWord);
            }
        });
    }


}
