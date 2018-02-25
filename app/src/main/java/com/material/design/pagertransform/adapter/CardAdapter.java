package com.material.design.pagertransform.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.material.design.pagertransform.fragment.CardFragment;
import com.material.design.pagertransform.model.Food;

import java.util.List;

/**
 * Created by chan on 9/13/17.
 */

public class CardAdapter extends FragmentPagerAdapter {

    private List<Food> foods;

    public CardAdapter(FragmentManager fm, List<Food>foods) {
        super(fm);
        this.foods = foods;
    }

    @Override
    public Fragment getItem(int position) {
        return CardFragment.getInstance(foods.get(position));
    }

    @Override
    public int getCount() {
        return foods.size();
    }
}
