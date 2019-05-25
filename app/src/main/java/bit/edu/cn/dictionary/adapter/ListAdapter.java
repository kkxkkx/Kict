package bit.edu.cn.dictionary.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bit.edu.cn.dictionary.SearchActivity;
import bit.edu.cn.dictionary.notification.CheckboxChangeListener;
import bit.edu.cn.dictionary.notification.ItemClickListener;
import bit.edu.cn.dictionary.notification.ItemLongClickListener;
import bit.edu.cn.dictionary.R;

import bit.edu.cn.dictionary.ListActivity;
import bit.edu.cn.dictionary.bean.RecentWord;
import bit.edu.cn.dictionary.select.OnWordClickListener;
import bit.edu.cn.dictionary.select.SelectableTextHelper;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.SaveViewHolder> {

    private ItemClickListener mlistener;
    private ItemLongClickListener mlonglistener;
    private CheckboxChangeListener mchecklistener;
    private static final String TAG = "ListAdapter";
    public static final List<RecentWord> words=new ArrayList<>();
    public SelectableTextHelper mSelectableTextHelper;
    public Context mcontext;

    public void setmListener(ItemClickListener mListener){
        this.mlistener=mListener;
    }
   // public void setMchecklistener()
    public void setMlonglistener(ItemLongClickListener mlonglistener){
        this.mlonglistener=mlonglistener;
    }

    public void setMyCheckboxListener(CheckboxChangeListener checkboxListener){
        this.mchecklistener=checkboxListener;
    }

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
    public void onBindViewHolder(@NonNull final SaveViewHolder holder, int i) {
        final RecentWord reword=words.get(i);
        holder.checkBox.setVisibility(ListActivity.isDeleteMode()?View.VISIBLE:View.GONE);

//        holder.checkBox.setOnClickListener(new View.OnClickListener() {
//                                               @Override
//                                               public void onClick(View v) {
//                                                   int position=holder.getAdapterPosition();
//                                                    mchecklistener.onCheckClick(v,position);
//                                               }
//                                           }
//        );

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("checkboxListener","");
                mchecklistener.onChanged();
               // int position=holder.getAdapterPosition();
               // mchecklistener.onCheckClick(buttonView,position);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                mlistener.onItemClick(v,position);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position=holder.getAdapterPosition();
                mlonglistener.onItemLongClick(v,position);
                holder.checkBox.setChecked(true);
                return true;
            }
        });
        holder.checkBox.setChecked(words.get(i).isChecked());
        holder.bind(words.get(i));


    }

    @Override
    public int getItemCount() {
        return words.size();
    }



    public static class SaveViewHolder extends RecyclerView.ViewHolder {

        public TextView Saved_text;
        public CheckBox checkBox;

        public CheckBox getCheckBox() {
            return checkBox;
        }

        public SaveViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox=itemView.findViewById(R.id.checkBox);
            Saved_text=itemView.findViewById(R.id.iv_theme_select);

        }

        public void bind(final RecentWord recentWord)
        {
            Saved_text.setText(recentWord.getWord());

        }
    }
}
