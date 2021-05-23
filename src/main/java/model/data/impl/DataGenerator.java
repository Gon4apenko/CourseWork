package model.data.impl;

import model.entities.Admin;
import model.entities.Catalog.Section;
import model.entities.Client;
import model.entities.Product;

public class DataGenerator {

    public static void putDataToDatabase(Database database)
    {
        Client client1 = new Client(1, "Grisha", "12345", "example@gmail.com");
        Client client2 = new Client(2, "Ivan", "123456", "example@gmail.com");
        Admin admin = new Admin(3, "Admin", "1234567", "example@gmail.com");

        database.getUsers().add(client1);
        database.getUsers().add(client2);
        database.getUsers().add(admin);

        Section section1 = new Section("Home appliances");
        Section section4 = new Section("Clothing");
        database.getCatalog().getCategories().add(section1);
        database.getCatalog().getCategories().add(section4);

        Section section2 = new Section("Big home appliances");
        section1.getChildren().add(section2);
        section2.setParent(section1);

        Section section3 = new Section("Fridges");
        section2.getChildren().add(section3);
        section3.setParent(section2);

        Product product1 = new Product(1, "Bosh", 5000, "Very good quality", 50, section3);
        database.getProducts().add(product1);

        Product product2 = new Product(2, "T-shirt Nike", 500, "Soft material", 500, section4);
        database.getProducts().add(product2);

        Product product3 = new Product(3, "Pants", 1000, "100% cotton", 300, section4);
        database.getProducts().add(product3);
    }
}
