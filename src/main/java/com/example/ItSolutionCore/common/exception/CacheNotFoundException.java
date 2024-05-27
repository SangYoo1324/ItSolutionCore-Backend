package com.example.ItSolutionCore.common.exception;

public class CacheNotFoundException extends Exception{
    public CacheNotFoundException(String message){
        super(message);
    }


    public void printStackTrace() {
        super.printStackTrace();
        System.out.println("Cache Not found");
    }
}
