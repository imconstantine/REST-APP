package com.imconstantine.netexam;

public class ThreadTester {

    private Thread thread;
    private volatile AssertionError exc;
    private volatile Exception exception;

    public ThreadTester(final Runnable runnable) {
        thread = new Thread(() -> {
            try {
                runnable.run();
            } catch (AssertionError e) {
                exc = e;
            } catch (Exception e) {
                exception = e;
            }
        });
    }

    public void start() {
        thread.start();
    }

    public void test() throws Exception {
        thread.join();
        if (exc != null) {
            throw exc;
        }
        if (exception != null) {
            throw exception;
        }
    }
}
