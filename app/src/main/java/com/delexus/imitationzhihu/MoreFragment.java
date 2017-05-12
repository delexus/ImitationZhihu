package com.delexus.imitationzhihu;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;

/**
 * 更多界面
 * Created by delexus on 2017/5/6.
 */

public class MoreFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener {

    private RelativeLayout mNightModeLayout;
    private Switch mNightSwitch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_more, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mNightModeLayout = (RelativeLayout) view.findViewById(R.id.layout_nightMode);
        mNightSwitch = (Switch) view.findViewById(R.id.switch_night);
        mNightSwitch.setOnCheckedChangeListener(this);
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.switch_night) {
            // TODO ..
//            changeTheme(isChecked);
            getActivity().setTheme(isChecked ? R.style.AppThemeNight : R.style.AppTheme);
        }
    }

    private void changeTheme(boolean isNight) {
        TypedArray a = getActivity().getTheme()
                .obtainStyledAttributes(isNight ? R.style.AppThemeNight : R.style.AppTheme,
                        R.styleable.ThemePrimaryColor);
        int itemColorBackground = a.getColor(R.styleable.ThemePrimaryColor_itemColorBackground,
                getResources().getColor(R.color.white));
        int textColorBackground = a.getColor(R.styleable.ThemePrimaryColor_textColorBackground,
                getResources().getColor(R.color.black));
        int imageTintColor = a.getColor(R.styleable.ThemePrimaryColor_imageTintColor,
                getResources().getColor(R.color.gray_600));
        int imageTintColorDeep = a.getColor(R.styleable.ThemePrimaryColor_imageTintColorDeep,
                getResources().getColor(R.color.gray_500));
        int lineColor = a.getColor(R.styleable.ThemePrimaryColor_lineColor,
                getResources().getColor(R.color.gray_500));
        int titleColor = a.getColor(R.styleable.ThemePrimaryColor_titleColor,
                getResources().getColor(R.color.black));
        int rippleColor = a.getColor(R.styleable.ThemePrimaryColor_rippleColor,
                getResources().getColor(R.color.blue_800));
        int searchBarColor = a.getColor(R.styleable.ThemePrimaryColor_searchBarColor,
                getResources().getColor(R.color.blue_400));
        a.recycle();

    }
}
