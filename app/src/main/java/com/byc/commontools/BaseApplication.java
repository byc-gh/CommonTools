package com.byc.commontools;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.byc.mylibrary.AppUtils;
import com.byc.mylibrary.L;

import cockroach.Cockroach;
import cockroach.ExceptionHandler;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        L.isDebug = true;
        AppUtils.context = this;
        install();
    }

    private void install() {
        Cockroach.install(BaseApplication.this, new ExceptionHandler() {
            @Override
            protected void onUncaughtExceptionHappened(Thread thread, Throwable throwable) {
                Log.e("AndroidRuntime", "--->onUncaughtExceptionHappened:" + thread + "<---", throwable);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }

            @Override
            protected void onBandageExceptionHappened(Throwable throwable) {
                throwable.printStackTrace();//打印警告级别log，该throwable可能是最开始的bug导致的，无需关心
            }

            @Override
            protected void onEnterSafeMode() {

            }
        });
    }
}
