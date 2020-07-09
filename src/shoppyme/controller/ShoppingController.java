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
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import shoppyme.model.Order;
import shoppyme.model.Product;
import shoppyme.model.Stock;
import shoppyme.model.customenum.OrderType;
import shoppyme.model.customenum.PaymentType;
import shoppyme.model.customenum.ProductType;
import shoppyme.model.customenum.SearchType;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.ServiceConfigurationError;

public class ShoppingController implements Initializable {

    private Order currentOrder = Controller.getCurrentOrder();

    private OrderType currentOrderType;

    private static ObservableList<Product> productObservableList;
    private static ObservableList<Product> orderObservableList;
    private static ObservableList<SearchType> typeObservableList;

    @FXML private ImageView price_arrow_imageview;
    @FXML private ImageView brand_arrow_imageview;

    @FXML private Label price_label;
    @FXML private Label brand_label;
    @FXML private TextField search_bar_textfield;
    @FXML private ComboBox type_search_combobox;
    @FXML private Button search_button;
    @FXML private Label total_price_label;

    @FXML private ListView<Product> productsList = new ListView<>();
    @FXML private ListView<Product> orderList = new ListView<>();

    public ShoppingController(){
        productObservableList = FXCollections.observableArrayList();

        List<Product> lista = new ArrayList<>(Stock.getInventoryBy("",null));
        productObservableList.addAll(Stock.getOrderedInventory(null, lista));

//        if(Controller.getCurrentOrder() == null)
//            Controller.setCurrentOrder(new Order(Controller.getCurrentUser()));

        orderObservableList = FXCollections.observableArrayList();

        typeObservableList = FXCollections.observableArrayList();
        typeObservableList.addAll(SearchType.values());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        productsList.setItems(productObservableList);
        productsList.setCellFactory(productListView -> new ProductListViewCell());
        productsList.setFocusTraversable(false);

        loadOrderList();

        orderList.setItems(orderObservableList);
        orderList.setCellFactory(orderListView -> new OrderListViewCell());
        orderList.setFocusTraversable(false);

        type_search_combobox.setItems(typeObservableList);
        type_search_combobox.getSelectionModel().select(SearchType.NAME);

    }

    public void loadOrderList() {
        orderObservableList.clear();
        orderObservableList.addAll(Controller.getCurrentOrder().getProducts().keySet());
        float total = Controller.getCurrentOrder().getTotalPrice();
        total_price_label.setText("€ " + total);
    }

    public void profileButtonClick(ActionEvent event) throws IOException {
        Controller.getInstance().goToProfileScene();
    }

    public void searchFilteredProduct(ActionEvent event) throws IOException {
        productObservableList.clear();
        String name = search_bar_textfield.getText();
        SearchType type = SearchType.valueOf(type_search_combobox.getValue().toString());

        List<Product> productList;

        if(name == null || type == null) {
            productList = new ArrayList<>(Stock.getInventoryBy(null,null));
        }
        else {
            productList = new ArrayList<>(Stock.getInventoryBy(name,type));
        }

        productObservableList.addAll(Stock.getOrderedInventory(currentOrderType, productList));
    }

    public void sortBy(){
        List<Product> orderedList = new ArrayList<>(productObservableList);
        productObservableList.clear();

        productObservableList.addAll(Stock.getOrderedInventory(currentOrderType, orderedList));
    }

    public void onClickBrandArrow(){
        price_label.setUnderline(false);

        if(currentOrderType == null || currentOrderType == OrderType.HIGHER_PRICE
                || currentOrderType == OrderType.LOWER_PRICE){
            currentOrderType = OrderType.AZ_BRAND;
            brand_arrow_imageview.setRotate(90);
        }

        else if ( currentOrderType == OrderType.ZA_BRAND){
            currentOrderType = OrderType.AZ_BRAND;
            brand_arrow_imageview.setRotate(90);
        }

        else {
            currentOrderType = OrderType.ZA_BRAND;
            brand_arrow_imageview.setRotate(-90);
        }

        brand_label.setUnderline(true);

        sortBy();
    }

    public void onClickPriceArrow(){

        brand_label.setUnderline(false);

        if(currentOrderType == null || currentOrderType == OrderType.AZ_BRAND
                || currentOrderType == OrderType.ZA_BRAND) {
            currentOrderType = OrderType.LOWER_PRICE;
            price_arrow_imageview.setRotate(90);
        }
        else if ( currentOrderType == OrderType.LOWER_PRICE){
            currentOrderType = OrderType.HIGHER_PRICE;
            price_arrow_imageview.setRotate(90);
        }

        else {
            currentOrderType = OrderType.LOWER_PRICE;
            price_arrow_imageview.setRotate(-90);
        }

        price_label.setUnderline(true);

        sortBy();
    }
}
