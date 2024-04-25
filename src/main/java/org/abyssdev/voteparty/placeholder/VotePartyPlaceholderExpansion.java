package org.abyssdev.voteparty.placeholder;

import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.abyssdev.abysslib.utils.Utils;
import org.abyssdev.voteparty.AbyssVoteParty;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public final class VotePartyPlaceholderExpansion extends PlaceholderExpansion {

    private final AbyssVoteParty plugin;

    @Override
    public @NotNull String getIdentifier() {
        return "abyssvoteparty";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Words";
    }

    @Override
    public @NotNull String getVersion() {
        return this.plugin.getDescription().getVersion();
    }

    @Override
    public @NotNull String onRequest(final OfflinePlayer player, @NotNull final String params) {

        switch (params.toLowerCase()) {
            case "current_votes":
                return Utils.format(this.plugin.getCurrentVotes());
            case "required_votes":
                return Utils.format(this.plugin.getRequiredVotes());
        }
        return "";
    }
}