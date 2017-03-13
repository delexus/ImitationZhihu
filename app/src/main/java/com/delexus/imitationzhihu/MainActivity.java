package com.delexus.imitationzhihu;

import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by ly-yangyuzhi on 2017/3/3.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private RadioImageButton mBtnFeed, mBtnDiscover, mBtnNotify,
                        mBtnMessage, mBtnMore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mBtnFeed = (RadioImageButton) findViewById(R.id.btn_feed);
        mBtnDiscover = (RadioImageButton) findViewById(R.id.btn_discover);
        mBtnNotify = (RadioImageButton) findViewById(R.id.btn_notification);
        mBtnMessage = (RadioImageButton) findViewById(R.id.btn_message);
        mBtnMore = (RadioImageButton) findViewById(R.id.btn_more);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_feed:


                break;
            case R.id.btn_discover:

                break;
            case R.id.btn_notification:

                break;
            case R.id.btn_message:

                break;
            case R.id.btn_more:

                break;
        }
    }
}
