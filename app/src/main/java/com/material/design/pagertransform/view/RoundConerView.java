package com.material.design.pagertransform.view;

/**
 * Created by chan on 9/21/17.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

import com.material.design.pagertransform.R;
import com.material.design.pagertransform.Util;


public class RoundConerView extends RelativeLayout {

    static RectF rectF;
    Paint p;
    int width, height;
    private float val = 0f;
    public float INCREASE_FRACTION = 0f;
    float toRadius = 0f;
    public boolean isConerRadius = true;
    Drawable background;
    int color = Color.parseColor("#ffffff");


    public RoundConerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public RoundConerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundConerView(Context context) {
        super(context);
        init(context, null);
    }

    public void init(Context context, AttributeSet attrs) {

        //get custom attribute value
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundConerView);
        try {
            isConerRadius = ta.getBoolean(R.styleable.RoundConerView_conerRadius, true);
            float dimension = ta.getDimension(R.styleable.RoundConerView_conerRadiusSize,10); //default is 10dp
            val = Util.dpToPx((int) dimension);
            Log.i("TAG","val ... " + dimension + "  ");
            ta.recycle();
        } catch (Exception e) {
        }

        if (isConerRadius) {

            background = this.getBackground();
            if (background != null) {
                if (background instanceof ColorDrawable) {
                    color = ((ColorDrawable) background).getColor();
                }
            }
            p = new Paint();
            p.setColor(color);
            p.setStyle(Paint.Style.FILL);
            p.setAntiAlias(true);
            setWillNotDraw(false);

//            this.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ValueAnimator anim = ValueAnimator.ofFloat(val, toRadius);
//                    anim.setDuration(400);
//                    anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                        @Override
//                        public void onAnimationUpdate(ValueAnimator animation) {
//                            setVal((float) animation.getAnimatedValue());
//                            invalidate();
//                        }
//                    });
//                    anim.addListener(new AnimatorListenerAdapter() {
//                        @Override
//                        public void onAnimationEnd(Animator animation) {
//                            super.onAnimationEnd(animation);
//                            setVal(toRadius); //set val to latest value
//                        }
//                    });
//                    anim.start();
//
//                }
//            });
            this.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    public void animateBorderRadius(){
        if(isConerRadius){
            ValueAnimator anim = ValueAnimator.ofFloat(val, toRadius);
            anim.setDuration(400);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    setVal((float) animation.getAnimatedValue());
                    invalidate();
                }
            });
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    setVal(toRadius); //set val to latest value
                }
            });
            anim.start();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        rectF = new RectF(0, 0, width, height);

    }

    //TODO: add border stroke


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isConerRadius) {
            canvas.drawRoundRect(rectF, val, val, p);
        }

    }

    public float getVal() {
        return val;
    }

    public void setVal(float val) {
        this.val = val;
    }

    public float getToRadius() {
        return toRadius;
    }

    public void setToRadius(float toRadius) {
        this.toRadius = toRadius;
    }
}