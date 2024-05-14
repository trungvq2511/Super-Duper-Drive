package com.udacity.jwdnd.course1.cloudstorage.security.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class SuperDuperDriveToken extends UsernamePasswordAuthenticationToken {
    private Integer userId;
    private String salt;

    public SuperDuperDriveToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities,
                                Integer userId, String salt) {
        super(principal, credentials, authorities);
        this.userId = userId;
        this.salt = salt;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getSalt() {
        return salt;
    }

    @Override
    public String toString() {
        return "SuperDuperDriveToken{" +
                "userId=" + userId +
                ", salt='" + salt + '\'' +
                '}';
    }
}
