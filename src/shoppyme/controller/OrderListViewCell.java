package shoppyme.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import shoppyme.model.Order;
import shoppyme.model.Product;

import java.io.IOException;

public class OrderListViewCell extends ListCell<Product> {

    @FXML private Button product_remove_button = new Button();
    @FXML private Button product_plus_button = new Button();
    @FXML private Pane order_gridpane;
    @FXML private ImageView item_image_imageview = new ImageView();
    @FXML private Label item_name_label;
    @FXML private Label item_quantity_label;
    @FXML private Label item_price_label;

    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(Product product, boolean empty) {
        super.updateItem(product, empty);

        if(empty || product == null) {
            setText(null);
            setGraphic(null);
        } else {
            mLLoader = new FXMLLoader(getClass().getResource("../view/public/order_cell.fxml"));
            mLLoader.setController(this);

            try {
                mLLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            item_image_imageview.setImage(new Image(getClass().getResourceAsStream(product.getImage())));
            item_name_label.setText(product.getName());
            int quantity = Controller.getCurrentOrder().getProducts().get(product);
            item_quantity_label.setText(String.valueOf(quantity));
            float price = product.getPrice() * quantity;
            item_price_label.setText(String.valueOf(price));

            setText(null);
            setGraphic(order_gridpane);
        }

        product_plus_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Order o = Controller.getCurrentOrder();
                o.addProduct(product);
                Controller.setCurrentOrder(o);
                Controller.shoppingController.loadProductList();
                Controller.shoppingController.loadOrderList();
            }
        });

        product_remove_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Order o = Controller.getCurrentOrder();
                o.removeProduct(product);
                Controller.setCurrentOrder(o);
                Controller.shoppingController.loadProductList();
                Controller.shoppingController.loadOrderList();
            }
        });
    }
}
