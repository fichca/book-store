package com.company.entity;

import java.io.Serializable;

public abstract class AbstractEntity implements Serializable {

    private int id;

    public AbstractEntity() {
    }

    public AbstractEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
