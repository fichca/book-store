package com.company.storage.file.object;

import com.company.entity.Address;
import com.company.storage.AddressStorage;
import com.company.storage.file.object.wrapper.AddressWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class AddressDataFileStorage extends AbstractObjectFileStorage implements AddressStorage {

    private static AddressWrapper addressWrapper;

    {
        if (isEmpty(ADDRESS_PATH)) {
            addressWrapper = new AddressWrapper(0, new ArrayList<>());
            saveData(addressWrapper, ADDRESS_PATH);
        } else {
            addressWrapper = (AddressWrapper) readData(ADDRESS_PATH);

        }
    }

    public static void main(String[] args) {
        AddressDataFileStorage addressDataFileStorage = new AddressDataFileStorage();
//        addressDataFileStorage.save(new Address("test2", 11));
//        addressDataFileStorage.save(new Address("test2", 22));
//        addressDataFileStorage.save(new Address("test3", 33));
        System.out.println(Arrays.toString(addressDataFileStorage.getAll()));
    }

    @Override
    public boolean save(Address address) {
        List<Address> addresses = addressWrapper.getList();
        address.setId(incrementNewId(addressWrapper));
        addresses.add(address);
        saveData(addressWrapper, ADDRESS_PATH);
        return true;

    }

    @Override
    public void remove(int id) {
        List<Address> addresses = addressWrapper.getList();
        for (Address address : addresses) {
            if (address.getId() == id) {
                addresses.remove(address);
                saveData(addressWrapper, ADDRESS_PATH);
                return;
            }
        }
    }

    @Override
    public void remove(Address address) {
        List<Address> addresses = addressWrapper.getList();
        for (Address address1 : addresses) {
            if (address1.equals(address)) {
                addresses.remove(address);
                saveData(addressWrapper, ADDRESS_PATH);
                return;
            }
        }
    }

    @Override
    public void updateAddressById(String street, int id) {
        List<Address> addresses = addressWrapper.getList();
        for (Address address : addresses) {
            if (address.getId() == id) {
                address.setStreet(street);
                saveData(addressWrapper, ADDRESS_PATH);
                return;
            }
        }
    }

    @Override
    public void updateHomeById(int home, int id) {
        List<Address> addresses = addressWrapper.getList();
        for (Address address : addresses) {
            if (address.getId() == id) {
                address.setHome(home);
                saveData(addressWrapper, ADDRESS_PATH);
                return;
            }
        }
    }

    @Override
    public Address[] getAll() {
        return getAllList().toArray(new Address[0]);
    }

    private List<Address> getAllList() {
        return addressWrapper.getList();
    }

    @Override
    public Address getById(int id) {
        List<Address> addresses = addressWrapper.getList();
        for (Address address : addresses) {
            if (address.getId() == id) {
                return address;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public Address[] getByStreet(String street) {
        List<Address> addresses = addressWrapper.getList();
        for (int i = 0; i < addresses.size(); i++) {
            if (!addresses.get(i).getStreet().equals(street)) {
                addresses.remove(addresses.get(i));
            }
        }
        return addresses.toArray(new Address[0]);
    }

    @Override
    public Address[] getByHome(int home) {
        List<Address> addresses = addressWrapper.getList();
        for (int i = 0; i < addresses.size(); i++) {
            if (!(addresses.get(i).getHome() == home)) {
                addresses.remove(addresses.get(i));
            }
        }
        return addresses.toArray(new Address[0]);
    }

    @Override
    public boolean contains(int id) {
        List<Address> addresses = addressWrapper.getList();
        for (Address address : addresses) {
            if (address.getId() == id) return true;
        }
        return false;
    }

    @Override
    public boolean contains(Address address) {
        List<Address> addresses = addressWrapper.getList();
        for (Address address1 : addresses) {
            if (address1.equals(address)) return true;
        }
        return false;
    }
}
