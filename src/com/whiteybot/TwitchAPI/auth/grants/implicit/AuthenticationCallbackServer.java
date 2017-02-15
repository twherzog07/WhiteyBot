package com.whiteybot.TwitchAPI.auth.grants.implicit;

import com.whiteybot.TwitchAPI.auth.Scopes;

import java.io.IOException;
import java.net.*;

/**
 * Created by Travis on 2/11/2017.
 */
public class AuthenticationCallbackServer implements AuthenticationListener {

    public static final String DEFAULT_AUTH_PAGE = "/authorize.html";
    public static final String DEFAULT_FAILURE_PAGE = "/authorize-failure.html";
    public static final String DEFAULT_SUCCESS_PAGE = "/authorize-success.html";

    private final URL authPage;
    private final URL failurePage;
    private final URL successPage;
    private int port;
    private ServerSocket serverSocket;
    private String accessToken;
    private Scopes[] accessScopes;
    private AuthenticationError authenticationError;

    public AuthenticationCallbackServer(int port) {
        this.port = port;

        authPage = getClass().getResource(DEFAULT_AUTH_PAGE);
        failurePage = getClass().getResource(DEFAULT_FAILURE_PAGE);
        successPage = getClass().getResource(DEFAULT_SUCCESS_PAGE);
    }

    public AuthenticationCallbackServer(int port, URL authPage, URL failurePage, URL successPage) {
        this.port = port;
        this.authPage = authPage == null ? getClass().getResource(DEFAULT_AUTH_PAGE) : authPage;
        this.failurePage = failurePage == null ? getClass().getResource(DEFAULT_FAILURE_PAGE) : failurePage;
        this.successPage = successPage == null ? getClass().getResource(DEFAULT_SUCCESS_PAGE) : successPage;
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(port, 0, InetAddress.getByName("127.0.0.1"));
        run();
    }

    private void run() throws IOException {
        while (true) {
            try {
                Socket connectionSocket = serverSocket.accept();
                AuthenticationCallbackRequest request = new AuthenticationCallbackRequest(connectionSocket, authPage,
                                                                                            failurePage, successPage);
                request.setAuthenticationListener(this);
                Thread thread = new Thread(request);
                thread.start();
            } catch (SocketException e) {
                break;
            }
        }
    }

    public void stop() {
        if (serverSocket != null && !serverSocket.isClosed()) {
            try { serverSocket.close(); }
            catch (IOException e) {}
            finally { serverSocket = null; }
        }
    }

    public boolean isRunning() {
        return serverSocket != null;
    }

    @Override
    public void onAccessTokenReceived(String token, Scopes... scopes) {
        accessToken = token;
        accessScopes = scopes;
        stop();
    }

    @Override
    public void onAuthenticationError(String error, String description) {
        authenticationError = new AuthenticationError(error, description);
        stop();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public AuthenticationError getAuthenticationError() {
        return authenticationError;
    }

    public boolean hasAuthenticationError() {
        return authenticationError != null;
    }
}
