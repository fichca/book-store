package com.company.console.action;

import com.company.console.ConsoleApplication;
import com.company.console.util.ConsoleWriter;
import com.company.entity.Book;
import com.company.ioc.annotation.Component;

@Component
public class BasketActionImpl implements BasketAction {
    @Override
    public void getAll() {
        Book[] all = ConsoleApplication.session.getBasket().getAll();
        for (Book book : all) {
            ConsoleWriter.writeString("Book: " + book.getTitle());
        }
    }
}
