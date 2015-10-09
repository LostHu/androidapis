package com.lity.android.apis.media;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Bitmap.CompressFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.lity.android.apis.R;

public class CameraWindow extends Activity {
    private final String DEBUG = "DEBUG";

    private Camera mCamera;
    private MediaRecorder mMediaRecorder;
    
    private SurfaceView mPreviewView;
    
    private Button mTakePictureButton;
    private Button mRecordVideoButton;
    
    private ImageView mImageOrVideoImageView;
    
    private SeekBar mSwitchSeekBar;
    
    /**
     *  正常状态
     */
    final int CAMERA_STATE_NORMAL = 0;
    
    /**
     * 正在预览
     */
    final int CAMERA_STATE_PREVIEWING = 1;
    
    /**
     * 正在拍照
     */
    final int CAMERA_STATE_TAKINGEPICTUTE = 2;
    
    /**
     * 正在录像
     */
    final int CAMERA_STATE_RECORDINGVIDEO = 3;
    
    final static int PATTERN_TAKEPICTURE = 0;
    
    final static int PATTERN_RECORDVIDEO = 1;
    
    final static int ININ_RECORDER = 0x1;
    
    int mPattern = PATTERN_TAKEPICTURE;
    
    
    /**
     * 摄像机的状态
     */
    private int mState = CAMERA_STATE_NORMAL;
    
    boolean mIsTaking = false;
    boolean mIsRecording = false;
    
    
    private SurfaceHolder mSurfaceHolder;
    private Handler mHandler = new MainHandler();
    private Thread mStartPreviewThread;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_window);
        mPreviewView = (SurfaceView)findViewById(R.id.camera_preview_surfaceview);
        mPreviewView.requestFocus();
        mTakePictureButton = (Button)findViewById(R.id.camera_takepicture_button);
        mTakePictureButton.setOnClickListener(new TakePicture());
        mRecordVideoButton = (Button)findViewById(R.id.camera_recordvideo_button);
        mRecordVideoButton.setOnClickListener(new RecordVideo());
        mImageOrVideoImageView = (ImageView)findViewById(R.id.camera_imageorrideo_imageview);
        
        
        SurfaceHolder holder = mPreviewView.getHolder();
//        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        

        
        // 切换到拍照模式
//        switchPattern(PATTERN_TAKEPICTURE);

        holder.addCallback(new HolderCallBack());
        
        mSwitchSeekBar = (SeekBar)findViewById(R.id.camera_switch_seekbar);
        mSwitchSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
            
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                    boolean fromUser) {
                if (0 == progress) {
                    Log.v(DEBUG, "switch takepciture pattern");
                    switchPattern(PATTERN_TAKEPICTURE);
                }
                else if (1 == progress) {
                    Log.v(DEBUG, "switch recordvideo pattern");
                    switchPattern(PATTERN_RECORDVIDEO);
                }
            }
        });
        
        
        mStartPreviewThread = new Thread(new Runnable() {
            
            @Override
            public void run() {
                startPreview();
            }
        });
        mStartPreviewThread.start();
        try {
            mStartPreviewThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    
    
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
//        getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_HOME == keyCode) {
            Log.v(DEBUG, "you click home key");
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 拍照时的回调方法
     * @author Lity
     *
     */
    class TakePicture extends Object implements View.OnClickListener, Camera.ShutterCallback, Camera.PictureCallback {

        @Override
        public void onClick(View v) {
            if (null != mCamera) {
                mCamera.takePicture(this, this, this);
            }
        }

        @Override
        public void onShutter() {
//            Log.v(DEBUG, "on shutter method");
//            mCamera.startPreview();
        }

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.v(DEBUG, "data :" + data);
            Log.v(DEBUG, "thread:" + Thread.currentThread());
//            if (null != data) {
//                Log.v(DEBUG, "write compress data to jpg file");
//                Bitmap cameraBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//                Matrix matrix = new Matrix();
//                matrix.postRotate(90);
//                cameraBitmap = Bitmap.createBitmap(cameraBitmap, 0, 0, cameraBitmap.getWidth(), cameraBitmap.getHeight(), matrix, true);
//                if (null != cameraBitmap) {
//                    Log.v(DEBUG, "camera == " + cameraBitmap);
//                    mImageOrVideoImageView.setImageBitmap(cameraBitmap);
//                }
//                File saveFile = new File("/sdcard/record/" + System.currentTimeMillis() + ".jpg");
//                BufferedOutputStream bs = null;
//                try {
//                    bs = new BufferedOutputStream(new FileOutputStream(saveFile));
//                    cameraBitmap.compress(CompressFormat.JPEG, 80, bs);
//                    bs.flush();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Log.v(DEBUG, "an Exception occurs :" + e);
//                }
//
//            }
        }
        
    }

    /**
     * 录像时的回调方法
     * @author Lity
     *
     */
    class RecordVideo extends Object implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (PATTERN_RECORDVIDEO == mPattern) {
                if (mIsRecording) {
//                    mMediaRecorder.reset();
//                    mMediaRecorder.stop();
                    mMediaRecorder.release();
                    mMediaRecorder = null;
                    
                    mHandler.sendEmptyMessage(ININ_RECORDER);
                    
                    Log.v(DEBUG, "stop record ...");
                }
                else {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    mMediaRecorder.start();
                    Log.v(DEBUG, "start record....");
                    
                }
                mIsRecording = !mIsRecording;
            }            
        }
        

    }
 
    /**
     * holder的回调方法
     * @author Lity
     *
     */
    class HolderCallBack extends Object implements SurfaceHolder.Callback{
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            mSurfaceHolder = null;
        }
        
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            mSurfaceHolder = holder;
        }
        
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                int height) {
            // 初始化camera和mediarecorder
            if (holder.isCreating()) {
                try {
                    mCamera.setPreviewDisplay(holder);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.v(DEBUG, "exception occurs: " + e);
                }
//                mCamera.unlock();
                mHandler.sendEmptyMessage(ININ_RECORDER);
                Log.v(DEBUG, "surfaceChanged mehthod");
            }
            
        }
    }
 
    class MainHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case ININ_RECORDER:
                initRecorder(true);
                break;

            default:
                super.handleMessage(msg);
                break;
            }
        }
        
    }
    
    /**
     * 初始化camera
     */
    private void initCamera(){


    }
    
    /**
     * 开始预览
     */
    private void startPreview(){
        mCamera = Camera.open();
//        mCamera.lock();
//        mCamera.l
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPictureFormat(PixelFormat.JPEG);
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
        mCamera.setParameters(parameters);
        mCamera.setDisplayOrientation(90);
        try {
            Log.v(DEBUG, "holder: " + mSurfaceHolder);
            mCamera.setPreviewDisplay(mSurfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
            Log.v(DEBUG, "exception occurs: " + e);

        }
        mCamera.startPreview();
    }
    
    /**
     * 初始化Recoder
     * @param toRecord true时录制,false时预览
     */
    private void initRecorder(boolean toRecord){
//        mMediaRecorder = new MediaRecorder();
//        
//        mMediaRecorder.setCamera(mCamera);
//        
//        // NOTICE 这些设置必须在setCamera()后面
//        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
//        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//        mMediaRecorder.setVideoSize(100, 100);
//        mMediaRecorder.setVideoFrameRate(15);
//        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H263);
//        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//        if (toRecord) {
//            mMediaRecorder.setOutputFile("/cache/record/" + System.currentTimeMillis() + ".3gp");  
//        }
//        else {
//            mMediaRecorder.setOutputFile("/dev/null"); 
//        }
//        mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
//        try {
//            mMediaRecorder.prepare();
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.v(DEBUG, "exception occurs: " + e);
//        } 
    }
    
    /**
     * 切换模式
     * @param pattern 模式Id
     */
    private void switchPattern(int pattern){
//        if (mPattern != pattern) {
//            if (PATTERN_TAKEPICTURE == pattern) {
//                mMediaRecorder.stop();
//                try {
//                    mCamera.reconnect();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Log.v(DEBUG, "exception occurs: " + e);
//                }
//                mCamera.startPreview();
//            }
//            else if (PATTERN_RECORDVIDEO == pattern) {
//                
//                mMediaRecorder.start();
//            }
//            mPattern = pattern;
//        }
    }
   
}
