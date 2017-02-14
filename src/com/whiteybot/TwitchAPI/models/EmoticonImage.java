package com.whiteybot.TwitchAPI.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Travis on 2/12/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmoticonImage {

    private int emoticonSet;
    private int height;
    private int width;
    private String url;

    @Override
    public String toString() {
        return "EmoticonImage{emoticonSet=" + emoticonSet + ", height=" + height + ", width=" + width + ", url='" + url + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmoticonImage emoticonImage = (EmoticonImage) o;
        if (emoticonSet != emoticonImage.emoticonSet) return false;
        if (height != emoticonImage.height) return false;
        if (width != emoticonImage.width) return false;
        return url.equals(emoticonImage.url);
    }

    @Override
    public int hashCode() {
        int result = emoticonSet;
        result = 31 * result + height;
        result = 31 * result + width;
        result = 31 * result + url.hashCode();
        return result;
    }

    public int getEmoticonSet() {
        return emoticonSet;
    }

    public void setEmoticonSet(int emoticonSet) {
        this.emoticonSet = emoticonSet;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
