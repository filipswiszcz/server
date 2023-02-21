package com.filipswiszcz.entity;

import java.util.UUID;

import com.filipswiszcz.security.Rank;

import net.minestom.server.entity.Player;
import net.minestom.server.network.player.PlayerConnection;

public class User extends Player {

    private Language language;
    
    private Rank.Type type;

    public User(UUID uuid, String username, PlayerConnection playerConnection) {
        this(uuid, username, playerConnection, Language.POLISH, Rank.Type.USER);
    }

    public User(UUID uuid, String username, PlayerConnection playerConnection, Language language, Rank.Type type) {
        super(uuid, username, playerConnection);
        this.language = language;
        this.type = type;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Rank.Type getRankType() {
        return type;
    }

    public void setRankType(Rank.Type type) {
        this.type = type;
    }

    public enum Language {

        ENGLISH,
        POLISH;

    }
    
}
