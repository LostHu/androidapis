package com.lity.android.apis.game3d;

import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

public class GLRender extends Object implements Renderer {
    int  one = 0x10000;
    float rotateTri = 0f;
    
    // 三角形的点
    private IntBuffer triggerBuffer = IntBuffer.wrap(new int[]{
            0, one, 0,
            -one, -one, 0,
            one, -one, 0
    });
    
    // 三角形各个点的颜色
    private IntBuffer colorTriggerBuffer = IntBuffer.wrap(new int[]{
            one, 0, 0, one,
            0, one, 0, one,
            0, 0, one, one,
    });
    
    // 四边形的点
    private IntBuffer quaterBuffer = IntBuffer.wrap(new int[]{
            one, one, 0,
            -one, one, 0,
            one, -one, 0,
            -one, -one, 0
    });
    
    @Override
    public void onDrawFrame(GL10 gl) {
        // TODO Auto-generated method stub
        rotateTri += 2.0f;
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -3.0f);
//        gl.glTranslatef(-1.5f, 0.0f, -6.0f);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FIXED, 0, triggerBuffer);
        gl.glColorPointer(4, GL10.GL_FIXED, 0, colorTriggerBuffer);
        gl.glRotatef(rotateTri, 0f, 0.0f, 1.0f);
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
//        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
//        gl.glLoadIdentity();
//        gl.glTranslatef(1.5f, 0.0f, -6.0f);
//        gl.glVertexPointer(3, GL10.GL_FIXED, 0, quaterBuffer);
//        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // TODO Auto-generated method stub
        float ratio = (float)width / height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // TODO Auto-generated method stub
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
        gl.glClearColor(0, 0, 0, 0);
        gl.glShadeModel(GL10.GL_SMOOTH);
        
        // 深度
        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);
    }

}
