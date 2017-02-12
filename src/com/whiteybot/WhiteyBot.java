package com.whiteybot;

import com.whiteybot.TwitchBot.*;

import static com.whiteybot.tools.LogTools.*;

import static java.lang.System.exit;

/**
 * Created by Travis on 2/10/2017.
 */
public class WhiteyBot {
    public static void main(String[] args) {
        TwitchBot bot = new TwitchBot();
        bot.initTwitch();

        int initTime = 5;
        while (!bot.isInitialized()) {
            initTime--;
            try {
                logMessage("Waiting for Twitch responses... " + initTime);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (!bot.isInitialized()) {
            logError("Failed to receive Twitch permissions!");
            exit(1);
        }

        bot.initChannels();

        float time;
        long timeStart, timeEnd;

        while (bot.isConnected()) {
            timeStart = System.nanoTime();

            for (TwitchChannel c : bot.getTwitchChannels())
                if (c.getCmdSent() > 0)
                    c.setCmdSent(c.getCmdSent() - 1);

            for (TwitchUser u : bot.getAllUsers())
                if (u.getCmdTimer() > 0)
                    u.setCmdTimer(u.getCmdTimer() - 1);

            timeEnd = System.nanoTime();
            time = (float)(timeEnd - timeStart) / 1000000.0f;
            bot.setLoopTime(time);

            try {
                if (time < 1000.0f)
                    Thread.sleep((long)(1000.0f - time));
                else
                    logError("Warning! Main thread loop time is longer than a second! Skipping sleep! Cycle-time: " + time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
