package com.company.storage.db;

import com.company.entity.Address;
import com.company.entity.Role;
import com.company.entity.User;
import com.company.ioc.annotation.Component;
import com.company.storage.UserStorage;
import com.company.storage.db.mapper.UserMapper;
import com.company.storage.db.util.UserUtil;

import java.nio.channels.NoConnectionPendingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static com.company.storage.db.Constant.*;
@Component
public class UserDbStorage extends AbstractDbStorage implements UserStorage {

    public static void main(String[] args) {
        UserDbStorage userDbStorage = new UserDbStorage();

        System.out.println(Arrays.toString(userDbStorage.getAll()));
//        userDbStorage.save(new User( "test99", "test3", "test3" , "test3", 33, Role.USER, new Address(3, "test2", 22)));
//        System.out.println(userDbStorage.updateAddressById(new Address(4, "tets", 22), 9));
//        userDbStorage.updateFirstNameById("test2", 9);
        System.out.println(userDbStorage.contains(1));
        System.out.println(userDbStorage.contains(44));
        System.out.println(userDbStorage.contains("test2"));
        System.out.println(userDbStorage.contains("test55"));
        System.out.println(userDbStorage.contains(Role.USER));
        System.out.println(userDbStorage.contains(Role.MODERATOR));
        System.out.println(userDbStorage.contains(new User( "test99", "test3", "test2" , "TEST", 99, Role.ADMIN, new Address(4, "test2", 22))));
        System.out.println(userDbStorage.contains(new User( "test99w", "test3", "TEST" , "TEST", 99, Role.ADMIN, new Address(4, "test2", 22))));
        System.out.println(Arrays.toString(userDbStorage.getAll()));
    }


    @Override
    public void save(User user) {
        try {
            connection.setAutoCommit(false);

            int idRole = UserUtil.getIdRoleFromDb(connection, user.getRole());

            PreparedStatement preparedStatement1 = connection.prepareStatement(CREATE_USER);
            preparedStatement1.setString(1, user.getLogin());
            preparedStatement1.setString(2, user.getPassword());
            preparedStatement1.setString(3, user.getFirstName());
            preparedStatement1.setString(4, user.getLastName());
            preparedStatement1.setInt(5, user.getAge());
            preparedStatement1.setInt(6, idRole);
            preparedStatement1.setInt(7, user.getAddress().getId());

            preparedStatement1.execute();

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
        throw new NoSuchElementException();

    }

    @Override
    public boolean delete(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    @Override
    public void delete(String login) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_BY_LOGIN);
            preparedStatement.setString(1, login);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void delete(User user) {
        try {
            connection.setAutoCommit(false);

            int idRole = UserUtil.getIdRoleFromDb(connection, user.getRole());

            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_BY_USER);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setInt(5, idRole);
            preparedStatement.setInt(6, user.getAddress().getId());

            preparedStatement.execute();
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
        throw new NoSuchElementException();

    }
    
//    private int getIdRole(User user){
//        try {
//            PreparedStatement preparedStatement1 = connection.prepareStatement(GET_ROLE_ID);
//            preparedStatement1.setString(1, user.getRole().name());
//            ResultSet resultSet = preparedStatement1.executeQuery();
//            return UserMapper.getIdRole(resultSet);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        throw new NoConnectionPendingException();
//
//    }

    @Override
    public String updateFirstNameById(String firstName, int id) {
        try {
            String old = getUserById(id).getFirstName();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_FIRST_NAME_BY_ID);
            preparedStatement.setString(1, firstName);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
            return old;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

    @Override
    public String updateLastNameById(String lastName, int id) {
        try {
            String old = getUserById(id).getLastName();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_LAST_NAME_BY_ID);
            preparedStatement.setString(1, lastName);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
            return old;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

    @Override
    public int updateAgeById(int age, int id) {
        try {
            int old = getUserById(id).getAge();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_AGE_BY_ID);
            preparedStatement.setInt(1, age);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
            return old;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

    @Override
    public Role updateRoleById(Role role, int id) {
        try {
            connection.setAutoCommit(false);
            Role old = getUserById(id).getRole();
            int idRoleFromDb = UserUtil.getIdRoleFromDb(connection, role);
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_ROLE_BY_ID);
            preparedStatement.setInt(1, idRoleFromDb);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
            connection.commit();
            return old;

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
        throw new NoSuchElementException();
    }

    @Override
    public Address updateAddressById(Address address, int id) {
        try {
        Address old = getUserById(id).getAddress();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_ADDRESS_BY_ID );
        preparedStatement.setInt(1, address.getId());
        preparedStatement.setInt(2, id);
            preparedStatement.execute();
        return old;

    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
        throw new NoConnectionPendingException();
    }

    private User getUserById(int id){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return UserMapper.getUser(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

    @Override
    public User[] getAll() {
        return UserUtil.getListUsereFromDb(connection).toArray(new User[0]);
    }

    @Override
    public User[] getAllByFirstName(String firstName) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USER_BY_FIRST_NAME);
            preparedStatement.setString(1, firstName);
            ResultSet resultSet = preparedStatement.executeQuery();
            return UserMapper.getUserList(resultSet).toArray(new User[0]);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

    @Override
    public User[] getAllByLastName(String lastName) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USER_BY_LAST_NAME);
            preparedStatement.setString(1, lastName);
            ResultSet resultSet = preparedStatement.executeQuery();
            return UserMapper.getUserList(resultSet).toArray(new User[0]);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

    @Override
    public User[] getAllByAge(int age) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USER_BY_AGE);
            preparedStatement.setInt(1, age);
            ResultSet resultSet = preparedStatement.executeQuery();
            return UserMapper.getUserList(resultSet).toArray(new User[0]);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

    @Override
    public User getById(int id) {
        return getUserById(id);
    }

    @Override
    public User getByLogin(String login) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_LOGIN);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            return UserMapper.getUser(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

    @Override
    public User[] getAllByRole(Role role) {
        try {
            connection.setAutoCommit(false);
            int idRoleFromDb = UserUtil.getIdRoleFromDb(connection, role);
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USER_BY_ROLE);
            preparedStatement.setInt(1, idRoleFromDb);
            ResultSet resultSet = preparedStatement.executeQuery();
            connection.commit();
            return UserMapper.getUserList(resultSet).toArray(new User[0]);
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
        throw new NoSuchElementException();
    }

    @Override
    public boolean contains(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CONTAINS_USER_BY_ID);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeQuery().next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(String login) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CONTAINS_USER_BY_LOGIN);
            preparedStatement.setString(1, login);
            return preparedStatement.executeQuery().next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CONTAINS_USER_BY_USER);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            return preparedStatement.executeQuery().next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(Role role) {
        try {
            connection.setAutoCommit(false);
            int idRoleFromDb = UserUtil.getIdRoleFromDb(connection, role);
            PreparedStatement preparedStatement = connection.prepareStatement(CONTAINS_USER_ROLE);
            preparedStatement.setInt(1, idRoleFromDb);
            return preparedStatement.executeQuery().next();
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
        throw new NoSuchElementException();
    }


}
