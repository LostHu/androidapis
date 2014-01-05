package com.lity.android.apis.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Spinner;

public class LocationSelect extends Spinner {

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public LocationSelect(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * @param context
     * @param attrs
     */
    public LocationSelect(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @param context
     */
    public LocationSelect(Context context) {
        super(context);
    }

}
