package com.company.storage.db;

import com.company.entity.Address;
import com.company.entity.Store;
import com.company.ioc.annotation.Component;
import com.company.storage.StoreStorage;
import com.company.storage.db.mapper.StoreMapper;
import com.company.storage.db.util.StoreUtil;

import java.nio.channels.NoConnectionPendingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.NoSuchElementException;
@Component
public class StoreDbStorage extends AbstractDbStorage implements StoreStorage {

    public static void main(String[] args) {
        StoreDbStorage storeDbStorage = new StoreDbStorage();
//        storeDbStorage.save(new Store("test2", new Address(1, "test", 1)));
//        storeDbStorage.save(new Store("test3", new Address(2, "test", 1)));
//        System.out.println(Arrays.toString(storeDbStorage.getAll()));
//        System.out.println(storeDbStorage.contains(new Address(5, "test", 22)));
//        System.out.println(storeDbStorage.contains(new Address(1, "test", 22)));
//        System.out.println(Arrays.toString(storeDbStorage.getAll()));
        System.out.println(Arrays.toString(storeDbStorage.getAll()));

    }

    @Override
    public void save(Store store) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.CREATE_STORE);
            preparedStatement.setString(1, store.getTitle());
            preparedStatement.setInt(2, store.getAddress().getId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public String updateTitle(String title, int id) {
        try {
            connection.setAutoCommit(false);
            Store storeByID = getStoreById(id);

            PreparedStatement preparedStatement1 = connection.prepareStatement(Constant.UPDATE_STORE_TITLE_BY_ID);

            preparedStatement1.setString(1, title);
            preparedStatement1.setInt(2, id);

            preparedStatement1.execute();
            connection.commit();
            return storeByID.getTitle();

        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        throw new NoSuchElementException();
    }


    @Override
    public Address updateAddress(Address address, int id) {
        try {
            connection.setAutoCommit(false);
            Store storeByID = getStoreById(id);

            PreparedStatement preparedStatement1 = connection.prepareStatement(Constant.UPDATE_STORE_ADDRESS_BY_ID);

            preparedStatement1.setInt(1, address.getId());
            preparedStatement1.setInt(2, id);

            preparedStatement1.execute();
            connection.commit();

            return storeByID.getAddress();

        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        throw new NoSuchElementException();
    }

    private Store getStoreById(int id){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.GET_STORE_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return StoreMapper.getStore(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.DELETE_STORE_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void delete(String title) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.DELETE_STORE_BY_TITLE);
            preparedStatement.setString(1, title);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Store store) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.DELETE_STORE_BY_STORE);
            preparedStatement.setString(1, store.getTitle());
            preparedStatement.setInt(2, store.getAddress().getId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public Store[] getAll() {
        return StoreUtil.getListStoreFromDb(connection).toArray(new Store[0]);
    }

    @Override
    public Store getByTitle(String title) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.GET_STORE_BY_TITLE);
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            return StoreMapper.getStore(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

    @Override
    public Store getById(int id) {
        return getStoreById(id);
    }

    @Override
    public boolean contains(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.CONTAINS_STORE_BY_ID);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeQuery().next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(String title) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.CONTAINS_STORE_BY_TITLE);
            preparedStatement.setString(1, title);
            return preparedStatement.executeQuery().next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(Store store) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.CONTAINS_STORE_BY_STORE);
            preparedStatement.setString(1, store.getTitle());
            preparedStatement.setInt(2, store.getAddress().getId());
            return preparedStatement.executeQuery().next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(Address address) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.CONTAINS_ADDRESS_BY_STORE);
            preparedStatement.setInt(1, address.getId());
            return preparedStatement.executeQuery().next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
