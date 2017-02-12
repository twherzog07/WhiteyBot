package com.whiteybot.TwitchAPI.auth.grants.implicit;

import com.whiteybot.TwitchAPI.auth.Scopes;

/**
 * Created by Travis on 2/11/2017.
 */
public interface AuthenticationListener {
    void onAccessTokenReceived(String token, Scopes... scopes);
    void onAuthenticationError(String error, String description);
}
