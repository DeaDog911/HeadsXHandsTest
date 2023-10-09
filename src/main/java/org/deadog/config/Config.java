package org.deadog.config;

import org.deadog.utils.PropertiesReader;

public class Config {
    public final int M;
    public final int N;
    private static Config instance = null;
    private Config() {
        M = Integer.parseInt(PropertiesReader.getProperty("M"));
        N = Integer.parseInt(PropertiesReader.getProperty("N"));
    }
    public static Config getInstance() {
        if (instance == null) {
            return new Config();
        }
        return instance;
    }
}
