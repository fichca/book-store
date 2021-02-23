package com.company.storage.db.mapper;

import com.company.entity.Address;
import com.company.entity.Author;
import com.company.entity.Book;
import com.company.entity.Store;

import java.nio.channels.NoConnectionPendingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StoreMapper {

    private static Store createStore(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        String title = resultSet.getString(2);
        int idAddress = resultSet.getInt(4);
        String street = resultSet.getString(5);
        int home = resultSet.getInt(6);
        return new Store(id, title, new Address(idAddress, street, home));
    }

    public static Store getStore(ResultSet resultSet){
        try {
            resultSet.next();
            return createStore(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

    public static List<Store> getStoreList(ResultSet resultSet){
        List<Store> stores = new ArrayList<>();
        while (true){
            while (true) {
                try {
                    while (resultSet.next()) {
                        Store store = createStore(resultSet);
                        stores.add(store);
                    }
                    return stores;
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}
