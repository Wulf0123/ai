package com.ser.jar;

import com.ser.file.TempFileManager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;

/**
 * Created by Brad
 * on 5/29/2016.
 */
public class Loader {

    public boolean load(String path) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        File jarFile = new File(path);
        if (jarFile.exists()) {
            //Copy to tmp file location
            //
            Path tempPath = new TempFileManager().copyToTemp("AI-LOADER", jarFile);
            URL fileURL = tempPath.toFile().toURI().toURL();
            String jarURL = "jar:" + fileURL + "!/";
            URL urls[] = {new URL(jarURL)};
            URLClassLoader ucl = new URLClassLoader(urls, this.getClass().getClassLoader());
            ucl.clearAssertionStatus();
            Object o = null;
            try{
                o = ucl.loadClass("com.ser.test.TestJar").newInstance();
            } catch(Exception e){
                e.printStackTrace();
                System.out.println("CAN'T LOAD");
            }
//            URLClassLoader loader = (URLClassLoader)ClassLoader.getSystemClassLoader();
//            DynamicJarLoader dynamicLoader = new DynamicJarLoader(loader.getURLs(), loader);
//            dynamicLoader.addURL(fileURL);
//            try {
//                dynamicLoader.loadClass("com.ser.test.TestJar");
//            }catch(Exception e){
//                System.out.println("CAN'T LOAD");
//            }
//
//            o = this.getClass().getClassLoader().loadClass("com.ser.test.TestJar").newInstance();
            if(o != null) {
                Method[] methods = o.getClass().getMethods();
                for(Method method : methods){
                    if(method.getName().equals("run")){
                        method.invoke(o);
                    }
                }
                return true;
            }
        }
        return false;
    }
}