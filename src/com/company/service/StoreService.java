package com.company.service;

import com.company.entity.Address;
import com.company.entity.Store;

public interface StoreService {
    void save(Store store);

    String updateTitle(String title, int id);

    Address updateAddress(Address address, int id);

    void delete(int id);

    void delete(String title);

    void delete(Store store);

    Store[] getAll();

    Store getByTitle(String title);


    Store getById(int id);
}
