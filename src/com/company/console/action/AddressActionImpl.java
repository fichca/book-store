package com.company.console.action;

import com.company.console.validator.AddressValidator;
import com.company.entity.Address;
import com.company.ioc.annotation.Component;
import com.company.ioc.annotation.Qualifier;
import com.company.service.AddressService;
import com.company.service.AddressServiceImpl;
import com.company.storage.db.AddressDbStorage;

import static com.company.console.util.ConsoleReader.readInt;
import static com.company.console.util.ConsoleReader.readString;
import static com.company.console.util.ConsoleWriter.writeString;
@Component
public class AddressActionImpl implements AddressAction {

    private final AddressService addressService;


    public AddressActionImpl(AddressService addressService) {
        this.addressService = addressService;
    }


    @Override
    public void save() {
        writeString("Enter street");
        String street = readString();
        if (!AddressValidator.validStreet(street)) {
            writeString("Invalid street");
            return;
        }
        writeString("Enter home");
        int home = readInt();
        if (!AddressValidator.validHome(home)) {
            writeString("Invalid home");
            return;
        }
        Address address = new Address(street, home);
        addressService.save(address);
    }

    @Override
    public void removeById() {
        writeString("Enter id");
        int id = readInt();
        if (!AddressValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }
        addressService.remove(id);

    }

    @Override
    public void removeByAddress() {
        writeString("Choice address");
        Address[] all = addressService.getAll();
        for (int i = 0; i < all.length; i++) {
            writeString((i + 1) + " Address " + all[i].getStreet());
        }
        int i = readInt() - 1;
        Address address = all[i];
        addressService.remove(address);
    }

    @Override
    public void updateAddressById() {
        writeString("Enter street");
        String street = readString();
        if (!AddressValidator.validStreet(street)) {
            writeString("Invalid street");
            return;
        }
        writeString("Enter id");
        int id = readInt();
        if (!AddressValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }
        addressService.updateAddressById(street, id);
    }

    @Override
    public void updateHomeById() {
        writeString("Enter home");
        int home = readInt();
        if (!AddressValidator.validHome(home)) {
            writeString("Invalid home");
            return;
        }
        writeString("Enter id");
        int id = readInt();
        if (!AddressValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }
        addressService.updateHomeById(home, id);
    }

    @Override
    public void getAll() {
        Address[] all = addressService.getAll();
        for (int i = 0; i < all.length; i++) {
            writeString((i + 1) + " " + all[i].getStreet() + " " + all[i].getStreet());
        }
    }

    @Override
    public void getById() {
        writeString("Enter id");
        int id = readInt();
        if (!AddressValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }
        addressService.getById(id);
    }

    @Override
    public void getByStreet() {
        writeString("Enter street");
        String street = readString();
        if (!AddressValidator.validStreet(street)) {
            writeString("Invalid street");
            return;
        }
        addressService.getByStreet(street);
    }

    @Override
    public void getByHome() {
        writeString("Enter home");
        int home = readInt();
        if (!AddressValidator.validHome(home)) {
            writeString("Invalid home");
            return;
        }
        addressService.getByHome(home);
    }
}
