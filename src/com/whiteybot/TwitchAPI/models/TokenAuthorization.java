package com.whiteybot.TwitchAPI.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.whiteybot.TwitchAPI.auth.Scopes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Travis on 2/12/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenAuthorization {

    private List<Scopes> scopes;
    private Date createdAt;
    private Date updatedAt;

    @Override
    public String toString() {
        return "TokenAuthorization{scopes=" + scopes + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TokenAuthorization tokenAuthorization = (TokenAuthorization) o;
        if (scopes != null ? !scopes.equals(tokenAuthorization.scopes) : tokenAuthorization.scopes != null) return false;
        if (createdAt != null ? !createdAt.equals(tokenAuthorization.createdAt) : tokenAuthorization.createdAt != null) return false;
        return !(updatedAt != null ? !updatedAt.equals(tokenAuthorization.updatedAt) : tokenAuthorization.updatedAt != null);
    }

    @Override
    public int hashCode() {
        int result = scopes != null ? scopes.hashCode() : 0;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    public List<Scopes> getScopes() {
        return scopes;
    }

    public void setScopes(List<String> scopes) {
        this.scopes = new ArrayList<Scopes>();
        for (String s : scopes)
            this.scopes.add(Scopes.fromString(s));
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
