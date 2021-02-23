package com.company.storage.inmemory;

import com.company.entity.Address;
import com.company.entity.Store;
import com.company.storage.StoreStorage;

import java.util.Arrays;

public class InMemoryStoreStorage implements StoreStorage {


    private static final Store[] stores = new Store[50];
    private static int size = 0;

    static {
        stores[0] = new Store(1, "test", new Address("test", 2));
        stores[1] = new Store(2, "test2", new Address("test2", 22));
    }

    @Override
    public void save(Store store) {
        stores[size++] = store;
    }

    @Override
    public String updateTitle(String title, int id) {

        for (int i = 0; i < size; i++) {
            if (stores[i].getId() == id) {
                String oldTitle = stores[i].getTitle();
                stores[i].setTitle(title);
                return oldTitle;
            }
        }
        return null;
    }

    @Override
    public Address updateAddress(Address address, int id) {
        for (int i = 0; i < size; i++) {
            if (stores[i].getId() == id) {
                Address oldAddress = stores[i].getAddress();
                stores[i].setAddress(address);
                return oldAddress;
            }
        }
        return null;
    }

    @Override
    public void delete(int id) {
        for (int i = 0; i < size; i++) {
            if (stores[i].getId() == id) {
                for (int j = i; j < size; j++) {
                    stores[j] = stores[j++];
                }
                break;
            }
        }
    }

    @Override
    public void delete(String title) {
        for (int i = 0; i < size; i++) {
            if (stores[i].getTitle().equals(title)) {
                for (int j = i; j < size; j++) {
                    stores[j] = stores[j++];
                }
                break;
            }
        }
    }

    @Override
    public void delete(Store store) {
        for (int i = 0; i < size; i++) {
            if (stores[i].equals(store)) {
                for (int j = i; j < size; j++) {
                    stores[j] = stores[j++];
                }
                break;
            }
        }

    }

    @Override
    public Store[] getAll() {
        int p = 0;
        for (int i = 0; i < stores.length; i++) {
            if (stores[i] == null) break;
            p++;
        }
        Store[] store = Arrays.copyOf(stores, p);

        return store;
    }

    @Override
    public Store getByTitle(String title) {
        for (int i = 0; i < stores.length; i++) {
            if (stores[i] == null) break;
            if (stores[i].getTitle().equals(title)) {
                return stores[i];
            }
        }

        return null;
    }

    @Override
    public Store getById(int id) {
        for (int i = 0; i < stores.length; i++) {
            if (stores[i] == null) break;
            if (stores[i].getId() == id) {
                return stores[i];
            }
        }
        return null;
    }

    @Override
    public boolean contains(int id) {
        for (int i = 0; i < stores.length; i++) {
            if (stores[i] == null) return false;
            if (stores[i].getId() == id) return true;
        }
        return false;
    }

    @Override
    public boolean contains(String title) {
        for (int i = 0; i < stores.length; i++) {
            if (stores[i] == null) return false;
            if (stores[i].getTitle().equals(title)) return true;
        }
        return false;
    }

    @Override
    public boolean contains(Store store) {
        for (int i = 0; i < stores.length; i++) {
            if (stores[i] == null) return false;
            if (stores[i].equals(store)) return true;
        }
        return false;
    }

    @Override
    public boolean contains(Address address) {
        for (int i = 0; i < stores.length; i++) {
            if (address == null) return false;
            if (stores[i].getAddress().equals(address)) return true;
        }
        return false;
    }
}
