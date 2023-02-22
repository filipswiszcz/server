package com.filipswiszcz.command;

import java.util.Locale;

import com.filipswiszcz.Server;

import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentEnum;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.ArgumentEnum.Format;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;

public final class Mode extends Command {

    public Mode() {
        super("tryb");
        final ArgumentEnum<GameMode> mode = ArgumentType.Enum("gamemode", GameMode.class).setFormat(Format.LOWER_CASED);
        mode.setCallback((sender, exception) -> {
            sender.sendActionBar(
                Component.text("Tryb niedostępny!", NamedTextColor.RED));
        });
        //final ArgumentEntity entity = ArgumentType.Entity("targets").onlyPlayers(true);
        addSyntax((sender, context) -> {
            if (!(sender instanceof Player player)) {
                sender.sendMessage
                (Component.text("Uruchom polecenie w grze!", NamedTextColor.RED));
                return;
            }
            execute(player, context.get(mode));
        }, mode);
    }

    private void execute(Player sender, GameMode mode) {
        sender.setGameMode(mode);
        String gamemodeString = "gameMode." + mode.name().toLowerCase(Locale.ROOT);
        Component gamemodeComponent = Component.translatable(gamemodeString);
        sender.sendMessage(Server.getMemory().getUser(sender.getName()).getName());

        //Send the translated message to the player.
        sender.sendMessage(Component.translatable("commands.gamemode.success.self", gamemodeComponent), MessageType.SYSTEM);
    }
    
}
