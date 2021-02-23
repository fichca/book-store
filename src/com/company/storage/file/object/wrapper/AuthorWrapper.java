package com.company.storage.file.object.wrapper;

import com.company.entity.Author;

import java.util.List;

public class AuthorWrapper extends AbstractWrapper<Author> {

    public AuthorWrapper(int id, List<Author> list) {
        super(id, list);
    }
}
