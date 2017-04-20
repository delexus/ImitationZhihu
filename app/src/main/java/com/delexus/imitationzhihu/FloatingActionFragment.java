package com.delexus.imitationzhihu;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.delexus.imitationzhihu.view.FloatingActionLayout;

/**
 * 提供、回答、分享按钮的界面
 * Created by delexus on 2017/4/19.
 */

public class FloatingActionFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return new FloatingActionLayout(inflater.getContext());
    }

}
