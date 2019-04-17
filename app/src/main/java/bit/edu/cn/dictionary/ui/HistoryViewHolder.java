package bit.edu.cn.dictionary.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import bit.edu.cn.dictionary.R;
import bit.edu.cn.dictionary.bean.RecentWord;
import bit.edu.cn.dictionary.search.HistoryOperator;


public class HistoryViewHolder extends RecyclerView.ViewHolder {

    private TextView wordText;
    private TextView interpretText;
    private final HistoryOperator operator;
    private ImageButton deleteBtn;
    private final static String TAG="Holder";

    public HistoryViewHolder(@NonNull View itemView , final HistoryOperator operator) {
        super(itemView);
        this.operator=operator;
        wordText=itemView.findViewById(R.id.text_word);
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
