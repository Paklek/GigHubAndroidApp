package com.gighub.app.model;

/**
 * Created by Paklek on 7/30/2016.
 */
public class Response {

    private String status;
    private int error;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }
}
