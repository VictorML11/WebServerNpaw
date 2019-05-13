package me.victorml.npaw.logic;

public enum StatusCode {

    OK("200 OK"),
    NOT_FOUND("404 Not Found"),
    NOT_IMPLEMENTED("501 Not Implemented");

    private String info;

    StatusCode(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
