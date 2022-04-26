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