package com.company.service;

import com.company.entity.Address;

public interface AddressService {

    boolean save(Address address);

    void remove(int id);

    void remove(Address address);

    void updateAddressById(String street, int id);

    void updateHomeById(int home, int id);

    Address[] getAll();

    Address getById(int id);

    Address[] getByStreet(String street);

    Address[] getByHome(int home);

}
