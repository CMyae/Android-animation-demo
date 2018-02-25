package com.material.design.pagertransform.view;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by chan on 9/14/17.
 */

public class ScrollerCustomDuration extends Scroller {

    private double scrollFactor;

    public ScrollerCustomDuration(Context context) {
        super(context);
    }

    public ScrollerCustomDuration(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public ScrollerCustomDuration(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    public void setScrollFactor(double scrollFactor) {
        this.scrollFactor = scrollFactor;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, (int)(duration*scrollFactor));
    }
}
