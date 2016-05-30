package com.ser.jar;

import com.ser.file.TempFileManager;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by Brad
 * on 5/29/2016.
 */
public class Loader {
    public boolean load(String path) throws IllegalAccessException, IOException, InvocationTargetException {
        if(path.endsWith(".jar")){
            return loadJar(path);
        } else{
            return loadDirectory(path);
        }
    }

    public boolean loadDirectory(String path) throws IOException, InvocationTargetException, IllegalAccessException {
        File directory = new File(path);
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File directory, String fileName) {
                return fileName.endsWith(".jar");
            }
        };
        System.out.println(String.format("In directory %s", directory.getPath()));
        for(File file : directory.listFiles()){
            System.out.println(file.getName());
        }
        if(directory.exists()){
            List<URL> jarUrls = new ArrayList<>();
            File[] jarFiles = directory.listFiles(filter);
            System.out.println(String.format("Found %s jar files", jarFiles.length));
            for(File file : jarFiles){
                Path tempPath = new TempFileManager().copyToTemp("AI-LOADER", file);
                URL fileURL = tempPath.toFile().toURI().toURL();
                String jarURL = "jar:" + fileURL + "!/";
                jarUrls.add(new URL(jarURL));
            }
            URL[] urls = jarUrls.toArray(new URL[jarUrls.size()]);
            URLClassLoader ucl = new URLClassLoader(urls, this.getClass().getClassLoader());
            List<Object> objects = new ArrayList<>();
            for(File file : jarFiles){
                JarFile jarFile = new JarFile(file);
                java.util.Enumeration<JarEntry> entries = jarFile.entries();
                while(entries.hasMoreElements()){
                    JarEntry entry = entries.nextElement();
                    if(entry.getName().endsWith(".class")){
                        Object o;
                        try{
                            o = ucl.loadClass(entry.getName().replaceAll("/", ".").replace(".class", "")).newInstance();
                            System.out.println(o.getClass());
                            if(o instanceof RunnableJar) {
                                objects.add(o);
                            }
                        } catch(Exception e){

                        }
                    }
                }
            }
            System.out.println(String.format("Found %s runnable jars", objects.size()));
            for(Object o : objects){
                Method[] methods = o.getClass().getMethods();
                for(Method method : methods){
                    if(method.getName().equals("run")){
                        method.invoke(o);
                    }
                }
            }
        }
        return false;
    }

    public boolean loadJar(String path) throws IOException, InvocationTargetException, IllegalAccessException {
        File jarFile = new File(path);
        if (jarFile.exists()) {
            //Copy to tmp file location
            //
            Path tempPath = new TempFileManager().copyToTemp("AI-LOADER", jarFile);
            URL fileURL = tempPath.toFile().toURI().toURL();
            String jarURL = "jar:" + fileURL + "!/";
            URL urls[] = {new URL(jarURL)};
            URLClassLoader ucl = new URLClassLoader(urls, this.getClass().getClassLoader());
            JarFile asdfa = new JarFile(jarFile);
            java.util.Enumeration<JarEntry> entries = asdfa.entries();
            List<Object> objects = new ArrayList<>();
            while(entries.hasMoreElements()){
                JarEntry entry = entries.nextElement();
                if(entry.getName().endsWith(".class")){
                    Object o = null;
                    try{
                        o = ucl.loadClass(entry.getName().replaceAll("/", ".").replace(".class", "")).newInstance();
                        if(o instanceof RunnableJar) {
                            objects.add(o);
                        }
                    } catch(Exception e){

                    }
                }
            }
//            Object o = null;
//            try{
//                o = ucl.loadClass("com.ser.test.TestJar").newInstance();
//            } catch(Exception e){
//                e.printStackTrace();
//                System.out.println("CAN'T LOAD");
//            }
//            if(o != null) {
//                Method[] methods = o.getClass().getMethods();
//                for(Method method : methods){
//                    if(method.getName().equals("run")){
//                        method.invoke(o);
//                    }
//                }
//                return true;
//            }
            for(Object o : objects){
                Method[] methods = o.getClass().getMethods();
                for(Method method : methods){
                    if(method.getName().equals("run")){
                        method.invoke(o);
                    }
                }
            }
        }
        return false;
    }
}