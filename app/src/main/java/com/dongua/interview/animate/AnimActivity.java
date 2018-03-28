package com.dongua.interview.animate;

import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongua.interview.BaseActivity;
import com.dongua.interview.R;
import com.elvishew.xlog.XLog;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: Lewis
 * date: On 18-2-24.
 */

public class AnimActivity extends BaseActivity {
    @BindView(R.id.tv_anim)
    TextView animTv;
    @BindView(R.id.iv_anim)
    ImageView animIv;

    @Override
    public int getLayoutID() {

        return R.layout.activity_anim;
    }

    @OnClick({R.id.btn_set, R.id.btn_frame})
    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btn_set:
                showTweenAnimation();
                break;
            case R.id.btn_frame:
                animIv.setBackground(getResources().getDrawable(R.drawable.frame_list));
                AnimationDrawable animationDrawable = (AnimationDrawable) animIv.getBackground();
                animationDrawable.start();

                break;
            default:
                break;
        }
    }

    private void showTweenAnimation() {
        Animation animationSet = AnimationUtils.loadAnimation(this, R.anim.tween_set);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                XLog.i("onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                XLog.i("onAnimationEnd");

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                XLog.i("onAnimationRepeat");

            }
        });
        animationSet.setDuration(5000);
        animTv.setAnimation(animationSet);
        animTv.startAnimation(animationSet);
    }
}
