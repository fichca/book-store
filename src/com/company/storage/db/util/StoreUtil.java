package com.company.storage.db.util;

import com.company.entity.Store;
import com.company.storage.db.AbstractDbStorage;
import com.company.storage.db.mapper.StoreMapper;

import java.nio.channels.NoConnectionPendingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StoreUtil extends AbstractDbStorage {

    public static List<Store> getListStoreFromDb(Connection connection){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from stores s join addresses a on a.id = s.address_id");
            ResultSet resultSet = preparedStatement.executeQuery();
            return StoreMapper.getStoreList(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }
}
