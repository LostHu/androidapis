package com.lity.android.apis.game3d;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class TriangleWindow extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        GLSurfaceView mGlSurfaceView = new GLSurfaceView(this);
        CubeGLRender mRender = new CubeGLRender();
        mGlSurfaceView.setRenderer(mRender);
        setContentView(mGlSurfaceView);
    }

}
