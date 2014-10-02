package com.viewpagerindicator;

import android.widget.TextView;

/**
 * Created by tadas on 02/10/14.
 */
public interface TabViewProvider {
    public void setTabView(int pos, TextView titleView, TextView badgeView);
}
