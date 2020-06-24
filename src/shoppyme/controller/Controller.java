package shoppyme.controller;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import shoppyme.model.Product;
import shoppyme.model.Stock;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private JFXListView<HBox> productsList = new JFXListView<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(Product p: Stock.getInventory().keySet()) {
            Label name = new Label(p.getName());
            Label brand = new Label(p.getBrand());
            Label type = new Label(p.getType().toString());
            HBox hbox = new HBox(name, brand, type);
            productsList.getItems().addAll(hbox);
        }
    }

//    static class ProductCell extends ListCell<String> {
//        HBox hbox = new HBox();
//        Label name = new Label("Penne");
//        Label brand = new Label("Barilla");
//        Label type = new Label("Pasta");
//
//        public ProductCell() {
//            super();
//            hbox.getChildren().addAll(name, brand, type);
//            hbox.setHgrow();
//        }
//    }
}
