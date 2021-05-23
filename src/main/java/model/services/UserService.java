package model.services;

import model.data.UserDao;
import model.entities.Admin;
import model.entities.Client;
import model.entities.User;

import javax.servlet.http.HttpSession;
import java.util.Collection;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao)
    {
        this.userDao = userDao;
    }

    public User getUserById(int id)
    {
        if (id < 0) return null;

        return userDao.get(id);
    }

    public User getUserByUsername(String username)
    {
        if (username == null) return null;

        return userDao.getByUsername(username);
    }

    public User getUserByPassword(String password)
    {
        if (password == null) return null;

        return userDao.getByPassword(password);
    }

    public void deleteUser(User user)
    {
        userDao.delete(user);
    }

    public User  createUserAndInsert(String username, String password, String email, boolean isAdmin)
    {
        if (username == null  || username.isEmpty() || username.isBlank())
        {
            throw new IllegalArgumentException("Username is not specified!");
        }

        if (getUserByUsername(username) != null)
        {
            throw new IllegalArgumentException("Username is already taken!");
        }

        if (password == null || password.length() < 5)
        {
            throw new IllegalArgumentException("Password is not specified!");
        }

        if (getUserByPassword(password) != null)
        {
            throw new IllegalArgumentException("Password is already taken!");
        }

        if (email == null)
        {
            throw new IllegalArgumentException("Email is not specified!");
        }

        int generatedId = generateId();

        if (isAdmin)
        {
            Admin newAdmin = new Admin(generatedId, username, password, email);

            userDao.insert(newAdmin);

            return newAdmin;
        }
        else
        {
            Client newClient = new Client(generatedId, username, password, email);

            userDao.insert(newClient);

            return newClient;
        }
    }

    private int generateId()
    {
        Collection<User> users = userDao.getAll();

        return users.stream().mapToInt(User::getId).max().orElse(0) + 1;
    }

    public boolean isLoggedIn(HttpSession session)
    {
        return session.getAttribute("user") != null;
    }

    public boolean isLoggedInAsAdmin(HttpSession session)
    {
        return isLoggedIn(session) && (session.getAttribute("user") instanceof Admin);
    }

    public boolean isLoggedInAsClient(HttpSession session)
    {
        return isLoggedIn(session) && (session.getAttribute("user") instanceof Client);
    }
}
