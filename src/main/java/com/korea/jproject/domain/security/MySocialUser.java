package com.korea.jproject.domain.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MySocialUser {
    private String sub;
    private String pass;
    private String name;
    private String email;

    public String getLoginId(){
        return this.sub;
    }
}
