package com.dongua.interview.glvideo.video;

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
 * Created by lewis on 2018/5/12.
 */
public class LocalVideoPlayer extends BaseDrawer {

    private static final String TAG = "LocalVideoPlayer";


    ////顶点的XYZ坐标数据
    //private float[] mVertexData = {
    //    // X,    Y,     Z,
    //    -1.0f,  -1.0f,  0,
    //    1.0f,  -1.0f,  0,
    //    -1.0f,	 1.0f,  0,
    //    1.0f,   1.0f,  0,
    //};
    //
    ////纹理的UV坐标数据
    //private float[] mTexCoorData = {
    //    0.0f, 1.0f,
    //    1.0f, 1.0f,
    //    0.0f, 0.0f,
    //    1.0f, 0.0f,
    //
    //
    //};
    //顶点的XYZ坐标数据
    private float[] mVertexData = {
            // X,    Y,     Z,
            -1.0f, -1.0f, 0,
            1.0f, -1.0f, 0,
            0f, 1.0f, 0,
    };

    //纹理的UV坐标数据
    private float[] mTexCoorData = {
            0.0f, 0.0f,
            1.0f, 0.0f,
            0.0f, 1.0f

    };

    private int mTextureID;

    private FloatBuffer mVertexBuffer;
    private FloatBuffer mTexCoorBuffer;

    private int maPositionHandle;
    private int maTexCoorHandle;

    private int muMVPMatrixHandle;
    private float[] mMVPMatrix = new float[16];

    private static LocalVideoPlayer instance;

    public static LocalVideoPlayer getInstance(Context context) {
        if (instance == null) {
            synchronized (LocalVideoPlayer.class) {
                if (instance == null) {
                    instance = new LocalVideoPlayer(context);
                }
            }
        }
        return instance;
    }

    private LocalVideoPlayer(Context context) {
        initData();
        initShader(context);
    }


    public int getTextureID() {
        return mTextureID;
    }

    @Override
    public void initData() {
        //初始化顶点数据buffer
        ByteBuffer vbb = ByteBuffer.allocateDirect(mVertexData.length * FLOAT_SIZE_BYTES);
        vbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mVertexBuffer = vbb.asFloatBuffer();//转换为float型缓冲
        mVertexBuffer.put(mVertexData);//向缓冲区中放入顶点坐标数据
        mVertexBuffer.position(0);//设置缓冲区起始位置
        //初始化纹理坐标数据buffer
        ByteBuffer llbb = ByteBuffer.allocateDirect(mTexCoorData.length * FLOAT_SIZE_BYTES);
        llbb.order(ByteOrder.nativeOrder());
        mTexCoorBuffer = llbb.asFloatBuffer();
        mTexCoorBuffer.put(mTexCoorData);
        mTexCoorBuffer.position(0);
    }

    @Override
    public void initShader(Context context) {
        //此处加载的渲染器为texture2D 为视频播放准备的
        mProgram = ShaderUtil.createProgram(ShaderUtil.loadFromAssetsFile("gl_VertexShader.sh", context),
                ShaderUtil.loadFromAssetsFile("gl_Texture_FragShader.sh", context));
        if (mProgram == 0) {
            Log.e(TAG, "initShader: mProgram==0  return");
        }

        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        maTexCoorHandle = GLES20.glGetAttribLocation(mProgram, "aTexCoor");
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");


    }

    @Override
    public void draw() {
        GLES20.glUseProgram(mProgram);

        //选择纹理单元
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, mTextureID);

        //准备坐标数据 告诉openGL 数据的各种信息方便后续正确对应到每个位上
        GLES20.glVertexAttribPointer(maPositionHandle, SIZE_XYZ, GLES20.GL_FLOAT, false, SIZE_XYZ * FLOAT_SIZE_BYTES, mVertexBuffer);
        GLES20.glEnableVertexAttribArray(maPositionHandle);

        GLES20.glVertexAttribPointer(maTexCoorHandle, SIZE_UV, GLES20.GL_FLOAT, false, SIZE_UV * FLOAT_SIZE_BYTES, mTexCoorBuffer);
        GLES20.glEnableVertexAttribArray(maTexCoorHandle);


        //初始化单位矩阵  todo 应该放到initBuffer中吧
        Matrix.setIdentityM(mMVPMatrix, 0);

        //更改uniform变量 uniform mat4 uMVPMatrix;
        /**
         location
         指明要更改的uniform变量的位置

         count
         指明要更改的矩阵个数 如果目标uniform变量不是一个数组，那么这个值应该设为1；如果是数组，则应该设置为>=1

         transpose
         指明是否要转置矩阵，并将它作为uniform变量的值。必须为GL_FALSE。

         value
         指明一个指向count个元素的指针，用来更新指定的uniform变量。

         */
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, mMVPMatrix, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, VERTEX_NUM);


    }


}
