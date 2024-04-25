package org.abyssdev.voteparty;

import lombok.Getter;
import lombok.Setter;
import net.abyssdev.abysslib.config.AbyssConfig;
import net.abyssdev.abysslib.placeholder.PlaceholderReplacer;
import net.abyssdev.abysslib.plugin.AbyssPlugin;
import net.abyssdev.abysslib.text.MessageCache;
import net.abyssdev.abysslib.utils.Utils;
import org.abyssdev.voteparty.commands.VotePartyAdminCommand;
import org.abyssdev.voteparty.listeners.VoteListener;
import org.abyssdev.voteparty.placeholder.VotePartyPlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.List;

@Setter @Getter
public final class AbyssVoteParty extends AbyssPlugin {

    private final AbyssConfig settings = getAbyssConfig("settings.yml");
    private final MessageCache messageCache = new MessageCache(this.getConfig("messages"));

    private final int requiredVotes = this.settings.getInt("settings.required-votes");
    private final boolean announceVotes = this.settings.getBoolean("settings.announce-votes");
    private final List<String> commands = this.settings.getStringList("settings.commands");

    private int currentVotes;

    private VotePartyAdminCommand command;

    @Override
    public void onEnable() {
        this.loadVotes();

        this.loadMessages(this.messageCache, this.getConfig("messages"));

        this.command = new VotePartyAdminCommand(this);
        this.command.register();

        if (Bukkit.getServer().getPluginManager().isPluginEnabled("Votifier")) {
            new VoteListener(this);
        }

        if (!Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            return;
        }
        new VotePartyPlaceholderExpansion(this).register();
    }

    @Override
    public void onDisable() {
        this.saveVotes();
        this.command.unregister();
    }

    private void loadVotes() {

        final File file = new File(this.getDataFolder(), "votes.json");

        if (!file.exists()) {
            this.currentVotes = 0;
            return;
        }

        try {
            final FileReader fileReader = new FileReader(file);
            this.currentVotes = AbyssPlugin.GSON.fromJson(fileReader, Integer.class);
            fileReader.close();
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void saveVotes() {

        final File file = new File(this.getDataFolder(), "votes.json");

        try {
            final FileWriter fileWriter = new FileWriter(file);
            AbyssPlugin.GSON.toJson(this.currentVotes, fileWriter);
            fileWriter.flush();
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void addVote() {

        if (this.currentVotes + 1 >= this.requiredVotes) {
            this.startParty();
            return;
        }
        this.currentVotes++;
    }

    public void startParty() {

        this.currentVotes = 0;

        final PlaceholderReplacer replacer = new PlaceholderReplacer()
                .addPlaceholder("%required_votes%", Utils.format(this.requiredVotes))
                .addPlaceholder("%reward_count%", Utils.format(this.commands.size()));

        for (final Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
            this.messageCache.sendMessage(onlinePlayer, "vote-party-started", replacer);
        }
    }
}