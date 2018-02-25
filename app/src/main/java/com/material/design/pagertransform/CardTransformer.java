package com.material.design.pagertransform;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by chan on 9/13/17.
 */

public class CardTransformer implements ViewPager.PageTransformer {

    private float offset;
    private static float speed = 0.5f;
    private float mPosition;
    private Context context;
    private int page_num;

    public CardTransformer(float offset,Context context){
        this.offset = offset;
        this.context = context;
    }

    @Override
    public void transformPage(View page, float pos) {

        mPosition = pos - offset;

        float absposition = Math.abs(mPosition);
        float cardWidthposition = (mPosition) * page.getWidth();

        TextView tv_text = (TextView) page.findViewById(R.id.tv_text);
        ImageView imageView = (ImageView) page.findViewById(R.id.img_food);
        TextView btn_detail = (TextView) page.findViewById(R.id.btn_detail);

        if (mPosition <= -1.0f || mPosition >= 1.0f) {

            // Page is not visible -- stop any running animations

        } else if (mPosition == 0.0f) {

            // Page is selected -- reset any views if necessary
            tv_text.setText(mPosition+"");
            tv_text.setAlpha(1);
            imageView.setTranslationX(0);
            page_num = Integer.parseInt(page.getTag().toString());

            Log.i("TAG","0.0f equal");

        }else {

            tv_text.setText(mPosition+"");
            tv_text.setAlpha(1.0f - absposition*2);
            imageView.setTranslationX(cardWidthposition * speed);
            btn_detail.setTranslationX(cardWidthposition * 0.2f);

        }
    }

}
