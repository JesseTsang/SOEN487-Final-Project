package soen487.util;

import java.io.File;

/**
 * This is an utility class for locating a File
 * @author Ethan Shen
 */
public class FileLocator {
    
    private static final String USER_HOME = System.getProperty("user.home");
    private static final String RESOURCE_FOLDER = "/soen487-w18-team07/src/main/resources/";
    
    /**
     * This method returns a file locate under /src/main/resources/ + filePath
     * @param filePath
     * @return 
     */
    public static File getFile(String filePath){
        return new File(USER_HOME + RESOURCE_FOLDER + filePath);
    }
}
