package bit.edu.cn.dictionary.search;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bit.edu.cn.dictionary.R;

public class ErrorFragment extends Fragment {

    public View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_error, container,false);
        return view;
    }
}
