package com.filipswiszcz.security;

public class Permission {

    private final String name;
    private int value;

    public Permission(String name) {
        this(name, 1);
    }

    public Permission(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    
}
