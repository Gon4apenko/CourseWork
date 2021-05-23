package model.services;

import model.data.ProductDao;
import model.entities.Catalog;
import model.entities.Product;

import java.util.Collection;
import java.util.Map;

public class ProductService {

    ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Product getProductById(int id)
    {
        if (id < 0)
            return null;

        return productDao.get(id);
    }

    public Collection<Product> getProductCollectionBySection(Catalog.Section section)
    {
        return productDao.getProductsBySection(section);
    }

    public int insertProductWithId(Map<String, String[]> params, CatalogService catalogService)
    {
        String productName = params.get("name")[0];

        if (productName == null)
        {
            throw new IllegalArgumentException("Name is not given!");
        }

        if (productName.isEmpty() || productName.isBlank())
        {
            throw new IllegalArgumentException("Name is not valid!");
        }

        String productPrice = params.get("price")[0];

        if (productPrice == null)
        {
            throw new IllegalArgumentException("Price is not given!");
        }

        double price;

        try {
            price = Double.parseDouble(productPrice);

            if (price < 0)
                throw new IllegalArgumentException("Price can't be negative!");
        }catch (IllegalArgumentException e)
        {
            throw new IllegalArgumentException("Price is not valid!");
        }

        String productAmount = params.get("amount")[0];

        if (productAmount == null)
        {
            throw new IllegalArgumentException("Amount is not given!");
        }

        int amount;

        try {
            amount = Integer.parseInt(productAmount);

            if (amount < 0)
                throw new IllegalArgumentException("Amount can't be negative!");
        }catch (IllegalArgumentException e)
        {
            throw new IllegalArgumentException("Amount is not valid!");
        }

        String productDescription = params.get("description")[0];

        if (productDescription == null)
        {
            throw new IllegalArgumentException("Description is not given!");
        }

        String productCategory = params.get("category")[0];

        if (productCategory == null)
        {
            throw new IllegalArgumentException("Category is not given!");
        }

        Catalog.Section category = catalogService.getSectionByName(productCategory);

        if (category == null)
        {
            throw new IllegalArgumentException("Category is not valid!");
        }

        int generatedId = generateId();

        productDao.insert(new Product(generatedId, productName, price, productDescription, amount, category));

        return generatedId;
    }

    private int generateId()
    {
        Collection<Product> products = productDao.getAll();

        return products.stream().mapToInt(Product::getId).max().orElse(-1) + 1;
    }

    public void deleteProduct(Product product)
    {
        productDao.delete(product);
    }

    public int getProductIdFromPath(String path)
    {
        if (!path.startsWith("/app/product/"))
            throw new IllegalArgumentException();

        if (path.endsWith("/"))
            path = path.substring(0, path.length() - 1);

        String productId = path.replace("/app/product/", "");

        if (productId.contains("/"))
            throw new IllegalArgumentException();

        return Integer.parseInt(productId);
    }

    public Collection<Product> getAllProducts()
    {
        return productDao.getAll();
    }
}
