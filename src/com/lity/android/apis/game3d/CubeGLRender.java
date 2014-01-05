package com.lity.android.apis.game3d;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

public class CubeGLRender implements Renderer {
    int  one = 0x10000;
    float rotateTri = 0f;
    float xrot, yrot, zrot;
    // 四边形的点
    private IntBuffer cubePointerBuffer = IntBuffer.wrap(new int[]{
            one, 0, 0,
            one, one, 0,

            0, 0, 0,
            0, one, 0,
//            one, one, 0,
//            -one, one, 0,
//            one, -one, 0,
//            -one, -one, 0,
            
            one, one, one,
            0, one, one,

            one, 0, one,
            0, 0, one,
            
            0, one, 0,
            0, 0, 0,

            0, one, one,
            0, 0, one,
            
            one, one, 0,
            one, 0, 0,

            one, one, one,
            one, 0, one,
            
            one, one, one,
            0, one, one,

            one, one, 0,
            0, one, 0,
            
            one, 0, one,
            0, 0, one,

            one, 0, 0,
            0, 0, 0,
            
    });
    private IntBuffer colorBufferForQuad = IntBuffer.wrap(new int[]{
            
            0,one,0,one,
            0,one,0,one,
            0,one,0,one,
            0,one,0,one,
            
            one, one/2, 0, one,
            one, one/2, 0, one,
            one, one/2, 0, one,
            one, one/2, 0, one,
            
            one,0,0,one,
            one,0,0,one,
            one,0,0,one,
            one,0,0,one,
            
            one,one,0,one,
            one,one,0,one,
            one,one,0,one,
            one,one,0,one,
            
            0,0,one,one,
            0,0,one,one,
            0,0,one,one,
            0,0,one,one,
            
            one,0,one,one,
            one,0,one,one,
            one,0,one,one,
            one,0,one,one,
   });
    
    ByteBuffer indices = ByteBuffer.wrap(new byte[]{
            0,1,3,2,
            4,5,7,6,
            8,9,11,10,
            12,13,15,14,
            16,17,19,18,
            20,21,23,22,
    });
    IntBuffer texCoords = IntBuffer.wrap(new int[]{
            one,0,0,0,0,one,one,one,    
            0,0,0,one,one,one,one,0,
            one,one,one,0,0,0,0,one,
            0,one,one,one,one,0,0,0,
            0,0,0,one,one,one,one,0,
            one,0,0,0,0,one,one,one,
        });
    
    @Override
    public void onDrawFrame(GL10 gl) {
        // TODO Auto-generated method stub
//        rotateTri += 2.0f;
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        
        gl.glTranslatef(0.0f, 0.0f, -3.0f);
        
        //设置3个方向的旋转
        gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(zrot, 0.0f, 0.0f, 1.0f);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glTexCoordPointer(2, GL10.GL_FIXED, 0, texCoords);
        gl.glColorPointer(4, GL10.GL_FIXED, 0, colorBufferForQuad);
        gl.glVertexPointer(3, GL10.GL_FIXED, 0, cubePointerBuffer);
        for (int i = 0; i < 6; i++) {
            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, i * 4, 4);
        }
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        xrot+=0.5f;
        yrot+=0.6f; 
        zrot+=0.3f; 
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
