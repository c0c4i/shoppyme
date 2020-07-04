package shoppyme.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import shoppyme.model.Order;
import shoppyme.model.Product;
import shoppyme.model.Stock;
import shoppyme.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    private User currentUser = Controller.getCurrentUser();

    private static ObservableList<Order> oldOrderObservableList;
    private static ObservableList<Product> selectedOrderObservableList;

    @FXML
    private ListView<Order> oldOrderList = new ListView<>();

    @FXML
    private ListView<Product> selectedOrderList = new ListView<>();

    @FXML private TextField name_field;
    @FXML private TextField surname_field;
    @FXML private TextField phone_field;
    @FXML private TextField email_field;
    @FXML private TextField address_field;
    @FXML private TextField cap_field;
    @FXML private TextField city_field;

    @FXML private Label selected_delivery_date_label;
    @FXML private Label selected_delivery_time_label;
    @FXML private Label selected_payment_label;
    @FXML private Label selected_total_price_label;

    public ProfileController(){
        oldOrderObservableList = FXCollections.observableArrayList();
        oldOrderObservableList.addAll(Stock.getUserOrder(Controller.getCurrentUser()));
        selectedOrderObservableList = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        oldOrderList.setItems(oldOrderObservableList);
        oldOrderList.setCellFactory(oldOrderListView -> new OldOrderListViewCell());

        oldOrderList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Order o = oldOrderList.getSelectionModel().getSelectedItem();
                if(o != null) {
                    Controller.setSelectedOrder(o);
                    loadSelectedOrderList();
                }
            }
        });

        name_field.setText(currentUser.getName());
        surname_field.setText(currentUser.getSurname());
        phone_field.setText(currentUser.getPhone());
        email_field.setText(currentUser.getEmail());
        address_field.setText(currentUser.getAddress());
        cap_field.setText(currentUser.getCap());
        city_field.setText(currentUser.getCity());

        Order o = Controller.getSelectedOrder();
        if(o != null) {
            selected_delivery_date_label.setText(o.getDeliveryDate().toString());
        } else {
            selected_delivery_date_label.setText("--/--/----");
            selected_delivery_time_label.setText("--");
            selected_payment_label.setText("--");
            selected_total_price_label.setText("--");
        }
    }

    public void loadSelectedOrderList() {
        Order o = Controller.getSelectedOrder();
        selected_delivery_date_label.setText(o.getDeliveryDate().toString());
        selected_delivery_time_label.setText(o.getDeliveryInterval().toString());
        selected_payment_label.setText(o.getPaymentType().toString());
        selected_total_price_label.setText(String.valueOf(o.getTotalPrice()));

        selectedOrderObservableList.clear();        // rivedere come ho fatto nella spesa, per ora funziona
        selectedOrderObservableList.addAll(o.getProducts().keySet());
        selectedOrderList.setItems(selectedOrderObservableList);
        selectedOrderList.setCellFactory(oldOrderListView -> new SelectedOrderListViewCell());
//        orderObservableList.clear();
//        orderObservableList.addAll(Controller.getCurrentOrder().getProducts().keySet());
    }

    public void saveUserInfoButton() {
        currentUser.setName(name_field.getText());
        currentUser.setSurname(surname_field.getText());
        currentUser.setPhone(phone_field.getText());
        currentUser.setEmail(email_field.getText());
        currentUser.setAddress(address_field.getText());
        currentUser.setCap(cap_field.getText());
        currentUser.setCity(city_field.getText());
        Controller.setCurrentUser(currentUser);
    }

    public void newShoppingButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/spesa.fxml"));
        Parent shoppingViewParent = loader.load();

        Scene shoppingViewScene = new Scene(shoppingViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(shoppingViewScene);
        window.show();
    }

}
