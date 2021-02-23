package com.company.console.validator;

public class BookValidator {
    public static boolean validTitle(String title) {
        return title.length() > 2;
    }

    public static boolean validDescription(String description) {
        return description.length() > 3;
    }

    public static boolean validAuthor(String author) {
        return author.length() > 2;
    }

    public static boolean validPrice(int price) {
        return price > 0;
    }

    public static boolean validId(int id) {
        return id > 0;
    }
}
