package com.ser.log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Brad
 * on 5/30/2016.
 */
public class Logger {
    private String className;
    private static Map<String, Logger> loggers = new HashMap<>();

    private Logger(){

    }

    private Logger(String className){
        this.className = className;
    }

    public static Logger getInstance(Class callingClass){
        String name = callingClass.getName();
        synchronized (loggers){
            if(loggers.containsKey(name)){
                return loggers.get(name);
            } else {
                Logger logger = new Logger(name);
                loggers.put(name, logger);
                return logger;
            }
        }
    }

    public void log(LogLevel level, String message, Throwable exception){

    }

}

enum LogLevel{
    INFO,
    WARN,
    DEBUG,
    FATAL;
}