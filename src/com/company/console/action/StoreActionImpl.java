package com.company.console.action;

import com.company.console.validator.StoreValidator;
import com.company.entity.Address;
import com.company.entity.Store;
import com.company.ioc.annotation.Component;
import com.company.ioc.annotation.Qualifier;
import com.company.service.AddressService;
import com.company.service.AddressServiceImpl;
import com.company.service.StoreService;
import com.company.service.StoreServiceImpl;

import static com.company.console.util.ConsoleReader.readInt;
import static com.company.console.util.ConsoleReader.readString;
import static com.company.console.util.ConsoleWriter.writeString;
@Component
public class StoreActionImpl implements StoreAction {
    private final StoreService storeService;
    private final AddressService addressService;

    public StoreActionImpl( StoreService storeService, AddressService addressService) {
        this.storeService = storeService;
        this.addressService = addressService;
    }

    @Override
    public void save() {
        writeString("Enter title");
        String title = readString();
        if (!StoreValidator.validTitle(title)) {
            writeString("Invalid title");
        }

        writeString("Choice address");
        Address[] all = addressService.getAll();
        for (int i = 0; i < all.length; i++) {
            writeString((i + 1) + " Address " + all[i].getStreet());
        }
        int i = readInt() - 1;
        Address address = all[i];

        storeService.save(new Store(title, address));
    }

    @Override
    public void updateTitle() {
        writeString("Enter title");
        String title = readString();
        if (!StoreValidator.validTitle(title)) {
            writeString("Invalid title");
            return;
        }
        writeString("Enter id");
        int id = readInt();
        if (!StoreValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }

        storeService.updateTitle(title, id);
    }

    @Override
    public void updateAddress() {
        writeString("Enter id");
        int id = readInt();
        if (!StoreValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }
        Address[] all = addressService.getAll();
        writeString("Choice address");
        for (int i = 0; i < all.length; i++) {
            writeString(i + 1 + " " + all[i]);
        }
        int i = readInt() - 1;
        Address address = all[i];
        storeService.updateAddress(address, id);
    }

    @Override
    public void deleteById() {
        writeString("Enter id");
        int id = readInt();
        if (!StoreValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }
        storeService.delete(id);
    }

    @Override
    public void deleteByTitle() {
        writeString("Enter title");
        String title = readString();
        if (!StoreValidator.validTitle(title)) {
            writeString("Invalid title");
            return;
        }
        storeService.delete(title);
    }

    @Override
    public void deleteByStore() {
        Store[] all = storeService.getAll();
        writeString("Choice store");
        for (int i = 0; i < all.length; i++) {
            writeString((i + 1) + " " + all[i].getTitle());
        }
        int i = readInt() - 1;
        Store store = all[i];
        storeService.delete(store);
    }

    @Override
    public void getAll() {
        Store[] all = storeService.getAll();
        for (int i = 0; i < all.length; i++) {
            writeString(i + 1 + " Store " + all[i].getTitle() + " , address " + all[i].getAddress().getStreet());
        }

    }

    @Override
    public void getByTitle() {
        writeString("Enter title");
        String title = readString();
        if (!StoreValidator.validTitle(title)) {
            writeString("Invalid title");
            return;
        }
        Store byTitle = storeService.getByTitle(title);
        if (byTitle == null) {
            System.out.println("Title not found");
            return;
        }
        writeString("Title" + byTitle.getTitle() + " address store" + byTitle.getAddress());

    }

    @Override
    public void getById() {
        writeString("Enter id");
        int id = readInt();
        if (!StoreValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }

        Store byId = storeService.getById(id);
        if (byId == null) {
            System.out.println("Id not found");
            return;
        }
        writeString("Title" + byId.getTitle() + " address store" + byId.getAddress());

    }
}
