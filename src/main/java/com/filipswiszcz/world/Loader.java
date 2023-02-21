package com.filipswiszcz.world;

import java.util.HashMap;
import java.util.Map;

import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.AnvilLoader;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.generator.Generator;

public final class Loader {

    private final Map<String, Instance> worlds = new HashMap<>();

    public Loader() {
        this.setWorld();
    }

    public Instance getWorld(String name) {
        return worlds.get(name);
    }

    public void setWorld() {
        final Instance world = MinecraftServer.getInstanceManager()
            .createInstanceContainer(new AnvilLoader("worlds/earth"));
        world.setGenerator(new Earth());
        this.worlds.put("earth", world);
    }

    public void setWorld(String name) {
        final Instance world = MinecraftServer
            .getInstanceManager().createInstanceContainer();
        this.worlds.put(name, world);
    }

    public void setWorld(String name, Generator generator) {
        final Instance world = MinecraftServer.
            getInstanceManager().createInstanceContainer();
        world.setGenerator(generator);
        this.worlds.put(name, world);
    }
    
}
