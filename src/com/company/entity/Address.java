package com.company.entity;

import java.io.Serializable;
import java.util.Objects;

public class Address extends AbstractEntity implements Serializable {

    //    private int id;
    private String street;
    private int home;

//    public Address() {
//
//    }
//
//    public Address(int id, String street, int home) {
//        this.id = id;
//        this.street = street;
//        this.home = home;
//    }
//
//    public Address(String street, int home) {
//        this.street = street;
//        this.home = home;
//    }


    public Address() {
    }

    public Address(int id, String street, int home) {
        super(id);
        this.street = street;
        this.home = home;
    }

    public Address(String street, int home) {
        this.street = street;
        this.home = home;
    }


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHome() {
        return home;
    }

    public void setHome(int home) {
        this.home = home;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return getId() == address.getId() &&
                home == address.home &&
                Objects.equals(street, address.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), street, home);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + getId() +
                ", street='" + street + '\'' +
                ", home=" + home +
                '}';
    }
}
