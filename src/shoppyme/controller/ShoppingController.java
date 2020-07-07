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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import shoppyme.model.Order;
import shoppyme.model.Product;
import shoppyme.model.Stock;
import shoppyme.model.customenum.PaymentType;
import shoppyme.model.customenum.ProductType;
import shoppyme.model.customenum.SearchType;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.ServiceConfigurationError;

public class ShoppingController implements Initializable {

    private Order currentOrder = Controller.getCurrentOrder();

    private static ObservableList<Product> productObservableList;
    private static ObservableList<Product> orderObservableList;
    private static ObservableList<SearchType> typeObservableList;

    @FXML private TextField search_bar_textfield;
    @FXML private ComboBox type_search_combobox;
    @FXML private Button search_button;

    @FXML private ListView<Product> productsList = new ListView<>();
    @FXML private ListView<Product> orderList = new ListView<>();


    public ShoppingController(){
        productObservableList = FXCollections.observableArrayList();
        productObservableList.addAll(Stock.getInventoryBy("", null));

        if(Controller.getCurrentOrder() == null)
            Controller.setCurrentOrder(new Order(Controller.getCurrentUser()));

        orderObservableList = FXCollections.observableArrayList();
        loadOrderList();

        typeObservableList = FXCollections.observableArrayList();
        typeObservableList.addAll(SearchType.values());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productsList.setItems(productObservableList);
        productsList.setCellFactory(productListView -> new ProductListViewCell());

        orderList.setItems(orderObservableList);
        orderList.setCellFactory(orderListView -> new OrderListViewCell());

        type_search_combobox.setItems(typeObservableList);
        type_search_combobox.getSelectionModel().select(SearchType.NAME);
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

    public void searchFilteredProduct(ActionEvent event) throws IOException {
        productObservableList.clear();
        String name = search_bar_textfield.getText();
        SearchType type = SearchType.valueOf(type_search_combobox.getValue().toString());

        if(name == null || type == null) {
            productObservableList.addAll(Stock.getInventoryBy(null, null));
        }
        else {
            productObservableList.addAll(Stock.getInventoryBy(name, type));
        }
    }
}
