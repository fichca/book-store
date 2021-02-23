package com.company.storage.db;

import com.company.entity.Address;
import com.company.ioc.annotation.Component;
import com.company.storage.AddressStorage;
import com.company.storage.db.mapper.AddressMapper;
import com.company.storage.db.util.AddressUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class AddressDbStorage extends AbstractDbStorage implements AddressStorage {
//    private static final String CREATE = "INSERT INTO addresses VALUES(default, ?, ?)";
//    private static final String DELETE_BY_ID = "DELETE FROM addresses WHERE id = ?";
//    private static final String DELETE_BY_ADDRESS = "DELETE FROM addresses WHERE street = ? AND home = ?";
//    private static final String UPDATE_ADDRESS_BY_ID = "update addresses set street = ? where id = ?;";
//    private static final String UPDATE_HOME_BY_ID = "update addresses set home = ? where id = ?;";
//    private static final String GET_BY_ID = "select * from  addresses where id = ?";
//    private static final String GET_BY_STREET = "select * from  addresses where street = ?";
//    private static final String GET_BY_HOME = "select * from  addresses where home = ?";
//    private static final String CONTAINS_BY_ID = "select * from  addresses where id = ?";
//    private static final String CONTAINS_BY_ADDRESS = "select * from  addresses where street = ? AND home = ?";

    public static void main(String[] args) {
        AddressDbStorage addressDbStorage = new AddressDbStorage();
//        addressDbStorage.save(new Address("test4", 4));
//        addressDbStorage.save(new Address("test5", 5));
        System.out.println(Arrays.toString(addressDbStorage.getAll()));
        System.out.println(Arrays.toString(addressDbStorage.getByHome(3)));
//        addressDbStorage.save(new Address("test4", 55));
//        addressDbStorage.remove(1);
//        addressDbStorage.updateAddressById("TEST", 4);
//        addressDbStorage.updateHomeById(11, 4);
//        System.out.println(addressDbStorage.getById(5));
//        System.out.println(addressDbStorage.getById(5));

        System.out.println(Arrays.toString(addressDbStorage.getAll()));
    }

    @Override
    public boolean save(Address address) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.CREATE_ADDRESS);
            preparedStatement.setString(1, address.getStreet());
            preparedStatement.setInt(2, address.getHome());
            preparedStatement.execute();
            preparedStatement.close();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public void remove(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.DELETE_ADDRESS_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void remove(Address address) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.DELETE_BY_ADDRESS);
            preparedStatement.setString(1, address.getStreet());
            preparedStatement.setInt(2, address.getHome());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updateAddressById(String street, int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.UPDATE_ADDRESS_BY_ID);
            preparedStatement.setString(1, street);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updateHomeById(int home, int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.UPDATE_HOME_ADDRESS_BY_ID);
            preparedStatement.setInt(1, home);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Address[] getAll() {
        return AddressUtil.getListAddressFromDb(connection).toArray(new Address[0]);
    }

    @Override
    public Address getById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.GET_ADDRESS_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
//            resultSet.next();
//            String street = resultSet.getString(2);
//            int home = resultSet.getInt(3);
//            return new Address(id, street, home);
            return AddressMapper.getAddress(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

//        List<Address> listAddressFromDb = getListAddressFromDb(connection);
//        for (Address address : listAddressFromDb) {
//            if (address.getId() == id){
//                return address;
//            }
//        }
        throw new NoSuchElementException();
    }

    @Override
    public Address[] getByStreet(String street) {
        List<Address> listAddress = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.GET_ADDRESS_BY_STREET);
            preparedStatement.setString(1, street);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int home = resultSet.getInt(3);
                listAddress.add(new Address(id, street, home));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listAddress.toArray(new Address[0]);
    }

    @Override
    public Address[] getByHome(int home) {
        List<Address> listAddress = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.GET_ADDRESS_BY_HOME);
            preparedStatement.setInt(1, home);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String street = resultSet.getString(2);
                listAddress.add(new Address(id, street, home));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listAddress.toArray(new Address[0]);
    }

    @Override
    public boolean contains(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.CONTAINS_ADDRESS_BY_ID);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeQuery().next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(Address address) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.CONTAINS_BY_ADDRESS);
            preparedStatement.setString(1, address.getStreet());
            preparedStatement.setInt(2, address.getHome());
            return preparedStatement.executeQuery().next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
