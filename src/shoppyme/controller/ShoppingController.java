package shoppyme.controller;

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import shoppyme.model.Order;
import shoppyme.model.Product;
import shoppyme.model.Stock;
import shoppyme.model.User;

import javax.net.ssl.SNIHostName;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShoppingController implements Initializable {

    private Order currentOrder = Controller.getCurrentOrder();

    private ObservableList<Product> productObservableList;

    @FXML
    private ListView<Product> productsList = new ListView<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productsList.setItems(productObservableList);
        productsList.setCellFactory(productListView -> new ProductListViewCell());
    }

    public ShoppingController(){
        productObservableList = FXCollections.observableArrayList();
        productObservableList.addAll(Stock.getInventory().keySet());
    }

    public void profileButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/profile.fxml"));
        Parent profileViewParent = loader.load();

        Scene profileViewScene = new Scene(profileViewParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(profileViewScene);
        window.show();
    }
}
