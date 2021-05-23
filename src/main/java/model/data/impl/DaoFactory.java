package model.data.impl;

import model.data.CatalogDao;
import model.data.ProductDao;
import model.data.UserDao;

public class DaoFactory {

    private final ProductDao productDao;
    private final UserDao userDao;
    private final CatalogDao catalogDao;

    public DaoFactory(ProductDao productDao, UserDao userDao, CatalogDao catalogDao) {
        this.productDao = productDao;
        this.userDao = userDao;
        this.catalogDao = catalogDao;
    }

    public ProductDao getProductDao() {
        return productDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public CatalogDao getCatalogDao() {
        return catalogDao;
    }
}
