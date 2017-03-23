package com.delexus.imitationzhihu;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

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
import com.delexus.imitationzhihu.util.ReflectUtil;
import com.delexus.imitationzhihu.util.Util;

import java.util.Arrays;
import java.util.List;

/**
 * Created by delexus on 2017/3/20.
 */

public class SearchFragment extends Fragment {
    private static final String[] CHANNELS = new String[]{"综合", "用户", "话题", "专栏", "Live",
            "电子书"};
    private List<String> mDataList = Arrays.asList(CHANNELS);

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
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.gray50));
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
                linePagerIndicator.setLineWidth(Util.dip2px(getContext(), 72));
                return linePagerIndicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);

        SearchView searchView = (SearchView) view.findViewById(R.id.search_view);
        SearchView.SearchAutoComplete autoCompleteText = (SearchView.SearchAutoComplete)
                ReflectUtil.reflectFieldAndReturn(searchView,
                SearchView.SearchAutoComplete.class.getName(), "mSearchSrcTextView");
        if (autoCompleteText != null) {
            autoCompleteText.setBackground(null);
            InputMethodManager imm = (InputMethodManager) getContext().getApplicationContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (autoCompleteText.isFocusable()) {
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            } else {
                imm.hideSoftInputFromWindow(autoCompleteText.getWindowToken(), 0);
            }
        }

    }
}
