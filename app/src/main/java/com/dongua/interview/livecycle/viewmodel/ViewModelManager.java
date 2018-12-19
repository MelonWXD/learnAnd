package com.dongua.interview.livecycle.viewmodel;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.SparseArray;

/**
 * @CreateDate: 2018/12/18 下午4:06
 * @Author: Lewis Weng
 * @Description: 管理多个ViewModel的类
 */
public class ViewModelManager {
    private SparseArray<ViewModel> modelMaps;

    private static volatile ViewModelManager sInstance;

    private ViewModelManager() {
        modelMaps = new SparseArray<>();
    }

    public static ViewModelManager getInstance() {
        if (sInstance == null) {
            synchronized (ViewModelManager.class) {
                if (sInstance == null) {
                    sInstance = new ViewModelManager();
                }
            }
        }
        return sInstance;
    }


    public void put(ViewModel viewModel){
    }
}
