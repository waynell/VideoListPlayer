package com.waynell.videolist.visibility.calculator;

import com.waynell.videolist.visibility.scroll.ItemsPositionGetter;

/**
 * This is basic interface for Visibility calculator.
 * Methods of it strongly depends on Scroll events from ListView or RecyclerView
 */
public interface ListItemsVisibilityCalculator {
    void onScrolled(ItemsPositionGetter itemsPositionGetter, int scrollState);
    void onScrollStateIdle(ItemsPositionGetter itemsPositionGetter);
}
