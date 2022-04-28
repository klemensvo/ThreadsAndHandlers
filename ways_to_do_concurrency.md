# Ways to do concurrency

## Thread
class ExampleThread extends Thread {
    @Override
    public void run() {
        // do long running task
    }
}

Call in MainActivity:
public void startThread(View view) {
    ExampleThread exampleThread = new ExampleThread();
    exampleThread.start();
}

# Runnable
remark: preferred way to do it compared to Thread (Thread-calls implement the Runnable interface)
first implement the Runnable interface and then start it on a new thread with new Thread(runnable).start();

in top of Main Activity:
private Handler mainHandler = new Handler(); // not native Java, but from Android

in Runnable:
class ExampleRunnable implements Runnable {
        int seconds;
        ExampleRunnable (int seconds) {
            this.seconds = seconds;
        }
        @Override
        public void run() {
            for (int i = 0; i < seconds; i++) { // do long running task
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
    
Call in MainActivity:
public void startThread(View view) {
    ExampleRunnable exampleRunnable = new ExampleRunnable();
    new Thread(exampleRunnable).start();
}

# Handler
A handler is here to put work into a message queue, must be instantiated in the main thread
A Handler can only be called, when a Looper has been installed previously: 
Looper.prepare(); // creates a looper and a message queue (those 2 are tied together!)
after Looper.prepare() the handler is instantiated with:
handler = new Handler();
and then the looper starts the infinite for-loop:
Looper.loop();


Override
    public void run() {
        for (int i = 0; i < seconds; i++) {
            if (i == 5) {
                mainHandler.post(new Runnable() { // a runnable doesn't start a new thread,
                                                  // instead it provides new work to be done
                                                  // .post() calls from the main thread
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

# Looper
instead, instead of mainHandler created in MainActivity, we can create a threadHandler in the Runnable, by passing Looper.getMainLooper() to the new Handler

Handler threadHandler = new Handler(Looper.getMainLooper());
                    threadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            buttonStartThread.setText("50%");
                        }
                    });
                    
 # .post() from View class
 this is a built-in function that posts new work to the main thread (where the View was instantiated
 
 in MainActivity:
 private Button buttonStartThread;
 buttonStartThread = findViewById(R.id.button_start_thread);

 in Runnable:
 buttonStartThread.post(new Runnable() { // convenience method of the View class
                                         // buttonStartThread is a View
         @Override
         public void run() {
              buttonStartThread.setText("50%");
         }
 });
 
 # runOnUiThread
 runOnUiThread(new Runnable() { // runOnUiThread is an activity method
                                // can be called directly       
                                // doesn't need any variable before it
      @Override
      public void run() {
           buttonStartThread.setText("50%");
      }
});

# Anonymous Runnable

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
}).start();

# Messages
Difference:
* Runnable sends work to do to the message queue
* Message sends raw data to the message queue (must be interpreted)

# HandlerThread
creates a thread, a looper, a messagequeue and a handler for convenience

# IntentService
uses HandlerThread, but stops as soon as no more work is there to be done
