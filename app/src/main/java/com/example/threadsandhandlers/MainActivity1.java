package com.example.threadsandhandlers;

import static com.example.threadsandhandlers.ExampleHandler.TASK_A;
import static com.example.threadsandhandlers.ExampleHandler.TASK_B;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity1 extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ExampleLooperThread looperThread = new ExampleLooperThread();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
    }

    public void startThread(View view) {
        looperThread.start();
    }

    public void stopThread(View view) {
        looperThread.looper.quit();
        // looperThread.handler.getLooper().quit(); // this line does the same as the line above
    }

    public void taskA(View view) {
        /* // post a Runnable
        // Handler threadHandler = new Handler(); // this would call a handler on the main thread
        Handler threadHandler = new Handler(looperThread.looper); // pass the looper of another thread
        // then the following line:
        // looperThread.handler.post(new Runnable() { // calls the handler of the looperThread
        // this posts work to looper of the looperThread in the background
        // changes to:
        threadHandler.post(new Runnable() { // implicit reference to activity (is non-static)
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    Log.d(TAG, "run: " + i);
                    SystemClock.sleep(1000);
                }
            }
        }); */

        // Send a message directly
        // Message message = new Message(); // it is better to use the following line:
        Message message = Message.obtain(); // recycles old messages under the hood
        message.what = TASK_A; // .what indicates what type of action we want to execute (integer)
        looperThread.handler.sendMessage(message); // .sendMessage instead of .post here

        /*Handler threadHandler = new Handler(looperThread.looper);
        threadHandler.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    Log.d(TAG, "run: " + i);
                    SystemClock.sleep(1000);
                }
            }
        });*/
    }

    public void taskB(View view) {
        Message message = Message.obtain(); // recycles old messages under the hood
        message.what = TASK_B; // .what indicates what type of action we want to execute (integer)
        looperThread.handler.sendMessage(message); // .sendMessage instead of .post here

    }
}