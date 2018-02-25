package com.material.design.pagertransform.activities;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.Menu;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.material.design.pagertransform.CardTransformer;
import com.material.design.pagertransform.R;
import com.material.design.pagertransform.Util;
import com.material.design.pagertransform.adapter.CardAdapter;
import com.material.design.pagertransform.font.MMFontUtils;
import com.material.design.pagertransform.font.Rabbit;
import com.material.design.pagertransform.model.Food;
import com.material.design.pagertransform.model.PagerChangeListener;
import com.material.design.pagertransform.model.Position;
import com.material.design.pagertransform.view.CardPagerView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    CardPagerView viewPager;
    CardAdapter adapter;
    public static float LEFT_RIGHT = Util.dpToPx(20);
    public static float TOP_BOTTOM = Util.dpToPx(10);
    Point screen = new Point();
    List<Food> foods = new ArrayList<>();
    TextView tv_page_indicator;
    TextView tv_title;
    List<Integer> colors = new ArrayList<>();
    RelativeLayout container;
    int mPosition;
    boolean res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        generateFood();
        initComponent();


        getWindowManager().getDefaultDisplay().getSize(screen);
        float offset = LEFT_RIGHT / (float)(screen.x - 2*LEFT_RIGHT);

        adapter = new CardAdapter(getSupportFragmentManager(),foods);
        viewPager = (CardPagerView) findViewById(R.id.viewpager);
        viewPager.setScrollFactorDuration(1);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(false,new CardTransformer(offset,this));
        viewPager.setPageMargin((int)LEFT_RIGHT*2);
        viewPager.setPadding((int)LEFT_RIGHT,(int)TOP_BOTTOM,(int)LEFT_RIGHT,(int)TOP_BOTTOM);

        final ArgbEvaluator evaluator = new ArgbEvaluator();

        viewPager.addOnPageChangeListener(new PagerChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                animateIndicator(position);
                mPosition = position;
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);

                //change color
                int newColor = (Integer) evaluator.evaluate(positionOffset,
                        colors.get(position),
                        colors.get(position<colors.size()-1?position+1:position));
                container.setBackgroundColor(newColor);
                setStatusBarColor(newColor);

                //animate title
                if(mPosition>position){
                    //right swipe
                }else{
                    //left swipe
                    animateTitle(-(positionOffset*container.getWidth()*0.1f));
                }
            }
        });

        tv_page_indicator.setText(String.valueOf(1));

    }

    public void setStatusBarColor(int color){
        if(Build.VERSION.SDK_INT>=21){
            getWindow().setStatusBarColor(color);
        }
    }

    private void animateIndicator(int position) {
        tv_page_indicator.setText(String.valueOf(position+1));
        tv_page_indicator.animate().scaleX(1.3f).scaleY(1.3f)
                .setInterpolator(new OvershootInterpolator())
                .setDuration(100)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        tv_page_indicator.animate().setDuration(100)
                                .scaleX(1f).scaleY(1f).start();
                    }
                }).start();
    }

    public void animateTitle(float offset){
        tv_title.setTranslationX(offset);
        Log.i("OFFSET",offset+"");
    }

    public void initComponent(){
        container = (RelativeLayout) findViewById(R.id.container);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_page_indicator = (TextView) findViewById(R.id.tv_page_indicators);

    }

    public void generateFood(){
        String[] food_en = getResources().getStringArray(R.array.FOOD_ENG);
        String[] food_mm = getResources().getStringArray(R.array.FOOD_MM);
        for(int i=0;i<food_en.length;i++){
            Food food = new Food();
            food.setId(i);
            food.setEngTitle(food_en[i]);

            if(MMFontUtils.isSupportUnicode()){
                food.setMmTitle(Rabbit.zg2uni(food_mm[i]));
            }else{
                food.setMmTitle(changeUni(food_mm[i]));
            }

            food.setImgPath("fruit_" + (i+1));
            try {
                InputStream input = getAssets().open(food.getImgPath()+".png");
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                Palette palette = Palette.from(bitmap).generate();
                Palette.Swatch swatch = palette.getDominantSwatch();
                if(swatch!=null) {
                    food.setRgb(swatch.getRgb());
                    //add to color array
                    colors.add(swatch.getRgb());
                }

            }catch (Exception e){}
            foods.add(food);
        }

    }

    public String changeUni(String text){
        return MMFontUtils.mmText(Rabbit.zg2uni(text), MMFontUtils.TEXT_UNICODE, true, true);
    }

    public void goToDetailActivity(Food food,Position ... positions){
        Intent intent = new Intent(this, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Util.IMG_POS,positions[0]);
        bundle.putParcelable(Util.CARD_POS,positions[1]);
        bundle.putParcelable(Util.TITLE_POS,positions[2]);
        bundle.putParcelable(Util.TV_DETAIL_POS,positions[3]);
        bundle.putParcelable("FOOD",food);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(0,0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    //item ->  100
    //current -> 1
    //found -> 50
    //dest -> current + 5;
    //swap dest and found
    //then move to +5
    public void searchCard(int currentIndex,int foundIndex){
        if(foundIndex-currentIndex <=5 ){
            viewPager.setCurrentItem(foundIndex,true);
        }else{
            //swap item to prevent moving to large amount of card
            int swapIndex = currentIndex + 5;
            Collections.swap(foods,swapIndex,foundIndex);
            //update viewpager and move to +5
            //viewPager.setCurrentItem(swapIndex,true);
        }
    }


}
