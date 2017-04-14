package com.delexus.imitationzhihu;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by ly-yangyuzhi on 2017/3/3.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String SEARCH_FRAGMENT = SearchFragment.class.getSimpleName();
    private RadioImageButton mBtnFeed, mBtnDiscover, mBtnNotify,
            mBtnMessage, mBtnMore;
    private RecyclerView mRecyclerView;

    private CoordinatorLayout mCoordinatorLayout;
    private RadioImageGroup mRadioImageGroup;
    private FloatingActionButton mFab;
    private LinearLayout mSearchBar;

    private boolean mIsBottomVisible = true;

    private SearchFragment mSearchFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        loadData();
    }

    private void init() {
        mBtnFeed = (RadioImageButton) findViewById(R.id.btn_feed);
        mBtnDiscover = (RadioImageButton) findViewById(R.id.btn_discover);
        mBtnNotify = (RadioImageButton) findViewById(R.id.btn_notification);
        mBtnMessage = (RadioImageButton) findViewById(R.id.btn_message);
        mBtnMore = (RadioImageButton) findViewById(R.id.btn_more);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        mRadioImageGroup = (RadioImageGroup) findViewById(R.id.main_group);

        mSearchBar = (LinearLayout) findViewById(R.id.search_bar_layout);
        mSearchBar.setOnClickListener(this);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Log.d(TAG, "dx = " + String.valueOf(dx) + "dy = " + String.valueOf(dy));
                if (dy > 0 && mIsBottomVisible) { // 不可见
                    mIsBottomVisible = false;
                    ObjectAnimator animator = ObjectAnimator.ofFloat(mRadioImageGroup, View.TRANSLATION_Y,
                            mRadioImageGroup.getTranslationY(), mRadioImageGroup.getTranslationY() + mRadioImageGroup.getHeight());
                    ObjectAnimator fabAnimator = ObjectAnimator.ofFloat(mFab, View.TRANSLATION_Y,
                            mFab.getTranslationY(), mFab.getTranslationY() + mRadioImageGroup.getBottom() - mFab.getTop());
                    AnimatorSet set = new AnimatorSet();
                    set.play(animator).with(fabAnimator);
                    set.setDuration(300);
                    set.start();
                } else if (dy <= 0 && !mIsBottomVisible) { // 可见
                    mIsBottomVisible = true;
                    ObjectAnimator animator = ObjectAnimator.ofFloat(mRadioImageGroup, View.TRANSLATION_Y,
                            mRadioImageGroup.getTranslationY(), 0);
                    ObjectAnimator fabAnimator = ObjectAnimator.ofFloat(mFab, View.TRANSLATION_Y,
                            mFab.getTranslationY(), 0);
                    AnimatorSet set = new AnimatorSet();
                    set.play(animator).with(fabAnimator);
                    set.setDuration(300);
                    set.start();
                }
            }
        });

    }

    private void loadData() {
        SimpleAdapter adapter = new SimpleAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
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
            case R.id.search_bar_layout:
                if (mSearchFragment == null) {
                    mSearchFragment = new SearchFragment();
                    mSearchFragment.setOnFragmentState(new SearchFragment.OnFragmentStateListener() {
                        @Override
                        public void onHidden(boolean isHidden) {
                            if (isHidden) {
                                if (mSearchFragment != null && !mSearchFragment.isHidden()) {
                                    getFragmentManager()
                                            .beginTransaction()
                                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                                            .hide(mSearchFragment)
                                            .commitAllowingStateLoss();
                                }
                            }
                        }
                    });
                    getFragmentManager()
                            .beginTransaction()
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(SEARCH_FRAGMENT)
                            .add(R.id.content, mSearchFragment, SEARCH_FRAGMENT)
                            .commit();
                } else {
                    Fragment fragment = getFragmentManager().findFragmentByTag(SEARCH_FRAGMENT);
                    if (fragment != null) {
                        getFragmentManager()
                                .beginTransaction()
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .show(fragment)
                                .commit();
                    } else {
                        // When fragment is popped from back stack occurs.(press back button)
                        getFragmentManager()
                                .beginTransaction()
                                .addToBackStack(SEARCH_FRAGMENT)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .add(R.id.content, mSearchFragment, SEARCH_FRAGMENT)
                                .commit();
                    }
                }
                break;
            default:
                break;
        }
    }

    private static class SimpleAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHolder(new TextView(parent.getContext()));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyHolder myHolder = (MyHolder) holder;
            myHolder.mTextView.setText(String.valueOf(position));
        }

        @Override
        public int getItemCount() {
            return 60;
        }
    }

    private static class MyHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public MyHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
        }
    }
}
