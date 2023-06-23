package com.reactive.experimental.units;

import org.springframework.data.annotation.Id;

public class Word {

    private String name;
    @Id
    private Long id;

    public Word(){}
    public Word(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
