package com.example.ItSolutionCore.common.exception;

public class DupeException extends Exception{
    public DupeException(String message) {
        super(message);
    }

    public DupeException(Throwable cause) {
        super(cause);
    }

    public void printStackTrace() {
        super.printStackTrace();
        System.out.println("Dupe Exception");
    }
}
