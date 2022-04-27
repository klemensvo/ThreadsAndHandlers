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
