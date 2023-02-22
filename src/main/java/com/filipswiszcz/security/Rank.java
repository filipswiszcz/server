package com.filipswiszcz.security;

//import java.util.Collection;

public final class Rank {

    private final Type type;

    //private final Collection<Permission> permissions = new ArrayList<>(); // set would be probably better

    public Rank(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    /*public Collection<Permission> getPermissions() {
        return permissions;
    }

    public boolean hasPermission(Permission permission) {
        return permissions.contains(permission);
    }

    public void addPermission(Permission permission) {
        if (this.hasPermission(permission)) return;
        this.permissions.add(permission);
    }*/

    public enum Type {

        USER("user", 1),
        VIP("vip", 2),
        MOD("mod", 3),
        OWNER("root", 4),
        DEV("dev", 5);

        private final String name;
        private final int admittance;

        Type(String name, int admittance) {
            this.name = name;
            this.admittance = admittance;
        }

        public String getName() {
            return name;
        }

        public int getAdmittance() {
            return admittance;
        }

    }
    
}
