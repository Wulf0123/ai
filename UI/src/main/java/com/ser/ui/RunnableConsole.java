package com.ser.ui;

import com.ser.Testing;
import com.ser.jar.Loader;
import com.ser.jar.RunnableJar;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

/**
 * Created by Brad
 * on 5/30/2016.
 */
public class RunnableConsole implements RunnableJar {
    private Scanner console = new Scanner(System.in);

    static String endCommand = "quit"; //TODO allow this to be set

    @Override
    public void run() {
        System.out.println("Input commands now...");
        Testing testing = new Testing();
        testing.run();
        String input;
        System.out.println("BYE");
        do {
            System.out.println("HII");
            input = console.nextLine();
            if(input.startsWith("load")){
                String[] tokens = input.split("=");
                if(tokens.length == 2){
                    Loader loader = new Loader();
                    try {
                        System.out.println(String.format("Loading %s...", tokens[1]));
                        loader.load(tokens[1]);
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }
            }
        } while(input != null && !input.equalsIgnoreCase(endCommand));
        console.close();
    }

    @Override
    public void shutdown() {
        console.close();
    }
}
