package com.volokh.danylo.visibility_utils.calculator;

import com.volokh.danylo.visibility_utils.scroll.ItemsPositionGetter;

/**
 * This is basic interface for Visibility calculator.
 * Methods of it strongly depends on Scroll events from ListView or RecyclerView
 */
public interface ListItemsVisibilityCalculator {
    void onScrolled(ItemsPositionGetter itemsPositionGetter, int scrollState);
    void onScrollStateIdle(ItemsPositionGetter itemsPositionGetter);
}
