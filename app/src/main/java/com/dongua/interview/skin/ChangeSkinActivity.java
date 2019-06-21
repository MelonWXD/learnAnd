package com.dongua.interview.skin;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.dongua.interview.R;
import com.example.common.BaseActivity;
import com.zhy.changeskin.SkinManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @CreateDate: 2019/2/13 4:35 PM
 * @Author: Lewis Weng
 * @Description:
 */
public class ChangeSkinActivity extends BaseActivity {

    @BindView(R.id.iv_top_content)
    ImageView imageView;

    @BindView(R.id.btn_chg)
    Button changeSkinBtn;

    int c = 1;

    @OnClick(R.id.btn_chg)
    public void onChangeSkin(View view) {
        if (c % 2 == 0)
            SkinManager.getInstance().changeSkin("red");
        else
            SkinManager.getInstance().changeSkin("green");
        c++;
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_skin;
    }

    @Override
    public void init() {
        SkinManager.getInstance().register(this);
        SkinManager.getInstance().changeSkin("");


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unregister(this);
    }
}
