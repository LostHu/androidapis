/**
 * 
 */
package com.lity.android.apis;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainListWindow extends ListActivity {
    
    public final boolean DEBUG = true;
    public final String TAG = "DEBUG";

    /**
     * intent中的参数名
     */
    private final String PARAM_PREFIX = "PREFIX";
    
    /**
     * 标识category
     */
    public final String INTENT_CATEGORY = "CATEGORY_LITY_SAMPLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        String prefix = getIntent().getStringExtra(PARAM_PREFIX);
        if (null == prefix || prefix.equals("")) {
            prefix = "/";
        }
        Log.v(TAG, "prefix :" + prefix);
        if (!prefix.equals("/")) {
            setTitle(prefix);
        }
        
        setListAdapter(new SimpleAdapter(this, (List<? extends Map<String, ?>>) getData(prefix),
                android.R.layout.simple_list_item_1, new String[] { "title" },
                new int[] { android.R.id.text1 }));
        
        ListView l;
        View view;
    }
    
    
    
    private List<Map> getData(String prefix) {
        

        List<Map> myData = new ArrayList<Map>();
        Set<String> titleSet = new HashSet<String>();
        
        PackageManager pm = getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);

        mainIntent.addCategory(INTENT_CATEGORY);
//        mainIntent.addCategory(Intent.CATEGORY_SAMPLE_CODE);
        
        List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);
        
        final int len = list.size();
        for (int i = 0; i < len; i++) {
            ResolveInfo info = list.get(i);
            String label = info.loadLabel(pm).toString();
            if (label.startsWith(prefix)) {
                
                String suffix = label.substring(prefix.length());
                
                String[] prefixPath = suffix.split("/");
                String title = prefixPath[0];
                String nextPrefix = prefix + title + "/";
                
                if (!titleSet.contains(title)) {
                    titleSet.add(title);
                    Intent intent;
                    Log.v(TAG, "prefixPath.length: " + prefixPath.length);
                    if (prefixPath.length > 1) {
                        intent = browseIntent(nextPrefix);
                    }else {
                        intent = new Intent();
                        intent.setClassName(this, info.activityInfo.name);
                        
                        Log.v(TAG, "goto activity: " + info.activityInfo.name);
                    }
                    
                    Map<String, Object> temp = new HashMap<String, Object>();
                    temp.put("title", title);
                    temp.put("intent", intent);
                    myData.add(temp);
                }

                
            }
        }
        if (DEBUG) {
            Log.v(TAG, "len: " + len);
        }
        Collections.sort(myData, mDisplayComparator);
        
        return myData;
    }
    
    private Intent browseIntent(String prefix){
        Intent intent = new Intent();
        intent.setClass(this, getClass());
        intent.putExtra(PARAM_PREFIX, prefix);
        return intent;
        
    }



    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Map item = (Map)l.getItemAtPosition(position);
        
        Intent intent = (Intent)item.get("intent");
        startActivity(intent);
    }
    
    private Comparator<Map> mDisplayComparator = new Comparator<Map>() {
        private final Collator   collator = Collator.getInstance();
        
        @Override
        public int compare(Map map1, Map map2) {
            return collator.compare(map1.get("title"), map2.get("title"));
        }
    };
    
}
