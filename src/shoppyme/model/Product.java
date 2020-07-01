package shoppyme.model;

import shoppyme.model.customenum.ProductProperty;
import shoppyme.model.customenum.ProductType;

import java.util.List;

public class Product {

    public final int id;
    private String name;
    private String brand;
    private ProductType type;
    private List<ProductProperty> properties;
    private int package_quantity;
    private float price;
    private String image;

    public Product(int id, String name, String brand, ProductType type, List<ProductProperty> properties, int package_quantity, float price, String image) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.type = type;
        this.properties = properties;
        this.package_quantity = package_quantity;
        this.price = price;
        this.image = image;
    }

    @Override
    public String toString() {
        String format = String.format(
                        "#### %s ####\n" +
                        "Name: %s\n" +
                        "Brand: %s\n" +
                        "Type: %s\n" +
                        "Price: %.2f", id, name, brand, type, price);
        return format;
    }

    //Compare only account numbers
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Product)
            if(this.id == ((Product) obj).id)
                return true;
            else
                return false;
        return false;
    }

    public int getPackage_quantity() {
        return package_quantity;
    }

    public float getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getBrand() {
        return brand;
    }

    public ProductType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public List<ProductProperty> getProperties() {
        return properties;
    }

}
