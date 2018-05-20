package com.dongua.interview.enumTest;

import android.annotation.SuppressLint;
import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by lewis on 2018/5/9.
 */
public class EnumTest {

    public static final int Uninitialized = 0x01;
    public static final int Initialized = 0x02;
    public static final int Paused = 0x03;
    public static final int Playing = 0x04;
    public static final int Completed = 0x05;
    public static final int Stopped = 0x06;
    public static final int Seeking_at_Pause = 0x07;
    public static final int Error = 0x08;

    @IntDef({Uninitialized,Initialized, Paused, Playing, Completed, Stopped, Seeking_at_Pause, Error})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PreviewState {

    }

    @PreviewState
    private int state;

    public void setPreviewState(@PreviewState int state) {
        this.state = state;
    }


    public static void main(String[] args) {
        EnumTest test = new EnumTest();
        test.setPreviewState(200);
    }
}
