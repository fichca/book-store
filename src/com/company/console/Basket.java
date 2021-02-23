package com.company.console;

import com.company.entity.Book;
import com.company.ioc.annotation.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class Basket {
    private final List<Book> books = new ArrayList<>(10);
    private int size;

    public boolean add(Book book) {
        books.add(book);
        return true;
    }

    public  void cleanBasket(){
        books.clear();
    }

    public boolean isEmpty() {
        return books.isEmpty();
    }

    public Book[] getAll() {
        return books.toArray(new Book[0]);
    }
}
