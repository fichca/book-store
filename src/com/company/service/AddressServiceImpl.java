package com.company.service;

import com.company.entity.Address;
import com.company.ioc.annotation.Component;
import com.company.ioc.annotation.Qualifier;
import com.company.storage.AddressStorage;
import com.company.storage.db.AddressDbStorage;
import com.company.storage.file.object.AddressDataFileStorage;
@Component
public class AddressServiceImpl implements AddressService {
    private final AddressStorage addressStorage;

    public AddressServiceImpl(AddressStorage addressStorage) {
        this.addressStorage = addressStorage;
    }

    @Override
    public boolean save(Address address) {
        if (addressStorage.contains(address)) {
            return false;
        }
        addressStorage.save(address);
        return true;
    }

    @Override
    public void remove(int id) {
        if (addressStorage.contains(id)) {
            addressStorage.remove(id);
        }
    }

    @Override
    public void remove(Address address) {
        if (addressStorage.contains(address)) {
            addressStorage.remove(address);
        }
    }

    @Override
    public void updateAddressById(String street, int id) {
        if (addressStorage.contains(id)) {
            addressStorage.updateAddressById(street, id);
        }
    }

    @Override
    public void updateHomeById(int home, int id) {
        if (addressStorage.contains(id)) {
            addressStorage.updateHomeById(home, id);
        }
    }

    @Override
    public Address[] getAll() {
        return addressStorage.getAll();
    }

    @Override
    public Address getById(int id) {
        if (addressStorage.contains(id)) {
            Address address = addressStorage.getById(id);
            return address;
        }
        return null;
    }

    @Override
    public Address[] getByStreet(String street) {
        return addressStorage.getByStreet(street);
    }

    @Override
    public Address[] getByHome(int home) {
        return addressStorage.getByHome(home);
    }
}
