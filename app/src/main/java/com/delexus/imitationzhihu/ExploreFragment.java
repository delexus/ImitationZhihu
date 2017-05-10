package com.delexus.imitationzhihu;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.delexus.imitationzhihu.crawler.ContentItem;
import com.delexus.imitationzhihu.crawler.HttpRequest;
import com.delexus.imitationzhihu.util.ReflectUtil;
import com.delexus.imitationzhihu.util.Util;
import com.delexus.imitationzhihu.view.FloatingActionCheckBox;
import com.delexus.imitationzhihu.view.FloatingActionLayout;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by delexus on 2017/5/9.
 */

public class ExploreFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = ExploreFragment.class.getSimpleName();
    private static final String SEARCH_FRAGMENT = SearchFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private FloatingActionCheckBox mFab;
    private TextView mSearchBar;
    private SwipeRefreshLayout mLoadingLayout;
    private RelativeLayout mAppBarLayout;
    private FloatingActionLayout mFloatingLayout;

    private boolean mIsBottomVisible = true;
    private boolean mIsFloatingVisible = false;

    private OnListScrolledListener mOnListScrolledListener;

    private SearchFragment mSearchFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_explore, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        mAppBarLayout = (RelativeLayout) view.findViewById(R.id.app_bar);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mFab = (FloatingActionCheckBox) view.findViewById(R.id.fab);

        mFloatingLayout = (FloatingActionLayout) view.findViewById(R.id.floating_container);
        mLoadingLayout = (SwipeRefreshLayout) view.findViewById(R.id.loading_layout);
        mLoadingLayout.setProgressViewOffset(true, mLoadingLayout.getProgressViewStartOffset() + mAppBarLayout.getHeight() +
                Util.dip2px(getActivity(), 10), mLoadingLayout.getProgressViewEndOffset() + mAppBarLayout.getHeight() + Util.dip2px(getActivity(), 10));
        mLoadingLayout.setRefreshing(true);

        // 改变箭头的颜色
        ImageView circleView = (ImageView) ReflectUtil.reflectFieldAndReturn(mLoadingLayout, SwipeRefreshLayout.class.getName(),
                "mCircleView");
        Method setColorSchemeColorsMethod = ReflectUtil.reflectHideClassMethod("android.support.v4.widget.MaterialProgressDrawable",
                "setColorSchemeColors", int[].class);
        ReflectUtil.invokeMethod(circleView.getDrawable(), setColorSchemeColorsMethod, new int[]{getResources().getColor(R.color.colorPrimary)});

        mLoadingLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                HttpRequest.getInstance().loadRecommendationList();
            }
        });

        mSearchBar = (TextView) view.findViewById(R.id.search_bar_layout);
        mSearchBar.setOnClickListener(this);

        mRecyclerView.setVisibility(View.GONE);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                // deal with AppBar and Bottom animation.
                ObjectAnimator fabAnimator;
                ObjectAnimator appBarAnimator;
                AnimatorSet set = new AnimatorSet();
                if (dy > 3 && mIsBottomVisible) {
                    mIsBottomVisible = false;
                    fabAnimator = ObjectAnimator.ofFloat(mFab, View.TRANSLATION_Y,
                            0, view.getHeight() - mFab.getTop());
                    appBarAnimator = ObjectAnimator.ofFloat(mAppBarLayout, View.TRANSLATION_Y,
                            0, -mAppBarLayout.getHeight());
                    set.play(fabAnimator).with(appBarAnimator);
                    if (mOnListScrolledListener != null) {
                        mOnListScrolledListener.onScrolled(true, set);
                    }
                } else if (dy < -3 && !mIsBottomVisible) {
                    mIsBottomVisible = true;
                    fabAnimator = ObjectAnimator.ofFloat(mFab, View.TRANSLATION_Y,
                            mFab.getTranslationY(), 0);
                    appBarAnimator = ObjectAnimator.ofFloat(mAppBarLayout, View.TRANSLATION_Y,
                            mAppBarLayout.getTranslationY(), 0);
                    set.play(fabAnimator).with(appBarAnimator);
                    if (mOnListScrolledListener != null) {
                        mOnListScrolledListener.onScrolled(false, set);
                    }
                }

            }
        });

        mFab.setOnCheckedChangeListener(new FloatingActionCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(FloatingActionCheckBox actionView, boolean isChecked) {
                if (isChecked) {
                    mIsFloatingVisible = true;
                    mFloatingLayout.enterAnimation(true);
                    return;
                }
                mIsFloatingVisible = false;
                mFloatingLayout.enterAnimation(false);
            }
        });

        HttpRequest.getInstance().addOnHttpResponseListener(new HttpRequest.OnHttpResponseListener() {
            @Override
            public void onResponse(ArrayList<ContentItem> data) {
                if (data == null) {
                    return;
                }
                ItemAdapter adapter = new ItemAdapter(data);
                mRecyclerView.setAdapter(adapter);
                mLoadingLayout.setRefreshing(false);
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        });
        HttpRequest.getInstance().loadRecommendationList();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_bar_layout:
                getOnFragmentStateListener().onHidden(false);
                Fragment fragment = getFragmentManager().findFragmentByTag(SEARCH_FRAGMENT);
                if (fragment != null) {
                    getFragmentManager()
                            .beginTransaction()
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .show(fragment)
                            .commit();
                } else { // When fragment is popped from back stack occurs.(press back button)
                    mSearchFragment = new SearchFragment();
                    mSearchFragment.setOnFragmentState(new BaseFragment.OnFragmentStateListener() {
                        @Override
                        public void onHidden(boolean isHidden) {
                            if (isHidden) {
                                getOnFragmentStateListener()
                                        .onHidden(true);
                            }
                        }
                    });
                    getFragmentManager()
                            .beginTransaction()
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .add(R.id.content, mSearchFragment, SEARCH_FRAGMENT)
                            .commit();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onBackPressed() {
        if (mSearchFragment != null && mSearchFragment.onBackPressed()) {
            return true;
        }
        if (mIsFloatingVisible) {
            mFab.setChecked(false);
            return true;
        }
        mFab.setChecked(false);
        return super.onBackPressed();
    }

    public void setAppBarSearchEnable(boolean enable) {
        mSearchBar.setOnClickListener(enable ? this : null);
    }

    public void setOnListScrolledListener(OnListScrolledListener listener) {
        mOnListScrolledListener = listener;
    }

    public interface OnListScrolledListener {
        /**
         * 页面滑动的时候添加的动画
         *
         * @param isBottomVisible
         * @param set
         */
        void onScrolled(boolean isBottomVisible, AnimatorSet set);
    }
}
