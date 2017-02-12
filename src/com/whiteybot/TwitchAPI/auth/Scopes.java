package com.whiteybot.TwitchAPI.auth;

/**
 * Created by Travis on 2/11/2017.
 */
public enum Scopes {
    USER_READ("user_read"),
    USER_BLOCKS_EDIT("user_blocks_edit"),
    USER_BLOCKS_READ("user_blocks_read"),
    USER_FOLLOWS_EDIT("user_follows_edit"),
    CHANNEL_READ("channel_read"),
    CHANNEL_EDITOR("channel_editor"),
    CHANNEL_COMMERCIAL("channel_commercial"),
    CHANNEL_STREAM("channel_stream"),
    CHANNEL_SUBSCRIPTIONS("channel_subscriptions"),
    USER_SUBSCRIPTIONS("user_subscriptions"),
    CHANNEL_CHECK_SUBSCRIPTION("channel_check_subscription"),
    CHAT_LOGIN("chat_login");

    private String key;

    Scopes(String key) {
        this.key = key;
    }

    public static String join(Scopes... scopes) {
        if (scopes == null) return "";
        StringBuilder sb = new StringBuilder();
        for (Scopes scope : scopes)
            sb.append(scope.getKey()).append("+");
        return sb.toString();
    }

    public static Scopes fromString(String text) {
        if (text == null) return null;
        for (Scopes v : Scopes.values()) {
            if (text.equalsIgnoreCase(v.key))
                return v;
        }
        return null;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return key;
    }
}
