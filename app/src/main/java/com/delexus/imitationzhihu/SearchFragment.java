package com.delexus.imitationzhihu;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.delexus.imitationzhihu.indicator.ColorTransitionPagerTitleView;
import com.delexus.imitationzhihu.indicator.CommonNavigator;
import com.delexus.imitationzhihu.indicator.CommonNavigatorAdapter;
import com.delexus.imitationzhihu.indicator.ExamplePagerAdapter;
import com.delexus.imitationzhihu.indicator.IPagerIndicator;
import com.delexus.imitationzhihu.indicator.IPagerTitleView;
import com.delexus.imitationzhihu.indicator.LinePagerIndicator;
import com.delexus.imitationzhihu.indicator.MagicIndicator;
import com.delexus.imitationzhihu.indicator.SimplePagerTitleView;
import com.delexus.imitationzhihu.indicator.ViewPagerHelper;
import com.delexus.imitationzhihu.util.Util;

import java.util.Arrays;
import java.util.List;

/**
 * 搜索界面
 * Created by delexus on 2017/3/20.
 */

public class SearchFragment extends BaseFragment {
    private static final String[] CHANNELS = new String[]{"综合", "用户", "话题", "专栏", "Live",
            "电子书"};
    private List<String> mDataList = Arrays.asList(CHANNELS);

    private MySearchView mSearchView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_search_result, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        viewPager.setAdapter(new ExamplePagerAdapter(mDataList));
        MagicIndicator magicIndicator = (MagicIndicator) view.findViewById(R.id.magic_indicator);
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.gray300));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.white));
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                linePagerIndicator.setColors(Color.WHITE);
                linePagerIndicator.setLineWidth(Util.dip2px(getActivity(), 50));
                return linePagerIndicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);

        mSearchView = (MySearchView) view.findViewById(R.id.search_view);
        mSearchView.setIconified(false);
        mSearchView.setOnQueryTextListener(new MySearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mSearchView.setOnNavigateBackLister(new MySearchView.OnNavigateBackListener() {
            @Override
            public boolean onNavigateBack() {
                getFragmentManager()
                        .beginTransaction()
                        .hide(SearchFragment.this)
                        .commit();
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getView() == null) {
            return;
        }
        // Do it for rotate animation.
        getView().setPivotX(0);
        getView().setPivotY(getView().getHeight());
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            getOnFragmentStateListener().onHidden(true);
            return;
        }
        mSearchView.getSearchSrcTextView().requestFocus();
        mSearchView.setImeVisibility(true);
    }

    @Override
    public boolean onBackPressed() {
        if (!isHidden()) {
            getFragmentManager()
                    .beginTransaction()
                    .hide(SearchFragment.this)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
