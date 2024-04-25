package org.abyssdev.voteparty.commands;

import net.abyssdev.abysslib.command.AbyssCommand;
import net.abyssdev.abysslib.command.context.CommandContext;
import org.abyssdev.voteparty.AbyssVoteParty;
import org.abyssdev.voteparty.commands.subs.AddVoteCommand;
import org.abyssdev.voteparty.commands.subs.StartCommand;
import org.bukkit.command.CommandSender;

public class VotePartyAdminCommand extends AbyssCommand<AbyssVoteParty, CommandSender> {

    public VotePartyAdminCommand(final AbyssVoteParty plugin) {
        super(plugin, "votepartyadmin", CommandSender.class);

        this.register(
                new AddVoteCommand(plugin),
                new StartCommand(plugin)
        );
    }

    @Override
    public void execute(final CommandContext<CommandSender> context) {

        final CommandSender sender = context.getSender();

        if (!sender.hasPermission("abyssvoteparty.admin")) {
            this.plugin.getMessageCache().sendMessage(sender, "no-permission");
            return;
        }
        this.plugin.getMessageCache().sendMessage(sender, "admin-help-message");
    }
}
