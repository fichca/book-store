package com.company.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class Order extends AbstractEntity implements Serializable {

    private Book[] books;
    private Address address;
    private Store store;
    private User user;
    private int totalPrice;

    private Status status = Status.ACTIVE;
    private Timestamp date;

    public Order() {
    }

    public Order(Book[] books, Address address, Store store, User user, int totalPrice, Status status) {
        this.books = books;
        this.address = address;
        this.store = store;
        this.user = user;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Order(Book[] books, Store store, User user, int totalPrice, Status status) {
        this.books = books;
        this.store = store;
        this.user = user;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Order(Book[] books, Address address, User user, int totalPrice, Status status) {
        this.books = books;
        this.address = address;
        this.user = user;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Order(Book[] books, Address address, User user, int totalPrice) {
        this.books = books;
        this.address = address;
        this.user = user;
        this.totalPrice = totalPrice;
    }

    public Order(Book[] books, Store store, User user, int totalPrice) {
        this.books = books;
        this.store = store;
        this.user = user;
        this.totalPrice = totalPrice;
    }

    public Order(int id, Book[] books, Address address, Store store, User user, int totalPrice, Status status) {
        super(id);
        this.books = books;
        this.address = address;
        this.store = store;
        this.user = user;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Order(Book[] books, Address address, Store store, User user, int totalPrice, Status status, Timestamp date) {
        this.books = books;
        this.address = address;
        this.store = store;
        this.user = user;
        this.totalPrice = totalPrice;
        this.status = status;
        this.date = date;
    }

    public Order(int id, Book[] books, Address address, Store store, User user, int totalPrice, Status status, Timestamp date) {
        super(id);
        this.books = books;
        this.address = address;
        this.store = store;
        this.user = user;
        this.totalPrice = totalPrice;
        this.status = status;
        this.date = date;
    }

    public Order(Book[] books, Store store, User user, int totalPrice, Status status, Timestamp date) {
        this.books = books;
        this.store = store;
        this.user = user;
        this.totalPrice = totalPrice;
        this.status = status;
        this.date = date;
    }

    public Order(int id, Book[] books, Store store, User user, int totalPrice, Status status, Timestamp date) {
        super(id);
        this.books = books;
        this.store = store;
        this.user = user;
        this.totalPrice = totalPrice;
        this.status = status;
        this.date = date;
    }

    public Order(Book[] books, Address address, User user, int totalPrice, Status status, Timestamp date) {
        this.books = books;
        this.address = address;
        this.user = user;
        this.totalPrice = totalPrice;
        this.status = status;
        this.date = date;
    }

    public Order(int id, Book[] books, Address address, User user, int totalPrice, Status status, Timestamp date) {
        super(id);
        this.books = books;
        this.address = address;
        this.user = user;
        this.totalPrice = totalPrice;
        this.status = status;
        this.date = date;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Book[] getBooks() {
        return books;
    }

    public void setBooks(Book[] books) {
        this.books = books;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return totalPrice == order.totalPrice && Arrays.equals(books, order.books) && Objects.equals(address, order.address) && Objects.equals(store, order.store) && Objects.equals(user, order.user) && Objects.equals(date, order.date) && status == order.status;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(address, store, user, totalPrice, date, status);
        result = 31 * result + Arrays.hashCode(books);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "books=" + Arrays.toString(books) +
                ", address=" + address +
                ", store=" + store +
                ", user=" + user +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                ", date=" + date +
                '}';
    }
}
