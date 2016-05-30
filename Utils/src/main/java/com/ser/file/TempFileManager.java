package com.ser.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Created by Brad
 * on 5/30/2016.
 */
public class TempFileManager {
    public static String TEMP_DIRECTORY = System.getProperty("java.io.tmpdir");

    public Path copyToTemp(String directory, File file) throws IOException {
        File tempFile = new File(String.format("%s/%s/%s/%s", TEMP_DIRECTORY, directory, System.currentTimeMillis(), file.getName()));
        if(tempFile.exists() || tempFile.mkdirs()){
            return Files.copy(file.toPath(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        throw new IOException(String.format("Could not create a temp copy of %s", file.getName()));
    }
}
