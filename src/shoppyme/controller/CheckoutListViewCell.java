package shoppyme.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import shoppyme.model.Product;

import java.io.IOException;

public class CheckoutListViewCell extends ListCell<Product> {

    @FXML private GridPane product_gridpane;
    @FXML private ImageView product_image_imageview = new ImageView();
    @FXML private Label product_name_label;
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

            mLLoader = new FXMLLoader(getClass().getResource("../view/public/selected_order_cell.fxml"));
            mLLoader.setController(this);

            try {
                mLLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            product_image_imageview.setImage(new Image(getClass().getResourceAsStream(product.getImage())));
            product_name_label.setText(product.getName());
            int quantity = Controller.getCurrentOrder().getProducts().get(product);
            product_quantity_label.setText(String.valueOf(quantity));
            float item_price = Controller.getCurrentOrder().getOldProductsPrice().get(product);
            product_price_label.setText(String.valueOf(item_price));

            setText(null);
            setGraphic(product_gridpane);
        }
    }
}
