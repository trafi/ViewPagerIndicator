package com.viewpagerindicator;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by tadas on 26/11/13.
 */
public class TabWithBadgePageIndicator extends TabPageIndicator {


    protected final OnClickListener mTabClickListener = new OnClickListener() {
        public void onClick(View view) {
            BadgeTabView tabView = (BadgeTabView) view;
            final int oldSelected = mViewPager.getCurrentItem();
            final int newSelected = tabView.getIndex();
            mViewPager.setCurrentItem(newSelected);
            if (oldSelected == newSelected && mTabReselectedListener != null) {
                mTabReselectedListener.onTabReselected(newSelected);
            }
        }
    };

    public TabWithBadgePageIndicator(Context context) {
        this(context, null);
    }

    public TabWithBadgePageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getStyle() {
        return R.attr.vpiTabPageIndicatorBadgeStyle;
    }

    @Override
    public void notifyDataSetChanged() {
        mTabLayout.removeAllViews();
        PagerAdapter adapter = mViewPager.getAdapter();
        TabViewProvider iconAdapter = null;
        if (adapter instanceof TabViewProvider) {
            iconAdapter = (TabViewProvider) adapter;
        }
        final int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            addTab(i, iconAdapter);
        }
        if (mSelectedTabIndex > count) {
            mSelectedTabIndex = count - 1;
        }
        setCurrentItem(mSelectedTabIndex);
        requestLayout();
    }

    protected void addTab(int index, TabViewProvider badgeAdapter) {

        final BadgeTabView tabView = new BadgeTabView(getContext());
        tabView.mIndex = index;
        tabView.setFocusable(true);
        tabView.setOnClickListener(mTabClickListener);

        mTabLayout.addView(tabView, new LinearLayout.LayoutParams(0, WRAP_CONTENT, 1));

        if (badgeAdapter != null) {
            badgeAdapter.setTabView(index, tabView.mTitleView, tabView.mBadgeView);
        }
    }

    protected class BadgeTabView extends FrameLayout {
        protected final TextView mBadgeView;
        private final TextView mTitleView;
        protected int mIndex;

        public BadgeTabView(Context context) {
            super(context, null, getStyle());


            View v = View.inflate(context, R.layout.badgetab, this);

            mTitleView = (TextView) v.findViewById(R.id.title);
            mBadgeView = (TextView) v.findViewById(R.id.badge);
        }


        public int getIndex() {
            return mIndex;
        }
    }
}
