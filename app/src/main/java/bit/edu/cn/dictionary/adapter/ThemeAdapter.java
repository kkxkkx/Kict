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

public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ThemeViewHolder> {

    private static final String TAG="ThemeAdapter";
    List datas;
    public ThemeAdapter(List<String> messages){
        datas=messages;
    }

    private String data;

    @NonNull
    @Override
    public ThemeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_theme,viewGroup,false);
        ThemeAdapter.ThemeViewHolder viewHolder=new ThemeAdapter.ThemeViewHolder(view);
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
    }

    @Override
    public int getItemCount() {
        Log.v(TAG, String.valueOf(datas.size()));
        return datas.size();
    }

    public class ThemeViewHolder extends RecyclerView.ViewHolder{

        TextView tv_theme_select;
        ImageView iv_color;
        public ThemeViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_color=(ImageView)itemView.findViewById(R.id.iv_color);
            tv_theme_select=(TextView)itemView.findViewById(R.id.tv_theme_primary);

        }
    }
}
