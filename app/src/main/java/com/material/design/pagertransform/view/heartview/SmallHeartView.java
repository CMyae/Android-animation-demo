package com.material.design.pagertransform.view.heartview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.material.design.pagertransform.R;

import static com.material.design.pagertransform.view.heartview.Util.HEART_TYPE;
import static com.material.design.pagertransform.view.heartview.Util.STAR_TYPE;
import static com.material.design.pagertransform.view.heartview.Util.THUMB_TYPE;


/**
 * Created by chan on 9/23/17.
 */

public class SmallHeartView extends ImageView{

    public static int WIDTH;

    public SmallHeartView(Context context,int width,int type) {
        super(context);
        WIDTH = width;
        init(type);
    }

    public SmallHeartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(0);
    }

    public SmallHeartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    void init(int type){
        switch (type){
            case HEART_TYPE:
                setImageResource(R.drawable.ic_favourite);
                break;
            case STAR_TYPE:
                setImageResource(R.drawable.ic_star_on);
                break;
            case THUMB_TYPE:
                setImageResource(R.drawable.ic_thumb_on);
                break;
            default:
                break;
        }
        setScaleType(ScaleType.FIT_XY);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec){
        int preferred = WIDTH;
        return getMeasurement(measureSpec,preferred);
    }

    private int measureHeight(int measureSpec){
        int preferred = WIDTH;
        return getMeasurement(measureSpec,preferred);
    }

    private int getMeasurement(int measureSpec,int preferred){
        int specSie = MeasureSpec.getSize(measureSpec);
        int measurement = 0;
        switch (MeasureSpec.getMode(measureSpec)){
            case MeasureSpec.EXACTLY:
                //this means the width of this view has been given
                //normal case -> measurement = specSie;
                measurement = preferred;
                break;
            case MeasureSpec.AT_MOST:
                //take the minimum of the preferred size and what
                //we were told to be
                measurement = Math.min(preferred,specSie);
                break;
            default:
                measurement = preferred;
                break;
        }
        return measurement;
    }
}
