package com.delexus.imitationzhihu;

import android.app.Fragment;

/**
 * Created by delexus on 2017/5/9.
 */

public class BaseFragment extends Fragment {

    private SearchFragment.OnFragmentStateListener mOnFragmentStateListener;

    /**
     * 返回监听
     *
     * @return true 消费 false 不消费
     */
    public boolean onBackPressed() {
        return false;
    }

    public OnFragmentStateListener getOnFragmentStateListener() {
        return mOnFragmentStateListener;
    }

    public void setOnFragmentState(SearchFragment.OnFragmentStateListener listener) {
        mOnFragmentStateListener = listener;
    }

    /**
     * Fragment状态监听者
     */
    public interface OnFragmentStateListener {
        /**
         * 当前Fragment隐藏时调用
         * @param isHidden
         */
        void onHidden(boolean isHidden);
    }
}
