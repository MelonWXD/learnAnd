package com.dongua.interview.glvideo.img;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.Log;
import com.dongua.interview.glvideo.ShaderUtil;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by lewis.weng on 2018/5/14.
 */
public class ImageShower {

    private static final String TAG = "ImageShower";
    private Bitmap mBitmap;

    //float占用的字节数
    public static final int FLOAT_SIZE_BYTES = 4;
    //每个顶点占用的字节数
    public static final int VERTEX_DATA_PER_POINT = 3 * FLOAT_SIZE_BYTES;
    //顶点之间的数据差 rgba是4个 xyz坐标系3个  uv坐标系2个
    public static final int SIZE_RGBA = 4;
    public static final int SIZE_XYZ = 3;
    public static final int SIZE_XY = 2;
    public static final int SIZE_UV = 2;
    //顶点的数量 正方形4个顶点
    public static final int VERTEX_NUM = 4;

    //顶点的XYZ坐标数据
    private float[] mVertexData = {
        -1.0f, 1.0f,    //左上角
        -1.0f, -1.0f,   //左下角
        1.0f, 1.0f,     //右上角
        1.0f, -1.0f     //右下角
    };

    //纹理的UV坐标数据
    private float[] mTexCoorData = {
        0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,
    };

    private int mProgram;

    private int mTextureID;

    private FloatBuffer mVertexBuffer;
    private FloatBuffer mTexCoorBuffer;

    private int maPositionHandle;
    private int maTexCoorHandle;

    private int muMVPMatrixHandle;
    private float[] mMVPMatrix = new float[16];

    private static ImageShower instance;

    public static ImageShower getInstance(Context context) {
        if (instance == null) {
            synchronized (ImageShower.class) {
                if (instance == null) {
                    instance = new ImageShower(context);
                }
            }
        }
        return instance;
    }

    private ImageShower(Context context) {
        initBuffer();
        initShader(context);
    }

    public int getTextureID() {
        return mTextureID;
    }

    private void initBuffer() {
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

    public void draw(Bitmap bitmap) {

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        GLES20.glUseProgram(mProgram);

        mTextureID = createTexture(bitmap);

        //准备坐标数据 告诉openGL 数据的各种信息方便后续正确对应到每个位上
        GLES20.glVertexAttribPointer(maPositionHandle,
                                     SIZE_XYZ,
                                     GLES20.GL_FLOAT,
                                     false,
                                     SIZE_XYZ * FLOAT_SIZE_BYTES,
                                     mVertexBuffer);
        GLES20.glEnableVertexAttribArray(maPositionHandle);

        GLES20.glVertexAttribPointer(maTexCoorHandle,
                                     SIZE_UV,
                                     GLES20.GL_FLOAT,
                                     false,
                                     SIZE_UV * FLOAT_SIZE_BYTES,
                                     mTexCoorBuffer);
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

    private int createTexture(Bitmap bitmap) {
        int[] texture = new int[1];
        if (bitmap != null && !bitmap.isRecycled()) {
            //生成纹理
            GLES20.glGenTextures(1, texture, 0);
            //生成纹理
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture[0]);
            //设置缩小过滤为使用纹理中坐标最接近的一个像素的颜色作为需要绘制的像素颜色
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            //设置放大过滤为使用纹理中坐标最接近的若干个颜色，通过加权平均算法得到需要绘制的像素颜色
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            //设置环绕方向S，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            //设置环绕方向T，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
            //根据以上指定的参数，生成一个2D纹理
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
            bitmap.recycle();
            return texture[0];
        }
        return 0;
    }
}
