package com.example.threadsandhandlers;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;

public class ExampleHandlerThread extends HandlerThread {
    private static final String TAG = "ExampleHandlerThread";

    public static final int EXAMPLE_TASK = 1;

    private Handler handler;

    public ExampleHandlerThread(String name, int priority) {
        super(name, priority);
        // priority given by Process.THREAD... from -19 (high) to 20 (low priority)
        // or change priority by using Process.setThreadPriority();
    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void onLooperPrepared() {
        // handler = new Handler() ; // attached to the background thread
        handler = new Handler() { // or use a static subclass or a top-level class
            @Override
            public void handleMessage(@NonNull Message message) {
                switch (message.what) {
                    case EXAMPLE_TASK:
                        Log.d(TAG, "Example Task, arg1: " + message.arg1 + ", obj: " + message.obj);
                        for (int i = 0; i < 4; i++) {
                            Log.d(TAG, "handleMessage: " + i);
                            SystemClock.sleep(1000);
                        }
                        break;
                    case 414:
                        Log.d(TAG, "handleMessage: " + message.what);
                        break;
                }
            }
        };
    }

    public Handler getHandler() {
        return handler;
    }
}
