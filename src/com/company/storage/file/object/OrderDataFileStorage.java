package com.company.storage.file.object;

import com.company.entity.*;
import com.company.storage.OrderStorage;
import com.company.storage.file.object.maps.Maps;
import com.company.storage.file.object.maps.OrderMaps;
import com.company.storage.file.object.wrapper.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderDataFileStorage extends AbstractObjectFileStorage implements OrderStorage {

    private final OrderWrapper orderWrapper;

    {
        if (isEmpty(ORDER_PATH)) {
            orderWrapper = new OrderWrapper(0, new ArrayList<>());
            saveData(orderWrapper, ORDER_PATH);
        } else {
            orderWrapper = (OrderWrapper) readData(ORDER_PATH);
            System.out.println(reedOrderMaps(ORDER_MAP_PATH).getHashMap());
        }
    }

    public static void main(String[] args) {
        OrderDataFileStorage orderDataFileStorage = new OrderDataFileStorage();

//        Book[] books = { new Book(2,"test2", "test",new Author(1,"test"),11), new Book(2,"test", "test",new Author(1,"test"),11)};
//        Store store = new Store(2,"test2", new Address(1, "test", 11));
//        User user = new User(2, "test2", "test", "test", "test", 22, Role.ADMIN,  new Address(1, "test", 11));
//        Address address = new Address(2, "test2", 11);
//        orderDataFileStorage.save(new Order(books, address, store, user, 33, Status.ACTIVE ));

        System.out.println(orderDataFileStorage.getAllList());
//        System.out.println(orderDataFileStorage.updateStoreById(new Store(3, "teast", new Address("12", 11)), 1));
        orderDataFileStorage.updateStatusById(Status.CLOSE, 1);
        System.out.println(orderDataFileStorage.getAllList());

    }

    @Override
    public boolean save(Order order) {
        List<Order> list = orderWrapper.getList();
        int idOrder = incrementNewId(orderWrapper);

        order.setId(idOrder);
        saveMap(idOrder, getIdContainsInOrder(order), ORDER_MAP_PATH);
        order.setBooks(null);
        order.setStore(null);
        order.setAddress(null);
        order.setUser(null);

        list.add(order);
        orderWrapper.setId(idOrder);
        orderWrapper.setList(list);
        saveData(orderWrapper, ORDER_PATH);
        return true;
    }

    private List<Integer> getIdContainsInOrder(Order order) {
        int idStore = order.getStore().getId();
        int idAddress = order.getAddress().getId();
        int idUser = order.getUser().getId();

        ArrayList<Integer> list = new ArrayList<>();
        list.add(idStore);
        list.add(idAddress);
        list.add(idUser);

        Book[] books = order.getBooks();
        for (Book book : books) {
            list.add(book.getId());
        }
        return list;
    }

    @Override
    public void delete(int id) {
        List<Order> list = orderWrapper.getList();
        for (Order order : list) {
            if (order.getId() == id) {
                list.remove(order);
                break;
            }
        }
        Maps maps = reedMaps(ORDER_MAP_PATH);
        maps.getHashMap().remove(id);
        saveMap(maps);
    }

    @Override
    public void delete(Order order) {
        List<Order> list = orderWrapper.getList();
        for (Order order1 : list) {
            if (order1.equals(order)) {
                list.remove(order);
                break;
            }
        }
        Maps maps = reedMaps(ORDER_MAP_PATH);
        maps.getHashMap().remove(order.getId());
        saveMap(maps);

    }

    @Override
    public Address updateAddressById(Address address, int id) {
        OrderMaps orderMaps = reedOrderMaps(ORDER_MAP_PATH);
        HashMap<Integer, List<Integer>> hashMap = orderMaps.getHashMap();

        List<Integer> listId = hashMap.get(id);
        Integer oldId = listId.get(1);
        listId.set(1, address.getId());

        saveMap(orderMaps);

        AbstractWrapper<Address> addressWrapper = (AbstractWrapper<Address>) readData(ADDRESS_PATH);
        Address oldAddress = (Address) getObjectById(addressWrapper, oldId);

        return oldAddress;
    }

    @Override
    public Store updateStoreById(Store store, int id) {
        OrderMaps orderMaps = reedOrderMaps(ORDER_MAP_PATH);
        HashMap<Integer, List<Integer>> hashMap = orderMaps.getHashMap();

        Store oldStore = null;
        for (Order order : getAllList()) {
            if (order.getId() == id) {
                oldStore = order.getStore();
                break;
            }
        }

        List<Integer> listId = hashMap.get(id);
        listId.set(0, store.getId());

        saveMap(orderMaps);


        return oldStore;
    }

    @Override
    public Status updateStatusById(Status status, int id) {
        AbstractWrapper<Order> orderWrapper = (AbstractWrapper<Order>) readData(ORDER_PATH);

        Status oldStatus = null;
        List<Order> list = orderWrapper.getList();
        for (Order order : list) {
            if (order.getId() == id) {
                oldStatus = order.getStatus();
                order.setStatus(status);
                break;
            }
        }
        saveData(orderWrapper, ORDER_PATH);

        return oldStatus;
    }

    @Override
    public boolean addBookById(Book book, int id) {
        return false;
    }

    @Override
    public Book deleteBookById(Book book, int id) {
        return null;
    }

    @Override
    public Order getOrderById(int id) {
        return null;
    }

    @Override
    public Order getOrderByOrder(Order order) {
        return null;
    }

    @Override
    public Order[] getAllByUser(User user) {


        return new Order[0];
    }


    @Override
    public Order[] getAllByAddress(Address address) {
        return new Order[0];
    }

    @Override
    public Order[] getAllByStore(Store store) {
        return new Order[0];
    }

    @Override
    public Order[] getAllByStatus(Status status) {
        return new Order[0];
    }

    @Override
    public Order[] getAll() {
        return getAllList().toArray(new Order[0]);
    }


    private List<Order> getAllList() {
        HashMap<Integer, List<Integer>> hashMap = reedOrderMaps(ORDER_MAP_PATH).getHashMap();
        List<Order> list = new ArrayList<>(orderWrapper.getList());

        for (Order order : list) {
            List<Integer> listId = hashMap.get(order.getId());
            int storeId = listId.get(0);
            int addressId = listId.get(1);
            int userId = listId.get(2);
            List<Integer> books = new ArrayList<>();
            for (int i = 3; i < listId.size(); i++) {
                books.add(listId.get(i));
            }
            Integer[] booksId = books.toArray(new Integer[0]);
            getFullOrder(order, storeId, addressId, userId, booksId);
            books.clear();
        }

        return list;
    }

    private Order getFullOrder(Order order, int storeId, int addressId, int userId, Integer[] booksId) {
        StoreWrapper storeWrapper = (StoreWrapper) readData(STORE_PATH);
        AddressWrapper addressWrapper = (AddressWrapper) readData(ADDRESS_PATH);
        UserWrapper userWrapper = (UserWrapper) readData(USER_PATH);
        BookWrapper bookWrapper = (BookWrapper) readData(BOOK_PATH);
        AuthorWrapper authorWrapper = (AuthorWrapper) readData(AUTHOR_PATH);

        HashMap<Integer, Integer> hashMapBooks = reedMaps(BOOK_MAP_PATH).getHashMap();
        HashMap<Integer, Integer> hashMapStores = reedMaps(STORE_MAP_PATH).getHashMap();
        HashMap<Integer, Integer> hashMapUsers = reedMaps(USER_MAP_PATH).getHashMap();


        List<Store> listStore = storeWrapper.getList();
        for (Store store : listStore) {
            if (store.getId() == storeId) {
                Address address = (Address) getObjectById(addressWrapper, hashMapStores.get(storeId));
                store.setAddress(address);
                order.setStore(store);
                break;
            }
        }

        List<Address> listAddress = addressWrapper.getList();
        for (Address address : listAddress) {
            if (address.getId() == addressId) {
                order.setAddress(address);
                break;
            }
        }

        List<User> listUser = userWrapper.getList();
        for (User user : listUser) {
            if (user.getId() == userId) {
                Address address = (Address) getObjectById(addressWrapper, hashMapUsers.get(userId));
                user.setAddress(address);
                order.setUser(user);
                break;
            }
        }

        List<Book> listAllBooks = bookWrapper.getList();
        List<Book> listBooks = new ArrayList<>();

        for (Integer integer : booksId) {
            for (Book listAllBook : listAllBooks) {
                if (listAllBook.getId() == integer) {
                    listBooks.add(listAllBook);
                }
            }
        }

        for (Book listBook : listBooks) {
            Author author = (Author) getObjectById(authorWrapper, hashMapBooks.get(listBook.getId()));
            listBook.setAuthor(author);
        }
        Book[] books = listBooks.toArray(new Book[0]);
        order.setBooks(books);

        return order;
    }

    @Override
    public boolean contains(int id) {
        return false;
    }

    @Override
    public boolean contains(User user) {
        return false;
    }

    @Override
    public boolean contains(Address address) {
        return false;
    }

    @Override
    public boolean contains(Store store) {
        return false;
    }

    @Override
    public boolean contains(Book book) {
        return false;
    }

    @Override
    public boolean contains(Order order) {
        return false;
    }

    @Override
    public boolean contains(Status status) {
        return false;
    }
}
