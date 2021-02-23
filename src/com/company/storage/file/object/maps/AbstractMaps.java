package com.company.storage.file.object.maps;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public abstract class AbstractMaps<T> implements Serializable {

    String path;
    HashMap<Integer, T> hashMap;

    public AbstractMaps(String path, HashMap<Integer, T> hashMap) {
        this.path = path;
        this.hashMap = hashMap;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public HashMap<Integer, T> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<Integer, T> hashMap) {
        this.hashMap = hashMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractMaps<?> that = (AbstractMaps<?>) o;
        return Objects.equals(path, that.path) &&
                Objects.equals(hashMap, that.hashMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, hashMap);
    }

    @Override
    public String toString() {
        return "AbstractMaps{" +
                "path='" + path + '\'' +
                ", hashMap=" + hashMap +
                '}';
    }
}
