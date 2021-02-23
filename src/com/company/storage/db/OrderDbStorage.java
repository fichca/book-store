package com.company.storage.db;

import com.company.entity.*;
import com.company.ioc.annotation.Component;
import com.company.storage.OrderStorage;
import com.company.storage.db.mapper.OrderMapper;
import com.company.storage.db.util.OrderUtil;

import java.nio.channels.NoConnectionPendingException;
import java.sql.*;
import java.util.*;
import java.util.Date;

import static com.company.console.util.ConsoleWriter.writeString;
import static com.company.storage.db.Constant.*;
@Component
public class OrderDbStorage extends AbstractDbStorage implements OrderStorage {

    public static void main(String[] args) {
        OrderDbStorage orderDbStorage = new OrderDbStorage();
        System.out.println(Arrays.toString(orderDbStorage.getAll()));
        Address address = new Address(2, "test", 22);
        Store store = new Store(3, "tets", new Address("test", 22));
        User user = new User(3, "test", "test", "test", "test", 22, Role.USER, new Address(2, "test", 22));
        Book book = new Book(2, "test", "test", new Author(1, "test"), 22);
        Book book2 = new Book(3, "test", "test", new Author(1, "test"), 22);
        Book[] books = {book, book2};
//        Order order = new Order(books, store, user, 22, Status.ACTIVE);
//        Order order = new Order(books, address, user, 22, Status.ACTIVE);
//        orderDbStorage.save(order);
//        orderDbStorage.delete(7);
//        System.out.println(Arrays.toString(orderDbStorage.getAllByAddress(new Address(1, " test", 22))));
//        order.setId(11);



        System.out.println(Arrays.toString(orderDbStorage.getAll()));

    }

    @Override
    public boolean save(Order order) {
        try {
            connection.setAutoCommit(false);

            int idStatus = OrderUtil.getIdStatusFromDb(connection, order.getStatus());
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ORDER);
            preparedStatement.setInt(1, order.getUser().getId());
            preparedStatement.setInt(2, order.getTotalPrice());
            preparedStatement.setInt(3, idStatus);
            preparedStatement.setTimestamp(5, new Timestamp(new Date().getTime()));
            preparedStatement.setBoolean(4, order.getStore() != null);
            ResultSet resultSet1 = preparedStatement.executeQuery();
            resultSet1.next();
            int orderId = resultSet1.getInt(1);

            PreparedStatement preparedStatement1;
            if (order.getStore() == null) {
                preparedStatement1 = connection.prepareStatement(CREATE_ORDER_ADDRESS);
                preparedStatement1.setInt(1, orderId);
                preparedStatement1.setInt(2, order.getAddress().getId());
            } else {
                preparedStatement1 = connection.prepareStatement(CREATE_ORDER_STORE);
                preparedStatement1.setInt(1, orderId);
                preparedStatement1.setInt(2, order.getStore().getId());
            }
            preparedStatement1.execute();

            Book[] books = order.getBooks();
            for (Book book : books) {
                PreparedStatement preparedStatement2 = connection.prepareStatement(CREATE_ORDER_BOOK);
                preparedStatement2.setInt(1, orderId);
                preparedStatement2.setInt(2, book.getId());
                preparedStatement2.execute();
            }
            connection.commit();
            return true;

        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void delete(int id) {
        deleteById(id);
    }

    @Override
    public void delete(Order order) {
        delete(order.getId());
    }

    private void deleteById(int id) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("select delivery from orders where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            PreparedStatement preparedStatement3 = connection.prepareStatement(DELETE_ORDER_BY_ID);
            preparedStatement3.setInt(1, id);
            preparedStatement3.execute();
            boolean aBoolean = resultSet.getBoolean(1);
            PreparedStatement preparedStatement1;
            if (aBoolean) {
                preparedStatement1 = connection.prepareStatement("DELETE FROM order_store WHERE order_id = ?");
            } else {
                preparedStatement1 = connection.prepareStatement("DELETE FROM order_address WHERE order_id = ?");
            }
            preparedStatement1.setInt(1, id);
            preparedStatement1.execute();
            PreparedStatement preparedStatement2 = connection.prepareStatement(DELETE_ORDER_BOOK_ODER);
            preparedStatement2.setInt(1, id);
            preparedStatement2.execute();
            connection.commit();
            return;
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        throw new NoConnectionPendingException();
    }

    @Override
    public Address updateAddressById(Address address, int id) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER_ADDRESS_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Address orderAddress = OrderMapper.getOrderAddress(resultSet);

            PreparedStatement preparedStatement1 = connection.prepareStatement(UPDATE_ORDER_ADDRESS_BY_ID);
            preparedStatement1.setInt(1, address.getId());
            preparedStatement1.setInt(2, id);
            preparedStatement1.execute();
            connection.commit();
            return orderAddress;
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        throw new NoConnectionPendingException();
    }

    @Override
    public Store updateStoreById(Store store, int id) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER_STORE_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Store orderStore = OrderMapper.getOrderStore(resultSet);

            PreparedStatement preparedStatement1 = connection.prepareStatement(UPDATE_ORDER_STORE_BY_ID);
            preparedStatement1.setInt(1, store.getId());
            preparedStatement1.setInt(2, id);
            preparedStatement1.execute();
            connection.commit();
            return orderStore;
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        throw new NoConnectionPendingException();
    }

    @Override
    public Status updateStatusById(Status status, int id) {
        try {
            connection.setAutoCommit(false);
            Status statusByIdOrder = OrderUtil.getStatusByIdOrder(connection, id);
            int idStatusFromDb = OrderUtil.getIdStatusFromDb(connection, status);
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER_STATUS_BY_ID);
            preparedStatement.setInt(1, idStatusFromDb);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
            connection.commit();
            return statusByIdOrder;
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        throw new NoConnectionPendingException();
    }

    @Override
    public boolean addBookById(Book book, int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_ORDER_BOOK_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, book.getId());
            preparedStatement.execute();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public Book deleteBookById(Book book, int id) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ORDER_BOOK_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, book.getId());
            preparedStatement.execute();
            connection.commit();
            return book;
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        throw new NoConnectionPendingException();
    }

    @Override
    public Order getOrderById(int id) {
        return getOrder(id);
    }

    private Order getOrder(int id) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("select * from orders o join users u on u.id = o.user_id join addresses a2 on a2.id = u.address_id join roles r on r.id = u.role_id join statuses st on st.id = o.status_id where o.id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            Order order = OrderMapper.createOrder(resultSet);
            boolean delivery = resultSet.getBoolean(5);

            PreparedStatement preparedStatement1 = connection.prepareStatement("select * from order_book o join books b on b.id = o.book_id join authors a on a.id = b.author_id join genres g on g.id = b.genre_id where o.order_id = ?");
            preparedStatement1.setInt(1, id);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            List<Book> booksList = OrderMapper.getBooksList(resultSet1);
            order.setBooks(booksList.toArray(new Book[0]));
            if (!delivery) {
                PreparedStatement preparedStatement2 = connection.prepareStatement("select * from order_address o join addresses a on a.id = o.address_id");
                ResultSet resultSet2 = preparedStatement2.executeQuery();
                Address orderAddress = OrderMapper.getOrderAddress(resultSet2);
                order.setAddress(orderAddress);
            } else {
                PreparedStatement preparedStatement2 = connection.prepareStatement("select * from order_store o join stores s on s.id = o.store_id join addresses a on a.id = s.address_id");
                ResultSet resultSet2 = preparedStatement2.executeQuery();
                Store orderStore = OrderMapper.getOrderStore(resultSet2);
                order.setStore(orderStore);
            }

            connection.commit();
            return order;
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        throw new NoConnectionPendingException();
    }

    @Override
    public Order getOrderByOrder(Order order) {
        return getOrder(order.getId());
    }

    @Override
    public Order[] getAllByUser(User user) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("select * from orders o join users u on u.id = o.user_id join addresses a2 on a2.id = u.address_id join roles r on r.id = u.role_id join statuses st on st.id = o.status_id where o.user_id = ?");
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            Order[] orders = getMassiveOrders(resultSet);
            connection.commit();
            return orders;
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        throw new NoConnectionPendingException();
    }

    @Override
    public Order[] getAllByAddress(Address address) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("select * from orders o join users u on u.id = o.user_id join addresses a2 on a2.id = u.address_id join roles r on r.id = u.role_id join statuses st on st.id = o.status_id join order_address oa on oa.order_id = o.id where oa.address_id = ?");
            preparedStatement.setInt(1, address.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            Order[] orders = getMassiveOrders(resultSet);
            connection.commit();
            return orders;
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        throw new NoConnectionPendingException();
    }

    @Override
    public Order[] getAllByStore(Store store) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("select * from orders o join users u on u.id = o.user_id join addresses a2 on a2.id = u.address_id join roles r on r.id = u.role_id join statuses st on st.id = o.status_id join order_store os on os.order_id = o.id where os.store_id = ?");
            preparedStatement.setInt(1, store.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            Order[] orders = getMassiveOrders(resultSet);
            connection.commit();
            return orders;
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        throw new NoConnectionPendingException();
    }

    @Override
    public Order[] getAllByStatus(Status status) {
        try {
            connection.setAutoCommit(false);
            int idStatusFromDb = OrderUtil.getIdStatusFromDb(connection, status);
            PreparedStatement preparedStatement = connection.prepareStatement("select * from orders o join users u on u.id = o.user_id join addresses a2 on a2.id = u.address_id join roles r on r.id = u.role_id join statuses st on st.id = o.status_id where o.status_id = ?");
            preparedStatement.setInt(1, idStatusFromDb);
            ResultSet resultSet = preparedStatement.executeQuery();
            Order[] orders = getMassiveOrders(resultSet);
            connection.commit();
            return orders;
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        throw new NoConnectionPendingException();
    }

    @Override
    public Order[] getAll() {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("select * from orders o join users u on u.id = o.user_id join addresses a2 on a2.id = u.address_id join roles r on r.id = u.role_id join statuses st on st.id = o.status_id");
            ResultSet resultSet = preparedStatement.executeQuery();
            Order[] orders = getMassiveOrders(resultSet);
            connection.commit();
            return orders;
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        throw new NoConnectionPendingException();
    }

    private Order[] getMassiveOrders(ResultSet resultSet) throws SQLException {
        List<Order> orders = new ArrayList<>();
        List<Boolean> delivery = new ArrayList<>();

        while (resultSet.next()) {
            Order order = OrderMapper.createOrder(resultSet);
            orders.add(order);
            delivery.add(resultSet.getBoolean(5));
        }
        for (int i = 0; i < orders.size(); i++) {
            PreparedStatement preparedStatement1 = connection.prepareStatement("select * from order_book o join books b on b.id = o.book_id join authors a on a.id = b.author_id join genres g on g.id = b.genre_id where o.order_id = ?");
            preparedStatement1.setInt(1, orders.get(i).getId());
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            List<Book> booksList = OrderMapper.getBooksList(resultSet1);
            orders.get(i).setBooks(booksList.toArray(new Book[0]));
            if (!delivery.get(i)) {
                PreparedStatement preparedStatement2 = connection.prepareStatement("select * from order_address o join addresses a on a.id = o.address_id");
                ResultSet resultSet2 = preparedStatement2.executeQuery();
                Address orderAddress = OrderMapper.getOrderAddress(resultSet2);
                orders.get(i).setAddress(orderAddress);
            } else {
                PreparedStatement preparedStatement2 = connection.prepareStatement("select * from order_store o join stores s on s.id = o.store_id join addresses a on a.id = s.address_id");
                ResultSet resultSet2 = preparedStatement2.executeQuery();
                Store orderStore = OrderMapper.getOrderStore(resultSet2);
                orders.get(i).setStore(orderStore);
            }
        }
        return orders.toArray(new Order[0]);

    }

    @Override
    public boolean contains(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select id from orders where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

    @Override
    public boolean contains(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select user_id from orders where user_id = ?");
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

    @Override
    public boolean contains(Address address) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select address_id from order_address where address_id = ?");
            preparedStatement.setInt(1, address.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

    @Override
    public boolean contains(Store store) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select store_id from order_store where store_id = ?");
            preparedStatement.setInt(1, store.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

    @Override
    public boolean contains(Book book) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select book_id from order_book where book_id = ?");
            preparedStatement.setInt(1, book.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

    @Override
    public boolean contains(Order order) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select id from orders where id = ?");
            preparedStatement.setInt(1, order.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

    @Override
    public boolean contains(Status status) {
        try {
            connection.setAutoCommit(false);
            int idStatusFromDb = OrderUtil.getIdStatusFromDb(connection, status);
            PreparedStatement preparedStatement = connection.prepareStatement("select status_id from orders where status_id = ?");
            preparedStatement.setInt(1, idStatusFromDb);
            ResultSet resultSet = preparedStatement.executeQuery();
            connection.commit();
            return resultSet.next();
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        throw new NoConnectionPendingException();
    }
}
