package com.example.snacklearn.config;

import android.content.Context;
import android.content.res.AssetManager;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
    private static AppConfig instance;
    private Properties properties;

    private AppConfig(Context context) {
        properties = new Properties();
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("config.properties");
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized AppConfig getInstance(Context context) {
        if (instance == null) {
            instance = new AppConfig(context);
        }
        return instance;
    }

    public String getImageBaseUrl() {
        return properties.getProperty("image.base.url", "https://www.example.com/images/");
    }

    public String getDatabaseName() {
        return properties.getProperty("database.name", "snacks.db");
    }

    public int getDatabaseVersion() {
        return Integer.parseInt(properties.getProperty("database.version", "1"));
    }

    public int getPageSize() {
        return Integer.parseInt(properties.getProperty("page.size", "10"));
    }
}