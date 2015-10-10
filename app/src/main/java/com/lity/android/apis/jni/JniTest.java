package com.lity.android.apis.jni;

/*
 * ����so�ļ����̣�
 * 1. ��װndk, ���Ѹ�Ŀ¼��ӵ�ϵͳpath��.
 * 2. ��װ������뻷��cygwin.
 * 
 * 3. ��дjava��, ����native����.
 * 4. �����java�ļ�(��������), ����class
 * 5. �������ɵ�class�ļ�, ����cͷ�ļ�(javah , ������չ��class)
 * 6. ��дʵ��C����, ����Ҫ�����ɵ�cͷ�ļ�����һ��
 * 7. ��дAndroid.mk�ļ�
 * 
 * 8. ��ͷ�ļ���cʵ���ļ���Android.mk�ļ��ŵ�jniĿ¼��
 * 9. ��cygwin������, ���뵽jni�ĸ�Ŀ¼
 * 10. ����ndk-build����, �޴������./libs/armeabi�������ɵ�so�ļ�
 *  
 */


/*
 * so�ļ��ļ��ط�ʽ��(Android��ʱ��֧�ִ�sdcard����so�ļ�)
 * 1. �����apk�У��������ɵ�so�ļ�������Ŀlibs/armeabi/Ŀ¼��,
 * 		java class ͨ�� System.loadLibrary("jnitest");��̬����libjnitest.so
 * 2. ͨ��������ʽ��so�ļ����ڰ��İ�װ(/data/data/com.lity.android.apis/mylib/)Ŀ¼��,
 * 		java class ͨ��System.load(/data/data/com.lity.android.apis/mylib/libjnitest.so);��̬����libjnitest.so
 */



public class JniTest {
    static { 
    	System.load("/data/data/com.lity.android.apis/libjnitest.so");
	}
    public native int add(int a, int b);
    
    public native int sub(int a, int b);
}
