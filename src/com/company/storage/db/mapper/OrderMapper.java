package com.company.storage.db.mapper;

import com.company.entity.*;

import java.nio.channels.NoConnectionPendingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderMapper {
    public static int getIdStatus(ResultSet resultSet) {
        try {
            resultSet.next();
            return resultSet.getInt(1);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

    public static Order createOrder(ResultSet resultSet) throws SQLException {
        int idOrder = resultSet.getInt(1);
        int totalPrice = resultSet.getInt(3);

        Timestamp date = resultSet.getTimestamp(6);

        int idUser = resultSet.getInt(7);
        String login = resultSet.getString(8);
        String password = resultSet.getString(9);
        String firstName = resultSet.getString(10);
        String lastName = resultSet.getString(11);
        int age = resultSet.getInt(12);

        int idAddress = resultSet.getInt(15);
        String street = resultSet.getString(16);
        int home = resultSet.getInt(17);

        String role = resultSet.getString(19);
        String status = resultSet.getString(21);

        return new Order(idOrder, null, null, null, new User(idUser, login, password, firstName, lastName, age, Role.valueOf(role), new Address(idAddress, street, home)), totalPrice, Status.valueOf(status), date);
    }

    public static Book[] getBooks(ResultSet resultSet) throws SQLException {

        List<Book> books = new ArrayList<>();
        while (resultSet.next()) {
            books.add(createBook(resultSet));
        }
        return books.toArray(new Book[0]);
    }

    private static Book createBook(ResultSet resultSet) throws SQLException {
        int idBook = resultSet.getInt(3);
        String title = resultSet.getString(4);
        String description = resultSet.getString(5);
        int price = resultSet.getInt(6);
        int idAuthor = resultSet.getInt(9);
        String titleAuthor = resultSet.getString(10);

        int idGenre = resultSet.getInt(11);
        String name = resultSet.getString(12);
        String descriptionGenre = resultSet.getString(13);

        return new Book(idBook, title, description, new Author(idAuthor, titleAuthor), price, new Genre(idGenre, name, descriptionGenre));
    }
    public static List<Book> getBooksList(ResultSet resultSet){

        List<Book> books = new ArrayList<>();
        while (true){
            while (true) {
                try {
                    while (resultSet.next()) {
                        Book book = createBook(resultSet);
                        books.add(book);
                    }
                    return books;
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }


    private static Address createOrderAddress(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(3);
        String street = resultSet.getString(4);
        int home = resultSet.getInt(5);
        return new Address(id, street, home);
    }

    private static Store createOrderStore(ResultSet resultSet) throws SQLException {

        int idStore = resultSet.getInt(3);
        String title = resultSet.getString(4);

        int idAddress = resultSet.getInt(6);
        String street = resultSet.getString(7);
        int home = resultSet.getInt(8);

        return new Store(idStore, title, new Address(idAddress, street, home));
    }

    public static Address getOrderAddress(ResultSet resultSet) throws SQLException {
        resultSet.next();
        return createOrderAddress(resultSet);

    }

    public static Store getOrderStore(ResultSet resultSet) throws SQLException {
        resultSet.next();
        return createOrderStore(resultSet);
    }
}
