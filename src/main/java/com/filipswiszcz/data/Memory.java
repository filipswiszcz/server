package com.filipswiszcz.data;

import java.util.ArrayList;
import java.util.Collection;

import com.filipswiszcz.entity.User;
import com.filipswiszcz.security.Rank;

public final class Memory {

    private final Collection<User> users = new ArrayList<>();
    private final Collection<Rank> ranks = new ArrayList<>();
    
}
