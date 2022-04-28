package com.example.threadsandhandlers;

import static com.example.threadsandhandlers.ExampleHandlerThread.EXAMPLE_TASK;

import android.os.Bundle;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {
    private static final String TAG = "MainActivity2";
    private ExampleRunnable1 runnable1 = new ExampleRunnable1();
    private Object token = new Object();

    private ExampleHandlerThread handlerThread =
            new ExampleHandlerThread("ExampleHandlerThread debug", Process.THREAD_PRIORITY_BACKGROUND);
    // passed String variable "HandlerThread debug" only for debugging purposes
    // private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        handlerThread.start();
        // handler = new Handler(exampleHandlerThread.getLooper()); // pass the looper of the handlerThread
        // by attaching the handler to the looper, we can get work to the looper of handlerThread
        // and not into the looper of the ui-thread
    }

    @Override
    protected void onDestroy() { // to avoid a zombie thread
        super.onDestroy();
        handlerThread.quit();
    }

    public void doWork(View view) {
        // handler.postDelayed(new ExampleRunnable1(), 2000);
        // handler.postAtTime(new ExampleRunnable1(), SystemClock.uptimeMillis() + 1000);
        /*
        handler.post(new ExampleRunnable1());
        handler.post(new ExampleRunnable1());
        handler.postAtFrontOfQueue(new ExampleRunnable2()); */
        /*
        handlerThread.getHandler().postDelayed(new ExampleRunnable1(), 1000);
        handlerThread.getHandler().post(new ExampleRunnable2());
        handlerThread.getHandler().postAtFrontOfQueue(new ExampleRunnable3()); // 2, 3, 1
        */
        /*
        Message message = Message.obtain();
        message.what = 1;
        handlerThread.getHandler().sendMessage(message);
        // the same as the 3 lines above: */
        // handlerThread.getHandler().sendEmptyMessage(1);

        Message message = Message.obtain(handlerThread.getHandler());
        message.what = EXAMPLE_TASK;
        message.arg1 = 23;
        message.obj = "my new String";
        // handlerThread.getHandler().sendMessage(message);
        // message.setTarget(handlerThread.getHandler()); // alternative way to set target of message instead of at .obtain
        message.sendToTarget(); // sends message to its specified handlerThread
        handlerThread.getHandler().sendEmptyMessage(1);
        handlerThread.getHandler().post(runnable1);
        handlerThread.getHandler().postAtTime(runnable1, token, SystemClock.uptimeMillis()); // no delay...

        // handlerThread.getHandler().obtainMessage(414); // or send message directly to handlerThread

    }

    public void removeMessages(View view) {
        // handlerThread.getHandler().removeCallbacksAndMessages(null); // with token null it removes all pending callbacks and messages
        handlerThread.getHandler().removeCallbacks(runnable1, token);
    }

    static class ExampleRunnable1 implements Runnable { // can also be a top-level class
        @Override
        public void run() {
            for (int i = 0; i < 4; i++) {
                Log.d(TAG, "Runnable1: " + i);
                SystemClock.sleep(1000);
            }
        }
    }

    static class ExampleRunnable2 implements Runnable { // can also be a top-level class
        @Override
        public void run() {
            for (int i = 0; i < 4; i++) {
                Log.d(TAG, "Runnable2: " + i);
                SystemClock.sleep(1000);
            }
        }
    }

    static class ExampleRunnable3 implements Runnable { // can also be a top-level class
        @Override
        public void run() {
            for (int i = 0; i < 4; i++) {
                Log.d(TAG, "Runnable3: " + i);
                SystemClock.sleep(1000);
            }
        }
    }

}
