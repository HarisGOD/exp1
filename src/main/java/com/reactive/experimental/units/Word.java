package com.reactive.experimental.units;

import org.springframework.data.annotation.Id;

public class Word {
    @Override
    public String toString(){
        //{"name":"SHIT","id":0}
        return "{\"name\":\""+this.name+"\",\"id\":"+this.id+"}";
    };
    @Override
    public boolean equals(Object Eq){
        return this.toString() == Eq.toString();
    }
    private String name;
    @Id
    private Long id;

    public Word(){}
    public Word(String name){
        this.name = name;
    }
    public Word(String name,Long id){
        this.id = id;
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
