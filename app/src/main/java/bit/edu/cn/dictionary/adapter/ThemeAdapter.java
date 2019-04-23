package bit.edu.cn.dictionary.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import bit.edu.cn.dictionary.R;
import bit.edu.cn.dictionary.ThemeOperator;
import bit.edu.cn.dictionary.bean.RecentWord;
import bit.edu.cn.dictionary.search.HistoryOperator;

public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ThemeViewHolder> {

    private static final String TAG="ThemeAdapter";
    private ThemeOperator operator;
    List datas;

    public ThemeAdapter(List<String> messages, ThemeOperator operator){
        datas=messages;
        this.operator=operator;
    }

    private String data;

    @NonNull
    @Override
    public ThemeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_theme,viewGroup,false);
        ThemeAdapter.ThemeViewHolder viewHolder=new ThemeAdapter.ThemeViewHolder(view,operator);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ThemeViewHolder viewHolder, int i) {
        data= (String)datas.get(i);
        int  drawableId=0;
        if(data.equals("Primary"))
        {
            drawableId=R.color.colorPrimary;
        }
        if(data.equals("Blue"))
        {
            drawableId=R.color.colorBlue;
        }
        if(data.equals("Green"))
        {
            drawableId=R.color.colorGreen;
        }
        if(data.equals("Pink"))
        {
            drawableId=R.color.colorPink;
        }
        if(data.equals("Dark"))
        {
            drawableId=R.color.colorDark;
        }
        viewHolder.iv_color.setImageResource(drawableId);
        viewHolder.iv_color.bringToFront();
        viewHolder.tv_theme_select.setText(data);
        viewHolder.bind(data);
    }

    @Override
    public int getItemCount() {
        Log.v(TAG, String.valueOf(datas.size()));
        return datas.size();
    }

    public class ThemeViewHolder extends RecyclerView.ViewHolder{

        TextView tv_theme_select;
        ImageView iv_color;
        private final ThemeOperator operator;

        public ThemeViewHolder(@NonNull View itemView,final ThemeOperator operator) {
            super(itemView);
            this.operator=operator;
            iv_color=(ImageView)itemView.findViewById(R.id.iv_color);
            tv_theme_select=(TextView)itemView.findViewById(R.id.tv_theme_primary);

        }

        public void bind(final String type){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v(TAG,"search");
                    operator.changeTheme(type);
                }
            });
        }
    }
}
