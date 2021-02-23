package com.company.storage.db.mapper;

import com.company.entity.Address;

import java.awt.*;
import java.nio.channels.NoConnectionPendingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddressMapper {

    private static Address createAddress(ResultSet resultSet) throws SQLException {
        int idAddress = resultSet.getInt(1);
        String street = resultSet.getString(2);
        int home = resultSet.getInt(3);
        return new Address(idAddress, street, home);
    }

    public static Address getAddress(ResultSet resultSet) {
        try {
            resultSet.next();
            return createAddress(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

    public static List<Address> getAddressList(ResultSet resultSet) {
        List<Address> addresses = new ArrayList<>();
        while (true) {
            try {
                while (resultSet.next()) {
                    Address address = createAddress(resultSet);
                    addresses.add(address);
                }
                return addresses;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }
}
