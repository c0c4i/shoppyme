package shoppyme.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import shoppyme.model.Product;

import java.io.IOException;

public class ProductListViewCell extends ListCell<Product> {

    @FXML private GridPane product_gridpane;
    @FXML private ImageView product_image_imageview = new ImageView();
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

            mLLoader = new FXMLLoader(getClass().getResource("../view/product.fxml"));
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
            //product_add_button.setOnAction(Controller.getCurrentOrder().addProduct(product)));

//            product_add_button.setOnAction(
//                    new Button.() {
//                        @Override
//                        public void onClick(View v) {
//
//                            arrayNames.add(0, namesText.getText().toString());
//                            adapterNames.notifyDataSetChanged();
//                            namesText.setText("");
//
//                        }
//                    }
//            );

            System.out.println(Controller.getCurrentUser().toString() + Controller.getCurrentOrder().toString());

            setText(null);
            setGraphic(product_gridpane);
        }

    }
}
