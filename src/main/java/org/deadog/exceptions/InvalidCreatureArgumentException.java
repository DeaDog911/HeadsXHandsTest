package org.deadog.exceptions;

public class InvalidCreatureArgumentException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Атака и Защита должны быть в пределах от 1 до 30";
    }
}
