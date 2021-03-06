package shoppyme.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.SVGPath;
import shoppyme.model.Order;
import shoppyme.model.Product;
import shoppyme.model.Stock;

import java.io.IOException;

public class ProductListViewCell extends ListCell<Product> {

    @FXML private GridPane product_gridpane;
    @FXML private ImageView product_image_imageview = new ImageView();
    @FXML private SVGPath not_available_icon;
    @FXML private SVGPath available_icon;
    @FXML private Label product_name_label;
    @FXML private Label product_type_label;
    @FXML private Label product_brand_label;
    @FXML private Label product_price_label;
    @FXML private Label product_info_label;
    @FXML private Button product_add_button = new Button();

    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(Product product, boolean empty) {
        super.updateItem(product, empty);

        if(empty || product == null) {
            setText(null);
            setGraphic(null);
        } else {

            mLLoader = new FXMLLoader(getClass().getResource("../view/public/product.fxml"));
            mLLoader.setController(this);

            try {
                mLLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            product_image_imageview.setImage(new Image(getClass().getResourceAsStream(product.getImage())));
            product_name_label.setText(product.getName());
            product_type_label.setText(product.getType().toString());
            product_brand_label.setText(product.getBrand());
            product_price_label.setText(String.valueOf(product.getPrice()));
            product_info_label.setText(product.getProperties().toString());

            disableButton(product);

            product_add_button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Order o = Controller.getCurrentOrder();
                    if(o.addProduct(product)) {
                        Controller.setCurrentOrder(o);
                        Controller.shoppingController.loadOrderList();
                        available(true);
                    } else {
                        product_add_button.setDisable(true);
                        available(false);
                    }
                }
            });

            setText(null);
            setGraphic(product_gridpane);
        }
    }

    private void disableButton(Product p) {
        Integer quantity = Controller.getCurrentOrder().getProducts().get(p);

        if(quantity == null && !Stock.isAvailable(p, 1)) {
            product_add_button.setDisable(true);
            available(false);
            return;
        }

        if(quantity != null && !Stock.isAvailable(p, quantity + 1)) {
            product_add_button.setDisable(true);
            available(false);
            return;
        }

        available(true);
    }

    private void available(Boolean enable) {
        available_icon.setVisible(enable);
        not_available_icon.setVisible(!enable);
    }
}
