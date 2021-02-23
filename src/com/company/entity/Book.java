package com.company.entity;

import java.io.Serializable;
import java.util.Objects;

public class Book extends AbstractEntity implements Serializable {

    private String title;
    private String description;
    private Author author;
    private int price;
    private Genre genre;
    private Reviews reviews;

    public Book() {

    }

    public Book(String title, String description, Author author, int price, Genre genre) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.price = price;
        this.genre = genre;
    }

    public Book(String title, String description, Author author, int price) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.price = price;
    }


    public Book(int id, String title, String description, Author author, int price) {
        super(id);
        this.title = title;
        this.description = description;
        this.author = author;
        this.price = price;
    }

    public Book(int id, String title, String description, Author author, int price, Genre genre) {
        super(id);
        this.title = title;
        this.description = description;
        this.author = author;
        this.price = price;
        this.genre = genre;
    }

    public Book(String title, String description, Author author, int price, Genre genre, Reviews reviews) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.price = price;
        this.genre = genre;
        this.reviews = reviews;
    }

    public Book(int id, String title, String description, Author author, int price, Genre genre, Reviews reviews) {
        super(id);
        this.title = title;
        this.description = description;
        this.author = author;
        this.price = price;
        this.genre = genre;
        this.reviews = reviews;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(title, book.title) &&
                Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + getId() + '\'' +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", author=" + author +
                ", price=" + price +
                ", genre=" + genre +
                '}';
    }
}
