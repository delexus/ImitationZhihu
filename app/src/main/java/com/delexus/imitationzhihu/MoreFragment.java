package com.delexus.imitationzhihu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 更多界面
 * Created by delexus on 2017/5/6.
 */

public class MoreFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_more, container, false);
    }

    @Override
    public boolean onBackPressed() {
        if (isHidden()) {
            return false;
        }
        getFragmentManager()
                .beginTransaction()
                .hide(this)
                .commit();
        return true;
    }
}
