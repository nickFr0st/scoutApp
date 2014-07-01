package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Created by Malloch on 6/29/14
 */
public class Util {
    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static void saveProperties(Properties properties, String propertyFileName) {
        try {
            File f = new File(propertyFileName.substring(propertyFileName.indexOf("C")));
            OutputStream out = new FileOutputStream(f);
            properties.store(out, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
