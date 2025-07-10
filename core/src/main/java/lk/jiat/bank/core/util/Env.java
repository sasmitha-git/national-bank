package lk.jiat.bank.core.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Env {

    private static final Properties properties = new Properties();
    static {
        try{
            InputStream input =  Env.class.getClassLoader().getResourceAsStream("application.properties");
            properties.load(input);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static double getDouble(String key) {
        return Double.parseDouble(get(key));
    }
}
