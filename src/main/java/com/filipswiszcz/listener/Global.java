package com.filipswiszcz.listener;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import com.filipswiszcz.Server;
import com.filipswiszcz.entity.User;
import com.filipswiszcz.entity.User.Language;
import com.filipswiszcz.entity.User.Rank;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.MinecraftServer;
import net.minestom.server.advancements.FrameType;
import net.minestom.server.advancements.notifications.Notification;
import net.minestom.server.advancements.notifications.NotificationCenter;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerPreLoginEvent;
import net.minestom.server.event.player.PlayerChatEvent;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.server.ServerListPingEvent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

public final class Global {

    private final GlobalEventHandler handler;

    public Global() {
        this.handler = MinecraftServer.getGlobalEventHandler();
        this.initialize();
    }

    private void initialize() {
        try {
            this.setDescription();
            //this.setConnection();
            this.setLogin();
            this.setChat();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void setDescription() throws IOException {
        final BufferedImage reader = ImageIO.read(new File("logo.png"));
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(reader, "png", output);
        final String logo = Base64.getEncoder().encodeToString(output.toByteArray());
        MinecraftServer.getGlobalEventHandler().addListener(ServerListPingEvent.class, event -> {
            event.getResponseData().setMaxPlayer(100);
            event.getResponseData().setFavicon("data:image/png;base64," + logo);
            event.getResponseData()
                .setDescription(
                    Component.text("")
                    .append(Component.text("MCSU.PL",TextColor.color(155, 89, 182)))
                        .decoration(TextDecoration.BOLD, true)
                    .append(Component.text(" - ", TextColor.color(236, 240, 241)))
                        .decoration(TextDecoration.BOLD, false)
                    .append(Component.text("[1.19.3]", TextColor.color(189, 195, 199)))
                        .decoration(TextDecoration.BOLD, false)
                );
        });
    }

    private void setConnection() {
        this.handler.addListener(AsyncPlayerPreLoginEvent.class, event -> {
            if (!Server.getLoader().getWorld("earth").isChunkLoaded(new Pos(0, 200, 0))) {
                Server.getLoader().getWorld("earth").loadChunk(new Pos(0, 200, 0));
                event.getPlayer().getPlayerConnection().disconnect();
            } else return;
        });
    }

    private void setLogin() {
        this.handler.addListener(PlayerLoginEvent.class, event -> {
            User user = new User(event.getPlayer(), Language.ENGLISH, Rank.DEV);
            Server.getMemory().addUser(user);
            /*if (event.getPlayer().getPlayerConnection()
                .getServerAddress().equalsIgnoreCase("mcsu.pl")) 
                    user.setLanguage(Language.ENGLISH);*/ // change later, add subdomains
            event.setSpawningInstance(Server.getLoader().getWorld("earth"));
            event.getPlayer().setRespawnPoint(new Pos(0, 200, 0));
            event.getPlayer().setGameMode(GameMode.CREATIVE);
            switch (user.getLanguage()) {
                case ENGLISH -> {
                    final Notification connected = new Notification(
                        Component.text("Connected!", NamedTextColor.GREEN),
                        FrameType.GOAL, 
                        ItemStack.of(Material.REDSTONE));
                    NotificationCenter.send(connected, event.getPlayer());
                }
                case POLISH -> {
                    final Notification connected = new Notification(
                        Component.text("Połączono!", NamedTextColor.GREEN),
                        FrameType.GOAL,
                        ItemStack.of(Material.REDSTONE));
                    NotificationCenter.send(connected, event.getPlayer());        
                }
            }
        });
    }

    private void setChat() {
        this.handler.addListener(PlayerChatEvent.class, event -> {
            event.setChatFormat(format -> {
                switch (Server.getMemory().getUser(event.getPlayer().getName()).getRank()) {
                    case DEV -> {
                        return Component.text("<dev> ", TextColor.fromHexString("#bdc3c7"))
                            .append(Component.text(format.getPlayer().getUsername(), TextColor.fromHexString("#8e44ad")))
                            .append(Component.text(" -> ", TextColor.fromHexString("#9b59b6")))
                            .append(Component.text(format.getMessage(), TextColor.fromHexString("#ecf0f1")));
                    }
                    case ROOT -> {
                        return Component.text("<właściciel> ", TextColor.fromHexString("#bdc3c7"))
                            .append(Component.text(format.getPlayer().getUsername(), TextColor.fromHexString("#c0392b")))
                            .append(Component.text(" -> ", TextColor.fromHexString("#e74c3c")))
                            .append(Component.text(format.getMessage(), TextColor.fromHexString("#ecf0f1")));
                    }
                    default -> {
                        return Component.text(format.getPlayer().getUsername(),TextColor.fromHexString("#16a085"))
                            .append(Component.text(" -> ", TextColor.fromHexString("#1abc9c")))
                            .append(Component.text(format.getMessage(), TextColor.fromHexString("#ecf0f1")));
                    }
                }
            });
        });
    }
    
}