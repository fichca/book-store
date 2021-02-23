package com.company.storage.file.object.wrapper;

import com.company.entity.Address;

import java.util.List;

public class AddressWrapper extends AbstractWrapper<Address> {


    public AddressWrapper(int id, List<Address> list) {
        super(id, list);
    }
}
