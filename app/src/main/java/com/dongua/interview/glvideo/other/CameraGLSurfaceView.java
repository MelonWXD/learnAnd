package com.dongua.interview.glvideo.other;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * Created by lewis on 2018/5/8.
 */
public class CameraGLSurfaceView extends GLSurfaceView implements SurfaceTexture.OnFrameAvailableListener {

    private Context context;
    private SurfaceTexture surfaceTexture;
    private int textureID = -1;

    public CameraGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        //设置GL版本
        setEGLContextClientVersion(2);
        //设置渲染器
        setRenderer(new CameraGLRender());
        //设置渲染模式
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }
    public CameraGLSurfaceView(Context context) {
        super(context);

        this.context = context;
        setEGLContextClientVersion(2);
        setRenderer(new CameraGLRender());
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        requestRender();
    }




}
