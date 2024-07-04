package com.ihclweb.automation.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

/**
 * 
 * @author rajeevsai.t
 *
 */
public class ZipUtils {
	
	 private static final Logger LOG = Logger.getLogger(ZipUtils.class.getName());
	 private static final String IO_ERR_MSG = "Error occurred while read/write operation.";
    
	 private ZipUtils() {}
    
    public static void zipDirectory(Path srcDir, Path destFile) {
        try(ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(destFile.toFile()))) {
        	List<String> files = new ArrayList<>();
            populateFilesList(srcDir, files);
            for(String file : files){
                ZipEntry zipEntry = new ZipEntry(file.substring(srcDir.toFile().getAbsolutePath().length() + 1, file.length()));
                zipOutputStream.putNextEntry(zipEntry);
                try(FileInputStream inputStream = new FileInputStream(file)){
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = inputStream.read(buffer)) > 0) {
                    	zipOutputStream.write(buffer, 0, len);
                    }
                    zipOutputStream.closeEntry();
                }
            }
        } catch (IOException e) {
            LOG.error(IO_ERR_MSG, e);
        }
    }
    
    private static void populateFilesList(Path path, List<String> paths) throws IOException {
        File[] files = path.toFile().listFiles();
        for(File file : files){
            if(file.isFile()) {
            	paths.add(file.getAbsolutePath());
            }else {
            	populateFilesList(file.toPath(), paths);
            }
        }
    }

    public static void zipFile(File file, String zipFileName) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(zipFileName);
        		ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
                FileInputStream fileInputStream = new FileInputStream(file);){
            ZipEntry ze = new ZipEntry(file.getName());
            zipOutputStream.putNextEntry(ze);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fileInputStream.read(buffer)) > 0) {
            	zipOutputStream.write(buffer, 0, len);
            }
            zipOutputStream.closeEntry();
        } catch (IOException e) {
         LOG.error(IO_ERR_MSG, e);
        }
    }
}
