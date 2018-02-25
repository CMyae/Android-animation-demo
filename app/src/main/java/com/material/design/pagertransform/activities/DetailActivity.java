package com.material.design.pagertransform.activities;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.material.design.pagertransform.R;
import com.material.design.pagertransform.Util;
import com.material.design.pagertransform.font.MMFontUtils;
import com.material.design.pagertransform.model.Food;
import com.material.design.pagertransform.model.Position;

import java.io.InputStream;

public class DetailActivity extends AppCompatActivity {

    private RelativeLayout detail_container;
    private TextView tv_food_title_eng;
    private TextView tv_food_title_mm;
    private TextView tv_detail;
    private ImageView img_food;
    private int duration = 300;
    RelativeLayout main_container;
    Food mFood;
    Position img_pos,card_pos,title_container_pos,tv_detail_pos;
    LinearLayout title_container;
    View cardContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        //get with thread,calculate pos
        Bundle bdn = getIntent().getExtras();
        img_pos = bdn.getParcelable(Util.IMG_POS);
        card_pos = bdn.getParcelable(Util.CARD_POS);
        title_container_pos = bdn.getParcelable(Util.TITLE_POS);
        tv_detail_pos = bdn.getParcelable(Util.TV_DETAIL_POS);
        mFood = bdn.getParcelable("FOOD");

        initComponent(); //TODO:new Thread with handler //search function/smotth scroll

        main_container.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                main_container.getViewTreeObserver().removeOnPreDrawListener(this);
                setViewPostion(); //set view location and calculate position to animate
                runEnterAnimation();
                return true;
            }
        });

    }


    public void initComponent(){
        detail_container = (RelativeLayout) findViewById(R.id.detail_container);
        title_container = (LinearLayout) findViewById(R.id.title_container);
        main_container = (RelativeLayout) findViewById(R.id.main_container);
        tv_food_title_eng = (TextView) findViewById(R.id.tv_food_title_eng);
        tv_food_title_mm = (TextView) findViewById(R.id.tv_food_title_mm);
        tv_detail = (TextView) findViewById(R.id.tv_detail);
        img_food = (ImageView) findViewById(R.id.img_food);
        cardContainer = findViewById(R.id.card_container);

        //main_container.setBackgroundColor(mFood.getRgb());
        setStatusBarColor(mFood.getRgb());
        tv_food_title_eng.setText(mFood.getEngTitle());
        tv_food_title_mm.setText(mFood.getMmTitle());
        if(!MMFontUtils.isSupportUnicode()){
            Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/mymm.ttf");
            tv_food_title_mm.setTypeface(typeface);
        }
        try {
            InputStream input = getAssets().open(mFood.getImgPath()+".png");
            img_food.setImageDrawable(Drawable.createFromStream(input,null));
        }catch (Exception e){}

    }

    public void setPosition(View view,Position position,boolean isScale){
        view.setPivotX(0);
        view.setPivotY(0);
        view.setTranslationX(position.getLeftDelta());
        view.setTranslationY(position.getTopDelta());
        if(isScale) {
            view.setScaleX(position.getScaleX());
            view.setScaleY(position.getScaleY());
        }
    }

    public void runEnterAnimation(){
        tv_detail.animate()
                .translationX(0).translationY(0)
                .setDuration(duration).setInterpolator(new OvershootInterpolator())
                .start();
        title_container.animate()
                .translationX(0).translationY(0)
                .setDuration(duration).setInterpolator(new OvershootInterpolator())
                .start();
        img_food.animate().scaleX(1).scaleY(1)
                .translationX(0).translationY(0)
                .setDuration(duration).setInterpolator(new OvershootInterpolator())
                .start();
        cardContainer.animate().scaleX(1).scaleY(1)
                .translationX(0).translationY(0)
                .setDuration(duration).setInterpolator(new OvershootInterpolator())
                .start();
    }

    public void runExitAnimation(){
        Drawable drawable = getResources().getDrawable(R.drawable.card_background);
        cardContainer.setBackground(drawable);
        tv_detail.setText("DETAIL");

        tv_detail.animate()
                .translationX(tv_detail_pos.getLeftDelta())
                .translationY(tv_detail_pos.getTopDelta())
                .setDuration(duration)
                .start();
        title_container.animate()
                .translationX(title_container_pos.getLeftDelta())
                .translationY(title_container_pos.getTopDelta())
                .setDuration(duration)
                .start();
        img_food.animate().scaleX(img_pos.getScaleX())
                .scaleY(img_pos.getScaleY())
                .translationX(img_pos.getLeftDelta())
                .translationY(img_pos.getTopDelta())
                .setDuration(duration).setInterpolator(new OvershootInterpolator())
                .start();
        cardContainer.animate().scaleX(card_pos.getScaleX())
                .scaleY(card_pos.getScaleY())
                .translationX(card_pos.getLeftDelta())
                .translationY(card_pos.getTopDelta())
                .setDuration(duration).setInterpolator(new OvershootInterpolator())
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                        overridePendingTransition(0,0);
                    }
                })
                .start();
    }

    @Override
    public void onBackPressed() {
        runExitAnimation();

    }

    public void setStatusBarColor(int color){
        if(Build.VERSION.SDK_INT>=21){
            getWindow().setStatusBarColor(color);
        }
    }


    //TODO: use thread and handler
    public void setViewPostion(){
        long startTime = System.currentTimeMillis();
        int[] location = new int[2];
        cardContainer.getLocationOnScreen(location);
        card_pos.setLeftDelta(card_pos.getLeft() - location[0]);
        card_pos.setTopDelta(card_pos.getTop() - location[1]);
        card_pos.setScaleX((float)card_pos.getWidth()/cardContainer.getWidth());
        card_pos.setScaleY((float)card_pos.getHeight()/cardContainer.getHeight());

        img_food.getLocationOnScreen(location);
        img_pos.setLeftDelta(img_pos.getLeft() - location[0]);
        img_pos.setTopDelta(img_pos.getTop() - location[1]);
        img_pos.setScaleX((float)img_pos.getWidth()/img_food.getWidth());
        img_pos.setScaleY((float)img_pos.getHeight()/img_food.getHeight());

        title_container.getLocationOnScreen(location);
        title_container_pos.setLeftDelta(title_container_pos.getLeft() - location[0]);
        title_container_pos.setTopDelta(title_container_pos.getTop() - location[1]);

        tv_detail.getLocationOnScreen(location);
        tv_detail_pos.setLeftDelta(tv_detail_pos.getLeft() - location[0]);
        tv_detail_pos.setTopDelta(tv_detail_pos.getTop() - location[1]);

        long endTime = System.currentTimeMillis();
        Log.i("TAG","End time  " + (endTime-startTime) + "  millisecond" + "    " + startTime + "   " + endTime);

        setPosition(cardContainer,card_pos,true);// set value for card view
        setPosition(img_food,img_pos,true);// set value for image
        setPosition(title_container,title_container_pos,false);// set value for tv_eng
        setPosition(tv_detail,tv_detail_pos,false);// set value for tv_detail

    }

    public void finishActivity(View view){
        runExitAnimation();
    }



    //TODO: crete title translationY transition
}
