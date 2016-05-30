import com.ser.jar.Loader;

import java.io.File;

/**
 * Created by Brad
 * on 5/30/2016.
 */
public class Main {
    public static void main(String[] args){
        Loader loader = new Loader();
        File directory = new File(System.getProperty("directory", "."));
        try{
            loader.load(directory.getPath());
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
