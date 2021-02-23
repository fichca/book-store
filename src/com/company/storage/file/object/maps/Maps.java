package com.company.storage.file.object.maps;

import java.util.HashMap;

public class Maps extends AbstractMaps<Integer> {
    public Maps(String path, HashMap<Integer, Integer> hashMap) {
        super(path, hashMap);
    }


    //    String path;
//    HashMap<Integer, Integer> hashMap;
//
//    public Maps(String path, HashMap<Integer, Integer> hashMap) {
//        this.path = path;
//        this.hashMap = hashMap;
//    }
//
//    public String getPath() {
//        return path;
//    }
//
//    public void setPath(String path) {
//        this.path = path;
//    }
//
//    public HashMap<Integer, Integer> getHashMap() {
//        return hashMap;
//    }
//
//    public void setHashMap(HashMap<Integer, Integer> hashMap) {
//        this.hashMap = hashMap;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Maps maps = (Maps) o;
//        return Objects.equals(path, maps.path) &&
//                Objects.equals(hashMap, maps.hashMap);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(path, hashMap);
//    }
//
//    @Override
//    public String toString() {
//        return "Maps{" +
//                "path='" + path + '\'' +
//                ", hashMap=" + hashMap +
//                '}';
//    }
}
