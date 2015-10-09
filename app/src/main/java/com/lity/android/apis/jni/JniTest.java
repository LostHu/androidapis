package com.lity.android.apis.jni;

/*
 * 生成so文件过程：
 * 1. 安装ndk, 并把根目录添加到系统path中.
 * 2. 安装交叉编译环境cygwin.
 * 
 * 3. 编写java类, 声明native方法.
 * 4. 编译此java文件(包括包名), 生成class
 * 5. 依据生成的class文件, 生成c头文件(javah , 不带扩展名class)
 * 6. 编写实现C代码, 命名要和生成的c头文件保持一致
 * 7. 编写Android.mk文件
 * 
 * 8. 把头文件、c实现文件、Android.mk文件放到jni目录下
 * 9. 打开cygwin命令行, 进入到jni的父目录
 * 10. 运行ndk-build命令, 无错误会在./libs/armeabi看到生成的so文件
 *  
 */


/*
 * so文件的加载方式：(Android暂时不支持从sdcard加载so文件)
 * 1. 打包到apk中：复制生成的so文件放在项目libs/armeabi/目录下,
 * 		java class 通过 System.loadLibrary("jnitest");动态加载libjnitest.so
 * 2. 通过其它方式把so文件放在包的安装(/data/data/com.lity.android.apis/mylib/)目录下,
 * 		java class 通过System.load(/data/data/com.lity.android.apis/mylib/libjnitest.so);动态加载libjnitest.so
 */



public class JniTest {
    static { 
    	System.load("/data/data/com.lity.android.apis/libjnitest.so");
	}
    public native int add(int a, int b);
    
    public native int sub(int a, int b);
}
