package com.company.storage.file.object;

import com.company.entity.Address;
import com.company.entity.Store;
import com.company.storage.StoreStorage;
import com.company.storage.file.object.maps.Maps;
import com.company.storage.file.object.wrapper.AbstractWrapper;
import com.company.storage.file.object.wrapper.StoreWrapper;

import java.util.*;

public class StoreDataFileStorage extends AbstractObjectFileStorage implements StoreStorage {

    private final StoreWrapper storeWrapper;

    {
        if (isEmpty(STORE_PATH)) {
            storeWrapper = new StoreWrapper(0, new ArrayList<>());
            saveData(storeWrapper, STORE_PATH);
        } else {
            storeWrapper = (StoreWrapper) readData(STORE_PATH);
            System.out.println(reedMaps(STORE_MAP_PATH));
        }
    }

    public static void main(String[] args) {
        StoreDataFileStorage storeDataFileStorage = new StoreDataFileStorage();
//        storeDataFileStorage.save(new Store("test1", new Address(1, "test", 1)));
//        storeDataFileStorage.save(new Store("test2", new Address(2, "test", 1)));
//        storeDataFileStorage.save(new Store("test3", new Address(1, "test", 1)));
//        storeDataFileStorage.save(new Store("test4", new Address(2, "test", 1)));
//        storeDataFileStorage.save(new Store("test5", new Address(3, "test", 1)));
        System.out.println(Arrays.toString(storeDataFileStorage.getAll()));
//        storeDataFileStorage.delete(4);
//        storeDataFileStorage.delete(5);
    }


    @Override
    public void save(Store store) {
        List<Store> list = storeWrapper.getList();
        int idStore = incrementNewId(storeWrapper);
        int idAddress = store.getAddress().getId();

        store.setId(idStore);
        saveMap(idStore, idAddress, STORE_MAP_PATH);
        store.setAddress(null);
        list.add(store);
        storeWrapper.setId(idStore);
        storeWrapper.setList(list);
        saveData(storeWrapper, STORE_PATH);
    }

    @Override
    public String updateTitle(String title, int id) {
        List<Store> list = storeWrapper.getList();
        for (Store store : list) {
            if (store.getId() == id) {
                String old = store.getTitle();
                store.setTitle(title);
                storeWrapper.setList(list);
                saveData(storeWrapper, STORE_PATH);
                return old;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public Address updateAddress(Address address, int id) {
        List<Store> list = storeWrapper.getList();
        for (Store store : list) {
            if (store.getId() == id) {
                Address old = store.getAddress();
                Maps mapsFromFile = reedMaps(STORE_MAP_PATH);
                mapsFromFile.getHashMap().remove(id);
                mapsFromFile.getHashMap().put(id, address.getId());
                saveMap(mapsFromFile);
                store.setAddress(address);
                storeWrapper.setList(list);
                saveData(storeWrapper, STORE_PATH);
                return old;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public void delete(int id) {
        List<Store> list = storeWrapper.getList();
        for (Store store : list) {
            if (store.getId() == id) {
                delete(store, list);
                return;
            }
        }
    }

    @Override
    public void delete(String title) {
        List<Store> list = storeWrapper.getList();
        for (Store store : list) {
            if (store.getTitle().equals(title)) {
                delete(store, list);
                return;
            }
        }
    }

    @Override
    public void delete(Store store) {
        List<Store> list = storeWrapper.getList();
        for (Store store1 : list) {
            if (store1.equals(store)) {
                delete(store, list);
                return;
            }
        }
    }

    private void delete(Store store, List<Store> list) {
        Maps mapsFromFile = reedMaps(STORE_MAP_PATH);
        mapsFromFile.getHashMap().remove(store.getId());
        saveMap(mapsFromFile);
        list.remove(store);
        storeWrapper.setList(list);
        saveData(storeWrapper, STORE_PATH);
    }

    @Override
    public Store[] getAll() {
        HashMap<Integer, Integer> hashMap = reedMaps(STORE_MAP_PATH).getHashMap();
//        List<Store> list = storeWrapper.getList();
        List<Store> list = new ArrayList<>(storeWrapper.getList());
        AbstractWrapper<Address> abstractWrapper = (AbstractWrapper<Address>) readData(ADDRESS_PATH);

        for (Store store : list) {
            int id = store.getId();
            Integer addressId = hashMap.get(id);

            Address address = (Address) getObjectById(abstractWrapper, addressId);
            store.setAddress(address);
        }
        return list.toArray(new Store[0]);
    }


    @Override
    public Store getByTitle(String title) {
        List<Store> list = storeWrapper.getList();
        for (Store store : list) {
            if (store.getTitle().equals(title)) {
                return store;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public Store getById(int id) {
        List<Store> list = storeWrapper.getList();
        for (Store store : list) {
            if (store.getId() == id) {
                return store;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public boolean contains(int id) {
        List<Store> list = storeWrapper.getList();
        for (Store store : list) {
            if (store.getId() == id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(String title) {
        List<Store> list = storeWrapper.getList();
        for (Store store : list) {
            if (store.getTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(Store store) {
        List<Store> list = storeWrapper.getList();
        for (Store store1 : list) {
            if (store1.equals(store)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(Address address) {
        List<Store> list = storeWrapper.getList();
        for (Store store : list) {
            if (store.getAddress().equals(address)) {
                return true;
            }
        }
        return false;
    }
}
