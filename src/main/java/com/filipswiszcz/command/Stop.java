package com.filipswiszcz.command;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;

public final class Stop extends Command {

    public Stop() {
        super("stop", "zatrzymaj");
        addSyntax(this::execute);
    }

    private void execute(CommandSender sender, CommandContext context) {
        /*for (Instance instance : MinecraftServer.getInstanceManager().getInstances()) {
            instance.saveInstance().thenCompose(i -> instance.saveChunksToStorage());
        }
        sender.sendMessage(
            Component.text("Ukończono zapisywanie!"));*/
        MinecraftServer.stopCleanly();
    }
    
}
