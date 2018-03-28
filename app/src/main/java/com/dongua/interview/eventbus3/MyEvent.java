package com.dongua.interview.eventbus3;

/**
 * author: Lewis
 * date: On 18-3-12.
 */

public class MyEvent {
    private String message;

    public MyEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
