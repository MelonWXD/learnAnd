package com.dongua.interview.glvideo.other;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by lewis on 2018/5/8.
 */
public class CameraGLRender implements GLSurfaceView.Renderer {

    private Triangle mTriangle;
    private Square mSquare;

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // 设置背景的颜色
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        // 初始化一个三角形
        mTriangle = new Triangle();
        // 初始化一个正方形
        mSquare = new Square();
    }
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }
    @Override
    public void onDrawFrame(GL10 gl) {
        // 重绘背景颜色
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        mTriangle.draw();
    }

    public static int loadShader(int type, String shaderCode) {

        // 创建一个vertex shader 类型 (GLES20.GL_VERTEX_SHADER)
        // 或者一个 fragment shader 类型(GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // 将源码添加到shader并编译
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
}