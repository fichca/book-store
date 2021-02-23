package com.company.storage.file.object.wrapper;

import com.company.entity.Store;

import java.util.List;

public class StoreWrapper extends AbstractWrapper<Store> {
    public StoreWrapper(int id, List<Store> list) {
        super(id, list);
    }
}
