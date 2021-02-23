package com.company.storage.file.object.wrapper;

import com.company.entity.Book;

import java.util.List;

public class BookWrapper extends AbstractWrapper<Book> {

    public BookWrapper(int id, List<Book> list) {
        super(id, list);
    }
}
