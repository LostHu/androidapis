package com.lity.android.apis.adapter;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lity.android.apis.App;
import com.lity.android.apis.R;

public class ListWindow extends ListActivity {

    private Base2Adapter mAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adapter_list_window);
        mAdapter = new Base2Adapter(this);
        mAdapter.setItemXml(R.layout.adapter_list_listitem);
        mAdapter.setItemClass(ItemData.class);
        mAdapter.setDataSource(createData());
//        mAdapter.addRule("adapter_list_listitem_text1", "setText", new String[]{"getText1"});
        setListAdapter(mAdapter);
    }
    
    private ItemData[] createData() {
        String[] text1 = getResources().getStringArray(R.array.app_data_text1);
        String[] text2 = getResources().getStringArray(R.array.app_data_text2);
        String[] content = getResources().getStringArray(R.array.app_data_content);

        
        if (text1.length == text2.length && text2.length == content.length) {
            int count = text1.length;
            ItemData[] data = new ItemData[count];
            for (int i = 0; i < data.length; i++) {
                ItemData item = new ItemData();
                item.text1 = text1[i];
                item.text2 = text2[i];
                item.content = content[i];
                data[i] = item;
            }
//            data[0].imageId = R.drawable.detail_jiankang_img;
//            data[1].imageId = R.drawable.detail_cheyou_img;
//            data[2].imageId = R.drawable.detail_chalv_img;
//            data[3].imageId = R.drawable.detail_meishi_img;
//            data[4].imageId = R.drawable.detail_yuer_img;
            return data;
        }
        return null;
    }
    
    public static class ItemData extends Object {
        public boolean checked;
        public String text1;
        public String text2;
        public String content;
        public int imageId;
        
        public String getText1() {
            return text1;
        }
        
        public String getText2() {
            return text2;
        }
        public String getContent() {
            return content;
        }
    }
    
    public class Base2Adapter extends BaseAdapter {

        private int mItemXmlLayout = 0;
        
        private Class<? extends Object> mItemDataClass = null;
        
        private Object mDataSource = null;
        
        private ArrayList<String[]> mRules = new ArrayList<String[]>();
        
        private Context mContext;
        
        private List<Object> mDataList;
        private Object[] mDataArray;
        
        
        
        /**
         * 
         */
        public Base2Adapter(Context context) {
            super();
            mContext = context;
        }

        @Override
        public int getCount() {
            int count = 0;
            if (null != mDataList) {
                count = mDataList.size();
            }
            else if (null != mDataArray) {
                count = mDataArray.length;
            }
            return count;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                if (0 == mItemXmlLayout) {
                    throw new RuntimeException();
                }
                convertView = LayoutInflater.from(mContext).inflate(mItemXmlLayout, null);
                TextView text = null;
            }
            
            for (int i = 0; i < mRules.size(); i++) {
                execute(convertView, i, mDataArray[position]);
            }
            
     
            return convertView;
        }
        
        /**
         * layout item.xml
         * @param res resId
         */
        public void setItemXml(int res) {
            // TODO 处理res不存在
            mItemXmlLayout = res;
        }
        
        /**
         * 设置ItemClass
         * @param itemClass
         */
        public void setItemClass(Class<? extends Object> itemClass) {
            // TODO 如果DataSource不为空,进行判断
            mItemDataClass = itemClass;
        }
        
        /**
         * 连接数据源
         * @param obj 应该为{@link#setItemClass()}类型的数组或List
         */
        public void setDataSource(Object obj) {
            if (!(obj instanceof Array || obj instanceof List<?>)) {
                //TODO 暂时不支持其它数据源类型
            }
            // TODO 如果ItemClass不为空,再进行判断
            mDataSource = obj;
            if (mDataSource.getClass().isArray()) {
                mDataArray = (Object[])mDataSource;
            }
        }
        
        /**
         * View view = convertView.findViewById(viewId);<br />
         * view.method(objexArgs[0], objexArgs[1]...);<br />
         * @param viewId
         * @param method
         * @param classArgs
         * @param objextArgs
         * @return
         * 
         */
        public boolean addRule(String viewId, String method, Class[] classArgs, String[] objextArgs) {
            if (classArgs.length != objextArgs.length) {
                throw new RuntimeException();
            }
            final int two = 2;
            String[] rule = new String[2 * classArgs.length + two];
            rule[0] = viewId;
            rule[1] = method;
            
            int offset = two;
            for (int i = 0; i < classArgs.length; i++) {
                rule[i + offset] = classArgs[i].getName();
            }
            offset = two + classArgs.length;
            for (int i = 0; i < objextArgs.length; i++) {
                rule[i + offset] = objextArgs[i];
            }
            mRules.add(rule);
            return true;
        }
        
        private void execute(View convertView, int ruleIndex, Object data) {
            final String[] rule = mRules.get(ruleIndex);
            final int two = 2;
            final Class<? extends Object> itemClass = mItemDataClass;
            
            final int id = mContext.getResources().getIdentifier(rule[0], "id", mContext.getPackageName());
            
            Object childView = convertView.findViewById(id);
            if (null == childView) {
                throw new IllegalArgumentException();
            }
            
            // 找到参数函数及其返回值
            final int count = (rule.length - two) / 2;
//            Method[] argMethods = new Method[count];
            Class[] parameterTypes = new Class[count];
            Object[] argObjects = new Object[count];
            
            
            try {
                // 
                for (int i = 0; i < count; i++) {
                    parameterTypes[i] = Class.forName(rule[two + i]);
                }
                
                
                int j = 0;
                for (int i = two + count; i < rule.length; i++) {
                    Method method = itemClass.getMethod(rule[i], new Class[]{});
//                    argMethods[i] = method;
//                    returnTypes[i] = method.getReturnType();
                    argObjects[j++] = method.invoke(data, new Object[]{});
                }
                
                if (App.DEBUG) {
                    Log.v(App.TAG, "new String(aa) instanceof Object):" + (new String("aa") instanceof Object));
                }
             
                final Method executeMethod = childView.getClass().getMethod(rule[1], parameterTypes);
                if (null == executeMethod) {
                    throw new IllegalArgumentException();
                }
                executeMethod.invoke(childView, argObjects);
            } catch (Exception e) {
                // TODO: handle exception
                throw new IllegalArgumentException(e);
            }
        }
    }

}
