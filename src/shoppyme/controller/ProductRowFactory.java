package shoppyme.controller;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import shoppyme.model.Product;

public class ProductRowFactory implements Callback<ListView<Product>, ListCell<Product>> {

    @Override
    public ListCell<Product> call(ListView<Product> param) {
        return new ProductRow();
    }
}