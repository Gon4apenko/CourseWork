package model.entities;

public class Product {

    private final int id;
    private String name;
    private double price;
    private String description;
    private int amount;
    private Catalog.Section category;

    public Product(int id, String name, double price, String description, int amount, Catalog.Section category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.amount = amount;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Catalog.Section getCategory() {
        return category;
    }

    public void setCategory(Catalog.Section category) {
        this.category = category;
    }
}
