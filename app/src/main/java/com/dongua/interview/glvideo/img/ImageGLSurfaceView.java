package com.dongua.interview.glvideo.img;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import com.dongua.interview.R;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by lewis on 2018/5/12.
 */
public class ImageGLSurfaceView extends GLSurfaceView
    implements GLSurfaceView.Renderer, SurfaceTexture.OnFrameAvailableListener {

    private Context mCtx;
    private int mTextureID;
    private Bitmap mBitmap;
    private ImageShower mImageShower;

    public ImageGLSurfaceView(Context context) {
        super(context);
        mCtx = context;
    }

    public ImageGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCtx = context;
    }

    public void init(Context context) {
        mCtx = context;
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg);
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
        mImageShower = ImageShower.getInstance(mCtx);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // TODO: 2018/5/12
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glViewport(0, 0, getWidth(), getHeight());
        mImageShower.draw(mBitmap);
    }
}
