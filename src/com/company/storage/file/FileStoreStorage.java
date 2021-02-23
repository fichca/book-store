package com.company.storage.file;

import com.company.entity.Address;
import com.company.entity.Store;
import com.company.storage.StoreStorage;
import com.company.storage.exceptions.NoResultException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileStoreStorage extends AbstractFileStorage implements StoreStorage {


    private static final int TITLE = 1;
    private static final int ADDRESS_ID = 2;
    private final String CONTAINS = "#ID TITLE ADDRESS_ID \n";
    private final String formatStore = "%d %s %d \n";

    public static void main(String[] args) {
        FileStoreStorage fileStoreStorage = new FileStoreStorage();
        fileStoreStorage.save(new Store("test1", new Address(1, "test1", 1)));
        fileStoreStorage.save(new Store("test2", new Address(2, "test2", 1)));
        fileStoreStorage.save(new Store("test3", new Address(3, "test3", 1)));
        fileStoreStorage.save(new Store("test4", new Address(1, "test4", 1)));
        fileStoreStorage.updateTitle("TEST", 2);
    }


    @Override
    public void save(Store store) {
        try {
            FileWriter writer = new FileWriter(STORE_PATH, true);
            if (isEmpty(STORE_PATH)) {
                writer.write(CONTAINS);
            }
            String format = String.format(this.formatStore, getNewId(), store.getTitle(), store.getAddress().getId());
            writer.write(format);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getNewId() {
        List<Store> allList = getAllList();
        if (isEmpty(STORE_PATH)) return 1;
        Store store = allList.get(allList.size() - 1);
        return store.getId() + 1;
    }

    @Override
    public String updateTitle(String title, int id) {
        if (!contains(id)) throw new NoResultException();
        List<Store> allList = getAllList();
        String old = "";
        for (Store store : allList) {
            if (store.getId() == id) {
                old = store.getTitle();
                store.setTitle(title);
                break;
            }
        }
        clean(STORE_PATH);
        for (Store store : allList) {
            saveForDelete(store);
        }
        if (old.equals("")) throw new NoResultException();
        return old;
    }

    @Override
    public Address updateAddress(Address address, int id) {
        if (!contains(id)) throw new NoResultException();
        List<Store> allList = getAllList();
        Address address1 = null;
        for (Store store : allList) {
            if (store.getId() == id) {
                address1 = store.getAddress();
                store.setAddress(address);
                break;
            }
        }
        clean(STORE_PATH);
        for (Store store : allList) {
            saveForDelete(store);
        }
        return address1;
    }

    @Override
    public void delete(int id) {
        if (!contains(id)) throw new NoResultException();
        List<Store> allList = getAllList();
        for (Store store : allList) {
            if (store.getId() == id) {
                allList.remove(store);
                break;
            }
        }
        clean(STORE_PATH);
        for (Store store : allList) {
            saveForDelete(store);
        }
    }

    @Override
    public void delete(String title) {
        if (!contains(title)) throw new NoResultException();
        List<Store> allList = getAllList();
        for (Store store : allList) {
            if (store.getTitle().equals(title)) {
                allList.remove(store);
                break;
            }
        }
        clean(STORE_PATH);
        for (Store store : allList) {
            saveForDelete(store);
        }
    }

    @Override
    public void delete(Store store) {
        if (!contains(store)) throw new NoResultException();
        List<Store> allList = getAllList();
        for (Store stores : allList) {
            if (stores.equals(store)) {
                allList.remove(store);
                break;
            }
        }
        clean(STORE_PATH);
        for (Store stores : allList) {
            saveForDelete(stores);
        }
    }

    public void saveForDelete(Store store) {
        try {
            FileWriter writer = new FileWriter(STORE_PATH, true);
            if (isEmpty(STORE_PATH)) {
                writer.write(CONTAINS);
            }
            String format = String.format(this.formatStore, store.getId(), store.getTitle(), store.getAddress().getId());
            writer.write(format);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Store[] getAll() {
        return getAllList().toArray(new Store[0]);
    }

    private List<Store> getAllList() {
        List<Store> list = new ArrayList<>();
        if (isEmpty(STORE_PATH)) return new ArrayList<>();
        try {
            FileReader reader = new FileReader(STORE_PATH);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals("#ID TITLE ADDRESS_ID ")) continue;
                String[] split = line.split(SPLIT);
                Address address = getAddressById(Integer.parseInt(split[ADDRESS_ID]));
                list.add(new Store(Integer.parseInt(split[ID]), split[TITLE], address));
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new NoResultException();
    }

//    private Address getAddressById(int id){
//        try {
//            reader = new FileReader(pathAddress);
//            BufferedReader bufferedReader = new BufferedReader(reader);
//            String line;
//            while ((line = bufferedReader.readLine()) != null){
//                String[] split = line.split(SPLIT);
//                if (Integer.parseInt(split[ID]) == id){
//                    return new Address(id, split[1], Integer.parseInt(split[2]));
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        throw  new NoResultException(id + "not found");
//    }

    @Override
    public Store getByTitle(String title) {
        if (!contains(title)) throw new NoResultException();
        List<Store> allList = getAllList();
        for (Store store : allList) {
            if (store.getTitle().equals(title)) {
                return store;
            }
        }
        throw new NoResultException();
    }

    @Override
    public Store getById(int id) {
        if (!contains(id)) throw new NoResultException();
        List<Store> allList = getAllList();
        for (Store store : allList) {
            if (store.getId() == id) {
                return store;
            }
        }
        throw new NoResultException();
    }


    @Override
    public boolean contains(int id) {
        List<Store> allList = getAllList();
        for (Store store : allList) {
            if (store.getId() == id) return true;
        }
        return false;
    }

    @Override
    public boolean contains(String title) {
        List<Store> allList = getAllList();
        for (Store store : allList) {
            if (store.getTitle().equals(title)) return true;
        }
        return false;
    }

    @Override
    public boolean contains(Store store) {
        List<Store> allList = getAllList();
        for (Store stores : allList) {
            if (stores.equals(store)) return true;
        }
        return false;
    }

    @Override
    public boolean contains(Address address) {
        List<Store> allList = getAllList();
        for (Store store : allList) {
            if (store.getAddress().equals(address)) return true;
        }
        return false;
    }
}
