package com.gighub.app.model;

/**
 * Created by Paklek on 7/30/2016.
 */
public class Response {

    private String message;

    private int error;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
