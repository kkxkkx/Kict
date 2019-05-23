package bit.edu.cn.dictionary.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.ArrayList;
import java.util.List;

import bit.edu.cn.dictionary.CardActivity;
import bit.edu.cn.dictionary.InfoActivity;
import bit.edu.cn.dictionary.R;
import bit.edu.cn.dictionary.SearchActivity;
import bit.edu.cn.dictionary.bean.RecentWord;

import static bit.edu.cn.dictionary.ListActivity.ListAdapter;
import static bit.edu.cn.dictionary.ListActivity.ListWord;
import static bit.edu.cn.dictionary.ListActivity.audio;
import static bit.edu.cn.dictionary.SearchActivity.Word_Now;
import static bit.edu.cn.dictionary.utils.AudioPlayer.US_ACCENT;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.SampleViewHolder> {


    public static final List<RecentWord> words=new ArrayList<>();
    public static final String TAG="Sample";
    private DetailClickListener mlistener;
    private Animation.AnimationListener animationListener;

    public void setDetailListener(DetailClickListener mListener){
        this.mlistener=mListener;
    }

    public void setAnmationLisetner(Animation.AnimationListener anmationLisetner){this.animationListener=anmationLisetner;}

    public  void refresh(List<RecentWord> newWords){
        Log.v(TAG,"refresh");
        words.clear();
        if(newWords!=null){
            for(int i=0;i<newWords.size();i++)
                newWords.get(i).isFlipped=false;
            words.addAll(newWords);
        }
        notifyDataSetChanged();
    }

    public void swith(Card card)
    {
        if(card==Card.WORD)
        {
            for(int i=0;i<words.size();i++)
            {
                words.get(i).isFlipped=false;

            }

        }
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
                Log.v(TAG, String.valueOf(words.get(position).isFlipped));
                if (words.get(position).isFlipped) {
                    words.get(position).isFlipped = false;
                } else {
                    words.get(position).isFlipped = true;
                }
                holder.flipView.setFlipDuration(700);
                holder.flipView.flipTheView();
            }
        });
        holder.switch_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, String.valueOf(words.get(position).isFlipped));
                words.get(position).isFlipped = true;
                holder.flipView.setFlipDuration(700);
                holder.flipView.flipTheView();
            }
        });

        holder.no_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, String.valueOf(words.get(position).isFlipped));
                words.get(position).isFlipped = false;
                holder.flipView.setFlipDuration(700);
                holder.flipView.flipTheView();
            }
        });

        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                mlistener.onClick(words.get(position).getWord());
            }
        });

        holder.animationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.animationView.playAnimation();
                audio.playAndioByWord(words.get(position).getWord(),US_ACCENT);
            }
        });
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    public static class SampleViewHolder extends  RecyclerView.ViewHolder{

        public Button no_back;
        public Button details;
        public TextView pron_front;
        public TextView inter_back;
        public TextView word_front;
        public Button switch_front;
        public EasyFlipView flipView;
        public LottieAnimationView animationView;
        public SampleViewHolder(@NonNull View itemView) {
            super(itemView);
            animationView=itemView.findViewById(R.id.animation_view);
            no_back=itemView.findViewById(R.id.btnNo_cardback);
            details=itemView.findViewById(R.id.btnYes_cardback);
            word_front=itemView.findViewById(R.id.txtWord_cardfront);
            pron_front=itemView.findViewById(R.id.pron_front);
            inter_back=itemView.findViewById(R.id.txtExample_cardback);
            switch_front=itemView.findViewById(R.id.btnViewMeaning_cardfront);
            flipView=itemView.findViewById(R.id.flipView);
        }
        public void bind(final RecentWord cardword)
        {
            word_front.setText(cardword.getWord());
            inter_back.setText(cardword.getInterpret());
            pron_front.setText(cardword.getpron());
        }

    }
}


