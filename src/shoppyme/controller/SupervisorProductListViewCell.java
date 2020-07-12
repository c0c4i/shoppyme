package shoppyme.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import shoppyme.model.Order;
import shoppyme.model.Product;
import shoppyme.model.Stock;

import java.io.IOException;

public class SupervisorProductListViewCell extends ListCell<Product> {

    @FXML private GridPane product_gridpane;
    @FXML private ImageView product_image_imageview = new ImageView();
    @FXML private Label product_id_label;
    @FXML private Label product_name_label;
    @FXML private Label product_brand_label;
    @FXML private Label product_quantity_label;
    @FXML private Label product_price_label;

    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(Product product, boolean empty) {
        super.updateItem(product, empty);

        if(empty || product == null) {
            setText(null);
            setGraphic(null);
        } else {

            mLLoader = new FXMLLoader(getClass().getResource("../view/private/supervisor_product.fxml"));
            mLLoader.setController(this);

            try {
                mLLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            product_image_imageview.setImage(new Image(getClass().getResourceAsStream(product.getImage())));
            product_id_label.setText(String.valueOf(product.id));
            product_name_label.setText(product.getName());
            product_brand_label.setText(product.getBrand());
            product_quantity_label.setText(String.valueOf(Stock.getQuantityOf(product)));
            product_price_label.setText(String.valueOf(product.getPrice()));

            setText(null);
            setGraphic(product_gridpane);
        }
    }
}
