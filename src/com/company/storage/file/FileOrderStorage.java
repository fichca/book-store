package com.company.storage.file;

import com.company.entity.*;
import com.company.storage.OrderStorage;
import com.company.storage.exceptions.NoResultException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileOrderStorage extends AbstractFileStorage implements OrderStorage {


    private static final int TOTAL_PRICE = 1;
    private static final int STATUS = 2;
    private static final int USER_ID = 3;
    private static final int STORE_ID = 4;
    private static final int ADDRESS_ID = 5;
    private final String CONTAINS = "#ID TOTAL_PRICE STATUS USER_ID STORE_ID ADDRESS_ID \n";
    private final String formatOrder = "%d %d %s %d %d %d \n";

    public static void main(String[] args) {
        FileOrderStorage fileOrderStorage = new FileOrderStorage();
        Author author = new Author(1, "test1");
        Book book1 = new Book(1, "test1", "test1", author, 1);
        Book book2 = new Book(2, "test2", "test1", author, 1);
        Book[] books = new Book[2];
        books[0] = book1;
        books[1] = book2;
        Address address = new Address(3, "test2", 1);
        Store store = new Store(2, "test2", address);
        User user = new User(2, "admin", "admin", "Admin", "Admin", 22, Role.ADMIN, address);
        Status status = Status.ACTIVE;
        fileOrderStorage.save(new Order(books, store, user, 2, status));
        System.out.println(fileOrderStorage.getAllList());
        fileOrderStorage.delete(2);
        fileOrderStorage.addBookById(new Book(5, "test2", "test2", new Author(2, "test2"), 2), 3);
    }

    @Override
    public boolean save(Order order) {
        try {
            FileWriter writer = new FileWriter(ORDER_PATH, true);
            if (isEmpty(ORDER_PATH)) {
                writer.write(CONTAINS);
            }
            String format;
            if (order.getStore() == null) {

                format = String.format(this.formatOrder, getNewId(), order.getTotalPrice(), order.getStatus().name(), order.getUser().getId(), -1, order.getAddress().getId());
            } else {
                format = String.format(this.formatOrder, getNewId(), order.getTotalPrice(), order.getStatus().name(), order.getUser().getId(), order.getStore().getId(), -1);
            }
            writer.write(format);
            Book[] books = order.getBooks();
            for (int i = 0; i < books.length; i++) {
                if (i == 0) {
                    writer.write("Id books: ");
                }
                String book = books[i].getId() + " ";
                writer.write(book);
                int length = books.length;
                if (i + 1 == length) {
                    writer.write("\n");
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private int getNewId() {
        List<Order> allList = getAllList();
        if (isEmpty(ORDER_PATH)) return 1;
        Order order = allList.get(allList.size() - 1);
        return order.getId() + 1;
    }

    @Override
    public void delete(int id) {
        if (!contains(id)) return;
        List<Order> allList = getAllList();
        for (Order order : allList) {
            if (order.getId() == id) {
                System.out.println(order);
                System.out.println("     ");
                allList.remove(order);
                break;
            }
        }
        System.out.println(allList);
        clean(ORDER_PATH);
        for (Order order : allList) {
            saveForDelete(order);
        }

    }

    @Override
    public void delete(Order order) {
        if (!contains(order)) return;
        List<Order> allList = getAllList();
        for (Order orders : allList) {
            if (orders.equals(order)) {
                allList.remove(order);
                break;
            }
        }
        clean(ORDER_PATH);
        for (Order orders : allList) {
            saveForDelete(orders);
        }
    }

    public boolean saveForDelete(Order order) {
        try {
            FileWriter writer = new FileWriter(ORDER_PATH, true);
            if (isEmpty(ORDER_PATH)) {
                writer.write(CONTAINS);
            }
            String format;
            if (order.getStore() == null) {

                format = String.format(this.formatOrder, order.getId(), order.getTotalPrice(), order.getStatus().name(), order.getUser().getId(), -1, order.getAddress().getId());
            } else {
                format = String.format(this.formatOrder, order.getId(), order.getTotalPrice(), order.getStatus().name(), order.getUser().getId(), order.getAddress().getId(), -1);
            }
            writer.write(format);
            Book[] books = order.getBooks();
            for (int i = 0; i < books.length; i++) {
                if (i == 0) {
                    writer.write("Id books: ");
                }
                String book = books[i].getId() + " ";
                writer.write(book);
                int length = books.length;
                if (i + 1 == length) {
                    writer.write("\n");
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Address updateAddressById(Address address, int id) {
        Address old = new Address();
        List<Order> allList = getAllList();
        for (Order orders : allList) {
            if (orders.getId() == id) {
                old = orders.getAddress();
                orders.setAddress(address);
                break;
            }
        }
        clean(ORDER_PATH);
        for (Order orders : allList) {
            saveForDelete(orders);
        }
        return old;
    }

    @Override
    public Store updateStoreById(Store store, int id) {
        if (!contains(id)) throw new NoResultException();
        Store old = new Store();
        List<Order> allList = getAllList();
        for (Order orders : allList) {
            if (orders.getId() == id) {
                old = orders.getStore();
                orders.setStore(store);
                break;
            }
        }
        clean(ORDER_PATH);
        for (Order orders : allList) {
            saveForDelete(orders);
        }
        return old;
    }

    @Override
    public Status updateStatusById(Status status, int id) {
        if (!contains(id)) throw new NoResultException();
        Status old = null;
        List<Order> allList = getAllList();
        for (Order orders : allList) {
            if (orders.getId() == id) {
                old = orders.getStatus();
                orders.setStatus(status);
                break;
            }
        }
        clean(ORDER_PATH);
        for (Order orders : allList) {
            saveForDelete(orders);
        }
        return old;
    }

    @Override
    public boolean addBookById(Book book, int id) {
        if (!contains(book)) return false;
        List<Order> allList = getAllList();
        for (Order order : allList) {
            if (order.getId() == id) {
                List<Book> listBooks = new ArrayList<>(Arrays.asList(order.getBooks()));
                listBooks.add(book);

                System.out.println(listBooks);
                order.setBooks(listBooks.toArray(new Book[0]));
            }
        }
        clean(ORDER_PATH);
        for (Order orders : allList) {
            saveForDelete(orders);
        }
        return true;
    }

    @Override
    public Book deleteBookById(Book book, int id) {
        if (contains(book)) return null;
        List<Order> allList = getAllList();
        for (Order order : allList) {
            if (order.getId() == id) {
                List<Book> listBooks = new ArrayList<>(Arrays.asList(order.getBooks()));
                listBooks.remove(book);
                order.setBooks(listBooks.toArray(new Book[0]));
            }
        }
        clean(ORDER_PATH);
        for (Order orders : allList) {
            saveForDelete(orders);
        }
        return book;
    }

    @Override
    public Order getOrderById(int id) {
        if (!contains(id)) return null;
        List<Order> allList = getAllList();
        for (Order orders : allList) {
            if (orders.getId() == id) {
                return orders;
            }
        }
        throw new NoResultException();
    }

    @Override
    public Order getOrderByOrder(Order order) {
        if (!contains(order)) throw new NoResultException();
        List<Order> allList = getAllList();
        for (Order orders : allList) {
            if (orders.equals(order)) {
                return orders;
            }
        }
        throw new NoResultException();
    }

    @Override
    public Order[] getAllByUser(User user) {
        if (!contains(user)) throw new NoResultException();
        List<Order> allList = getAllList();
        List<Order> allUsers = new ArrayList<>();
        for (Order orders : allList) {
            if (orders.getUser().equals(user)) {
                allUsers.add(orders);
            }
        }
        return allUsers.toArray(new Order[0]);
    }

    @Override
    public Order[] getAllByAddress(Address address) {
        if (!contains(address)) throw new NoResultException();
        List<Order> allList = getAllList();
        List<Order> allAddresses = new ArrayList<>();
        for (Order orders : allList) {
            if (orders.getAddress().equals(address)) {
                allAddresses.add(orders);
            }
        }
        return allAddresses.toArray(new Order[0]);
    }

    @Override
    public Order[] getAllByStore(Store store) {
        if (!contains(store)) throw new NoResultException();
        List<Order> allList = getAllList();
        List<Order> allStore = new ArrayList<>();
        for (Order orders : allList) {
            if (orders.getStore().equals(store)) {
                allStore.add(orders);
            }
        }
        return allStore.toArray(new Order[0]);
    }

    @Override
    public Order[] getAllByStatus(Status status) {
        if (!contains(status)) throw new NoResultException();
        List<Order> allList = getAllList();
        List<Order> allStatus = new ArrayList<>();
        for (Order orders : allList) {
            if (orders.getStatus().equals(status)) {
                allStatus.add(orders);
            }
        }
        return allStatus.toArray(new Order[0]);
    }

    @Override
    public Order[] getAll() {
        return getAllList().toArray(new Order[0]);
    }

    private List<Order> getAllList() {

        List<Order> list = new ArrayList<>();

        if (isEmpty(ORDER_PATH)) return new ArrayList<>();
        try {
            FileReader reader = new FileReader(ORDER_PATH);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;

            Order order = new Order();
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals("#ID TOTAL_PRICE STATUS USER_ID STORE_ID ADDRESS_ID ")) continue;
                String[] split = line.split(SPLIT);

                if (!split[ID].equals("Id")) {
                    order = createOrder(split);
                } else {
                    list.add(addMassiveToOrder(split, order));
                }
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new NoResultException();
    }

    private Order addMassiveToOrder(String[] split, Order order) {
        int[] idBooks;
        idBooks = new int[split.length - 2];
        for (int i = 2; i < split.length; i++) {
            idBooks[i - 2] = Integer.parseInt(split[i]);
        }
        Book[] massiveBooks = getMassiveBooks(idBooks);
        order.setBooks(massiveBooks);
        return order;
    }

    private Order createOrder(String[] split) {
        Address address = new Address();
        if (Integer.parseInt(split[ADDRESS_ID]) != -1) {
            address = getAddressById(Integer.parseInt(split[ADDRESS_ID]));
        }
        Store store = new Store();
        if (Integer.parseInt(split[STORE_ID]) != -1) {
            store = getStoreById(Integer.parseInt(split[STORE_ID]));
        }
        User user = getUserById(Integer.parseInt(split[USER_ID]));
        Status status = Status.valueOf(split[STATUS]);
        return new Order(Integer.parseInt(split[ID]), new Book[0], address, store, user, Integer.parseInt(split[TOTAL_PRICE]), status);
    }

    @Override
    public boolean contains(int id) {
        List<Order> allList = getAllList();
        for (Order order : allList) {
            if (order.getId() == id) return true;
        }
        return false;
    }

    @Override
    public boolean contains(User user) {
        List<Order> allList = getAllList();
        for (Order order : allList) {
            if (order.getUser().equals(user)) return true;
        }
        return false;
    }

    @Override
    public boolean contains(Address address) {
        List<Order> allList = getAllList();
        for (Order order : allList) {
            if (order.getAddress().equals(address)) return true;
        }
        return false;
    }

    @Override
    public boolean contains(Store store) {
        List<Order> allList = getAllList();
        for (Order order : allList) {
            if (order.getStore().equals(store)) return true;
        }
        return false;
    }

    @Override
    public boolean contains(Book book) {
        try {
            FileReader reader = new FileReader(BOOK_PATH);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals("#ID TITLE DESCRIPTION PRICE AUTHOR_ID ")) continue;
                String[] split = line.split(SPLIT);
                if (Integer.parseInt(split[ID]) == book.getId()) {
                    Author authorById = getAuthorById(Integer.parseInt(split[4]));
                    Book book1 = new Book(Integer.parseInt(split[ID]), split[1], split[2], authorById, Integer.parseInt(split[3]));
                    if (book1.equals(book)) return true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(Order order) {
        List<Order> allList = getAllList();
        for (Order orders : allList) {
            if (orders.equals(order)) return true;
        }
        return false;
    }

    @Override
    public boolean contains(Status status) {
        List<Order> allList = getAllList();
        for (Order order : allList) {
            if (order.getStatus().equals(status)) return true;
        }
        return false;
    }
}
