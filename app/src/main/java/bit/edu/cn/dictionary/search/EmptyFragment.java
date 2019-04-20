package bit.edu.cn.dictionary.search;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bit.edu.cn.dictionary.R;
public class EmptyFragment extends Fragment {
    public View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(view!=null){
            ViewGroup group=(ViewGroup)view.getParent();
            if(group!=null){
                //如果不为空，先从group移去，避免出现空白页面
                group.removeView(view);
            }
            return view;
        }
        view=inflater.inflate(R.layout.fragment_empty, container,false);
        return view;
    }
}
