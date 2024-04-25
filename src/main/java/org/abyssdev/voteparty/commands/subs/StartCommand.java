package org.abyssdev.voteparty.commands.subs;


import net.abyssdev.abysslib.command.AbyssSubCommand;
import net.abyssdev.abysslib.command.context.CommandContext;
import org.abyssdev.voteparty.AbyssVoteParty;
import org.bukkit.command.CommandSender;
import org.eclipse.collections.impl.factory.Sets;

public class StartCommand extends AbyssSubCommand<AbyssVoteParty> {

    public StartCommand(final AbyssVoteParty plugin) {
        super(plugin, false, 0, Sets.immutable.of("start"));
    }

    @Override
    public void execute(final CommandContext<?> context) {

        final CommandSender sender = context.getSender();

        if (!sender.hasPermission("abyssvoteparty.admin")) {
            this.plugin.getMessageCache().sendMessage(sender, "no-permission");
            return;
        }

        this.plugin.startParty();
        this.plugin.getMessageCache().sendMessage(sender, "force-started");
    }
}
