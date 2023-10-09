package org.deadog.utils;

import org.deadog.exceptions.PropertyException;

public class PropertiesReader {
    public static void configure(String filename) {
        try {
            System.getProperties().load(ClassLoader.getSystemResourceAsStream(filename));
        } catch (Exception e) {
            throw new PropertyException();
        }
    }

    public static String getProperty(String property) {
        return System.getProperty(property);
    }
}
