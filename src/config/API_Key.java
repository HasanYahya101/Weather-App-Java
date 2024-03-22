package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class API_Key {
    public static String getAPIKey() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config.properties"));
            return properties.getProperty("api.key");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getGeoLocAPIKey() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config.properties"));
            return properties.getProperty("geolocation.api");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}