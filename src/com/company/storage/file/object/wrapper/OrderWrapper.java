package com.company.storage.file.object.wrapper;

import com.company.entity.Order;

import java.util.List;

public class OrderWrapper extends AbstractWrapper<Order> {
    public OrderWrapper(int id, List<Order> list) {
        super(id, list);
    }
}
