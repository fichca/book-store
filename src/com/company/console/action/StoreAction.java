package com.company.console.action;

public interface StoreAction {
    void save();

    void updateTitle();

    void updateAddress();

    void deleteById();

    void deleteByTitle();

    void deleteByStore();

    void getAll();

    void getByTitle();

    void getById();
}
