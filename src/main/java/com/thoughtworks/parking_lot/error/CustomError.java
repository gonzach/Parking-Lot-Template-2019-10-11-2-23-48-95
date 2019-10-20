package com.thoughtworks.parking_lot.error;

public class CustomError {

    private int code;

    public String getMessage() {
        return message;
    }

    private String message;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
