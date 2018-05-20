package com.dongua.interview.glvideo;

import android.opengl.GLSurfaceView;
import android.os.Environment;

import butterknife.BindView;

import com.dongua.interview.BaseActivity;
import com.dongua.interview.R;
import com.dongua.interview.glvideo.baseuse.NormalGLSurfaceView;
import com.dongua.interview.glvideo.video.VideoGLSurfaceView;

/**
 * Created by lewis on 2018/5/8.
 */
public class VideoActivity extends BaseActivity {

    //    @BindView(R.id.gl_video)
//    VideoGLSurfaceView glSurfaceView;

    @BindView(R.id.gl_video)
    NormalGLSurfaceView glSurfaceView;

    String videoPath = "/storage/emulated/0/DCIM/Camera/VID_20180514_163229.mp4";
    String video_url = Environment.getExternalStorageDirectory() + "/video/t4.mp4";

    @Override
    public int getLayoutID() {
        return R.layout.activity_video;
    }

    @Override
    public void init() {
        glSurfaceView.init(this);
//        glSurfaceView.init(this, videoPath);
    }


    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }


}
