package com.company.service;

import com.company.entity.*;

public interface OrderService {
    boolean save(Order order);

    void delete(int id);

    void delete(Order order);

    Address updateAddressById(Address address, int id);

    Store updateStoreById(Store store, int id);

    Status updateStatusById(Status status, int id);

    boolean addBookById(Book book, int id);

    Book deleteBookById(Book book, int id);

    Order getOrderById(int id);

    Order getOrderByOrder(Order order);

    Order[] getAllByUser(User user);

    Order[] getAllByAddress(Address address);

    Order[] getAllByStore(Store store);

    Order[] getAllByStatus(Status status);

    Order[] getAll();
}
