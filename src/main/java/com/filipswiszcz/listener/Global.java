package com.filipswiszcz.listener;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

import javax.imageio.ImageIO;

import com.filipswiszcz.Server;
import com.filipswiszcz.entity.User;
import com.filipswiszcz.entity.User.Language;

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
            this.setConnection();
            this.setLogin();
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
                    .appendNewline()
                    .append(Component.text("        v", TextColor.color(236, 240, 241)))
                        .decoration(TextDecoration.BOLD, false)
                    .append(Component.text(" - ", TextColor.color(236, 240, 241)))
                        .decoration(TextDecoration.BOLD, false)
                    .append(Component.text("0.0.1", TextColor.color(236, 240, 241)))
                        .decoration(TextDecoration.BOLD, false)
                );
        });
    }

    private void setConnection() {
        this.handler.addListener(AsyncPlayerPreLoginEvent.class, event -> {
            if (!Server.getLoader().getWorld("earth").isChunkLoaded(new Pos(0, 200, 0))) {
                event.getPlayer().getPlayerConnection().disconnect();
                Server.getLoader().getWorld("earth").loadChunk(new Pos(0, 200, 0));
            } else return;
        });
    }

    private void setLogin() {
        int random = new Random().nextInt(100);
        this.handler.addListener(PlayerLoginEvent.class, event -> {
            User user = new User(event.getPlayer().getUuid(), event.getPlayer().getName().toString(), event.getPlayer().getPlayerConnection());
            /*if (event.getPlayer().getPlayerConnection()
                .getServerAddress().equalsIgnoreCase("mcsu.pl")) 
                    user.setLanguage(Language.ENGLISH);*/ // change later, add subdomains
            event.setSpawningInstance(Server.getLoader().getWorld("earth"));
            event.getPlayer().setRespawnPoint(new Pos(0, 200, 0));
            event.getPlayer().setGameMode(GameMode.CREATIVE);
            if (user.getLanguage().equals(Language.ENGLISH)) {
                final Notification connected = new Notification(
                    Component.text("Connected!", NamedTextColor.GREEN),
                    FrameType.GOAL, 
                    ItemStack.of(Material.REDSTONE));
                NotificationCenter.send(connected, event.getPlayer());
            } else {
                final Notification connected = new Notification(
                    Component.text("Połączono!", NamedTextColor.GREEN),
                    FrameType.GOAL,
                    ItemStack.of(Material.REDSTONE));
                NotificationCenter.send(connected, event.getPlayer());
            }
            event.getPlayer().sendMessage(user.getRankType().getName());
        });
    }
    
}
