package com.example.demo_project.diary;

import lombok.Getter;
import lombok.Setter;

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
}
