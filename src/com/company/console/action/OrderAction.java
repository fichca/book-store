package com.company.console.action;

public interface OrderAction {

    void save();

    void deleteById();

    void deleteByOrder();

    void updateAddressById();

    void updateStoreById();

    void updateStatusById();

    void addBookById();

    void deleteBookById();

    void getAllOrderByUserForUser();

    void getOrderById();

    void getOrderByOrder();

    void getAllByUser();

    void getAllByAddress();

    void getAllByStore();

    void getAllByStatus();

    void getAllCloseStatus();

    void getAllActiveStatus();

    void getAll();
}
