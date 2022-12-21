package com.cgvsu.math.exception;

public class MathExceptions extends IllegalArgumentException{
    public MathExceptions(){
        super("Error in values!");
    }

    public MathExceptions(String message){
        super(message);
    }
}
