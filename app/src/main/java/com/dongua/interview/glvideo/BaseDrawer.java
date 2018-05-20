package com.dongua.interview.glvideo;

import android.content.Context;

/**
 * Created by lewis.weng on 2018/5/20.
 */
public abstract class BaseDrawer {
    //float占用的字节数
    public static final int FLOAT_SIZE_BYTES = 4;
    //每个顶点占用的字节数
    public static final int VERTEX_DATA_PER_POINT = 3 * FLOAT_SIZE_BYTES;
    //顶点之间的数据差 rgba是4个 xyz坐标系3个  uv坐标系2个
    public static final int SIZE_RGBA = 4;
    public static final int SIZE_XYZ = 3;
    public static final int SIZE_UV = 2;
    //顶点的数量 正方形4个顶点
    public static final int VERTEX_NUM = 4;

    public int mProgram;

    public abstract void initData();

    public abstract void initShader(Context context);

    public abstract void draw();
}
