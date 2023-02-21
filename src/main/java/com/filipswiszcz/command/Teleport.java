package com.filipswiszcz.command;

import com.filipswiszcz.Server;

import net.kyori.adventure.text.Component;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.condition.Conditions;
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
import net.minestom.server.entity.Player;

public class Teleport extends Command {

    public Teleport() {
        super("teleportacja");
        this.setCondition(Conditions::playerOnly);
        this.setDefaultExecutor(this::executor);
        var value = ArgumentType.Integer("name").between(0, 2);
        

        var argument = ArgumentType.String("name");
        argument.setCallback(this::callback);
        addSyntax(this::syntax);
    }

    private void executor(CommandSender sender, CommandContext context) {
        if (!(sender instanceof Player player)) 
            sender.sendMessage(Component.text("Uruchom polecenie w grze!"));
        else sender.sendActionBar(Component.text("/teleportacja <świat>"));
    }

    private void syntax(CommandSender sender, CommandContext context) {
        final String world = context.getInput();
        Player player = (Player) sender;
        if (world.equalsIgnoreCase("earth")) 
            player.setInstance(Server.getLoader().getWorld("earth"));
        else player.setInstance(Server.getLoader().getWorld("nether"));
    }

    private void callback(CommandSender sender, ArgumentSyntaxException exception) {
        final String input = exception.getInput();
        sender.sendMessage(Component.text("Świat " + input + " nie istnieje!")); return;
    }
    
}
