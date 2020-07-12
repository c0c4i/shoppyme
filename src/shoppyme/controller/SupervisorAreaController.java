package shoppyme.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import shoppyme.model.*;
import shoppyme.model.customenum.ProductProperty;
import shoppyme.model.customenum.ProductType;
import shoppyme.model.customenum.Status;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SupervisorAreaController implements Initializable {

    private Supervisor currentSupervisor = Controller.getCurrentSupervisor();

    private static ObservableList<Product> productsObservableList;
    private static ObservableList<Order> orderObservableList;
    private static ObservableList<Product> selectedOrderObservableList;
    private static Product selectedProduct;
    private static Order selectedOrder;
    private static ObservableList<ProductType> productTypeObservableList;
    private static ObservableList<Status> orderStatusObservableList;

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

    @FXML private ComboBox selected_order_status_combobox;

    @FXML private Label error_label;
    @FXML private Rectangle error_rectangle;
    @FXML private Label order_saved_label;

    @FXML private TextField search_bar_product_textfield;
    @FXML private TextField search_bar_order_textfield;

    @FXML private Button save_product_button;
    @FXML private Button save_order_button;

    public SupervisorAreaController(){
        productsObservableList = FXCollections.observableArrayList();
        selectedOrderObservableList = FXCollections.observableArrayList();
        orderObservableList = FXCollections.observableArrayList();

        productTypeObservableList = FXCollections.observableArrayList();
        productTypeObservableList.addAll(ProductType.values());

        orderStatusObservableList = FXCollections.observableArrayList();
        orderStatusObservableList.addAll(Status.values());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        save_product_button.setDisable(true);
        save_order_button.setDisable(true);

        loadProductList();
        loadOrderList();

        productsList.setItems(productsObservableList);
        productsList.setCellFactory(oldOrderListView -> new SupervisorProductListViewCell());

        orderList.setItems(orderObservableList);
        orderList.setCellFactory(oldOrderListView -> new SupervisorOrderListViewCell());

        productsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                save_product_button.setDisable(false);
                clearErrorMessage();
                selectedProduct = productsList.getSelectionModel().getSelectedItem();
                if(selectedProduct != null) {
                    product_name_textield.setText(selectedProduct.getName());
                    product_type_combobox.setValue(selectedProduct.getType());
                    product_brand_textield.setText(selectedProduct.getBrand());
                    product_price_textield.setText(String.format("%.2f", selectedProduct.getPrice()));
                    product_package_quantity_textield.setText(String.valueOf(selectedProduct.getPackage_quantity()));
                    product_available_textield.setText(String.valueOf(Stock.getInventory().get(selectedProduct)));
                    setProperties();
                }
            }
        });

        orderList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                save_order_button.setDisable(false);
                clearErrorMessage();
                selectedOrder = orderList.getSelectionModel().getSelectedItem();
                if(selectedOrder != null) {
                    Controller.setSelectedOrder(selectedOrder);
                    loadSelectedOrderList();
                }
            }
        });

        product_type_combobox.setItems(productTypeObservableList);
//        product_type_combobox.getSelectionModel().select(selectedProduct.getType());

        selected_order_status_combobox.setItems(orderStatusObservableList);
    }

    public void loadProductList() {
        productsObservableList.clear();
        String id = search_bar_product_textfield.getText();
        if(id.length() == 0)
            productsObservableList.addAll(Stock.getInventory().keySet());
        else
            productsObservableList.addAll(Stock.getSearchedProductBy(Integer.parseInt(id)));
    }

    public void loadOrderList() {
        orderObservableList.clear();
        String id = search_bar_order_textfield.getText();
        if(id.length() == 0)
            orderObservableList.addAll(Stock.getOrders());
        else
            orderObservableList.addAll(Stock.getSearchedOrderBy(Integer.parseInt(id)));
    }

    public void loadSelectedOrderList() {
        selected_delivery_date_label.setText(selectedOrder.getDeliveryDate().toString());
        selected_delivery_time_label.setText(selectedOrder.getDeliveryInterval());
        selected_order_status_combobox.setValue(selectedOrder.getStatus());

        selectedOrderObservableList.clear();
        selectedOrderObservableList.addAll(selectedOrder.getProducts().keySet());
        selectedOrderList.setItems(selectedOrderObservableList);
        selectedOrderList.setCellFactory(oldOrderListView -> new SupervisorSelectedOrderListViewCell());
    }

    public void searchProduct() {
        loadProductList();
    }

    public void searchOrder() {
        loadOrderList();
    }

    public void saveProduct() {
        if(!formValidation()){
            return;
        }

        showSuccess();

        selectedProduct.setName(product_name_textield.getText());
        selectedProduct.setType((ProductType) product_type_combobox.getValue());
        selectedProduct.setBrand(product_brand_textield.getText());
        selectedProduct.setPrice(Float.parseFloat(product_price_textield.getText()));
        selectedProduct.setPackageQuantity(Integer.parseInt(product_package_quantity_textield.getText()));
        int q = Integer.parseInt(product_available_textield.getText());
        selectedProduct.setProperties(getProperties());
        Stock.updateProduct(selectedProduct, q);
        loadProductList();
    }

    public void saveOrder() {
        selectedOrder.setStatus((Status) selected_order_status_combobox.getValue());
        Stock.updateOrder(selectedOrder);
        order_saved_label.setVisible(true);
        loadOrderList();
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

    public void setProperties() {
        for(ProductProperty prop : selectedProduct.getProperties()) {
            switch(prop) {
                case INTEGRALE:
                    integral_checkbox.setSelected(true);
                    break;
                case VEGAN:
                    vegan_checkbox.setSelected(true);
                    break;
                case SENZA_GLUTINE:
                    gluten_free_checkbox.setSelected(true);
                    break;
                case BIO:
                    bio_checkbox.setSelected(true);
                    break;
                case SENZA_LATTOSIO:
                    lactose_free_checkbox.setSelected(true);
                    break;
            }
        }
    }

    private boolean formValidation(){
        if( product_name_textield.getText().length() == 0 ){
            showError("Nome obbligatorio");
            return false;
        }

        if( product_brand_textield.getText().length() == 0 ){
            showError("Marca obbligatoria");
            return false;
        }

        if( product_price_textield.getText().matches(".*\\f.*") ){
            showError("Prezzo non valido");
            return false;
        }

        if( !product_package_quantity_textield.getText().matches(".*\\d.*") ){
            showError("Quantit� per confezione non valida");
            return false;
        }

        if( !product_available_textield.getText().matches(".*\\d.*") ){
            showError("Quantit� magazzino non valida");
            return false;
        }

        return true;
    }

    private void showSuccess() {
        error_label.setText("Prodotto Salvato");
        error_label.setTextFill(Color.web("#5cb85c"));
        error_rectangle.setFill(Color.web("#5cb85c", 0.2));
        error_rectangle.setStroke(Color.web("#5cb85c"));
        error_label.setVisible(true);
        error_rectangle.setVisible(true);
//        Thread.sleep(3000);
    }

    private void showError(String error){
        error_label.setText(error);
        error_label.setTextFill(Color.web("#d9534f"));
        error_rectangle.setFill(Color.web("#d9534f", 0.2));
        error_rectangle.setStroke(Color.web("#d9534f"));
        error_label.setVisible(true);
        error_rectangle.setVisible(true);
    }

    public void clearErrorMessage(){
        error_label.setVisible(false);
        error_rectangle.setVisible(false);
        order_saved_label.setVisible(false);
    }

    public void Logout() {
        Controller.getInstance().goToLoginScene();
    }
}
