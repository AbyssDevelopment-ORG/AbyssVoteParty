package org.abyssdev.voteparty.listeners;

import com.vexsoftware.votifier.model.VotifierEvent;
import net.abyssdev.abysslib.listener.AbyssListener;
import net.abyssdev.abysslib.placeholder.PlaceholderReplacer;
import net.abyssdev.abysslib.utils.Utils;
import org.abyssdev.voteparty.AbyssVoteParty;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.concurrent.CompletableFuture;

public class VoteListener extends AbyssListener<AbyssVoteParty> {

    public VoteListener(final AbyssVoteParty plugin) {
        super(plugin);
    }

    @EventHandler
    public void onVote(final VotifierEvent event) {
        CompletableFuture.runAsync(() -> {
            this.plugin.addVote();

            if (!this.plugin.isAnnounceVotes()) {
                return;
            }

            final PlaceholderReplacer placeholderReplacer = new PlaceholderReplacer()
                    .addPlaceholder("%player%", event.getVote().getUsername())
                    .addPlaceholder("%votes%", Utils.format(this.plugin.getCurrentVotes()))
                    .addPlaceholder("%required_votes%", Utils.format(this.plugin.getRequiredVotes()));

            for (final Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
                this.plugin.getMessageCache().sendMessage(onlinePlayer, "played-voted", placeholderReplacer);
            }
        });
    }
}
