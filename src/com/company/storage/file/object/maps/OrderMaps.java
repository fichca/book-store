package com.company.storage.file.object.maps;

import java.util.HashMap;
import java.util.List;

public class OrderMaps extends AbstractMaps<List<Integer>> {
    public OrderMaps(String path, HashMap<Integer, List<Integer>> hashMap) {
        super(path, hashMap);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
