package com.example.ItSolutionCore.common.exception;

public class DataNotFoundException extends Exception{
    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(Throwable cause) {
        super(cause);
    }

    public void printStackTrace() {
        super.printStackTrace();
        System.out.println("Data Not found");
    }
}
