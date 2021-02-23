package com.company.storage.file.object.wrapper;

import com.company.entity.User;

import java.util.List;

public class UserWrapper extends AbstractWrapper<User> {

    public UserWrapper(int id, List<User> list) {
        super(id, list);
    }
}
