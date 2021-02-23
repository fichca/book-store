package com.company.storage.file.object.wrapper;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractWrapper<T> implements Serializable {
    private int id;
    private List<T> list;

    public AbstractWrapper(int id, List<T> list) {
        this.id = id;
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
