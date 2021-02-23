package com.company.storage.file.object;

import com.company.entity.AbstractEntity;
import com.company.storage.file.object.maps.AbstractMaps;
import com.company.storage.file.object.maps.Maps;
import com.company.storage.file.object.maps.OrderMaps;
import com.company.storage.file.object.wrapper.AbstractWrapper;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class AbstractObjectFileStorage implements Serializable {

    protected static final String ADDRESS_PATH = "address-data.txt";
    protected static final String ORDER_PATH = "orders-data.txt";
    protected static final String ORDER_MAP_PATH = "orders-map.txt";
    protected static final String BOOK_PATH = "books-data.txt";
    protected static final String BOOK_MAP_PATH = "books-map.txt";
    protected static final String STORE_PATH = "stores-data.txt";
    protected static final String STORE_MAP_PATH = "stores-map.txt";
    protected static final String USER_PATH = "users-data.txt";
    protected static final String USER_MAP_PATH = "users-map.txt";
    protected static final String AUTHOR_PATH = "authors-data.txt";


    protected int incrementNewId(AbstractWrapper<?> object) {
        int newId = object.getId() + 1;
        object.setId(newId);
        return newId;
    }

    protected boolean isEmpty(String path) {
        return new File(path).length() == 0;
    }

    protected AbstractWrapper<?> readData(String path) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(path))) {
            return (AbstractWrapper<?>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new NoSuchElementException();
    }

    protected void saveData(AbstractWrapper<?> object, String path) {
        saveObject(object, path);
    }

    protected void saveMap(int key, int value, String path) {
        Maps maps = new Maps(path, reedMaps(path).getHashMap());
        HashMap<Integer, Integer> hashMap = maps.getHashMap();
        hashMap.put(key, value);
        saveObject(maps, path);
    }

    protected void saveMap(AbstractMaps maps) {
        saveObject(maps, maps.getPath());
    }


    protected void saveMap(int key, List<Integer> list, String path) {
        OrderMaps orderMaps = new OrderMaps(path, reedOrderMaps(path).getHashMap());
        HashMap<Integer, List<Integer>> hashMap = orderMaps.getHashMap();
        hashMap.put(key, list);
        saveObject(orderMaps, path);
    }

    protected Maps reedMaps(String path) {
        if (isEmpty(path)) {
            saveObject(new Maps(path, new HashMap<>()), path);
        }
        return (Maps) getObject(path);
    }

    protected OrderMaps reedOrderMaps(String path) {
        if (isEmpty(path)) {
            saveObject(new OrderMaps(path, new HashMap<>()), path);
        }
        return (OrderMaps) getObject(path);
    }


    protected Object getObjectById(AbstractWrapper abstractWrapper, int id) {
        List<AbstractEntity> list = abstractWrapper.getList();
        for (AbstractEntity abstractEntity : list) {
            if (abstractEntity.getId() == id) {
                return abstractEntity;
            }
        }
        throw new NoSuchElementException();
    }

    private void saveObject(Object object, String path) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(path))) {
            objectOutputStream.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Object getObject(String path) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(path));
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new NoSuchElementException();
    }

}
