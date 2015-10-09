package com.lity.android.apis.media;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.lity.android.apis.R;

public class VedioWindow extends Activity {

    private final String PATH_NAME = "/sdcard/record/record";
    
    private Button mPlayButton;
    private Button mRecorderButton;
    private Button mStopButton;
    
    private MediaRecorder mRecorder;
    private SurfaceView mSurfaceView;
    
    private MediaPlayer mPlayer;
    private VideoView mVideoView;
    private File mRecodeFile;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vedio_window);
        mPlayButton = (Button)findViewById(R.id.vedio_play_button);
        mRecorderButton = (Button)findViewById(R.id.vedio_recoder_button);
        mStopButton = (Button)findViewById(R.id.vedio_stop_button);
        mSurfaceView = (SurfaceView)findViewById(R.id.video_preview_surfaceview);
        mVideoView = (VideoView)findViewById(R.id.video_playview_videoview);
        
        // 录制的文件名
        Log.v("DEBUG", "file storage directory: " + Environment.getExternalStorageState());
//        try {
            mRecodeFile = new File("/sdcard/record/record.mp4");
//            mRecodeFile.
            // = File.createTempFile("recorder", "mp4", Environment.getExternalStorageDirectory());
//            mRecodeFile = File.createTempFile(PATH_NAME, "3gp");
//        } catch (IOException e1) {
//            e1.printStackTrace();
//            mRecodeFile = null;
//        }
        
       
        
        // 初始化recorder
        mRecorder = new MediaRecorder();
        
        // 初始化player
        mPlayer = new MediaPlayer();
        
        // 设置预览,TODO 放在initRecorder中
        mSurfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mRecorder.setPreviewDisplay(mSurfaceView.getHolder().getSurface());
        

        // 播放
        mPlayButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                mVideoView.setVideoPath(mRecodeFile.getAbsolutePath());
                mVideoView.setMediaController(new MediaController(VedioWindow.this));
                mVideoView.requestFocus();
                mVideoView.start();
            }
        });
        
        // 录制
        mRecorderButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                initRecorder();
                try {
                    mRecorder.prepare();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.v("DEBUG", "bug:" + e);
                    e.printStackTrace();
                }
                mRecorder.start();
            }
        });
        
        // 停止录制
        mStopButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                mRecorder.reset();
            }
        });
        
        mRecorder.setOnErrorListener(new OnErrorListener() {
            
            @Override
            public void onError(MediaRecorder mr, int what, int extra) {
                Log.v("DEBUG", "an error :" + what + " ,extra:" + extra);
            }
        });
    }
    
    /**
     * 初始化Recoder
     */
    private void initRecorder(){
        mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setVideoSize(100, 100);
        mRecorder.setVideoFrameRate(15);
        mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H263);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mRecorder.setOutputFile(mRecodeFile.getAbsolutePath());  
    }

}
