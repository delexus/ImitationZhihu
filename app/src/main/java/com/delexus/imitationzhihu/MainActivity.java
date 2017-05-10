package com.delexus.imitationzhihu;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by delexus on 2017/3/3.
 */

public class MainActivity extends AppCompatActivity implements
        RadioImageGroup.OnCheckedChangeListener, ExploreFragment.OnListScrolledListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String MORE_FRAGMENT = MoreFragment.class.getSimpleName();
    private static final String EXPLORE_FRAGMENT = ExploreFragment.class.getSimpleName();

    private RadioImageGroup mRadioImageGroup;

    private BaseFragment mCurrentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication.getRefWatcher(this).watch(this);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mRadioImageGroup = (RadioImageGroup) findViewById(R.id.main_group);
        mRadioImageGroup.setOnCheckedChangeListener(this);
        showFragment(R.id.content, EXPLORE_FRAGMENT, false, false);

        if (mCurrentFragment instanceof ExploreFragment) {
            ExploreFragment fragment = (ExploreFragment) mCurrentFragment;
            fragment.setOnListScrolledListener(this);
        }
    }

    @Override
    public void onBackPressed() {
        if (mCurrentFragment != null) {
            if (mCurrentFragment.onBackPressed()) {
                return;
            }
        }
        super.onBackPressed();
    }

    @Override
    public void onCheckedChanged(RadioImageGroup group, @IdRes int checkedId) {
        // 底部按钮切换到其它Fragment先隐藏掉当前的Fragment，但不要隐藏ExploreFragment
        if (mCurrentFragment != null) {
            if (mCurrentFragment instanceof ExploreFragment) {
                ExploreFragment fragment = (ExploreFragment) mCurrentFragment;
            } else {
                getFragmentManager()
                        .beginTransaction()
                        .hide(mCurrentFragment)
                        .commit();
            }
        }
        switch (checkedId) {
            case R.id.btn_feed:
                showFragment(R.id.content, EXPLORE_FRAGMENT, false, false);
                ExploreFragment fragment = (ExploreFragment) mCurrentFragment;
                break;
            case R.id.btn_discover:
                Toast.makeText(this, "discover", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_notification:
                Toast.makeText(this, "notification", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_message:
                Toast.makeText(this, "message", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_more:
                showFragment(R.id.content, MORE_FRAGMENT, false, false);
                break;
            default:
                break;
        }
    }

    @Override
    public void onScrolled(boolean isBottomVisible, AnimatorSet set) {
        ObjectAnimator animator;
        if (isBottomVisible) {
            animator = ObjectAnimator.ofFloat(mRadioImageGroup, View.TRANSLATION_Y,
                    0, mRadioImageGroup.getHeight());
        } else {
            animator = ObjectAnimator.ofFloat(mRadioImageGroup, View.TRANSLATION_Y,
                    mRadioImageGroup.getTranslationY(), 0);
        }
        set.setDuration(300);
        set.playTogether(animator);
        set.start();
    }

    private void showFragment(@IdRes int id, String tag, final boolean useTransaction, boolean addToBackStack) {
        mCurrentFragment = (BaseFragment) getFragmentManager().findFragmentByTag(tag);
        if (mCurrentFragment != null) {
            getFragmentManager()
                    .beginTransaction()
                    .setTransition(useTransaction ? FragmentTransaction.TRANSIT_FRAGMENT_OPEN :
                            FragmentTransaction.TRANSIT_NONE)
                    .show(mCurrentFragment)
                    .commit();
        } else { // When fragment is popped from back stack occurs.(press back button)
            if (tag.equals(EXPLORE_FRAGMENT)) {
                mCurrentFragment = new ExploreFragment();
            } else {
                mCurrentFragment = new MoreFragment();
            }
            mCurrentFragment.setOnFragmentState(new BaseFragment.OnFragmentStateListener() {
                @Override
                public void onHidden(boolean isHidden) {
                    if (isHidden) {
                        if (mCurrentFragment instanceof ExploreFragment) {
                            mRadioImageGroup.setVisibility(View.VISIBLE);
                        }
                    } else {
                        if (mCurrentFragment instanceof ExploreFragment) {
                            mRadioImageGroup.setVisibility(View.GONE);
                        }
                    }
                }
            });
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            if (addToBackStack) {
                transaction.addToBackStack(tag);
            }
            transaction.setTransition(useTransaction ? FragmentTransaction.TRANSIT_FRAGMENT_OPEN :
                    FragmentTransaction.TRANSIT_NONE)
                    .add(id, mCurrentFragment, tag)
                    .commit();
        }
    }

}
