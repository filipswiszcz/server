package com.filipswiszcz.entity;

import net.minestom.server.entity.Player;

public class User {

    private final long id;

    private final Player player;

    private Language language;
    private Rank rank;

    public User(Player player) {
        this(player, Language.POLISH, Rank.USER);
    }

    public User(Player player, Language language, Rank rank) {
        this.id = 0; // generate id
        this.player = player;
        this.language = language;
        this.rank = rank;
    }

    public long getId() {
        return id;
    }

    public Player getChild() {
        return player;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public enum Language {

        ENGLISH,
        POLISH;

    }

    public enum Rank {

        USER("user", 1),
        VIP("vip", 2),
        MOD("mod", 3),
        ROOT("root", 4),
        DEV("dev", 5);
        // ROOT("root", 4, "<owner>", "<właściciel>")

        private final String name;
        private final int admittance;
        // private final Component english;
        // private final Component polish;

        Rank(String name, int admittance) {
            this.name = name;
            this.admittance = admittance;
        }

        public String getName() {
            return name;
        }

        public int getAdmittance() {
            return admittance;
        }

    }
    
}
