package com.whiteybot.TwitchAPI.auth.grants.implicit;

import com.whiteybot.TwitchAPI.auth.Scopes;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Travis on 2/11/2017.
 */
public class AuthenticationCallbackRequest implements Runnable {

    private static final String EOL = "\r\n";

    private Socket socket;
    private URL authPage, failurePage, successPage;
    private AuthenticationListener authenticationListener;

    public AuthenticationCallbackRequest(Socket socket, URL authPage, URL failurePage, URL successPage) {
        this.socket = socket;
        this.authPage = authPage;
        this.failurePage = failurePage;
        this.successPage = successPage;
    }

    private static void sendFileBytes(InputStream fis, OutputStream os) throws IOException {
        byte[] buffer = new byte[1024];
        int bytes = 0;
        while ((bytes = fis.read(buffer)) != -1) {
            os.write(buffer, 0, bytes);
        }
    }

    private static Map<String, String> extractQueryParams(String request) {
        Map<String, String> params = new HashMap<>();

        String[] parts = request.split("\\?", 2);
        if (parts.length < 2)
            return params;

        String query = parts[1];
        for (String param : query.split("&")) {
            String[] pair = param.split("=");

            try {
                String key = URLDecoder.decode(pair[0], "UTF-8");
                String value = "";
                if (pair.length > 1) {
                    value = URLDecoder.decode(pair[1], "UTF-8");
                }
                params.put(key, value);
            } catch (UnsupportedEncodingException e) {}
        }

        return params;
    }

    public void setAuthenticationListener(AuthenticationListener receiver) {
        this.authenticationListener = receiver;
    }

    @Override
    public void run() {
        try {
            processRequest();
        } catch (Exception e) {}
    }

    private void processRequest() throws IOException {
        InputStream is = socket.getInputStream();
        DataOutputStream os = new DataOutputStream(socket.getOutputStream());

        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String requestLine = br.readLine();
        StringTokenizer tokens = new StringTokenizer(requestLine);
        String requestMethod = tokens.nextToken();
        String requestFilename = tokens.nextToken();
        Map<String, String> queryParams = extractQueryParams(requestFilename);

        String accessToken = queryParams.get("access_token");
        String[] scopes = new String[0];
        if (queryParams.containsKey("scope")) {
            scopes = queryParams.get("scope").split(" ");
        }

        String error = queryParams.get("error");
        String errorDescription = queryParams.get("error_description");

        InputStream fis;
        String contentTypeLine;
        if (requestFilename.startsWith("/auth.js") || requestFilename.startsWith("/auth-success.js")) {
            fis = getClass().getResourceAsStream(requestFilename);
            contentTypeLine = "Content-type: text/javascript" + EOL;
        }
        else {
            if (accessToken != null) {
                fis = successPage.openStream();
            }
            else if (error != null) {
                fis = failurePage.openStream();
            }
            else {
                fis = authPage.openStream();
            }
            contentTypeLine = "Content-type: text/html" + EOL;
        }

        boolean fileExists = fis != null;

        String statusLine = null;
        String entityBody = null;
        if (fileExists)
            statusLine = "HTTP/1.1 200 OK" + EOL;
        else {
            statusLine = "HTTP/1.1 404 Not Found" + EOL;
            entityBody = "404 Not Found";
        }

        os.writeBytes(statusLine);
        os.writeBytes(contentTypeLine);
        os.writeBytes(EOL);

        if (fileExists) {
            sendFileBytes(fis, os);
            fis.close();
        }
        else
            os.writeBytes(entityBody);

        os.close();
        br.close();
        socket.close();

        if (authenticationListener != null) {
            if (accessToken != null) {
                Scopes[] accessScopes = new Scopes[scopes.length];
                for (int i = 0; i < scopes.length; i++)
                    accessScopes[i] = Scopes.fromString(scopes[i]);
                authenticationListener.onAccessTokenReceived(accessToken, accessScopes);
            }
            if (error != null)
                authenticationListener.onAuthenticationError(error, errorDescription);
        }
    }
}
