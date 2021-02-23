package com.company.entity;

import java.io.Serializable;
import java.util.Objects;

public class Author extends AbstractEntity implements Serializable {
    private String fullName;

    public Author() {
    }

    public Author(int id, String fullName) {
        super(id);
        this.fullName = fullName;
    }

    public Author(String fullName) {
        this.fullName = fullName;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(fullName, author.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName);
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + getId() +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
