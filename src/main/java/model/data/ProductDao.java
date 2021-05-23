package model.data;

import model.entities.Catalog;
import model.entities.Product;

import java.util.List;

public interface ProductDao extends DefaultDao<Product> {

    List<Product> getProductsBySection(Catalog.Section section);
    Product getByName(String name);
}
