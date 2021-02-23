package com.company.storage.inmemory;

import com.company.entity.Address;
import com.company.storage.AddressStorage;

import java.util.Arrays;

public class InMemoryAddressStorage implements AddressStorage {
    private static final Address[] addresses = new Address[50];
    private int size;


    @Override
    public boolean save(Address address) {
        addresses[size++] = address;
        return true;
    }

    @Override
    public void remove(int id) {
        for (int i = 0; i < addresses.length; i++) {
            if (addresses[i] == null) break;
            if (addresses[i].getId() == id) {
                for (int j = i; j < addresses.length - 1; j++) {
                    addresses[i] = addresses[i + 1];
                }
                size--;
                break;
            }
        }
    }

    @Override
    public void remove(Address address) {
        for (int i = 0; i < addresses.length; i++) {
            if (addresses[i] == null) break;
            if (addresses[i].equals(address)) {
                for (int j = i; j < addresses.length - 1; j++) {
                    addresses[i] = addresses[i + 1];
                }
                size--;
                break;
            }
        }
    }

    @Override
    public void updateAddressById(String street, int id) {
        for (int i = 0; i < addresses.length; i++) {
            if (addresses[i] == null) break;
            if (addresses[i].getId() == id) {
                addresses[i].setStreet(street);
                break;
            }

        }

    }

    @Override
    public void updateHomeById(int home, int id) {
        for (int i = 0; i < addresses.length; i++) {
            if (addresses[i] == null) break;
            if (addresses[i].getId() == id) {
                addresses[i].setHome(home);
                break;
            }

        }

    }

    @Override
    public Address[] getAll() {
        return Arrays.copyOf(addresses, size);
    }

    @Override
    public Address getById(int id) {
        for (int i = 0; i < size; i++) {
            if (addresses[i].getId() == id) {
                Address address = addresses[i];
                return address;
            }
        }
        return null;
    }

    @Override
    public Address[] getByStreet(String street) {
        int p = 0;
        for (int i = 0; i < size; i++) {
            if (addresses[i].getStreet().equals(street)) p++;
        }
        Address[] addresses2 = new Address[p];

        for (int i = 0, j = 0; i < size; i++) {
            if (addresses[i].getStreet().equals(street)) {
                addresses2[j++] = addresses[i];
            }
        }
        return addresses2;
    }

    @Override
    public Address[] getByHome(int home) {
        int p = 0;
        for (int i = 0; i < size; i++) {
            if (addresses[i].getHome() == home) p++;
        }
        Address[] addresses2 = new Address[p];

        for (int i = 0, j = 0; i < size; i++) {
            if (addresses[i].getHome() == home) {
                addresses2[j++] = addresses[i];
            }
        }
        return addresses2;
    }

    @Override
    public boolean contains(int id) {
        for (int i = 0; i < addresses.length; i++) {
            if (addresses[i] == null) return false;
            if (addresses[i].getId() == id) return true;
        }
        return false;
    }

    @Override
    public boolean contains(Address address) {
        for (int i = 0; i < addresses.length; i++) {
            if (addresses[i] == null) return false;
            if (addresses[i].equals(address)) return true;
        }
        return false;
    }
}
