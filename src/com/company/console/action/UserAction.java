package com.company.console.action;

public interface UserAction {
    void save();

    void registration();

    void authorization();

    void logout();

    void deleteById();

    void deleteByLogin();

    void deleteByUser();

    void updateFirstNameById();

    void updateLastNameById();

    void updateAgeById();

    void updateRoleById();

    void updateAddressById();

    void updateFirstName();

    void updateLastName();

    void updateAddress();

    void updateAge();

    void getAll();

    void getAllByFirstName();

    void getAllByLastName();

    void getAllByAge();

    void getById();

    void getByLogin();

    void getAllByRole();

    void getHistory();
}
