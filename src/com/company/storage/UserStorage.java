package com.company.storage;

import com.company.entity.Address;
import com.company.entity.Role;
import com.company.entity.User;

public interface UserStorage {
    void save(User user);

    boolean delete(int id);

    void delete(String login);

    void delete(User user);

    String updateFirstNameById(String firstName, int id);

    String updateLastNameById(String lastName, int id);

    int updateAgeById(int age, int id);

    Role updateRoleById(Role role, int id);

    Address updateAddressById(Address address, int id);

    User[] getAll();

    User[] getAllByFirstName(String firstName);

    User[] getAllByLastName(String lastName);

    User[] getAllByAge(int age);

    User getById(int id);

    User getByLogin(String login);

    User[] getAllByRole(Role role);

    boolean contains(int id);
    boolean contains(String login);

    boolean contains(User user);

    boolean contains(Role role);
}
