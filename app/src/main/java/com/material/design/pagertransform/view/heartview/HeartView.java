package com.material.design.pagertransform.view.heartview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Property;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

import com.material.design.pagertransform.R;

import static com.material.design.pagertransform.view.heartview.Util.MAX;
import static com.material.design.pagertransform.view.heartview.Util.MIN;
import static com.material.design.pagertransform.view.heartview.Util.NORMAL;


/**
 * Created by chan on 9/23/17.
 */

public class HeartView extends FrameLayout{

    private float BIG_HEART_WIDTH;
    private float SMALL_HEART_WIDTH;

    private int width,height;
    private float angle[];
    private float circleRadius;
    private boolean isFirstTime=true;
    private boolean isLiked;
    private int iconType;
    private int splitMode;
    private int color;
    private Interpolator interpolator = new DecelerateInterpolator();
    private long duration = 400;

    private OnAnimationListener listener;


    public HeartView(Context context) {
        super(context);
        init(null);
    }

    public HeartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public HeartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        getAttribute(attrs);
    }


    private void getAttribute(AttributeSet attrs){
        TypedArray ta = getContext().obtainStyledAttributes(attrs,R.styleable.HeartView);
        try {
            iconType = ta.getInteger(R.styleable.HeartView_iconType, 0);
            splitMode = ta.getInteger(R.styleable.HeartView_splitMode,0);

        }catch (Exception e){
            e.printStackTrace();
        }
        ta.recycle();

        addAngle(splitMode);
    }

    private void addHeartView(){
        for(int i=0;i<angle.length;i++) {
            SmallHeartView smallHeart = new SmallHeartView(getContext(), (int) SMALL_HEART_WIDTH,iconType);
            this.addView(smallHeart);
        }
        BigHeartView bigHeart = new BigHeartView(getContext(),(int)BIG_HEART_WIDTH,iconType);
        this.addView(bigHeart);
    }

    //create angle for small heart view
    private void addAngle(int mode){
        int length = 0;
        int num = 0;
        switch (mode) {
            case NORMAL:
                length = 5;
                break;
            case MAX:
                length = 8;
                break;
            case MIN:
                length = 4;
                break;
        }
        num = 360/length;
        float val =  num + 270;
        angle = new float[length];
        for (int i = 0; i < angle.length; i++) {
            angle[i] = val;
            val+=num;
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        circleRadius = 0; //default size

        setBIG_HEART_WIDTH(width/3); //set big heart view size to one/three of container view
        setSMALL_HEART_WIDTH(BIG_HEART_WIDTH/2.5f); //set small heart view size to one/three of big heart view

        if(isFirstTime){
            addHeartView();
            isFirstTime = false;
            if(!isLiked){
                setSmallHeartViewAlpha(0f);
            }else{
                setSmallHeartViewAlpha(1f);
            }
        }

        //place small heart to specific position to animate
        animateSmallHeart();
        //place big heart view at center
        BigHeartView bigHeart = (BigHeartView) getChildAt(getChildCount()-1);
        bigHeart.setX(width/2 - BigHeartView.WIDTH/2);
        bigHeart.setY(height/2-BigHeartView.WIDTH/2);
    }


    private void animateSmallHeart(){
        for (int i=0;i<angle.length;i++){
            View view = getChildAt(i);
            float x = findX(angle[i]);
            float y = findY(angle[i]);
            view.setX(x- SmallHeartView.WIDTH/2);
            view.setY(y-SmallHeartView.WIDTH/2);
        }
    }


    private float findX(float angle){
        // x = cx + r*cos(a)
        float x = (float)((float)width/2 + Math.cos(Math.toRadians(angle)) * circleRadius);
        return x;
    }

    private float findY(float angle){
        int sth = 1;
        if(angle%360 > 180 && angle%360 < 360){
            sth = -1;
        }
        float y = (float)(width/2 + sth * Math.abs(Math.sin(Math.toRadians(angle))) * circleRadius);
        return y;
    }



    private void startAnimation(){
        if(isLiked) {
            getBigHeartView().setFavouriteImage(iconType);
            ObjectAnimator animator = ObjectAnimator.ofFloat(this, CIRCLE_RADIUS, 0f, (float) width / 2.5f);
            animator.setDuration(duration);
            animator.setInterpolator(interpolator);


            ValueAnimator alpha = ValueAnimator.ofFloat(1f, 0f);
            alpha.setDuration(duration);
            alpha.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    setSmallHeartViewAlpha((Float) animation.getAnimatedValue());
                }
            });
            AnimatorSet set = new AnimatorSet();
            set.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    if(listener!=null){
                        listener.onAnimationStart();
                    }
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if(listener!=null){
                        listener.onAnimationFinish();
                    }
                }
            });
            set.playTogether(animator, alpha);
            set.start();

        }else{
            getBigHeartView().setNonFavouriteImage(iconType);
        }
    }

    private void changeIcon(){
        if(isLiked) {
            getBigHeartView().setFavouriteImage(iconType);
        }else{
            getBigHeartView().setNonFavouriteImage(iconType);
        }
    }




    private BigHeartView getBigHeartView(){
        return (BigHeartView) getChildAt(getChildCount()-1);
    }

    private void setSmallHeartViewAlpha(float alpha){
        for (int i=0;i<angle.length;i++){
            SmallHeartView view = (SmallHeartView) getChildAt(i);
            view.setAlpha(alpha);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                this.animate().scaleX(0.7f).scaleY(0.7f).setDuration(50)
                        .start();
                setPressed(true);
                break;
            case MotionEvent.ACTION_UP:
                this.animate().scaleX(1f).scaleY(1f).setDuration(400)
                        .setInterpolator(new OvershootInterpolator())
                        .start();
                if(isPressed()){
                    performClick();
                    setPressed(false);
                }
                break;

        }
        return true;
    }

    public void addListener(OnAnimationListener listener) {
        this.listener = listener;
    }

    public float getBIG_HEART_WIDTH() {
        return BIG_HEART_WIDTH;
    }

    public void setBIG_HEART_WIDTH(float BIG_HEART_WIDTH) {
        this.BIG_HEART_WIDTH = BIG_HEART_WIDTH;
    }

    private float getSMALL_HEART_WIDTH() {
        return SMALL_HEART_WIDTH;
    }

    private void setSMALL_HEART_WIDTH(float SMALL_HEART_WIDTH) {
        this.SMALL_HEART_WIDTH = SMALL_HEART_WIDTH;
    }

    private float getCircleRadius() {
        return circleRadius;
    }

    private void setCircleRadius(float circleRadius) {
        this.circleRadius = circleRadius;
        animateSmallHeart();
    }

    public Interpolator getInterpolator() {
        return interpolator;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }


    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
        changeIcon();
    }
    public void setLikedWithAnimation(boolean liked){
        isLiked = liked;
        startAnimation();
    }

    public interface OnAnimationListener{
        void onAnimationStart();
        void onAnimationFinish();
    }

    private static final Property<HeartView,Float> CIRCLE_RADIUS = new Property<HeartView, Float>(Float.class,"circleRadius") {
        @Override
        public Float get(HeartView object) {
            return object.getCircleRadius();
        }

        @Override
        public void set(HeartView object, Float value) {
            object.setCircleRadius(value);
        }
    };
}
