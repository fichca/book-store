package com.company.service;

import com.company.entity.Address;
import com.company.entity.Store;
import com.company.ioc.annotation.Component;
import com.company.ioc.annotation.Qualifier;
import com.company.storage.StoreStorage;
import com.company.storage.db.StoreDbStorage;
import com.company.storage.file.object.StoreDataFileStorage;
@Component
public class StoreServiceImpl implements StoreService {
    private final StoreStorage storeStorage;

    public StoreServiceImpl(StoreStorage storeStorage) {
        this.storeStorage = storeStorage;
    }

    @Override
    public void save(Store store) {
        if (storeStorage.contains(store)) {
            storeStorage.save(store);
        }
    }

    @Override
    public String updateTitle(String title, int id) {
        if (storeStorage.contains(title)) {
            return null;
        }

        if (storeStorage.contains(id)) {
            return storeStorage.updateTitle(title, id);
        }
        return null;
    }

    @Override
    public Address updateAddress(Address address, int id) {
        if (storeStorage.contains(address)) {
            return null;
        }

        if (storeStorage.contains(id)) {
            return storeStorage.updateAddress(address, id);
        }
        return null;
    }

    @Override
    public void delete(int id) {
        if (storeStorage.contains(id)) {
            storeStorage.delete(id);
        }
    }

    @Override
    public void delete(String title) {
        if (storeStorage.contains(title)) {
            storeStorage.delete(title);
        }
    }

    @Override
    public void delete(Store store) {
        if (storeStorage.contains(store)) {
            storeStorage.delete(store);
        }
    }

    @Override
    public Store[] getAll() {
        return storeStorage.getAll();
    }


    @Override
    public Store getByTitle(String title) {
        if (storeStorage.contains(title)) {
            return storeStorage.getByTitle(title);
        }
        return null;
    }

    @Override
    public Store getById(int id) {
        if (storeStorage.contains(id)) {
            return storeStorage.getById(id);
        }
        return null;
    }
}
