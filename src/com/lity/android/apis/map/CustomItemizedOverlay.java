package com.lity.android.apis.map;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;
import com.lity.android.apis.App;

public class CustomItemizedOverlay extends ItemizedOverlay<OverlayItem> {

    private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
    
    public CustomItemizedOverlay(Drawable defaultMarker) {
        super(boundCenterBottom(defaultMarker));
    }

    @Override
    protected OverlayItem createItem(int i) {
        return mOverlays.get(i);
    }

    @Override
    public int size() {
        return mOverlays.size();
    }
    
    public void addOverlay(OverlayItem overlayItem) {
        mOverlays.add(overlayItem);
//        mOverlays.get(2).setm
        populate();
    }
    
    public void removeOverlay() {
        mOverlays.clear();
        populate();
    }

    @Override
    protected boolean onTap(int index) {
        if (App.DEBUG) {
            Log.v(App.TAG, "onTap method: index:" + index);
        }
        return super.onTap(index);
    }
    
    
}
