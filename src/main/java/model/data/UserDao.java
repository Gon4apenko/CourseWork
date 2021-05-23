package model.data;

import model.entities.User;

public interface UserDao extends DefaultDao<User> {

    User getByUsername(String username);
    User getByPassword(String username);
}
