package com.ser.test;

/**
 * Created by brad
 * on 5/29/2016.
 */
public class TestJar {
    public void run(){
        System.out.println("FOO2");
        new TestJar2().test();
    }

    public static void main(String[] args){
        System.out.println("BAR");
    }
}
