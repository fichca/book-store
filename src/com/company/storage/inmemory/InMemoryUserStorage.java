package com.company.storage.inmemory;

import com.company.entity.Address;
import com.company.entity.Role;
import com.company.entity.User;
import com.company.storage.UserStorage;

import java.util.Arrays;

public class InMemoryUserStorage implements UserStorage {
    private static final User[] users = new User[50];

    static {
        users[0] = new User(0, "admin", "admin", "Admin", "Admin", 22, Role.ADMIN, new Address(0, "malina", 44));
        users[1] = new User(1, "user", "user", "User", "User", 33, Role.USER, new Address(1, "malina", 40));
    }

    @Override
    public void save(User user) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) {
                users[i] = user;
                break;
            }
        }
    }

    @Override
    public boolean delete(int id) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getId() == id) {
                for (int i1 = i; i1 < users.length - 1; i1++) {
                    users[i1] = users[i1 + 1];
                }
                break;
            }
        }
        return false;
    }

    @Override
    public void delete(String login) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getLogin().equals(login)) {
                for (int i1 = i; i1 < users.length - 1; i1++) {
                    users[i1] = users[i1 + 1];
                }
                break;
            }
        }
    }

    @Override
    public void delete(User user) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].equals(user)) {
                for (int i1 = i; i1 < users.length - 1; i1++) {
                    users[i1] = users[i1 + 1];
                }
                break;
            }
        }
    }

    @Override
    public String updateFirstNameById(String firstName, int id) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getId() == id) {
                String oldFirstName = users[i].getFirstName();
                users[i].setFirstName(firstName);
                return oldFirstName;
            }
        }
        return null;
    }

    @Override
    public String updateLastNameById(String lastName, int id) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getId() == id) {
                String oldLastName = users[i].getLastName();
                users[i].setLastName(lastName);
                return oldLastName;
            }
        }
        return null;
    }

    @Override
    public int updateAgeById(int age, int id) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getId() == id) {
                int oldAge = users[i].getAge();
                users[i].setAge(age);
                return oldAge;
            }
        }
        return -1;
    }

    @Override
    public Role updateRoleById(Role role, int id) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getId() == id) {
                Role role1 = users[i].getRole();
                users[i].setRole(role);
                return role1;
            }
        }
        return null;
    }

    @Override
    public Address updateAddressById(Address address, int id) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getId() == id) {
                Address address1 = users[i].getAddress();
                users[i].setAddress(address);
                return address1;
            }
        }
        return null;
    }

    @Override
    public User[] getAll() {
        int p = 0;
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            p++;
        }
        User[] user = Arrays.copyOf(users, p);

        return user;
    }

    @Override
    public User[] getAllByFirstName(String firstName) {
        int p = 0;
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getFirstName().equals(firstName)) {
                p++;
            }
        }
        User[] user = new User[p];
        for (int i = 0, j = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getFirstName().equals(firstName)) {
                user[j] = users[i];
                j++;
            }

        }

        return user;
    }

    @Override
    public User[] getAllByLastName(String lastName) {
        int p = 0;
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getLastName().equals(lastName)) {
                p++;
            }
        }
        User[] user = new User[p];
        for (int i = 0, j = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getLastName().equals(lastName)) {
                user[j] = users[i];
                j++;
            }

        }

        return user;
    }

    @Override
    public User[] getAllByAge(int age) {
        int p = 0;
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getAge() == age) {
                p++;
            }
        }
        User[] user = new User[p];
        for (int i = 0, j = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getAge() == age) {
                user[j] = users[i];
                j++;
            }

        }

        return user;
    }

    @Override
    public User getById(int id) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getId() == id) {
                return users[i];
            }
        }
        return null;
    }

    @Override
    public User getByLogin(String login) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getLogin().equals(login)) {
                return users[i];
            }
        }
        return null;
    }

    @Override
    public User[] getAllByRole(Role role) {
        int p = 0;
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getRole().equals(role)) {
                p++;
            }
        }
        User[] user = new User[p];
        for (int i = 0, j = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getRole().equals(role)) {
                user[j] = users[i];
                j++;
            }

        }

        return user;
    }

    @Override
    public boolean contains(int id) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) return false;
            if (users[i].getId() == id) return true;
        }
        return false;
    }

    @Override
    public boolean contains(String login) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(User user) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) return false;
            if (users[i].equals(users)) return true;
        }
        return false;
    }

    @Override
    public boolean contains(Role role) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) return false;
            if (users[i].getRole().equals(role)) return true;
        }
        return false;
    }
}
