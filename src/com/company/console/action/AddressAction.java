package com.company.console.action;

public interface AddressAction {
    void save();

    void removeById();

    void removeByAddress();

    void updateAddressById();

    void updateHomeById();

    void getAll();

    void getById();

    void getByStreet();

    void getByHome();
}
