package com.company.service;

import com.company.entity.Address;
import com.company.entity.Role;
import com.company.entity.User;
import com.company.ioc.annotation.Component;
import com.company.ioc.annotation.Qualifier;
import com.company.storage.UserStorage;
import com.company.storage.db.UserDbStorage;
import com.company.storage.file.object.UserDataFileStorage;
@Component
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    public UserServiceImpl(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public void save(User user) {
        if (!userStorage.contains(user)) {
            userStorage.save(user);
        }
    }

    @Override
    public void delete(int id) {
        if (userStorage.delete(id)) {
            userStorage.delete(id);
        }
    }

    @Override
    public void delete(String login) {
        if (userStorage.contains(login)) {
            userStorage.delete(login);
        }
    }

    @Override
    public void delete(User user) {
        if (userStorage.contains(user)) {
            userStorage.delete(user);
        }
    }

    @Override
    public String updateFirstNameById(String firstName, int id) {
        if (userStorage.contains(id)) {
            return userStorage.updateFirstNameById(firstName, id);
        }
        return null;
    }

    @Override
    public String updateLastNameById(String lastName, int id) {
        if (userStorage.contains(id)) {
            return userStorage.updateLastNameById(lastName, id);
        }
        return null;
    }

    @Override
    public int updateAgeById(int age, int id) {
        if (userStorage.contains(id)) {
            return userStorage.updateAgeById(age, id);
        }
        return 0;
    }

    @Override
    public Role updateRoleById(Role role, int id) {
        if (userStorage.contains(id)) {
            return userStorage.updateRoleById(role, id);
        }
        return null;
    }

    @Override
    public Address updateAddressById(Address address, int id) {
        if (userStorage.contains(id)) {
            return userStorage.updateAddressById(address, id);
        }
        return null;
    }

    @Override
    public User[] getAll() {
        return userStorage.getAll();
    }

    @Override
    public User[] getAllByFirstName(String firstName) {
        if (userStorage.contains(firstName)) {
            return userStorage.getAllByFirstName(firstName);
        }
        return null;
    }

    @Override
    public User[] getAllByLastName(String lastName) {
        if (userStorage.contains(lastName)) {
            return userStorage.getAllByLastName(lastName);
        }
        return null;
    }

    @Override
    public User[] getAllByAge(int age) {
        return userStorage.getAllByAge(age);
    }

    @Override
    public User getById(int id) {
        if (userStorage.contains(id)) {
            return userStorage.getById(id);
        }
        return null;
    }

    @Override
    public User getByLogin(String login) {
        if (userStorage.contains(login)) {
            return userStorage.getByLogin(login);
        }
        return null;
    }

    @Override
    public User[] getAllByRole(Role role) {
        if (userStorage.contains(role)) {
            return userStorage.getAllByRole(role);
        }
        return null;
    }
}
