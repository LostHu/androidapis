package com.lity.android.apis.textview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.SingleLineTransformationMethod;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class ChineseWindow extends Activity {

    private ChineseTextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mTextView = new ChineseTextView(this);
        mTextView.setBackgroundColor(Color.RED);
//        mTextView.
//        mTextView.setText("I have a Magento installation with two store versions: Store A and Store B. When you go to 'mydomain.com' I get this error message:Until you click on the link for either Store A or Store B -- " +
//        		"then it saves the store in a cookie called 'store' and remembers this when you go to 'mydomain.com'. " +
//                "Is it possible to set 'Store A' as the selected store / homepage by default if no 'store' cookie exists yet?" + 
        mTextView.setText("����һ�����ĵ�15901782875���Կ������У��ģ��Ű����ʲô���������⡣�ؿ�һ��ʱ��2011-09-12��ȥʱ�ڡ���ʵ��,.�Դ�����������ͬ,whereisI,bug,bug,bug.");
//        mTextView.setEllipsize(TruncateAt.END);
//        mTextView.setSpannableFactory(factory)
        addContentView(mTextView, new LayoutParams(LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        
        SingleLineTransformationMethod transformationMethod = SingleLineTransformationMethod.getInstance();
    }
    
}
