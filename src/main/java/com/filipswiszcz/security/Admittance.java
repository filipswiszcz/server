package com.filipswiszcz.security;

public enum Admittance {
    
    USER(1),
    VIP(2),
    MOD(3),
    OWNER(4),
    DEV(5);

    private final int level;

    Admittance(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

}
