package com.company.storage;

import com.company.entity.Address;
import com.company.entity.Store;

public interface StoreStorage {
    void save(Store store);

    String updateTitle(String title, int id);

    Address updateAddress(Address address, int id);

    void delete(int id);

    void delete(String title);

    void delete(Store store);

    Store[] getAll();

    Store getByTitle(String title);

    Store getById(int id);

    boolean contains(int id);

    boolean contains(String title);

    boolean contains(Store store);

    boolean contains(Address address);
}
