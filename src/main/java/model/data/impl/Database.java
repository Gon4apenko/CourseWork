package model.data.impl;

import model.entities.Catalog;
import model.entities.Product;
import model.entities.User;

import java.util.Comparator;
import java.util.TreeSet;

public class Database {

    TreeSet<User> users;
    TreeSet<Product> products;
    Catalog catalog;

    public Database()
    {
        users = new TreeSet<>(Comparator.comparing(User::getUsername));
        products = new TreeSet<>(Comparator.comparing(Product::getName));
        catalog = new Catalog();
    }

    public TreeSet<User> getUsers() {
        return users;
    }

    public void setUsers(TreeSet<User> users) {
        this.users = users;
    }

    public TreeSet<Product> getProducts() {
        return products;
    }

    public void setProducts(TreeSet<Product> products) {
        this.products = products;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }
}
