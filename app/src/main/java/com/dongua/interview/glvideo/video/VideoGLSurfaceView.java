package com.dongua.interview.glvideo.video;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.view.Surface;
import java.io.IOException;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by lewis on 2018/5/12.
 */
public class VideoGLSurfaceView extends GLSurfaceView
    implements GLSurfaceView.Renderer, SurfaceTexture.OnFrameAvailableListener {

    private Context mCtx;
    private LocalVideoPlayer mVideoPlayer;
    private String videoUrl;
    private MediaPlayer mMediaPlayer;
    private SurfaceTexture mSurfaceTexture;

    public VideoGLSurfaceView(Context context) {
        super(context);
        mCtx = context;
    }

    public VideoGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCtx = context;
    }

    public void init(Context context,String videoUrl) {
        mCtx=context;
        this.videoUrl = videoUrl;
        this.setEGLContextClientVersion(2);
        this.setRenderer(this);
        this.setRenderMode(RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        this.requestRender();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        }
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mMediaPlayer.setDataSource(videoUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }


        mVideoPlayer = LocalVideoPlayer.getInstance(mCtx);
        mSurfaceTexture = new SurfaceTexture(mVideoPlayer.getTextureID());
        mSurfaceTexture.setOnFrameAvailableListener(this);

        Surface surface = new Surface(mSurfaceTexture);
        mMediaPlayer.setSurface(surface);
        surface.release();

        try {
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // TODO: 2018/5/12
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        synchronized (this){
            mSurfaceTexture.updateTexImage();
        }
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glViewport(0, 0, getWidth(), getHeight());
        //mVideoPlayer.draw();
        mVideoPlayer.draw();
    }


    @Override
    public void onPause() {
        super.onPause();
        if(mMediaPlayer != null && mMediaPlayer.isPlaying()){
            mMediaPlayer.pause();
            mMediaPlayer.stop();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMediaPlayer != null){
            mMediaPlayer.start();
        }
    }
}
