package bit.edu.cn.dictionary.search;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.airbnb.lottie.LottieAnimationView;

import bit.edu.cn.dictionary.R;

public class ErrorFragment extends Fragment {

    public View view;
    public LottieAnimationView animation_cry;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_error, container,false);
        animation_cry=view.findViewById(R.id.animation_cry);
        animation_cry.setRepeatCount(Animation.INFINITE);
        animation_cry.playAnimation();
        return  view;
    }
}
