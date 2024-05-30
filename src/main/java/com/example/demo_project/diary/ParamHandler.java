package com.example.demo_project.diary;

import lombok.Getter;
import lombok.Setter;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Getter
@Setter
public class ParamHandler {

    private String keyword;

    private Boolean isSearchModal;

    private String type;

    public ParamHandler () {
        this.keyword = "";
        this.isSearchModal = false;
        this.type = "all";
    }
    public String getQueryParam () {
        return String.format("?keyword=%s&type=%s".formatted(URLEncoder.encode(keyword, StandardCharsets.UTF_8),URLEncoder.encode(type, StandardCharsets.UTF_8)));
    }
    public String getRedirectUrl (String url) {
        return "redirect:" + url + getQueryParam();
    }
}
