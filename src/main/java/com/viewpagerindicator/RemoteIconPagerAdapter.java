package com.viewpagerindicator;

import android.widget.ImageView;

/**
 * Created by tadas on 26/11/13.
 */
public interface RemoteIconPagerAdapter {

    public void loadImageOnView(int position,ImageView imageView);

    // From PagerAdapter
    public int getCount();
}
