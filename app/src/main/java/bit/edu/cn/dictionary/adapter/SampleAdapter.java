package bit.edu.cn.dictionary.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.ArrayList;
import java.util.List;

import bit.edu.cn.dictionary.R;
import bit.edu.cn.dictionary.bean.RecentWord;

public class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.SampleViewHolder> {


  public static final List<RecentWord> words=new ArrayList<>();
  public static final String TAG="Sample";

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
  public SampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
    View itemView =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);

    return new SampleViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(@NonNull final SampleViewHolder holder, final int position) {
    final RecentWord cardword=words.get(position);
    holder.bind(cardword);
    if (holder.flipView.getCurrentFlipState() == EasyFlipView.FlipState.FRONT_SIDE && words.get(
            position).isFlipped) {
      holder.flipView.setFlipDuration(0);
      holder.flipView.flipTheView();
    } else if (holder.flipView.getCurrentFlipState() == EasyFlipView.FlipState.BACK_SIDE
            && !words.get(position).isFlipped) {
      holder.flipView.setFlipDuration(0);
      holder.flipView.flipTheView();
    }
    holder.flipView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (words.get(position).isFlipped) {
          words.get(position).isFlipped = false;
        } else {
          words.get(position).isFlipped = true;
        }
        holder.flipView.setFlipDuration(700);
        holder.flipView.flipTheView();
      }
    });
  }

  @Override
  public int getItemCount() {
    return words.size();
  }

  public static class SampleViewHolder extends  RecyclerView.ViewHolder{

    public Button no_back;
    public Button yes_back;
    public TextView word_back;
    public TextView pron_back;
    public TextView inter_back;
    public TextView word_front;
    public Button switch_front;
    public EasyFlipView flipView;
    public SampleViewHolder(@NonNull View itemView) {
      super(itemView);
      no_back=itemView.findViewById(R.id.btnNo_cardback);
      yes_back=itemView.findViewById(R.id.btnYes_cardback);
      word_back=itemView.findViewById(R.id.txtWord_cardback);
      word_front=itemView.findViewById(R.id.txtWord_cardfront);
      pron_back=itemView.findViewById(R.id.txtpron_cardback);
      inter_back=itemView.findViewById(R.id.txtExample_cardback);
      switch_front=itemView.findViewById(R.id.btnViewMeaning_cardfront);
      flipView=itemView.findViewById(R.id.flipView);
    }
    public void bind(final RecentWord cardword)
    {
      word_front.setText(cardword.getWord());
      word_back.setText(cardword.getWord());
      inter_back.setText(cardword.getInterpret());
    }

  }
}


