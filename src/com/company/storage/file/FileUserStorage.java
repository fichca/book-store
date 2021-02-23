package com.company.storage.file;

import com.company.entity.Address;
import com.company.entity.Role;
import com.company.entity.User;
import com.company.storage.UserStorage;
import com.company.storage.exceptions.NoResultException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUserStorage extends AbstractFileStorage implements UserStorage {


    private static final int LOGIN = 1;
    private static final int PASSWORD = 2;
    private static final int FIRST_NAME = 3;
    private static final int LAST_NAME = 4;
    private static final int AGE = 5;
    private static final int ROLE = 6;
    private static final int ADDRESS_ID = 7;
    String CONTAINS = "#ID LOGIN PASSWORD FIRST_NAME LAST_NAME AGE ROLE ADDRESS_ID \n";
    private final String formatUser = "%d %s %s %s %s %d %s %d \n";

    public static void main(String[] args) {
        Role role = Role.USER;
        Role role1 = Role.ADMIN;
        FileUserStorage fileUserStorage = new FileUserStorage();
        fileUserStorage.save(new User("user", "user", "user", "user", 24, role, new Address(1, "test1", 1)));
        fileUserStorage.save(new User("admin", "admin", "admin", "admin", 24, role1, new Address(2, "test2", 2)));
//        fileUserStorage.save(new User("test3", "test3", "test3", "test3", 1, role, new Address(3, "test3", 3)));
//            fileUserStorage.delete(2);
    }

    @Override
    public void save(User user) {
        try {
            FileWriter writer = new FileWriter(USER_PATH, true);
            if (isEmpty(USER_PATH)) writer.write(CONTAINS);
            String format = String.format(this.formatUser, getNewId(), user.getLogin(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getAge(), user.getRole().name(), user.getAddress().getId());
            writer.write(format);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getNewId() {
        List<User> allList = getAllList();
        if (isEmpty(USER_PATH)) return 1;
        User user = allList.get(allList.size() - 1);
        return user.getId() + 1;
    }

    @Override
    public boolean delete(int id) {
        if (!contains(id)) return false;
        List<User> allList = getAllList();
        for (User user : allList) {
            if (user.getId() == id) {
                allList.remove(user);
                break;
            }
        }
        clean(USER_PATH);
        for (User user : allList) {
            saveForDelete(user);
        }
        return true;
    }

    @Override
    public void delete(String login) {
        if (!contains(login)) throw new NoResultException();
        List<User> allList = getAllList();
        for (User user : allList) {
            if (user.getLogin().equals(login)) {
                allList.remove(user);
                break;
            }
        }

        clean(USER_PATH);
        for (User user : allList) {
            saveForDelete(user);
        }
    }

    @Override
    public void delete(User user) {
        if (!contains(user)) throw new NoResultException();
        List<User> allList = getAllList();
        for (User users : allList) {
            if (users.equals(user)) {
                allList.remove(users);
                break;
            }
        }

        clean(USER_PATH);
        for (User users : allList) {
            saveForDelete(users);
        }
    }

    public void saveForDelete(User user) {
        try {
            FileWriter writer = new FileWriter(USER_PATH, true);
            if (isEmpty(USER_PATH)) writer.write(CONTAINS);
            String format = String.format(this.formatUser, user.getId(), user.getLogin(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getAge(), user.getRole().name(), user.getAddress().getId());
            writer.write(format);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String updateFirstNameById(String firstName, int id) {
        if (!contains(id)) throw new NoResultException();
        List<User> allList = getAllList();
        String old = null;
        for (User user : allList) {
            if (user.getId() == id) {
                old = user.getFirstName();
                user.setFirstName(firstName);
                break;
            }
        }
        clean(USER_PATH);
        for (User user : allList) {
            saveForDelete(user);
        }
        return old;
    }

    @Override
    public String updateLastNameById(String lastName, int id) {
        if (!contains(id)) throw new NoResultException();
        List<User> allList = getAllList();
        String old = null;
        for (User user : allList) {
            if (user.getId() == id) {
                old = user.getLastName();
                user.setLastName(lastName);
                break;
            }
        }
        clean(USER_PATH);
        for (User user : allList) {
            saveForDelete(user);
        }
        return old;
    }

    @Override
    public int updateAgeById(int age, int id) {
        if (!contains(id)) throw new NoResultException();
        List<User> allList = getAllList();
        int old = 0;
        for (User user : allList) {
            if (user.getId() == id) {
                old = user.getAge();
                user.setAge(age);
                break;
            }
        }
        clean(USER_PATH);
        for (User user : allList) {
            saveForDelete(user);
        }
        return old;
    }

    @Override
    public Role updateRoleById(Role role, int id) {
        if (!contains(id)) throw new NoResultException();
        List<User> allList = getAllList();
        Role old = null;
        for (User user : allList) {
            if (user.getId() == id) {
                old = user.getRole();
                user.setRole(role);
                break;
            }
        }
        clean(USER_PATH);
        for (User user : allList) {
            saveForDelete(user);
        }
        return old;
    }

    @Override
    public Address updateAddressById(Address address, int id) {
        if (!contains(id)) throw new NoResultException();
        List<User> allList = getAllList();
        Address old = null;
        for (User user : allList) {
            if (user.getId() == id) {
                old = user.getAddress();
                user.setAddress(address);
                break;
            }
        }
        clean(USER_PATH);
        for (User user : allList) {
            saveForDelete(user);
        }
        return old;
    }

    @Override
    public User[] getAll() {
        return getAllList().toArray(new User[0]);
    }


    private List<User> getAllList() {
        List<User> list = new ArrayList<>();
        if (isEmpty(USER_PATH)) return new ArrayList<>();
        try {
            FileReader reader = new FileReader(USER_PATH);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals("#ID LOGIN PASSWORD FIRST_NAME LAST_NAME AGE ROLE ADDRESS_ID ")) continue;
                String[] split = line.split(SPLIT);
                Address address = getAddressById(Integer.parseInt(split[ADDRESS_ID]));
                Role role = Role.valueOf(split[ROLE]);
                list.add(new User(Integer.parseInt(split[ID]), split[LOGIN], split[PASSWORD], split[FIRST_NAME], split[LAST_NAME], Integer.parseInt(split[AGE]), role, address));
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new NoResultException();
    }


    @Override
    public User[] getAllByFirstName(String firstName) {
        if (isEmpty(USER_PATH)) throw new NoResultException("Empty");
        if (!contains(firstName)) throw new NoResultException("Not found");
        List<User> allList = getAllList();
        List<User> allSubList = new ArrayList<>();

        for (User user : allList) {
            if (user.getFirstName().equals(firstName)) {
                allSubList.add(user);
            }
        }
        return allSubList.toArray(new User[0]);
    }

    @Override
    public User[] getAllByLastName(String lastName) {
        if (isEmpty(USER_PATH)) throw new NoResultException("Empty");
        if (!contains(lastName)) throw new NoResultException("Not found");
        List<User> allList = getAllList();
        List<User> allSubList = new ArrayList<>();

        for (User user : allList) {
            if (user.getLastName().equals(lastName)) {
                allSubList.add(user);
            }
        }
        return allSubList.toArray(new User[0]);
    }

    @Override
    public User[] getAllByAge(int age) {
        if (isEmpty(USER_PATH)) throw new NoResultException("Empty");
        if (!contains(age)) throw new NoResultException("Not found");
        List<User> allList = getAllList();
        List<User> allSubList = new ArrayList<>();

        for (User user : allList) {
            if (user.getAge() == age) {
                allSubList.add(user);
            }
        }
        return allSubList.toArray(new User[0]);
    }

    @Override
    public User getById(int id) {
        if (isEmpty(USER_PATH)) throw new NoResultException("Empty");
        if (!contains(id)) throw new NoResultException("Not found");
        List<User> allList = getAllList();

        for (User user : allList) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User getByLogin(String login) {
        if (isEmpty(USER_PATH)) throw new NoResultException("Empty");
        if (!contains(login)) throw new NoResultException("Not found");
        List<User> allList = getAllList();

        for (User user : allList) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User[] getAllByRole(Role role) {
        if (isEmpty(USER_PATH)) throw new NoResultException("Empty");
        if (!contains(role)) throw new NoResultException("Not found");
        List<User> allList = getAllList();
        List<User> allSubList = new ArrayList<>();

        for (User user : allList) {
            if (user.getRole().equals(role)) {
                allSubList.add(user);
            }
        }
        return allSubList.toArray(new User[0]);
    }


    @Override
    public boolean contains(int id) {
        List<User> allList = getAllList();
        for (User user : allList) {
            if (user.getId() == id) return true;
        }
        return false;
    }

    @Override
    public boolean contains(String login) {
        List<User> allList = getAllList();
        for (User user : allList) {
            if (user.getLogin().equals(login)) return true;
        }
        return false;
    }

    @Override
    public boolean contains(User user) {
        List<User> allList = getAllList();
        for (User users : allList) {
            if (users.equals(user)) return true;
        }
        return false;
    }

    @Override
    public boolean contains(Role role) {
        List<User> allList = getAllList();
        for (User user : allList) {
            if (user.getRole().equals(role)) return true;
        }
        return false;
    }
}
