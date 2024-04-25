package org.abyssdev.voteparty.commands.subs;

import net.abyssdev.abysslib.command.AbyssSubCommand;
import net.abyssdev.abysslib.command.context.CommandContext;
import org.abyssdev.voteparty.AbyssVoteParty;
import org.bukkit.command.CommandSender;
import org.eclipse.collections.impl.factory.Sets;

public class AddVoteCommand extends AbyssSubCommand<AbyssVoteParty> {

    public AddVoteCommand(final AbyssVoteParty plugin) {
        super(plugin, false, 0, Sets.immutable.of("addvote"));
    }

    @Override
    public void execute(final CommandContext<?> context) {

        final CommandSender sender = context.getSender();

        if (!sender.hasPermission("abyssvoteparty.admin")) {
            this.plugin.getMessageCache().sendMessage(sender, "no-permission");
            return;
        }
        this.plugin.addVote();
        this.plugin.getMessageCache().sendMessage(sender, "vote-added");
    }
}
