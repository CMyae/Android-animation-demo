package com.material.design.pagertransform.fragment;


import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.material.design.pagertransform.R;
import com.material.design.pagertransform.activities.MainActivity;
import com.material.design.pagertransform.font.MMFontUtils;
import com.material.design.pagertransform.model.Food;
import com.material.design.pagertransform.model.Position;

import java.io.InputStream;


public class CardFragment extends Fragment {

    private Food food;
    Typeface typeface;

    public static CardFragment getInstance(Food food){
        CardFragment fragment = new CardFragment();
        Bundle bdn = new Bundle();
        bdn.putParcelable("P",food);
        fragment.setArguments(bdn);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        food = bundle.getParcelable("P");
        typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/mymm.ttf");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_card, container, false);
        final TextView tv_food_eng = (TextView) v.findViewById(R.id.tv_food_title_eng);
        final TextView tv_food_mm = (TextView) v.findViewById(R.id.tv_food_title_mm);
        final ImageView img_food = (ImageView) v.findViewById(R.id.img_food);
        final TextView tv_detail = (TextView) v.findViewById(R.id.btn_detail);
        final LinearLayout title_container = (LinearLayout) v.findViewById(R.id.title_container);

        //change typeface
        if(!MMFontUtils.isSupportUnicode()){
            tv_food_mm.setTypeface(typeface);
        }

        v.setTag(food.getId());
        tv_food_eng.setText(food.getEngTitle());
        tv_food_mm.setText(food.getMmTitle());

        try {
            InputStream input = getContext().getAssets().open(food.getImgPath()+".png");
            img_food.setImageDrawable(Drawable.createFromStream(input,null));
        }catch (Exception e){}

        tv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get View position to animate
                int[] loc1 = new int[2];
                img_food.getLocationOnScreen(loc1);
                Position img_position = new Position(img_food.getWidth(),img_food.getHeight(),loc1[0],loc1[1]);

                v.getLocationOnScreen(loc1);
                Position card_position = new Position(v.getWidth(),v.getHeight(),loc1[0],loc1[1]);

                title_container.getLocationOnScreen(loc1);
                Position title_container_position = new Position(title_container.getWidth(),title_container.getHeight(),loc1[0],loc1[1]);

                tv_detail.getLocationOnScreen(loc1);
                Position tv_detail_position = new Position(tv_detail.getWidth(),tv_detail.getHeight(),loc1[0],loc1[1]);

                ((MainActivity)getContext()).goToDetailActivity(food,img_position,card_position,title_container_position,tv_detail_position);
            }
        });

        v.findViewById(R.id.tv_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((MainActivity)getContext()).searchCard();
            }
        });

        return v;
    }


}
