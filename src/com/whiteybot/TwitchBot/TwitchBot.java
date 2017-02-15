package com.whiteybot.TwitchBot;

import static com.whiteybot.tools.Globals.*;
import static com.whiteybot.tools.LogTools.*;
import static java.lang.System.exit;

import com.whiteybot.TwitchAPI.TwitchAPI;
import com.whiteybot.TwitchAPI.handlers.ChannelResponseHandler;
import com.whiteybot.TwitchAPI.handlers.UserResponseHandler;
import com.whiteybot.TwitchAPI.models.*;
import com.whiteybot.tools.FileTools;

import org.jibble.pircbot.*;
import org.jibble.pircbot.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Travis on 2/10/2017.
 */
public class TwitchBot extends PircBot {

    private float mLoopTime;
    private float mCommandTime;
    private boolean mHasCommands, mHasMembership, mHasTags;
    private ArrayList<TwitchChannel> mChannels;
    private ArrayList<TwitchUser> mModerators;
    private TwitchAPI mTwitchAPI;

    public TwitchBot() {
        mLoopTime = 0.0f;
        mCommandTime = 0.0f;
        mHasMembership = false;
        mHasCommands = false;
        mHasTags = false;
        mChannels = new ArrayList<>();
        mModerators = new ArrayList<>();
        mTwitchAPI = new TwitchAPI();

        mTwitchAPI.setClientID(gTwitchClientID);
        setName(gBotName);
        setVersion(gBotVersion);
        setVerbose(false);
    }

    public void initTwitch() {
        logMessage("Attempting to connect to irc.twitch.tv...");
        try {
            connect("irc.twitch.tv", 6667, gBotOAuth);
        } catch (IOException | IrcException e) {
            logError(e.getStackTrace().toString());
            exit(1);
        }

        logMessage("Requesting twitch membership capabilities...");
        sendRawLine(gServerMembershipRequest);

        logMessage("Requesting twitch commands capabilities...");
        sendRawLine(gServerCommandsRequest);

        logMessage("Requesting twitch tags capabilities");
        sendRawLine(gServerTagsRequest);
    }

    public void initChannels() {
        logMessage("Attempting to join all registered channels...");
        ArrayList<String> loadedChannels = FileTools.readTextFile("data/channels.txt");
        for (String s : loadedChannels) {
            if (!s.startsWith("#"))
                s = "#" + s;
            joinToChannel(s);
        }
    }

    public void joinToChannel(String channel) {
        logMessage("Attempting to join channel " + channel);
        joinChannel(channel);
        TwitchChannel twitchChannel = new TwitchChannel(channel);

        if (channel.startsWith("#"))
            channel = channel.replace("#", "");
        final String channelStr = channel;

        mTwitchAPI.channels().get(channel, new ChannelResponseHandler() {
            @Override
            public void onSuccess(Channel channel) {
                twitchChannel.setTwitchChannel(channel);
                logMessage("Successfully loaded Twitch profile for channel: " + channel.toString());
            }

            @Override
            public void onFailure(int statusCode, String statusMessage, String errorMessage) {
                logError(String.format("Failed to load information for channel ({0}) - Error Message: {1}", channelStr, errorMessage));
            }

            @Override
            public void onFailure(Throwable throwable) {
                logError(String.format("Failed to load information for channel ({0}) - Error Message: {1}", channelStr, throwable.getMessage()));
            }
        });

        mChannels.add(twitchChannel);
    }

    public void partFromChannel(String channel) {
        logMessage("Attempting to part from channel " + channel);
        partChannel(channel);
        mChannels.remove(getTwitchChannel(channel));
    }

    public void addChannel(String channel, String sender) {
        ArrayList<String> channels = FileTools.readTextFile("data/channels.txt");
        if (channels.size() <= 0 || !channels.contains(channel)) {
            logMessage("User (" + sender + ") registered new channel: " + channel);
            sendTwitchMessage(channel, "User (" + sender + ") registered new channel: " + channel);
            FileTools.writeTextToFile("data", "channels.txt", channel);
            joinToChannel(channel);
        }
        else {
            logError("Failed to register a new channel: " + channel);
            sendTwitchMessage(channel, "That channel is already registered!");
        }
    }

    public void delChannel(String channel, String sender) {
        if (!Arrays.asList(getChannels()).contains(channel)) {
            logError("Can't delete channel " + channel + " from the global channels list because it isn't in the joined channels list!");
            return;
        }

        logMessage(sender + " requested deletion of channel: " + channel);
        sendTwitchMessage(channel, sender + " requested deletion of channel: " + channel);
        partFromChannel(channel);
        FileTools.removeTextFromFile("data", "channels.txt", channel);
    }

    public void addUser(String channel, String userName) {
        TwitchChannel tc = getTwitchChannel(channel);
        TwitchUser mod = getOfflineModerator(userName);
        String prefix = "";

        if (mod != null)
            prefix = mod.getPrefix();
        TwitchUser twitchUser = new TwitchUser(userName, prefix);

        mTwitchAPI.users().get(twitchUser.getName(), new UserResponseHandler() {
            @Override
            public void onSuccess(com.whiteybot.TwitchAPI.models.User user) {
                twitchUser.setTwitchProfile(user);
                logMessage("Successfully loaded Twitch user profile for user: " + user.toString());
            }

            @Override
            public void onFailure(int statusCode, String statusMessage, String errorMessage) {
                logError(String.format("Failed to add user ({0}) to channel ({1}) - Error Message: {2}", userName, channel, errorMessage));
            }

            @Override
            public void onFailure(Throwable throwable) {
                logError(String.format("Failed to add user ({0}) to channel ({1}) - Error Message: {2}", userName, channel, throwable.getMessage()));
            }
        });

        tc.addUser(twitchUser);
        logMessage("Adding new user (" + twitchUser + ") to channel (" + tc.toString() + ")");
    }

    public void sendTwitchMessage(String channel, String message) {
        TwitchChannel tc = getTwitchChannel(channel);
        TwitchUser user = tc.getUser(gBotName);

        if (user == null)
            user = gNullUser;

        if ((user.isOperator() && tc.getCmdSent() <= 48) | tc.getCmdSent() <= 16) {
            tc.setCmdSent(tc.getCmdSent() + 1);
            sendMessage(channel, message);
        }
        else {
            logError(String.format("Cannot send a message to channel ({0})! {1} Messages per 30s limit nearly exceeded! ({2})", tc, (user.isOperator()) ? "100" : "20", tc.getCmdSent()));
        }
    }

    public void processCommand(TwitchChannel channel, TwitchUser sender, String message) {
        String[] msg_array = message.split(" ");
        String msg_command = msg_array[0];
        String channelTarget;
        float time;
        long timeStart, timeEnd;

        timeStart = System.nanoTime();

            /*
             * Commands available on bot's channel
             */
        if (channel.equals(gBotChannel)) {
            switch (msg_command) {
                case "help":
                    sendTwitchMessage(channel.getName(), "List of available commands on this channel: " + gCommandsBot);
                    break;
                case "register":
                    addChannel("#" + sender.getName(), sender.getName());
                    break;
                case "unregister":
                    delChannel("#" + sender.getName(), sender.getName());
                    break;
            }
        }

            /*
             * Commands available on all channels
             */
        switch (msg_command) {
                /*
                 * Normal channel user commands below
                 */
            case "help":
                String helpText = "List of available commands to you: " + gCommandsUser;

                if (sender.isOperator())
                    helpText += " " + gCommandsOp;

                if (sender.isModerator())
                    helpText += " " + gCommandsMod;

                if (sender.isAdmin())
                    helpText += " " + gCommandsAdmin;

                sendTwitchMessage(channel.getName(), helpText);
                break;

            case "info":
                sendTwitchMessage(channel.getName(), "Language: Java v" + getVersion() + " - Bot Version: " + gBotVersion);
                break;

            case "performance":
                sendTwitchMessage(channel.getName(), "My current main loop cycle time is " + mLoopTime + "ms. My current command loop cycle time is " + mCommandTime + "ms.");
                break;

            case "date":
                sendTwitchMessage(channel.getName(), gDateFormat.format(new Date()));
                break;

            case "time":
                sendTwitchMessage(channel.getName(), gTimeFormat.format(new Date()));
                break;

            case "users":
                if (msg_array.length <= 1) {
                    sendTwitchMessage(channel.getName(), "Users in this channel: " + channel.getUsers().size());
                    break;
                }

                if (msg_array[1].equals("all")) {
                    sendTwitchMessage(channel.getName(), "Users in all channels: " + getAllUsers().size());
                    break;
                }

                channelTarget = msg_array[1];
                if (!channelTarget.startsWith("#"))
                    channelTarget = "#" + channelTarget;

                TwitchChannel usersChannel = getTwitchChannel(channelTarget);
                if (usersChannel == null) {
                    logError("Error on !users channel, channel (" + channelTarget + ") doesn't exist!");
                    break;
                }

                sendTwitchMessage(channel.getName(), "Users in channel (" + usersChannel + "): " + usersChannel.getUsers().size());
                break;

            case "ops":
                if (msg_array.length <= 1) {
                    sendTwitchMessage(channel.getName(), "Operators in this channel: " + channel.getOperators().size());
                    break;
                }

                if (msg_array[1].equals("all")) {
                    sendTwitchMessage(channel.getName(), "Operators in all channels: " + getAllOperators().size());
                    break;
                }

                channelTarget = msg_array[1];
                if (!channelTarget.startsWith("#"))
                    channelTarget = "#" + channelTarget;

                TwitchChannel opsChannel = getTwitchChannel(channelTarget);
                if (opsChannel == null) {
                    logError("Error on !ops channel, channel (" + channelTarget + ") doesn't exist!");
                    break;
                }

                sendTwitchMessage(channel.getName(), "Operators in channel (" + opsChannel + "): " + opsChannel.getOperators().size());
                break;

            case "mods":
                break;

            case "channel":
                break;

            case "channels":
                break;

            case "slots":

            case "permit":

            case "joinchan":
        }

        timeEnd = System.nanoTime();
        time = (float)(timeEnd - timeStart) / 1000000.0f;
        setCommandTime(getCommandTime() * 0.1f + time * 0.9f);
    }

    @Override
    public void handleLine(String line) {
        logMessage("handleLine | " + line);

        if (line.startsWith("@"))
            line = line.substring(line.indexOf(":"), line.length());

        super.handleLine(line);

        if (!isInitialized()) {
            if (line.equals(gServerMembershipResponse))
                mHasMembership = true;
            else if (line.equals(gServerCommandsResponse))
                mHasCommands = true;
            else if (line.equals(gServerTagsResponse))
                mHasTags = true;
        }

        if (line.contains(":jtv ")) {
            line = line.replace(":jtv ", "");
            String[] line_array = line.split(" ");

            if (line_array[0].equals("MODE") && line_array.length >= 4)
                onMode(line_array[1], line_array[3], line_array[3], "", line_array[2]);
        }
    }

    @Override
    public void onUserList(String channel, User[] users) {
        super.onUserList(channel, users);

        TwitchChannel tc = getTwitchChannel(channel);

        if (tc == null) {
            logError("Error on USERLIST, channel (" + channel + ") doesn't exist!");
            return;
        }

        for (User u : users) {
            if (tc.getUser(u.getNick()) == null) {
                addUser(channel, u.getNick());
            }
        }
    }

    @Override
    public void onJoin(String channel, String sender, String login, String hostname) {
        super.onJoin(channel, sender, login, hostname);

        TwitchChannel tc = getTwitchChannel(channel);
        TwitchUser user = tc.getUser(sender);
        TwitchUser mod = getOfflineModerator(sender);

        if (tc != null && user == null) {
            addUser(channel, sender);
        }
    }

    @Override
    public void onPart(String channel, String sender, String login, String hostname) {
        super.onPart(channel, sender, login, hostname);

        TwitchChannel tc = getTwitchChannel(channel);
        TwitchUser user = tc.getUser(sender);

        if (tc != null && user != null) {
            tc.delUser(user);
            logMessage("Removing user (" + user + ") from channel (" + tc.toString() + ")");
        }
    }

    @Override
    public void onMode(String channel, String sourceNick, String sourceLogin, String sourceHostname, String mode) {
        super.onMode(channel, sourceNick, sourceLogin, sourceHostname, mode);

        TwitchChannel tc = getTwitchChannel(channel);
        TwitchUser user = tc.getUser(sourceNick);

        if (user == null) {
            logError("Error on MODE, cannot find (" + user + ") from channel (" + tc.toString() + ")");
            return;
        }

        if (mode.equals("+o")) {
            logMessage("Adding +o MODE for user (" + user + ") in channel (" + tc.toString() + ")");
            user.addPrefixChar("@");
        }
        else if (mode.equals("-o")) {
            logMessage("Adding -o MODE for user (" + user + ") in channel (" + tc.toString() + ")");
            user.delPrefixChar("@");
        }
    }

    @Override
    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        logMessage("logs/channels/" + channel.replace("#", ""), "onMessage", "User: " + sender + " Hostname: " + hostname + " Message: " + message);

        TwitchChannel tc = getTwitchChannel(channel);

        /*
         * Handle chat commands
         */
        if (message.startsWith("!")) {
            TwitchUser user = tc.getUser(sender);

            if (user == null) {
                logError("Error on ONMESSAGE, user (" + sender + ") doesn't exist! Creating a temp null user object for user!");
                user = gNullUser;
            }

            if (message.length() > 3) {
                if (user.getCmdTimer() > 0) {
                    if (user.getCmdTimer() > 10 && tc.getCmdSent() < 32)
                        sendTwitchMessage(channel, user + " please wait " + user.getCmdTimer() + " seconds before sending a new command.");
                    user.setCmdTimer(user.getCmdTimer() + 5);
                    return;
                }
                else {
                    if (!user.getName().equals("null"))
                        user.setCmdTimer(5);
                }
            }

            message = message.replace("!", "");
            processCommand(tc, user, message);
        }
    }

    @Override
    public void onPrivateMessage(String sender, String login, String hostname, String message) {

    }

    public ArrayList<TwitchUser> getAllUsers() {
        ArrayList<TwitchUser> result = new ArrayList<>();

        for (TwitchChannel c : mChannels)
            result.addAll(c.getUsers());

        return result;
    }

    public ArrayList<TwitchUser> getAllOperators() {
        ArrayList<TwitchUser> result = new ArrayList<>();

        for (TwitchChannel c : mChannels)
            result.addAll(c.getOperators());

        return result;
    }

    public ArrayList<TwitchUser> getOnlineModerators() {
        ArrayList<TwitchUser> result = new ArrayList<>();

        for (TwitchChannel c : mChannels)
            result.addAll(c.getModerators());

        return result;
    }

    public TwitchUser getOfflineModerator(String name) {
        TwitchUser result = null;

        for (TwitchUser user : mModerators)
            if (user.getName().equals(name))
                result = user;

        return result;
    }

    public ArrayList<TwitchUser> getOfflineModerators() {
        return mModerators;
    }

    public TwitchChannel getTwitchChannel(String name) {
        TwitchChannel result = null;

        for (TwitchChannel channel : mChannels) {
            if (channel.getName().equals(name)) {
                result = channel;
                break;
            }
        }

        return result;
    }

    public ArrayList<TwitchChannel> getTwitchChannels() {
        return mChannels;
    }

    public boolean isInitialized() {
        return mHasMembership & mHasCommands & mHasTags;
    }

    public float getCommandTime() {
        return mCommandTime;
    }

    public float getLoopTime() {
        return mLoopTime;
    }

    public void setCommandTime(float commandTime) {
        mCommandTime = commandTime;
    }

    public void setLoopTime(float loopTime) {
        mLoopTime = loopTime;
    }
}
