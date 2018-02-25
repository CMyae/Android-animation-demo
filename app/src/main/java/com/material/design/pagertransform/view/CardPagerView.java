package com.material.design.pagertransform.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.animation.OvershootInterpolator;

import java.lang.reflect.Field;

/**
 * Created by chan on 9/16/17.
 */

public class CardPagerView extends ViewPager {

    private ScrollerCustomDuration mScroller;

    public CardPagerView(Context context) {
        super(context);
        initViewPager();
    }

    public CardPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViewPager();
    }

    public void initViewPager(){
        try{
            Class viewpager = ViewPager.class;
            Field scroller = viewpager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            Field interpolator = viewpager.getDeclaredField("sInterpolator");
            interpolator.setAccessible(true);
            mScroller = new ScrollerCustomDuration(getContext(),new OvershootInterpolator(1.3f));
            scroller.set(this,mScroller);
        }catch (Exception e){

        }
    }

    public void setScrollFactorDuration(double duration) {
        mScroller.setScrollFactor(duration);
    }
}
