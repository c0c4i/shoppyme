package shoppyme.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import shoppyme.model.Order;
import shoppyme.model.Product;
import shoppyme.model.Stock;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShoppingController implements Initializable {

    private Order currentOrder = Controller.getCurrentOrder();

    private static ObservableList<Product> productObservableList;
    private static ObservableList<Product> orderObservableList;

    @FXML
    private ListView<Product> productsList = new ListView<>();

    @FXML
    private ListView<Product> orderList = new ListView<>();

    public ShoppingController(){
        productObservableList = FXCollections.observableArrayList();
        productObservableList.addAll(Stock.getInventory().keySet());

        if(Controller.getCurrentOrder() == null)
            Controller.setCurrentOrder(new Order(Controller.getCurrentUser()));

        orderObservableList = FXCollections.observableArrayList();
//        System.out.println(Controller.getCurrentOrder());
        loadOrderList();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productsList.setItems(productObservableList);
        productsList.setCellFactory(productListView -> new ProductListViewCell());

        orderList.setItems(orderObservableList);
        orderList.setCellFactory(orderListView -> new OrderListViewCell());
    }

    public static void loadOrderList() {
        orderObservableList.clear();
        orderObservableList.addAll(Controller.getCurrentOrder().getProducts().keySet());
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
