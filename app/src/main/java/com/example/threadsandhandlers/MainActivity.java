package com.example.threadsandhandlers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button buttonStartThread;
    private volatile boolean stopThread = false; // volatile always gets the current value, no cached value
    private Handler mainHandler = new Handler(); // not native Java, but from Android
    // a handler is here to put messages into the messageQueue
    // instantiated in the thread where is was called, here in MainActivity being the main thread

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonStartThread = findViewById(R.id.button_start_thread);

    }

    /*
    public void startThread(View view) {
        ExampleThread exampleThread = new ExampleThread(8);
        exampleThread.start();
    } */

    public void startThread(View view) {
        stopThread = false;
        ExampleRunnable exampleRunnable = new ExampleRunnable(10);
        new Thread(exampleRunnable).start();
        /*
        new Thread(new Runnable() { // anonymous call of Runnable
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    if (i == 4) {
                        buttonStartThread.post(new Runnable() {
                            @Override
                            public void run() {
                                buttonStartThread.setText("40%");
                            }

                        });

                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start(); */

    }

    public void stopThread(View view) {
        stopThread = true;
    }

    class ExampleThread extends Thread { // preferred way: Runnable, see below

        int seconds;
        ExampleThread(int seconds) {
            this.seconds = seconds;
        }

        @Override
        public void run() {
            for (int i = 0; i < seconds; i++) {
                Log.d(TAG, "startThread: " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class ExampleRunnable implements Runnable {
        int seconds;
        ExampleRunnable (int seconds) {
            this.seconds = seconds;
        }
        @Override
        public void run() {
            for (int i = 0; i < seconds; i++) {
                if (stopThread)
                    return; // will stop this thread, it's work is done
                if (i == 5) {
                    /*
                    mainHandler.post(new Runnable() { // a runnable doesn't start a new thread,
                                                      // instead it provides new work to be done
                                                      // .post() calls from the main thread
                        */
                    /*
                    Handler threadHandler = new Handler(Looper.getMainLooper());
                    threadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            buttonStartThread.setText("50%");
                        }
                    }); */
                    /*
                    buttonStartThread.post(new Runnable() { // convenience method of the View class
                                                            // buttonStartThread is a View
                        @Override
                        public void run() {
                            buttonStartThread.setText("50%");
                        }
                    }); */
                    runOnUiThread(new Runnable() { // runOnUiThread is an activity method
                                            // can be called directly
                                            // doesn't need any variable before it
                        @Override
                        public void run() {
                            buttonStartThread.setText("50%");
                        }
                    });
                }
                Log.d(TAG, "startThread: " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}