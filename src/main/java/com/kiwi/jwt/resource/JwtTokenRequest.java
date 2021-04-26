package com.kiwi.jwt.resource;

import java.io.Serializable;

public class  JwtTokenRequest implements Serializable {

  private static final long serialVersionUID = -5616176897013108345L;

  private String username;
    private String password;

    public JwtTokenRequest() {
        super();
    }

    public JwtTokenRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }
    /*
    {
        "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzZW5hIiwiZXhwIjoxNTk2MzkzNDcxLCJpYXQiOjE1OTU3ODg2NzF9.4GujHe0nLUPkJO2
        -HQXBnNjXnziuhbDvJQz8L8rRhKW2EYwn-SAtcubEhVJprjDHKcy89jLNOjI9fr2f2Sqilw"
    }
    {
        refresh:"token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzZW5hIiwiZXhwIjoxNTk2MzkzOTgzLCJpYXQiOjE1OTU3ODkxODN9.
        SZ7udcoQLyFwIUSzhJecQWi6x1t4-si-BpPuHu6bJYTgstoVzFfZC9Mrc4jb2HcSE0fGlG7PYXTHZ9hFA4GxOA"
}

     */


    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
