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
        String format = String.format("\n\t\t\"product\": {\n\t\t\t\"id\": %d," +
                    "\n\t\t\t\"name\": \"%s\"," +
                    "\n\t\t\t\"brand\": \"%s\"," +
                    "\n\t\t\t\"type\": \"%s\"," +
                    "\n\t\t\t\"properties\": [",id,name,brand,type);
        for(ProductProperty pp : properties){
            if(properties.indexOf(pp) == properties.size() - 1) {
                format += String.format("\"%s\"", pp);
            }
            else {
                format += String.format("\"%s\",", pp);
            }
        }

        format += String.format("],\n\t\t\t\"package_quantity\": %d," +
                "\n\t\t\t\"price\": %s," +
                "\n\t\t\t\"image\": \"%s\"" +
                "\n\t\t}",package_quantity,String.valueOf(price).replace(",","."),image);

        return format;
    }

//    Compare only account numbers
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Product)
            if(this.id == ((Product) obj).id) {
                return true;
            }
            else
                return false;
        return false;
    }

    public int hashCode() {
        return id;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public void setProperties(List<ProductProperty> properties) {
        this.properties = properties;
    }

    public void setPackageQuantity(int package_quantity) {
        this.package_quantity = package_quantity;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
