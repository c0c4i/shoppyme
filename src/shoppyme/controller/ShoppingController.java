package shoppyme.controller;

import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import shoppyme.model.Order;
import shoppyme.model.Product;
import shoppyme.model.Stock;
import shoppyme.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShoppingController implements Initializable {

    private Order currentOrder = Controller.getCurrentOrder();

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

    public void profileButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/profile.fxml"));
        Parent profileViewParent = loader.load();

        Scene profileViewScene = new Scene(profileViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(profileViewScene);
        window.show();
    }
}
