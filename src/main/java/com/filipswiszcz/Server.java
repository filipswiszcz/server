package com.filipswiszcz;

import com.filipswiszcz.command.Mode;
import com.filipswiszcz.command.Stop;
import com.filipswiszcz.listener.Global;
import com.filipswiszcz.world.Loader;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;

/*
 * TO DO
 * - language support (en, pl)
 * - book welcome
 * - regions
 * OBJECTIVES
 * - use more books and npcs to guide players
 * FEATURES
 * - players can give job to other player/players
 */
public final class Server {

    public static Loader loader;
    public static Global global;

    public static void main(String[] args) {
        final MinecraftServer server = MinecraftServer.init();
        loader = new Loader();
        global = new Global();
        final CommandManager commands = MinecraftServer.getCommandManager();
        commands.register(new Mode());
        commands.register(new Stop());
        server.start("146.59.126.33", 25565);
    }

    public static Loader getLoader() {
        return loader;
    }

    public static Global getGlobal() {
        return global;
    }

}
