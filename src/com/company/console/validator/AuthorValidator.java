package com.company.console.validator;

import com.company.entity.Author;

public class AuthorValidator {
    public static boolean validAuthor(Author author) {
        String fullName = author.getFullName();
        return fullName.length() > 2;
    }

    public static boolean validAuthorFullName(String author) {
        return author.length() > 2;
    }

    public static boolean validId(int id) {
        return id > 0;
    }
}
