package com.dongua.interview.enumTest;

/**
 * Created by lewis on 2018/5/9.
 */
public class EnumTest2 {

    //public static final int Uninitialized = 0x01;
    //public static final int Initialized = 0x02;
    //public static final int Paused = 0x03;
    //public static final int Playing = 0x04;
    //public static final int Completed = 0x05;
    //public static final int Stopped = 0x06;
    //public static final int Seeking_at_Pause = 0x07;
    //public static final int Error = 0x08;

    public enum PreviewState {

        Uninitialized, Initialized, Paused, Playing, Completed, Stopped, Seeking_at_Pause, Error
    }

    private PreviewState state;

    public void setPreviewState(PreviewState state) {
        this.state = state;
    }

    public static void main(String[] args) {
        EnumTest2 test = new EnumTest2();
        test.setPreviewState(PreviewState.Completed);
    }
}
