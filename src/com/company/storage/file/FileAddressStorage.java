package com.company.storage.file;

import com.company.entity.Address;
import com.company.storage.AddressStorage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileAddressStorage extends AbstractFileStorage implements AddressStorage {


    private static List<Address> listAddress;
    private final String FORMAT = "%d %s %d \n";
    private final String CONTAINS = "#ID STREET HOME \n";

    public FileAddressStorage() {
        listAddress = getAllList();
    }

    public static void main(String[] args) {
        FileAddressStorage fileAddressStorage = new FileAddressStorage();
        fileAddressStorage.save(new Address("test1", 1));
        fileAddressStorage.save(new Address("test2", 2));
//        fileAddressStorage.save(new Address("test3", 3));
//        FileOrderStorage
        System.out.println(fileAddressStorage.getById(14));
//        fileAddressStorage.remove(2);

//        Address byId = fileAddressStorage.getById(2);
//        fileAddressStorage.remove(byId);
//        Address[] tests = fileAddressStorage.getByStreet("test");
//        System.out.println(Arrays.toString(tests));
//        fileAddressStorage.remove(2);
    }

    @Override
    public boolean save(Address address) {
        try {
            FileWriter writer = new FileWriter(ADDRESS_PATH, true);
            if (isEmpty(ADDRESS_PATH)) {
                writer.write(CONTAINS);
            }
            String format = String.format(this.FORMAT, getNewId(), address.getStreet(), address.getHome());

            writer.write(format);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private int getNewId() {
        List<Address> allList = getAllList();
        if (isEmpty(ADDRESS_PATH)) return 1;
        Address address = allList.get(allList.size() - 1);
        return address.getId() + 1;
    }

    @Override
    public void remove(int id) {
        if (!contains(id)) return;
        List<Address> allList = getAllList();
        for (Address address : allList) {
            if (address.getId() == id) {
                allList.remove(address);
                break;
            }
        }
        clean(ADDRESS_PATH);
        for (Address address : allList) {

            saveForRemove(address);
        }
    }

    @Override
    public void remove(Address address) {
        if (!contains(address)) return;
        List<Address> allList = getAllList();
        for (Address addresses : allList) {
            if (addresses.equals(address)) {
                allList.remove(addresses);
                break;
            }
        }
        clean(ADDRESS_PATH);
        for (Address addresses : allList) {
            saveForRemove(addresses);
        }
    }

    private boolean saveForRemove(Address address) {
        try {
            FileWriter writer = new FileWriter(ADDRESS_PATH, true);
            if (isEmpty(ADDRESS_PATH)) {
                writer.write(CONTAINS);
            }
            String format = String.format(this.FORMAT, address.getId(), address.getStreet(), address.getHome());

            writer.write(format);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void updateAddressById(String street, int id) {
        if (!contains(id)) return;
        List<Address> all = getAllList();
        for (Address address : all) {
            if (address.getId() == id) {
                address.setStreet(street);
                break;
            }
        }
        clean(ADDRESS_PATH);
        for (Address address : all) {
            saveForRemove(address);
        }
    }

    @Override
    public void updateHomeById(int home, int id) {
        if (!contains(id)) return;
        List<Address> all = getAllList();
        for (Address address : all) {
            if (address.getId() == id) {
                address.setHome(home);
                break;
            }
        }
        clean(ADDRESS_PATH);
        for (Address address : all) {
            saveForRemove(address);
        }
    }

    @Override
    public Address[] getAll() {
        return getAllList().toArray(new Address[0]);
    }

    private List<Address> getAllList() {
        listAddress = new ArrayList<>();
        if (isEmpty(ADDRESS_PATH)) return listAddress;
        FileReader reader = null;
        try {
            reader = new FileReader(ADDRESS_PATH);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals("#ID STREET HOME ")) continue;

                String[] split = line.split(SPLIT);
                Address address = new Address();
                address.setId(Integer.parseInt(split[0]));
                address.setStreet(split[1]);
                address.setHome(Integer.parseInt(split[2]));
                listAddress.add(address);
            }
            bufferedReader.close();
            return listAddress;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listAddress;
    }

    @Override
    public Address getById(int id) {
        if (!contains(id)) return null;
//        List<Address> all = getAllList();
//        for (Address address : all) {
//            if (address.getId() == id){
//                return address;
//            }
        for (Address address : listAddress) {
            if (address.getId() == id) {
                return address;
            }
        }
        return null;
    }

    @Override
    public Address[] getByStreet(String street) {
        List<Address> all = getAllList();
        List<Address> allByStreet = new ArrayList<>();
        for (Address address : all) {
            if (address.getStreet().equals(street)) {
                allByStreet.add(address);
            }
        }
        return allByStreet.toArray(new Address[0]);
    }

    @Override
    public Address[] getByHome(int home) {
        return new Address[0];
    }


    @Override
    public boolean contains(int id) {
        listAddress = getAllList();
        for (Address address : listAddress) {
            if (address.getId() == id) return true;
        }
        return false;
    }

    @Override
    public boolean contains(Address address) {
        listAddress = getAllList();
        for (Address addresses : listAddress) {
            if (addresses.equals(address)) return true;
        }
        return false;
    }
}
