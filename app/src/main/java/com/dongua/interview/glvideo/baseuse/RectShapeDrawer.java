package com.dongua.interview.glvideo.baseuse;

import android.content.Context;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import com.dongua.interview.glvideo.BaseDrawer;
import com.dongua.interview.glvideo.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by lewis.weng on 2018/5/20.
 */
public class RectShapeDrawer extends BaseDrawer {

    private static final String TAG = "RectShapeDrawer";
    private float[] mRectVertexData = {
//            x,y
            -1f, 1f,
            -1f, -1f,
            1f, -1f,
            1f, 1f
    };
    private FloatBuffer mVertexBuffer;

    private int maPositionHandle;
    private int muColorHandle;


    public RectShapeDrawer(Context context) {
        initData();
        initShader(context);
    }

    @Override
    public void initData() {
        //初始化顶点数据buffer
        ByteBuffer vbb = ByteBuffer.allocateDirect(mRectVertexData.length * FLOAT_SIZE_BYTES);
        vbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mVertexBuffer = vbb.asFloatBuffer();//转换为float型缓冲
        mVertexBuffer.put(mRectVertexData);//向缓冲区中放入顶点坐标数据
        mVertexBuffer.position(0);//设置缓冲区起始位置
    }

    @Override
    public void initShader(Context context) {
        mProgram = ShaderUtil.createProgram(ShaderUtil.loadFromAssetsFile("gl_Rect_VertexShader.glsl", context),
                ShaderUtil.loadFromAssetsFile("gl_Rect_FragShader.glsl", context));
        if (mProgram == 0) {
            Log.e(TAG, "initShader: mProgram==0  return");
        }

        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        muColorHandle = GLES20.glGetUniformLocation(mProgram, "uColor");
    }

    @Override
    public void draw() {
        GLES20.glUseProgram(mProgram);


        //准备坐标数据 告诉openGL 数据的各种信息方便后续正确对应到每个位上
        GLES20.glVertexAttribPointer(maPositionHandle, SIZE_XY, GLES20.GL_FLOAT, false, SIZE_XY * FLOAT_SIZE_BYTES, mVertexBuffer);
        GLES20.glEnableVertexAttribArray(maPositionHandle);


        GLES20.glUniform4f(muColorHandle, 1.0f,0.8f,0.6f,1f);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, VERTEX_NUM_RECT);

    }
}
