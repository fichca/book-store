package com.company.storage.inmemory;

import com.company.entity.*;
import com.company.storage.OrderStorage;

public class InMemoryOrderStorage implements OrderStorage {
    private static final Order[] orders = new Order[50];

    @Override
    public boolean save(Order order) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) {
                orders[i] = order;
                return true;
            }
        }
        return false;
    }

    @Override
    public void delete(int id) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            if (orders[i].getId() == id) {
                for (int i1 = i; i1 < orders.length - 1; i1++) {
                    orders[i1] = orders[i1 + 1];
                }
                break;
            }
        }

    }

    @Override
    public void delete(Order order) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            if (orders[i].equals(order)) {
                for (int i1 = i; i1 < orders.length - 1; i1++) {
                    orders[i1] = orders[i1 + 1];
                }
                break;
            }
        }
    }

    @Override
    public Address updateAddressById(Address address, int id) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            if (orders[i].getId() == id) {
                Address address1 = orders[i].getAddress();
                orders[i].setAddress(address);
                return address1;
            }
        }
        return null;
    }

    @Override
    public Store updateStoreById(Store store, int id) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            if (orders[i].getId() == id) {
                Store store1 = orders[i].getStore();
                orders[i].setStore(store);
                return store1;
            }
        }
        return null;
    }

    @Override
    public Status updateStatusById(Status status, int id) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            if (orders[i].getId() == id) {
                Status status1 = orders[i].getStatus();
                orders[i].setStatus(status);
                return status1;
            }
        }
        return null;
    }

    @Override
    public boolean addBookById(Book book, int id) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            if (orders[i].getId() == id) {
                Book[] books = orders[i].getBooks();
                for (int i1 = 0; i1 < books.length; i1++) {
                    if (books[i] == null) {
                        books[i] = book;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Book deleteBookById(Book book, int id) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            if (orders[i].getId() == id) {
                Book[] books = orders[i].getBooks();
                for (int i1 = 0; i1 < books.length; i1++) {
                    if (books[i1] == null) return null;
                    if (books[i1].equals(book)) {
                        Book book1 = books[i1];
                        for (int i2 = i1; i2 < books.length - 1; i2++) {
                            books[i2] = books[i2 + 1];
                        }
                        return book1;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Order getOrderById(int id) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            if (orders[i].getId() == id) {
                return orders[i];
            }
        }
        return null;
    }

    @Override
    public Order getOrderByOrder(Order order) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            if (orders[i].equals(order)) {
                return orders[i];
            }
        }
        return null;
    }

    @Override
    public Order[] getAllByUser(User user) {
        int p = 0;
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            if (orders[i].getUser().equals(user)) {
                p++;
            }
        }
        Order[] order = new Order[p];
        for (int i = 0, j = 0; i < orders.length; i++) {
            if (orders[i] == null) return order;
            if (orders[i].getUser().equals(user)) {
                order[j] = orders[i];
                j++;
            }
        }
        return order;
    }

    @Override
    public Order[] getAllByAddress(Address address) {
        int p = 0;
        for (Order value : orders) {
            if (value == null) break;
            if (value.getAddress().equals(address)) {
                p++;
            }
        }
        Order[] order = new Order[p];
        for (int i = 0, j = 0; i < orders.length; i++) {
            if (orders[i] == null) return order;
            if (orders[i].getAddress().equals(address)) {
                order[j] = orders[i];
                j++;
            }
        }
        return order;
    }

    @Override
    public Order[] getAllByStore(Store store) {
        int p = 0;
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            if (orders[i].getStore().equals(store)) {
                p++;
            }
        }
        Order[] order = new Order[p];
        for (int i = 0, j = 0; i < orders.length; i++) {
            if (orders[i] == null) return order;
            if (orders[i].getStore().equals(store)) {
                order[j] = orders[i];
                j++;
            }
        }
        return order;
    }

    @Override
    public Order[] getAllByStatus(Status status) {
        int p = 0;
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            if (orders[i].getStatus().equals(status)) {
                p++;
            }
        }
        Order[] order = new Order[p];
        for (int i = 0, j = 0; i < orders.length; i++) {
            if (orders[i] == null) return order;
            if (orders[i].getStatus().equals(status)) {
                order[j] = orders[i];
                j++;
            }
        }
        return order;
    }

    @Override
    public Order[] getAll() {
        int p = 0;
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            p++;
        }
        Order[] order = new Order[p];
        for (int i = 0; i < order.length; i++) {
            order[i] = orders[i];
        }
        return order;
    }

    @Override
    public boolean contains(int id) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) return false;
            if (orders[i].getId() == id) return true;
        }
        return false;
    }

    @Override
    public boolean contains(User user) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) return false;
            if (orders[i].getUser().equals(user)) return true;
        }
        return false;
    }

    @Override
    public boolean contains(Address address) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) return false;
            if (orders[i].getAddress().equals(address)) return true;
        }
        return false;
    }

    @Override
    public boolean contains(Store store) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) return false;
            if (orders[i].getStore().equals(store)) return true;
        }
        return false;
    }

    @Override
    public boolean contains(Book book) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) return false;
            if (orders[i].getBooks().equals(book)) return true;
        }
        return false;
    }

    @Override
    public boolean contains(Order order) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) return false;
            if (orders[i].equals(order)) return true;
        }
        return false;
    }

    @Override
    public boolean contains(Status status) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) return false;
            if (orders[i].getStatus().equals(status)) return true;
        }
        return false;
    }
}
