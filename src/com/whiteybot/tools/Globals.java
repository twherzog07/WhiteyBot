package com.whiteybot.tools;

import com.whiteybot.TwitchBot.TwitchUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Travis on 2/10/2017.
 */
public class Globals {

    public static String gBotOAuth = "oauth:7fh3ma26v9z3k4df315y14j9nt344l";
    public static String gBotName = "WhiteyHBot";
    public static String gBotVersion = "0.1";
    public static String gBotChannel = "#whitey_h";

    public static final String gCommandsAdmin = "!addmod !delmod";
    public static final String gCommandsBot = "!help !register !unregister";
    public static final String gCommandsMod = "!joinchan !partchan !addchan !delchan";
    public static final String gCommandsOp = "!permit";
    public static final String gCommandsUser = "!help !info !performance !date !time !users !ops !mods !channel !channels !slots";

    public static String gServerMembershipRequest = "CAP REQ :twitch.tv/membership";
    public static String gServerMembershipResponse = ":tmi.twitch.tv CAP * ACK :twitch.tv/membership";
    public static String gServerCommandsRequest = "CAP REQ :twitch.tv/commands";
    public static String gServerCommandsResponse = ":tmi.twitch.tv CAP * ACK :twitch.tv/commands";
    public static String gServerTagsRequest = "CAP REQ :twitch.tv/tags";
    public static String gServerTagsResponse = ":tmi.twitch.tv CAP * ACK :twitch.tv/tags";

    public static DateFormat gDateFormat = new SimpleDateFormat("MM.dd.yyyy");
    public static DateFormat gTimeFormat = new SimpleDateFormat("HH:mm:ss");

    public static TwitchUser gNullUser = new TwitchUser("null", "");
}
