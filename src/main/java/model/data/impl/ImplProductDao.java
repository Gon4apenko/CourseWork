package model.data.impl;

import model.data.ProductDao;
import model.entities.Catalog;
import model.entities.Product;

import java.util.*;
import java.util.stream.Collectors;

public class ImplProductDao implements ProductDao {

    TreeSet<Product> products;

    public ImplProductDao(TreeSet<Product> products)
    {
        this.products = products;
    }

    @Override
    public Product get(int id) {
        Optional<Product> productOptional = products.stream().filter(product -> product.getId() == id).findFirst();

        return productOptional.orElse(null);
    }

    @Override
    public List<Product> getProductsBySection(Catalog.Section section) {
        if (section == null) return null;

        return products.stream().filter(product -> section.equals(product.getCategory())).collect(Collectors.toList());
    }

    @Override
    public Product getByName(String name) {
        return products.stream().filter(product -> product.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public void insert(Product entity) {
        if (get(entity.getId()) != null)
            throw new IllegalArgumentException("Id is not unique!");

        if (getByName(entity.getName()) != null)
            throw new IllegalArgumentException("Name is not unique");

        products.add(entity);
    }

    @Override
    public void delete(Product entity) {
        products.remove(entity);
    }

    @Override
    public Collection<Product> getAll() {
        return products;
    }
}
