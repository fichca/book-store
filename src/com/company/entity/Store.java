package com.company.entity;

import java.io.Serializable;
import java.util.Objects;

public class Store extends AbstractEntity implements Serializable {

    private String title;
    private Address address;

//    public Store() {
//    }

//    public Store(String title, Address address) {
//        this.title = title;
//        this.address = address;
//    }


//    public Store(int id, String title, Address address) {
//        this.id = id;
//        this.title = title;
//        this.address = address;
//    }


//    public Store() {
//    }
//
//    public Store(int id, String title, Address address) {
//        super(id);
//        this.title = title;
//        this.address = address;
//    }
//
//    public Store(String title, Address address) {
//        this.title = title;
//        this.address = address;
//    }


    public Store() {
    }

    public Store(String title, Address address) {
        this.title = title;
        this.address = address;
    }

    public Store(int id, String title, Address address) {
        super(id);
        this.title = title;
        this.address = address;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
        return Objects.equals(title, store.title) &&
                Objects.equals(address, store.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, address);
    }

    @Override
    public String toString() {
        return "Store{" +
                "id=" + getId() +
                ", title='" + title + '\'' +
                ", address=" + address +
                '}';
    }
}
