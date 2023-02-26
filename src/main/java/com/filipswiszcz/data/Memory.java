package com.filipswiszcz.data;

import java.util.Collection;
import java.util.HashSet;

import com.filipswiszcz.entity.User;

import net.kyori.adventure.text.Component;

public final class Memory {

    private final Collection<User> users = new HashSet<>();

    public Memory() {}

    public Collection<User> getUsers() {
        return users;   
    }
    
    public User getUser(Component name) {
       return users.stream()
            .filter(user -> user.getChild().getName().equals(name)
                 && user.getChild().isActive()).findAny().get();
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void removeUser(User user) {
        this.users.remove(user);
    }

}
