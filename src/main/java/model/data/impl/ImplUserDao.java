package model.data.impl;

import model.data.UserDao;
import model.entities.User;

import java.util.Collection;
import java.util.Optional;
import java.util.TreeSet;

public class ImplUserDao implements UserDao {

    TreeSet<User> users;

    public ImplUserDao(TreeSet<User> users)
    {
        this.users = users;
    }

    @Override
    public User get(int id) {
        Optional<User> userOpt = users.stream().filter(user -> user.getId() == id).findFirst();

        return userOpt.orElse(null);
    }

    @Override
    public User getByUsername(String username) {
        Optional<User> userOpt = users.stream().filter(user -> user.getUsername().equals(username)).findFirst();

        return userOpt.orElse(null);
    }

    @Override
    public User getByPassword(String password) {
        Optional<User> userOpt = users.stream().filter(user -> user.getPassword().equals(password)).findFirst();

        return userOpt.orElse(null);
    }

    @Override
    public void insert(User entity)
    {
        if (get(entity.getId()) != null)
            throw new IllegalArgumentException("Id is not unique!");

        if (getByUsername(entity.getUsername()) != null)
            throw new IllegalArgumentException("Username is not unique!");

        if (getByPassword(entity.getPassword()) != null)
            throw new IllegalArgumentException("Password is not unique!");

        users.add(entity);
    }

    @Override
    public void delete(User entity) {
        users.remove(entity);
    }

    @Override
    public Collection<User> getAll() {
        return users;
    }
}
