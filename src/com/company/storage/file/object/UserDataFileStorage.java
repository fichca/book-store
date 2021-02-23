package com.company.storage.file.object;

import com.company.entity.Address;
import com.company.entity.Role;
import com.company.entity.User;
import com.company.storage.UserStorage;
import com.company.storage.file.object.maps.Maps;
import com.company.storage.file.object.wrapper.AbstractWrapper;
import com.company.storage.file.object.wrapper.UserWrapper;

import java.util.*;

public class UserDataFileStorage extends AbstractObjectFileStorage implements UserStorage {

    UserWrapper userWrapper;

    {
        if (isEmpty(USER_PATH)) {
            userWrapper = new UserWrapper(0, new ArrayList<>());
            saveData(userWrapper, USER_PATH);
        } else {
            userWrapper = (UserWrapper) readData(USER_PATH);
        }
    }

    public static void main(String[] args) {
        UserDataFileStorage userDataFileStorage = new UserDataFileStorage();
//        userDataFileStorage.save(new User("test4", "test2", "test2", "test2", 33, Role.USER, new Address(1, "test1", 1)));
//        userDataFileStorage.save(new User("test5", "test2", "test2", "test2", 33, Role.ADMIN, new Address(2, "test1", 1)));
//        userDataFileStorage.save(new User("test6", "test2", "test2", "test2", 33, Role.ADMIN, new Address(3, "test1", 1)));
        System.out.println(Arrays.toString(userDataFileStorage.getAll()));
    }


    @Override
    public void save(User user) {
        List<User> list = userWrapper.getList();
        int idUser = incrementNewId(userWrapper);
        int addressId = user.getAddress().getId();

        user.setId(idUser);
        saveMap(idUser, addressId, USER_MAP_PATH);
        user.setAddress(null);
        list.add(user);
        userWrapper.setId(idUser);
        userWrapper.setList(list);
        saveData(userWrapper, USER_PATH);
    }

    @Override
    public boolean delete(int id) {
        List<User> list = userWrapper.getList();
        for (User user : list) {
            if (user.getId() == id) {
                delete(user, list);
                return true;
            }
        }
        return false;
    }

    @Override
    public void delete(String login) {
        List<User> list = userWrapper.getList();
        for (User user : list) {
            if (user.getLogin().equals(login)) {
                delete(user, list);
                return;
            }
        }
    }

    @Override
    public void delete(User user) {
        List<User> list = userWrapper.getList();
        for (User user1 : list) {
            if (user1.equals(user)) {
                delete(user, list);
                return;
            }
        }
    }

    private void delete(User user, List<User> list) {
        Maps mapsFromFile = reedMaps(USER_MAP_PATH);
        mapsFromFile.getHashMap().remove(user.getId());
        saveMap(mapsFromFile);
        list.remove(user);
        userWrapper.setList(list);
        saveData(userWrapper, USER_PATH);
    }


    @Override
    public String updateFirstNameById(String firstName, int id) {
        List<User> list = userWrapper.getList();
        for (User user : list) {
            if (user.getId() == id) {
                String old = user.getFirstName();
                user.setFirstName(firstName);
                saveData(userWrapper, USER_PATH);
                return old;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public String updateLastNameById(String lastName, int id) {
        List<User> list = userWrapper.getList();
        for (User user : list) {
            if (user.getId() == id) {
                String old = user.getLastName();
                user.setLastName(lastName);
                saveData(userWrapper, USER_PATH);
                return old;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public int updateAgeById(int age, int id) {
        List<User> list = userWrapper.getList();
        for (User user : list) {
            if (user.getId() == id) {
                int old = user.getAge();
                user.setAge(age);
                saveData(userWrapper, USER_PATH);
                return old;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public Role updateRoleById(Role role, int id) {
        List<User> list = userWrapper.getList();
        for (User user : list) {
            if (user.getId() == id) {
                Role old = user.getRole();
                user.setRole(role);
                saveData(userWrapper, USER_PATH);
                return old;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public Address updateAddressById(Address address, int id) {
        List<User> list = userWrapper.getList();
        for (User user : list) {
            if (user.getId() == id) {
                Address old = user.getAddress();
                Maps mapsFromFile = reedMaps(USER_MAP_PATH);
                mapsFromFile.getHashMap().remove(id);
                mapsFromFile.getHashMap().put(id, address.getId());
                saveMap(mapsFromFile);
                user.setAddress(address);
                saveData(userWrapper, USER_PATH);
                return old;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public User[] getAll() {

        return getAllList().toArray(new User[0]);
    }

    private List<User> getAllList() {
        HashMap<Integer, Integer> hashMap = reedMaps(USER_MAP_PATH).getHashMap();
        List<User> list = userWrapper.getList();

        AbstractWrapper<Address> abstractWrapper = (AbstractWrapper<Address>) readData(ADDRESS_PATH);

        for (User user : list) {
            int id = user.getId();
            Integer addressId = hashMap.get(id);

            Address address = (Address) getObjectById(abstractWrapper, addressId);
            user.setAddress(address);
        }
        return list;
    }

    @Override
    public User[] getAllByFirstName(String firstName) {
        List<User> allList = new ArrayList<>(getAllList());
        for (int i = 0; i < allList.size(); i++) {
            if (!allList.get(i).getFirstName().equals(firstName)) {
                allList.remove(allList.get(i));
            }
        }
        return allList.toArray(new User[0]);
    }

    @Override
    public User[] getAllByLastName(String lastName) {
        List<User> allList = new ArrayList<>(getAllList());
        for (int i = 0; i < allList.size(); i++) {
            if (!allList.get(i).getLastName().equals(lastName)) {
                allList.remove(allList.get(i));
            }
        }
        return allList.toArray(new User[0]);
    }

    @Override
    public User[] getAllByAge(int age) {
        List<User> allList = new ArrayList<>(getAllList());
        for (int i = 0; i < allList.size(); i++) {
            if (!(allList.get(i).getAge() == age)) {
                allList.remove(allList.get(i));
            }
        }
        return allList.toArray(new User[0]);
    }

    @Override
    public User getById(int id) {
        List<User> allList = new ArrayList<>(getAllList());
        for (User user : allList) {
            if (user.getId() == id) {
                return user;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public User getByLogin(String login) {
        List<User> allList = getAllList();
        for (User user : allList) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public User[] getAllByRole(Role role) {
        List<User> allList = new ArrayList<>(getAllList());
        for (int i = 0; i < allList.size(); i++) {
            if (!(allList.get(i).getRole().equals(role))) {
                allList.remove(allList.get(i));
            }
        }
        return (User[]) allList.toArray();
    }

    @Override
    public boolean contains(int id) {
        List<User> list = userWrapper.getList();
        for (User user : list) {
            if (user.getId() == id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(String login) {
        List<User> list = userWrapper.getList();
        for (User user : list) {
            if (user.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(User user) {
        List<User> list = userWrapper.getList();
        for (User user1 : list) {
            if (user1.equals(user)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(Role role) {
        List<User> list = userWrapper.getList();
        for (User user : list) {
            if (user.getRole().equals(role)) {
                return true;
            }
        }
        return false;
    }
}
