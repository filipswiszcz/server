package com.filipswiszcz.security;

import java.util.ArrayList;
import java.util.Collection;

import com.filipswiszcz.entity.User;

public final class Rank {

    private final Type type;

    private final Collection<Permission> permissions = new ArrayList<>();

    private boolean capacity;
    private int size;
    private final Collection<User> members = new ArrayList<>();

    public Rank(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public Collection<Permission> getPermissions() {
        return permissions;
    }

    public boolean hasPermission(Permission permission) {
        return permissions.contains(permission);
    }

    public void addPermission(Permission permission) {
        if (this.hasPermission(permission)) return;
        this.permissions.add(permission);
    }

    public boolean hasCapacity() {
        return capacity;
    }

    public void setCapacity(boolean capacity) {
        this.capacity = capacity;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        if (!this.hasCapacity()) return;
        this.size = size;
    }

    public Collection<User> getMembers() {
        return members;
    }

    public boolean hasMember(User user) {
        return members.contains(user);
    }

    public void addMember(User user) {
        if (!this.hasCapacity()) {
            if (!this.hasMember(user)) this.addMember(user);
            else return;
        } else {
            if (this.getMembers().size() >= this.getSize()) return;
            else this.addMember(user);
        }

    }

    public enum Type {

        USER("user"),
        VIP("vip"),
        MOD("mod"),
        OWNER("owner"),
        DEV("dev");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }
    
}
