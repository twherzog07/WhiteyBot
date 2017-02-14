package com.whiteybot.TwitchAPI.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Travis on 2/12/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Error {

    @JsonProperty("error")
    private String statusText;
    @JsonProperty("status")
    private int statusCode;
    @JsonProperty("message")
    private String message;

    @Override
    public String toString() {
        return "Error{statusText='" + statusText + '\'' + ", statusCode=" + statusCode + ", message='" + message + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Error error = (Error) o;
        if (statusCode != error.statusCode) return false;
        if (statusText != null ? !statusText.equals(error.statusText) : error.statusText != null) return false;
        return !(message != null ? !message.equals(error.message) : error.message != null);
    }

    @Override
    public int hashCode() {
        int result = statusText != null ? statusText.hashCode() : 0;
        result = 31 * result + statusCode;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
