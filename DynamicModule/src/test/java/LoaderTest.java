import com.ser.jar.Loader;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Brad
 * on 5/29/2016.
 */
public class LoaderTest {
    @Ignore
    @Test
    public void test() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, InterruptedException {
        Loader loader = new Loader();
        while(true) {
            loader.loadJar("C:\\Users\\bmr01\\Documents\\GitHub\\ai\\TestJar\\build\\libs/TestJar-1.0-SNAPSHOT.jar");
            Thread.sleep(100);
        }
    }
}
