package com.example.demo_project.diary.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OAuth2Member {

    private String username;

    private String password;

    private String nickname;

    private String email;
}
