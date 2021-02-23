package com.company.service;

import com.company.entity.*;
import com.company.ioc.annotation.Component;
import com.company.ioc.annotation.Qualifier;
import com.company.storage.OrderStorage;
import com.company.storage.db.OrderDbStorage;
import com.company.storage.file.FileOrderStorage;
@Component
public class OrderServiceImpl implements OrderService {
    private final OrderStorage orderStorage;

    public OrderServiceImpl(OrderStorage orderStorage) {
        this.orderStorage = orderStorage;
    }

    @Override
    public boolean save(Order order) {
        if (!orderStorage.contains(order)) return orderStorage.save(order);
        return false;
    }

    @Override
    public void delete(int id) {
        if (orderStorage.contains(id)) orderStorage.delete(id);
    }

    @Override
    public void delete(Order order) {
        if (orderStorage.contains(order)) orderStorage.delete(order);
    }

    @Override
    public Address updateAddressById(Address address, int id) {
        if (orderStorage.contains(id)) return orderStorage.updateAddressById(address, id);
        return null;
    }

    @Override
    public Store updateStoreById(Store store, int id) {
        if (orderStorage.contains(id)) orderStorage.updateStoreById(store, id);
        return null;
    }

    @Override
    public Status updateStatusById(Status status, int id) {
        if (orderStorage.contains(id)) orderStorage.updateStatusById(status, id);
        return null;
    }

    @Override
    public boolean addBookById(Book book, int id) {
        if (orderStorage.contains(id)) orderStorage.addBookById(book, id);
        return false;
    }

    @Override
    public Book deleteBookById(Book book, int id) {
        if (orderStorage.contains(id)) orderStorage.deleteBookById(book, id);
        return null;
    }

    @Override
    public Order getOrderById(int id) {
        if (orderStorage.contains(id)) orderStorage.getOrderById(id);
        return null;
    }

    @Override
    public Order getOrderByOrder(Order order) {
        if (orderStorage.contains(order)) return orderStorage.getOrderByOrder(order);
        return null;
    }

    @Override
    public Order[] getAllByUser(User user) {
        if (orderStorage.contains(user)) return orderStorage.getAllByUser(user);
        return null;
    }

    @Override
    public Order[] getAllByAddress(Address address) {
        if (orderStorage.contains(address)) orderStorage.getAllByAddress(address);
        return null;
    }

    @Override
    public Order[] getAllByStore(Store store) {
        if (orderStorage.contains(store)) return orderStorage.getAllByStore(store);
        return null;
    }

    @Override
    public Order[] getAllByStatus(Status status) {
        if (orderStorage.contains(status)) return orderStorage.getAllByStatus(status);
        return null;
    }

    @Override
    public Order[] getAll() {
        return orderStorage.getAll();
    }
}
