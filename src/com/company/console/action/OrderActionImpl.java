package com.company.console.action;

import com.company.console.ConsoleApplication;
import com.company.console.validator.AddressValidator;
import com.company.entity.*;
import com.company.ioc.annotation.Component;
import com.company.ioc.annotation.Qualifier;
import com.company.service.*;

import java.sql.Timestamp;
import java.util.*;

import static com.company.console.util.ConsoleReader.readInt;
import static com.company.console.util.ConsoleReader.readString;
import static com.company.console.util.ConsoleWriter.writeString;
@Component
public class OrderActionImpl implements OrderAction {

    private final OrderService orderService;
    private final StoreService storeService;
    private final BookService bookService;
    private final UserService userService;
    private final AddressService addressService;

    public OrderActionImpl( OrderService orderService, StoreService storeService, BookService bookService, UserService userService, AddressService addressService) {
        this.orderService = orderService;
        this.storeService = storeService;
        this.bookService = bookService;
        this.userService = userService;
        this.addressService = addressService;
    }

    public static void main(String[] args) throws ClassNotFoundException {

    }

    @Override
    public void save() {
        writeString("Choose type order");
        writeString("1 - Delivery");
        writeString("2 - Pickup");
        switch (readInt()) {
            case 1:
                writeString("Enter street");
                String street = readString();
                writeString("Enter home");
                int home = readInt();
                if (!AddressValidator.validStreet(street) && !AddressValidator.validHome(home)) {
                    writeString("Invalid address");
                    return;
                }
                Address address = new Address(street, home);
                addressService.save(address);
                Address[] allAddress = addressService.getAll();
                Book[] all = ConsoleApplication.session.getBasket().getAll();
                User user = ConsoleApplication.session.getUser();
                if (all.length == 0) {
                    writeString("Basket is empty");
                    return;
                }
                int totalPrice = getTotalPrice(all);
                orderService.save(new Order(all, allAddress[allAddress.length - 1], user, totalPrice, Status.ACTIVE));
                break;
            case 2:
                writeString("Choose store");
                Store[] all1 = storeService.getAll();
                for (int i = 0; i < all1.length; i++) {
                    writeString(i + 1 + " " + all1[i] + " " + all1[i].getAddress());
                }
                int i = readInt() - 1;
                Store store = all1[i];
                Book[] all2 = ConsoleApplication.session.getBasket().getAll();
                User user2 = ConsoleApplication.session.getUser();
                if (all2.length == 0) {
                    writeString("Basket is empty");
                    return;
                }
                int totalPrice2 = getTotalPrice(all2);
                orderService.save(new Order(all2, store, user2, totalPrice2));
                break;
            default:
                writeString("Operation not found");
        }
        ConsoleApplication.session.getBasket().cleanBasket();
    }

    private int getTotalPrice(Book[] all) {
        int total = 0;
        for (Book book : all) {
            total = total + book.getPrice();
        }
        return total;
    }

    @Override
    public void deleteById() {
        writeString("Enter id");
        int id = readInt();
        if (!AddressValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }
        orderService.delete(id);
    }

    @Override
    public void deleteByOrder() {
        writeString("Choose order");
        Order[] all = orderService.getAll();
        for (int i = 0; i < all.length; i++) {
            writeString(i + 1 + " " + all[i].toString());
        }
        int i = readInt() - 1;
        Order order = all[i];
        orderService.delete(order);
    }

    @Override
    public void updateAddressById() {
        writeString("Enter id");
        int id = readInt();
        if (!AddressValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }
        writeString("Enter street");
        String street = readString();
        writeString("Enter home");
        int home = readInt();
        if (!AddressValidator.validStreet(street) && !AddressValidator.validHome(home)) {
            writeString("Invalid address");
        }
        Address address = new Address(street, home);
        addressService.save(address);
        Address[] all = addressService.getAll();
        address.setId(all.length - 1);
        orderService.updateAddressById(address, id);
    }

    @Override
    public void updateStoreById() {
        writeString("Enter id");
        int id = readInt();
        if (!AddressValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }
        writeString("Choose store");
        Store[] all1 = storeService.getAll();
        for (int i = 0; i < all1.length; i++) {
            writeString(i + 1 + " " + all1[i] + " " + all1[i].getAddress());
        }
        int i = readInt() - 1;
        Store store = all1[i];
        orderService.updateStoreById(store, id);
    }

    @Override
    public void updateStatusById() {
        writeString("Enter id");
        int id = readInt();
        if (!AddressValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }
        writeString("Choose status");
        writeString("1 - Status active");
        writeString("2 - Status close");
        switch (readInt()) {
            case 1:
                orderService.updateStatusById(Status.ACTIVE, id);
                break;
            case 2:
                orderService.updateStatusById(Status.CLOSE, id);
                break;
            default:
                writeString("Operation not found");
        }
    }

    @Override
    public void addBookById() {
        writeString("Enter id");
        int id = readInt();
        if (!AddressValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }
        writeString("Choose book");
        Book[] books = bookService.getAll();
        for (int i = 0; i < books.length; i++) {
            writeString(i + 1 + " " + books[i].toString());
        }
        int i = readInt() - 1;
        Book book = books[i];

        orderService.addBookById(book, id);
    }

    @Override
    public void deleteBookById() {
        writeString("Enter id");
        int id = readInt();
        if (!AddressValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }
        writeString("Choose book");
        Book[] books = orderService.getOrderById(id).getBooks();
        for (int i = 0; i < books.length; i++) {
            writeString(i + 1 + " " + books[i].toString());
        }
        int i = readInt() - 1;
        Book book = books[i];

        orderService.deleteBookById(book, id);
    }

    @Override
    public void getAllOrderByUserForUser() {
        User user = ConsoleApplication.session.getUser();
        Order[] allByUser = orderService.getAllByUser(user);
        for (int i = 0; i < allByUser.length; i++) {
            writeString(i + 1 + " " + allByUser[i].toString());
        }
    }

    @Override
    public void getOrderById() {
        writeString("Enter id");
        int id = readInt();
        if (!AddressValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }
        writeString(orderService.getOrderById(id).toString());
    }

    @Override
    public void getOrderByOrder() {
        writeString("Choose order");
        Order[] all = orderService.getAll();
        for (int i = 0; i < all.length; i++) {
            writeString(i + 1 + " " + all[i].getUser());
        }
        int i = readInt() - 1;
        Order order = all[i];
        Order orderByOrder = orderService.getOrderByOrder(order);
        writeString(orderByOrder.toString());
    }

    @Override
    public void getAllByUser() {
        writeString("Choose user");
        writeString("1 - Search user by list");
        writeString("2 - Enter user manually");
        User user = new User();
        switch (readInt()) {
            case 1:
                writeString("Choose user");
                User[] all = userService.getAll();
                for (int i = 0; i < all.length; i++) {
                    writeString(i + 1 + " " + all[i].getLogin());
                }
                int i = readInt() - 1;
                user = all[i];
                Order[] allByUser = orderService.getAllByUser(user);
                for (int i1 = 0; i1 < allByUser.length; i1++) {
                    writeString("Order: " + allByUser[i].toString());
                }
                break;
            case 2:
                writeString("Enter login by user");
                String user1 = readString();
                user.setLogin(user1);
                Order[] allByUser1 = orderService.getAllByUser(user);
                for (int i1 = 0; i1 < allByUser1.length; i1++) {
                    writeString(allByUser1[i1].toString());
                }
                break;
            default:
                writeString("Operation not found");
        }
    }

    @Override
    public void getAllByAddress() {
        writeString("Choose address");
        Order[] all = orderService.getAll();
        for (int i = 0; i < all.length; i++) {
            writeString(i + 1 + " " + all[i].getAddress().toString());
        }
        int i = readInt() - 1;
        Address address = all[i].getAddress();
        Order[] allByAddress = orderService.getAllByAddress(address);
        for (int i1 = 0; i1 < allByAddress.length; i1++) {
            writeString(i1 + 1 + " " + allByAddress[i].toString());
        }
    }


    @Override
    public void getAllByStore() {
        writeString("Choose store");
        Store[] all = storeService.getAll();
        for (int i = 0; i < all.length; i++) {
            writeString(i + 1 + " " + all[i].getTitle());
        }
        int i = readInt() - 1;
        Store store = all[i];
        Order[] allByStore = orderService.getAllByStore(store);
        if (allByStore == null) {
            writeString("Orders not found");
            return;
        }
        for (int i1 = 0; i1 < allByStore.length; i1++) {
            writeString(i1 + 1 + " " + allByStore[i1].toString());
        }
    }

    @Override
    public void getAllByStatus() {
        writeString("Choose status");
        writeString("1 - Active");
        writeString("2 - Close");
        switch (readInt()) {
            case 1:
                Status statusActive = Status.ACTIVE;
                Order[] allByStore1 = orderService.getAllByStatus(statusActive);
                for (int i1 = 0; i1 < allByStore1.length; i1++) {
                    writeString(i1 + 1 + " " + allByStore1[i1].toString());
                }
                break;
            case 2:
                Status statusClose = Status.CLOSE;
                Order[] allByStore2 = orderService.getAllByStatus(statusClose);
                for (int i1 = 0; i1 < allByStore2.length; i1++) {
                    writeString(i1 + 1 + " " + allByStore2[i1].toString());
                }
                break;
            default:
                writeString("Operation not found");
        }
    }

    @Override
    public void getAllCloseStatus() {
        Order[] all = orderService.getAllByStatus(Status.ACTIVE);
        getStatus(all);

    }

    @Override
    public void getAllActiveStatus() {
        Order[] all = orderService.getAllByStatus(Status.CLOSE);
        getStatus(all);
    }

    private void getStatus(Order[] all){
        Map<Timestamp, Order> map = new TreeMap<>(Comparator.reverseOrder());
        for (Order order : all) {
            map.put(order.getDate(), order);
        }
        Set<Map.Entry<Timestamp, Order>> entries = map.entrySet();
        int i = 1;
        for (Map.Entry<Timestamp, Order> entry : entries) {
            writeString(i + " " + entry.getValue());
            i++;
        }
    }

    @Override
    public void getAll() {
        Order[] all = orderService.getAll();
        for (int i = 0; i < all.length; i++) {
            writeString(i + 1 + " " + all[i].toString());
        }
    }
}
