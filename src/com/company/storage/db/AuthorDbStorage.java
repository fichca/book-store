package com.company.storage.db;

import com.company.entity.Author;
import com.company.ioc.annotation.Component;
import com.company.storage.AuthorStorage;
import com.company.storage.db.util.AuthorUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.NoSuchElementException;
@Component
public class AuthorDbStorage extends AbstractDbStorage implements AuthorStorage {

    public static void main(String[] args) {
        AuthorDbStorage authorDbStorage = new AuthorDbStorage();
//        authorDbStorage.save(new Author("test1"));
//        authorDbStorage.save(new Author("test2"));
//        authorDbStorage.save(new Author("test3"));
        authorDbStorage.updateFullName("test4", 4);
        System.out.println(Arrays.toString(authorDbStorage.getAll()));
//        authorDbStorage.save(new Author("test1"));
//        authorDbStorage.save(new Author("test2"));
        System.out.println(Arrays.toString(authorDbStorage.getAll()));
//
//        System.out.println(Arrays.toString(authorDbStorage.getAll()));
    }

    @Override
    public void save(Author author) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.CREATE_AUTHOR);
            preparedStatement.setString(1, author.getFullName());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void remove(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.DELETE_AUTHOR_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void remove(String fullName) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.DELETE_AUTHOR_FULL_NAME);
            preparedStatement.setString(1, fullName);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public String updateFullName(String newFullName, int id) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.GET_AUTHOR_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet1 = preparedStatement.executeQuery();
            resultSet1.next();
            String old = resultSet1.getString(2);

            PreparedStatement preparedStatement1 = connection.prepareStatement(Constant.UPDATE_AUTHOR_FULL_NAME);

            preparedStatement1.setString(1, newFullName);
            preparedStatement1.setInt(2, id);

            preparedStatement1.execute();
            connection.commit();
            return old;

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
    public Author[] getAll() {

        return AuthorUtil.getListAuthorFromDb(connection).toArray(new Author[0]);
    }

    @Override
    public Author getById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.GET_AUTHOR_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String fullName = resultSet.getString(2);
            return new Author(id, fullName);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Author fullName(String fullName) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.GET_AUTHOR_FULL_NAME);
            preparedStatement.setString(1, fullName);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int id = resultSet.getInt(1);
            return new Author(id, fullName);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean contains(String fullName) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.CONTAINS_BY_AUTHOR);
            preparedStatement.setString(1, fullName);
            return preparedStatement.executeQuery().next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.CONTAINS_AUTHOR_BY_ID);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeQuery().next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
