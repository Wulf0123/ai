package com.ser.jar;

/**
 * Created by Brad
 * on 5/30/2016.
 */
public interface RunnableJar extends Runnable {
    @Override
    void run();
    void shutdown();
}
