package shoppyme.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import shoppyme.model.*;
import shoppyme.model.customenum.PaymentType;
import shoppyme.model.customenum.ProductProperty;
import shoppyme.model.customenum.ProductType;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SupervisorAreaController implements Initializable {

    private Supervisor currentSupervisor = Controller.getCurrentSupervisor();

    private static ObservableList<Product> productsObservableList;
    private static ObservableList<Order> orderObservableList;
    private static ObservableList<Product> selectedOrderObservableList;
    private static Product selectedProduct;
    private static Order selectedOrder;
    private static ObservableList<ProductType> productTypeObservableList;

    @FXML private ListView<Product> productsList = new ListView<>();
    @FXML private ListView<Order> orderList = new ListView<>();
    @FXML private ListView<Product> selectedOrderList = new ListView<>();

    @FXML private TextField product_name_textield;
    @FXML private ComboBox product_type_combobox;
    @FXML private TextField product_brand_textield;
    @FXML private TextField product_price_textield;
    @FXML private TextField product_package_quantity_textield;
    @FXML private TextField product_available_textield;
    @FXML private CheckBox integral_checkbox;
    @FXML private CheckBox vegan_checkbox;
    @FXML private CheckBox gluten_free_checkbox;
    @FXML private CheckBox bio_checkbox;
    @FXML private CheckBox lactose_free_checkbox;

    @FXML private Label selected_delivery_date_label;
    @FXML private Label selected_delivery_time_label;

    @FXML private Label error_label;
    @FXML private Rectangle error_rectangle;


    public SupervisorAreaController(){
        productsObservableList = FXCollections.observableArrayList();
        loadProductList();

        selectedOrderObservableList = FXCollections.observableArrayList();

        orderObservableList = FXCollections.observableArrayList();
        orderObservableList.addAll(Stock.getOrders());

        productTypeObservableList = FXCollections.observableArrayList();
        productTypeObservableList.addAll(ProductType.values());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productsList.setItems(productsObservableList);
        productsList.setCellFactory(oldOrderListView -> new OldOrderListViewCell());    // to do

        orderList.setItems(orderObservableList);
        orderList.setCellFactory(oldOrderListView -> new OldOrderListViewCell());    // to do

//        selectedOrderList.setMouseTransparent(true);
//        selectedOrderList.setFocusTraversable(false);

        productsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selectedProduct = productsList.getSelectionModel().getSelectedItem();
                if(selectedProduct != null) {
                    product_name_textield.setText(selectedProduct.getName());
                    product_type_combobox.setValue(selectedProduct.getType());
                    product_brand_textield.setText(selectedProduct.getBrand());
                    product_price_textield.setText(String.format("%.2f", selectedProduct.getPrice()));
                    product_package_quantity_textield.setText(String.valueOf(selectedProduct.getPackage_quantity()));
                    product_available_textield.setText(String.valueOf(Stock.getInventory().get(selectedProduct)));
                }
            }
        });

        orderList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selectedOrder = orderList.getSelectionModel().getSelectedItem();
                if(selectedOrder != null) {
                    loadSelectedOrderList();
                }
            }
        });

        product_type_combobox.setItems(productTypeObservableList);
        product_type_combobox.getSelectionModel().select(selectedProduct.getType());
    }

    public void loadProductList() {
        productsObservableList.clear();
        productsObservableList.addAll(Stock.getInventory().keySet());
    }

    public void loadSelectedOrderList() {
        selected_delivery_date_label.setText(selectedOrder.getDeliveryDate().toString());
        selected_delivery_time_label.setText(selectedOrder.getDeliveryInterval());

        selectedOrderObservableList.clear();
        selectedOrderObservableList.addAll(selectedOrder.getProducts().keySet());
        selectedOrderList.setItems(selectedOrderObservableList);
        selectedOrderList.setCellFactory(oldOrderListView -> new SelectedOrderListViewCell());
    }

    public void saveProduct() {
        if(!formValidation()){
            return;
        }

        clearErrorMessage();

        selectedProduct.setName(product_name_textield.getText());
        selectedProduct.setType((ProductType) product_type_combobox.getValue());
        selectedProduct.setBrand(product_brand_textield.getText());
        selectedProduct.setPrice(Float.parseFloat(product_price_textield.getText()));
        selectedProduct.setPackageQuantity(Integer.parseInt(product_package_quantity_textield.getText()));
        int q = Integer.parseInt(product_package_quantity_textield.getText());
        selectedProduct.setProperties(getProperties());
        Stock.updateProduct(selectedProduct, q);
        loadProductList();
    }

    public List<ProductProperty> getProperties() {
        List<ProductProperty> prop = new ArrayList<>();
        if(integral_checkbox.isSelected()) prop.add(ProductProperty.INTEGRALE);
        if(vegan_checkbox.isSelected()) prop.add(ProductProperty.VEGAN);
        if(gluten_free_checkbox.isSelected()) prop.add(ProductProperty.SENZA_GLUTINE);
        if(bio_checkbox.isSelected()) prop.add(ProductProperty.BIO);
        if(lactose_free_checkbox.isSelected()) prop.add(ProductProperty.SENZA_LATTOSIO);
        return prop;
    }

    private boolean formValidation(){
//        if( name_field.getText().length() == 0 ){
//            showError("Nome obbligatorio");
//            return false;
//        }
//        if( name_field.getText().matches(".*\\d.*") ){
//            showError("Nome non valido");
//            return false;
//        }
//        if( surname_field.getText().length() == 0 ){
//            showError("Cognome obbligatorio");
//            return false;
//        }
//        if( surname_field.getText().matches(".*\\d.*") ){
//            showError("Cognome non valido");
//            return false;
//        }
//        if( phone_field.getText().length() == 0 ){
//            showError("Telefono obbligatorio");
//            return false;
//        }
//        if( !phone_field.getText().matches("[0-9]+")){
//            showError("Numero di telefono non valido");
//            return false;
//        }
//        if( email_field.getText().length() == 0 ){
//            showError("Email obbligatoria");
//            return false;
//        }
//
//        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
//        Matcher mat = pattern.matcher(email_field.getText());
//
//        if( !mat.matches() ){
//            showError("Email non valida");
//            return false;
//        }
//        if( address_field.getText().length() == 0 ){
//            showError("Indirizzo obbligatorio");
//            return false;
//        }
//        if( cap_field.getText().length() == 0 ){
//            showError("CAP obbligatorio");
//            return false;
//        }
//        if( !cap_field.getText().matches("[0-9]+")){
//            showError("CAP non valido");
//            return false;
//        }
//        if( city_field.getText().length() == 0 ){
//            showError("Città obbligatoria");
//            return false;
//        }
//        if( city_field.getText().matches(".*\\d.*") ){
//            showError("Città non valida");
//            return false;
//        }

        return true;
    }

    private void showError(String error){
        error_label.setText(error);
        error_label.setVisible(true);
        error_rectangle.setVisible(true);
    }

    public void clearErrorMessage(){
        error_label.setVisible(false);
        error_rectangle.setVisible(false);
    }

    public void Logout() {
        Controller.getInstance().goToLoginScene();
    }
}
