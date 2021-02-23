package com.company.storage.db;

import com.company.entity.Genre;
import com.company.ioc.annotation.Component;
import com.company.storage.GenreStorage;
import com.company.storage.db.mapper.GenreMapper;
import com.company.storage.db.util.GenreUtil;

import java.nio.channels.NoConnectionPendingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import static com.company.storage.db.Constant.*;
@Component
public class GenreDbStorage extends AbstractDbStorage implements GenreStorage {

    public static void main(String[] args) {
        GenreDbStorage genreDbStorage= new GenreDbStorage();
        System.out.println(Arrays.toString(genreDbStorage.getAll()));
        System.out.println(Arrays.toString(genreDbStorage.getAll()));
    }

    @Override
    public void add(Genre genre) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_GENRE);
            preparedStatement.setString(1, genre.getName());
            preparedStatement.setString(2, genre.getDescription());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void remove(Genre genre) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_GENRE_BY_GENRE);
            preparedStatement.setString(1, genre.getName());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void remove(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_GENRE_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updateNameById(String name, int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_GENRE_NAME_BY_ID);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updateDescriptionById(String description, int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_GENRE_DESCRIPTION_BY_ID);
            preparedStatement.setString(1, description);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Genre[] getAll() {
        return GenreUtil.getListGenreFromDb(connection).toArray(new Genre[0]);
    }

    @Override
    public Genre getById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_GENRE_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return GenreMapper.getGenre(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

    @Override
    public Genre getByName(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_GENRE_BY_NAME);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            return GenreMapper.getGenre(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

    @Override
    public boolean contains(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CONTAINS_GENRE_BY_ID);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeQuery().next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CONTAINS_GENRE_BY_NAME);
            preparedStatement.setString(1, name);
            return preparedStatement.executeQuery().next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
