package com.company.storage.db;

public final class Constant {


    protected static final String GET_ROLE_ID = "select * from  roles where role = ?";
    protected static final String GET_ROLE_BY_ID = "select * from  roles where id = ?";

    protected static final String CREATE_ADDRESS = "INSERT INTO addresses VALUES(DEFAULT, ?, ?)";
    protected static final String DELETE_ADDRESS_BY_ID = "DELETE FROM addresses WHERE id = ?";
    protected static final String DELETE_BY_ADDRESS = "DELETE FROM addresses WHERE street = ? AND home = ?";
    protected static final String UPDATE_ADDRESS_BY_ID = "update addresses set street = ? where id = ?";
    protected static final String UPDATE_HOME_ADDRESS_BY_ID = "update addresses set home = ? where id = ?;";
    protected static final String GET_ADDRESS_BY_ID = "select * from  addresses where id = ?";
    protected static final String GET_ADDRESS_BY_STREET = "select * from  addresses where street = ?";
    protected static final String GET_ADDRESS_BY_HOME = "select * from  addresses where home = ?";
    protected static final String CONTAINS_ADDRESS_BY_ID = "select * from  addresses where id = ?";
    protected static final String CONTAINS_BY_ADDRESS = "select * from  addresses where street = ? AND home = ?";

    protected static final String CREATE_AUTHOR = "INSERT INTO authors VALUES(DEFAULT, ?)";
    protected static final String DELETE_AUTHOR_BY_ID = "DELETE FROM authors WHERE id = ?";
    protected static final String DELETE_AUTHOR_FULL_NAME = "DELETE FROM authors WHERE full_name = ?";
    protected static final String UPDATE_AUTHOR_FULL_NAME = "update authors set full_name = ? where id = ?;";
    protected static final String GET_AUTHOR_BY_ID = "select * from  authors where id = ?";
    protected static final String GET_ALL_AUTHOR = "select * from  authors";
    protected static final String GET_AUTHOR_FULL_NAME = "select * from  authors where full_name = ?";
    protected static final String CONTAINS_AUTHOR_BY_ID = "select * from  authors where id = ?";
    protected static final String CONTAINS_BY_AUTHOR = "select * from  authors where full_name = ?";

    protected static final String CREATE_BOOK = "INSERT INTO books VALUES(DEFAULT, ?, ?, ?, ?, ?)";

    protected static final String CREATE_COUNT_VIEWS = "INSERT INTO count_views VALUES(?, ?)";
    protected static final String GET_VIEWS_BY_BOOK = "select * from count_views where book_id = ?";

    protected static final String DELETE_BOOK_BY_ID = "DELETE FROM books WHERE id = ?";
    protected static final String DELETE_BOOK = "DELETE FROM books WHERE title = ? AND description = ? AND price = ? AND author_id = ?";
    protected static final String UPDATE_BOOK_TITLE_BY_ID = "update books set title = ? where id = ?";
    protected static final String UPDATE_BOOK_DESCRIPTION_BY_ID = "update books set description = ? where id = ?";
    protected static final String UPDATE_BOOK_PRICE_BY_ID = "update books set price = ? where id = ?";
    protected static final String UPDATE_BOOK_AUTHOR_BY_ID = "update books set author_id = ? where id = ?";
    protected static final String UPDATE_BOOK_GENRE_BY_ID = "update books set genre_id = ? where id = ?";
    protected static final String GET_BOOK_BY_ID = "select * from  books b join authors a on a.id = b.author_id join genres g on g.id = b.genre_id where b.id = ?";
    protected static final String GET_BOOK_BY_TITLE = "select * from  books b join authors a on a.id = b.author_id join genres g on g.id = b.genre_id where title = ?";
    protected static final String GET_ALL_BOOKS = "select * from books b join authors a on a.id = b.author_id join genres g on g.id = b.genre_id";
    protected static final String GET_ALL_BOOKS_BY_AUTHOR = "select * from  books b join authors a on a.id = b.author_id join genres g on g.id = b.genre_id where author_id = ?";
    protected static final String GET_ALL_BOOKS_BY_PRICE = "select * from  books b join authors a on a.id = b.author_id join genres g on g.id = b.genre_id where price = ?";
    protected static final String GET_ALL_BOOKS_BY_GENRE = "select * from  books b join authors a on a.id = b.author_id join genres g on g.id = b.genre_id where b.genre_id = ?";
    protected static final String CONTAINS_BOOK_BY_ID = "select * from  books WHERE id = ?";
    protected static final String CONTAINS_BOOK_BY_BOOK = "select * from  books WHERE title = ? AND description = ? AND price = ? AND author_id = ?";
    protected static final String CONTAINS_BOOK_BY_TITLE = "select * from  books where title = ?";

    protected static final String CREATE_STORE = "INSERT INTO stores VALUES(DEFAULT, ?, ?)";
    protected static final String UPDATE_STORE_TITLE_BY_ID = "update stores set title = ? where id = ?;";
    protected static final String UPDATE_STORE_ADDRESS_BY_ID  = "update stores set address_id = ? where id = ?;";
    protected static final String DELETE_STORE_BY_ID = "DELETE FROM stores WHERE id = ?";
    protected static final String DELETE_STORE_BY_TITLE = "DELETE FROM stores WHERE title = ?";
    protected static final String DELETE_STORE_BY_STORE = "DELETE FROM stores WHERE title = ? AND address_id = ?";
    protected static final String GET_ALL_STORE = "select * from stores s join addresses a on a.id = s.address_id";
    protected static final String GET_STORE_BY_TITLE = "select * from stores s join addresses a on a.id = s.address_id where s.title = ?";
    protected static final String GET_STORE_BY_ID = "select * from stores s join addresses a on a.id = s.address_id where s.id = ?";
    protected static final String CONTAINS_STORE_BY_ID = "select * from  stores WHERE id = ?";
    protected static final String CONTAINS_STORE_BY_TITLE = "select * from  stores where title = ?";
    protected static final String CONTAINS_STORE_BY_STORE = "select * from  stores WHERE title = ? AND address_id = ?";
    protected static final String CONTAINS_ADDRESS_BY_STORE = "select * from  stores WHERE address_id = ?";

    protected static final String CREATE_USER = "INSERT INTO users VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?)";
    protected static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE id = ?";
    protected static final String DELETE_USER_BY_LOGIN = "DELETE FROM users WHERE login = ?";
    protected static final String DELETE_USER_BY_USER = "DELETE FROM users WHERE login = ? AND password = ? AND first_name = ? AND last_name = ? AND role_id = ? AND address_id = ?";
    protected static final String UPDATE_USER_FIRST_NAME_BY_ID = "update users set first_name = ? where id = ?";
    protected static final String UPDATE_USER_LAST_NAME_BY_ID = "update users set last_name = ? where id = ?";
    protected static final String UPDATE_USER_AGE_BY_ID = "update users set age = ? where id = ?";
    protected static final String UPDATE_USER_ADDRESS_BY_ID = "update users set address_id = ? where id = ?";
    protected static final String UPDATE_USER_ROLE_BY_ID = "update users set role_id = ? where id = ?";
    protected static final String GET_ALL_USER = "select * from users u join addresses a on a.id = u.address_id join roles r on r.id = u.role_id";
    protected static final String GET_ALL_USER_BY_FIRST_NAME = "select * from users u join addresses a on a.id = u.address_id join roles r on r.id = u.role_id where first_name = ?";
    protected static final String GET_ALL_USER_BY_LAST_NAME = "select * from users u join addresses a on a.id = u.address_id join roles r on r.id = u.role_id where last_name = ?";
    protected static final String GET_ALL_USER_BY_AGE = "select * from users u join addresses a on a.id = u.address_id join roles r on r.id = u.role_id where age = ?";
    protected static final String GET_USER_BY_ID = "select * from users u join addresses a on a.id = u.address_id join roles r on r.id = u.role_id where u.id = ?";
    protected static final String GET_USER_BY_LOGIN = "select * from users u join addresses a on a.id = u.address_id join roles r on r.id = u.role_id where login = ?";
    protected static final String GET_ALL_USER_BY_ROLE = "select * from users u join addresses a on a.id = u.address_id join roles r on r.id = u.role_id where role_id = ?";
    protected static final String CONTAINS_USER_BY_ID = "select * from  users WHERE id = ?";
    protected static final String CONTAINS_USER_BY_LOGIN = "select * from  users  where login = ?";
    protected static final String CONTAINS_USER_BY_USER = "select * from  users WHERE login = ? AND password = ? AND first_name = ? AND last_name = ?";
    protected static final String CONTAINS_USER_ROLE = "select * from  users where role_id = ?";


    protected static final String CREATE_ORDER = "INSERT INTO orders VALUES(DEFAULT, ?, ?, ?, ?, ?) returning id";
    protected static final String CREATE_ORDER_BOOK = "INSERT INTO order_book VALUES(?, ?)";
    protected static final String CREATE_ORDER_ADDRESS = "INSERT INTO order_address VALUES(?, ?)";
    protected static final String CREATE_ORDER_STORE = "INSERT INTO order_store VALUES(?, ?)";
    protected static final String GET_STATUS_ID = "select * from statuses where status = ?";

    protected static final String DELETE_ORDER_BY_ID = "DELETE FROM orders  WHERE id = ?";
    protected static final String DELETE_ORDER_BY_ODER = "DELETE FROM orders WHERE address_id = ? AND store_id = ? AND user_id = ? AND total_price = ? AND status_id = ?";
    protected static final String DELETE_ORDER_BOOK_ODER = "DELETE FROM order_book WHERE order_id = ?";

    protected static final String UPDATE_ORDER_ADDRESS_BY_ID = "update order_address set address_id = ? where order_id = ?";
    protected static final String GET_ORDER_ADDRESS_BY_ID = "select * from order_address o join addresses a on a.id = o.address_id where id = ?";
    protected static final String UPDATE_ORDER_STORE_BY_ID = "update order_store set store_id = ? where order_id = ?";
    protected static final String GET_ORDER_STORE_BY_ID = "select * from order_store o join stores s on s.id = o.store_id join addresses a on a.id = s.address_id where o.order_id = ? ";
    protected static final String UPDATE_ORDER_STATUS_BY_ID = "update orders set status_id = ? where id = ?";

    protected static final String ADD_ORDER_BOOK_BY_ID = "INSERT INTO order_book VALUES(?, ?)";
    protected static final String DELETE_ORDER_BOOK_BY_ID = "DELETE FROM order_book WHERE order_id = ? AND book_id = ?";

    protected static final String GET_ORDER_BY_ID = "select * from  orders o join addresses a on a.id = o.address_id join stores s on s.id = o.store_id join users u on u.id = o.user_id join statuses st on st.id = o.status_id where id = ?";
    protected static final String GET_ALL_ORDER_BY_USER = "select * from  orders o join addresses a on a.id = o.address_id join stores s on s.id = o.store_id join users u on u.id = o.user_id join statuses st on st.id = o.status_id where user_id = ?";
    protected static final String GET_ALL_ORDER_BY_ADDRESS = "select * from  orders o join addresses a on a.id = o.address_id join stores s on s.id = o.store_id join users u on u.id = o.user_id join statuses st on st.id = o.status_id where address_id = ?";
    protected static final String GET_ALL_ORDER_BY_STORE = "select * from  orders o join addresses a on a.id = o.address_id join stores s on s.id = o.store_id join users u on u.id = o.user_id join statuses st on st.id = o.status_id where store_id = ?";
    protected static final String GET_ALL_ORDER_BY_STATUS = "select * from  orders o join addresses a on a.id = o.address_id join stores s on s.id = o.store_id join users u on u.id = o.user_id join statuses st on st.id = o.status_id where status_id = ?";
    protected static final String GET_ALL_ORDER = "select * from  orders o join addresses a on a.id = o.address_id join stores s on s.id = o.store_id join users u on u.id = o.user_id join statuses st on st.id = o.status_id";

    protected static final String CONTAINS_ORDER_BY_ID = "select * from  orders WHERE id = ?";
    protected static final String CONTAINS_ORDER_BY_USER = "select * from  orders WHERE user_id = ?";
    protected static final String CONTAINS_ORDER_BY_ADDRESS = "select * from  orders WHERE address_id = ?";
    protected static final String CONTAINS_ORDER_BY_STORE = "select * from  orders WHERE store_id = ?";
    protected static final String CONTAINS_ORDER_BY_BOOK = "select * from  order_book WHERE book_id = ?";
//    protected static final String CONTAINS_ORDER_BY_ORDER = "select * from  orders WHERE user_id = ? AND status_id = ?";
    protected static final String CONTAINS_ORDER_BY_STATUS = "select * from  orders WHERE status_id = ?";


    protected static final String CREATE_GENRE = "INSERT INTO genres VALUES(DEFAULT, ?, ?)";
    protected static final String DELETE_GENRE_BY_GENRE = "DELETE FROM genres WHERE name = ?";
    protected static final String DELETE_GENRE_BY_ID = "DELETE FROM genres WHERE id = ?";
    protected static final String UPDATE_GENRE_NAME_BY_ID = "update genres set name = ? where id = ?;";
    protected static final String UPDATE_GENRE_DESCRIPTION_BY_ID  = "update genres set description = ? where id = ?;";
    protected static final String GET_ALL_GENRE = "select * from genres";
    protected static final String GET_GENRE_BY_ID = "select * from genres where id = ?";
    protected static final String GET_GENRE_BY_NAME = "select * from genres where name = ?";
    protected static final String CONTAINS_GENRE_BY_ID = "select * from genres where id = ?";
    protected static final String CONTAINS_GENRE_BY_NAME = "select * from  genres where name = ?";

    protected static final String CREATE_REVIEWS = "INSERT INTO reviews VALUES(DEFAULT, ?, ?, ?) returning id";
    protected static final String CREATE_REVIEWS_BOOK = "INSERT INTO book_reviews VALUES(?, ?)";
    protected static final String DELETE_REVIEWS_BY_ID = "DELETE FROM reviews where id = ?";
    protected static final String UPDATE_REVIEWS_BY_ID = "update reviews set reviews = ? where id = ?;";
    protected static final String GET_ALL_REVIEWS = "select * from  reviews";
    protected static final String GET_REVIEWS_BY_ID = "select * from  reviews where id = ?";
    protected static final String CONTAINS_REVIEWS_BY_ID = "select * from reviews where id = ?";
    protected static final String CONTAINS_REVIEWS_BY_REVIEWS = "select * from reviews where reviews = ?";
    protected static final String CONTAINS_REVIEWS_BY_BOOK = "select * from book_reviews where book_id = ?";
}
