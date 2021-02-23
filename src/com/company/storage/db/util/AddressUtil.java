package com.company.storage.db.util;

import com.company.entity.Address;
import com.company.storage.db.AbstractDbStorage;
import com.company.storage.db.mapper.AddressMapper;

import java.nio.channels.NoConnectionPendingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AddressUtil extends AbstractDbStorage {
    public static List<Address> getListAddressFromDb(Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from addresses");
            ResultSet resultSet = preparedStatement.executeQuery();
            return AddressMapper.getAddressList(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }
}
