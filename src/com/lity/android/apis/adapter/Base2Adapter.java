package com.lity.android.apis.adapter;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.lity.android.apis.App;
import com.lity.android.apis.R;

import android.R.integer;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

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
        // TODO ����res������
        mItemXmlLayout = res;
    }
    
    /**
     * ����ItemClass
     * @param itemClass
     */
    public void setItemClass(Class<? extends Object> itemClass) {
        // TODO ���DataSource��Ϊ��,�����ж�
        mItemDataClass = itemClass;
    }
    
    /**
     * ��������Դ
     * @param obj Ӧ��Ϊ{@link#setItemClass()}���͵������List
     */
    public void setDataSource(Object obj) {
        if (!(obj instanceof Array || obj instanceof List<?>)) {
            //TODO ��ʱ��֧����������Դ����
        }
        // TODO ���ItemClass��Ϊ��,�ٽ����ж�
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
        
        // �ҵ������������䷵��ֵ
        final int count = (rule.length - two) / 2;
//        Method[] argMethods = new Method[count];
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
//                argMethods[i] = method;
//                returnTypes[i] = method.getReturnType();
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
