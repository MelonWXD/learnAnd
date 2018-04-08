package com.dongua.interview.rxjava;

import android.annotation.SuppressLint;

import io.reactivex.MaybeOperator;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * author: Lewis
 * date: On 18-3-28.
 */

public class RxLearn {

    public static void main(String[] args) {
        Observer observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };


        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e){
                e.onNext("haha");
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("1234");
            }
        })
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String string) {
                        //处理登录结果，返回UserInfo
                        if (!string.isEmpty()) {
                            return Integer.valueOf(string);
                        } else {
                            throw new RuntimeException("获取网络请求失败");
                        }
                    }
                })
                .doOnNext(new Consumer<Integer>() {    //保存登录结果UserInfo
                    @Override
                    public void accept(@NonNull Integer val) throws Exception {
                        System.out.println(val + "   doOnNext");
                    }
                })
                .subscribeOn(Schedulers.io())   //调度线程
                .observeOn(AndroidSchedulers.mainThread())  //调度线程
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer val) throws Exception {
                        //整个请求成功，根据获取的UserInfo更新对应的View
                        System.out.println(val + "  subscribe");

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        //请求失败，显示对应的View
                        System.out.println(throwable.getCause());
                    }
                });
    }


}
