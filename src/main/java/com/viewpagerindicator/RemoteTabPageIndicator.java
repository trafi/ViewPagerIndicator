package com.viewpagerindicator;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by tadas on 26/11/13.
 */
public class RemoteTabPageIndicator extends TabPageIndicator {

    private int mIconWidth = 0;

    protected final OnClickListener mTabClickListener = new OnClickListener() {
        public void onClick(View view) {
            RemoteTabView tabView = (RemoteTabView)view;
            final int oldSelected = mViewPager.getCurrentItem();
            final int newSelected = tabView.getIndex();
            mViewPager.setCurrentItem(newSelected);
            if (oldSelected == newSelected && mTabReselectedListener != null) {
                mTabReselectedListener.onTabReselected(newSelected);
            }
        }
    };

    public RemoteTabPageIndicator(Context context) {
        this(context, null);
    }

    public RemoteTabPageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void notifyDataSetChanged() {
        mTabLayout.removeAllViews();
        PagerAdapter adapter = mViewPager.getAdapter();
        RemoteIconPagerAdapter iconAdapter = null;
        if (adapter instanceof RemoteIconPagerAdapter) {
            iconAdapter = (RemoteIconPagerAdapter) adapter;
        }
        final int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            CharSequence title = adapter.getPageTitle(i);
            if (title == null) {
                title = EMPTY_TITLE;
            }

            addTab(i, title, iconAdapter);
        }
        if (mSelectedTabIndex > count) {
            mSelectedTabIndex = count - 1;
        }
        setCurrentItem(mSelectedTabIndex);
        requestLayout();
    }

    public void setTabIconWidth(int width) {
        mIconWidth = width;
        notifyDataSetChanged();
    }


    protected void addTab(int index, CharSequence text, RemoteIconPagerAdapter iconAdapter) {
        final RemoteTabView tabView = new RemoteTabView(getContext());
        tabView.mIndex = index;
        tabView.setFocusable(true);
        tabView.setOnClickListener(mTabClickListener);
        iconAdapter.loadImageOnView(tabView);
        mTabLayout.addView(tabView, new LinearLayout.LayoutParams(mIconWidth, MATCH_PARENT, 1));
    }

    protected class RemoteTabView extends ImageView {
        protected int mIndex;

        public RemoteTabView(Context context) {
            super(context, null, R.attr.vpiTabPageIndicatorStyle);
        }

        @Override
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            // Re-measure if we went beyond our maximum size.
            if (mMaxTabWidth > 0 && getMeasuredWidth() > mMaxTabWidth) {
                super.onMeasure(MeasureSpec.makeMeasureSpec(mMaxTabWidth, MeasureSpec.EXACTLY),
                        heightMeasureSpec);
            }
        }

        public int getIndex() {
            return mIndex;
        }
    }
}
