package org.deadog.exceptions;

public class PropertyException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Unable to read properties";
    }
}
