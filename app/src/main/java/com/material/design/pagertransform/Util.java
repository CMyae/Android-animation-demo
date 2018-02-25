package com.material.design.pagertransform;

import android.content.res.Resources;

/**
 * Created by chan on 9/13/17.
 */

public class Util {

    public static String IMG_POS = "IMG_POS";
    public static String CARD_POS = "LAYOUT_POS";
    public static String TITLE_POS = "TITLE_POS";
    public static String TV_DETAIL_POS = "TV_DETAIL_POS";

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

}
