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
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import shoppyme.model.*;
import shoppyme.model.customenum.PaymentType;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckoutController implements Initializable {

    private User currentUser = Controller.getCurrentUser();
    private Order currentOrder = Controller.getCurrentOrder();

    private ObservableList<Product> summaryObservableList;
    private ObservableList<PaymentType> paymentTypeObservableList;
    private ObservableList<String> deliveryIntervals;

    @FXML
    private ListView<Product> orderList = new ListView<>();

    @FXML private TextField name_field;
    @FXML private TextField surname_field;
    @FXML private TextField phone_field;
    @FXML private TextField address_field;
    @FXML private TextField cap_field;
    @FXML private TextField city_field;

    @FXML private DatePicker delivery_date_datepicker;
    @FXML private ComboBox delivery_interval_combobox;
    @FXML private ComboBox payment_type_combobox;

    @FXML private Label total_price_label;

    @FXML private Label error_label;
    @FXML private Rectangle error_rectangle;

    public CheckoutController(){

        summaryObservableList = FXCollections.observableArrayList();
        paymentTypeObservableList = FXCollections.observableArrayList();
        deliveryIntervals = FXCollections.observableArrayList();

        paymentTypeObservableList.addAll(PaymentType.values());
        deliveryIntervals.addAll("9 - 11", "11 - 13", "14 - 16", "16 - 18");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name_field.setText(currentUser.getName());
        surname_field.setText(currentUser.getSurname());
        phone_field.setText(currentUser.getPhone());
        address_field.setText(currentUser.getAddress());
        cap_field.setText(currentUser.getCap());
        city_field.setText(currentUser.getCity());

        orderList.setMouseTransparent(true);
        orderList.setFocusTraversable(false);

        summaryObservableList.addAll(Controller.getCurrentOrder().getProducts().keySet());
        orderList.setItems(summaryObservableList);
        orderList.setCellFactory(orderListView -> new CheckoutListViewCell());

        total_price_label.setText(String.format("€ %.2f", Controller.getCurrentOrder().getTotalPrice()));

        delivery_interval_combobox.setItems(deliveryIntervals);

        payment_type_combobox.setItems(paymentTypeObservableList);
        payment_type_combobox.getSelectionModel().select(currentUser.getPayment_type());

        delivery_date_datepicker.setValue(LocalDate.now());
        delivery_date_datepicker.setConverter(new StringConverter<LocalDate>() {
            String pattern = "dd-MM-yyyy";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                delivery_date_datepicker.setPromptText(pattern.toLowerCase());
            }

            @Override public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
        delivery_date_datepicker.setTooltip(new Tooltip("Almeno due giorni dopo!"));
    }

    public void newShoppingButton(ActionEvent event) throws IOException {
        Controller.getInstance().goToShoppingScene();
        Controller.setSelectedOrder(null);
    }

    public void GoToProfile() throws IOException {
        Controller.getInstance().goToProfileScene();
    }

    public void GoToShopping() throws IOException {
        Controller.getInstance().goToShoppingScene();
    }

    public void completeOrderButton() throws IOException {

        if(!validForm())
            return;

        LocalDate delivery_date = delivery_date_datepicker.getValue();
        int[] delivery_interval = new int[2];

        switch(delivery_interval_combobox.getSelectionModel().getSelectedIndex()) {
            case 0:
                delivery_interval[0] = 9;
                delivery_interval[1] = 11;
                break;
            case 1:
                delivery_interval[0] = 11;
                delivery_interval[1] = 13;
                break;
            case 2:
                delivery_interval[0] = 14;
                delivery_interval[1] = 16;
                break;
            case 3:
                delivery_interval[0] = 16;
                delivery_interval[1] = 18;
                break;
        }

        PaymentType p = (PaymentType) payment_type_combobox.getValue();

        currentOrder.setDeliveryDate(delivery_date);
        currentOrder.setDeliveryInterval(delivery_interval);
        currentOrder.setPaymentType(p);
        currentOrder.completeOrder();
        Controller.getInstance().goToProfileScene();
    }

    private boolean validForm() {
        LocalDate d = delivery_date_datepicker.getValue();
        if(delivery_date_datepicker.getValue() == null) {
            showError("Data Obbligatoria");
            return false;
        }

        DayOfWeek dayweek = d.getDayOfWeek();
        if(dayweek == DayOfWeek.SATURDAY || dayweek == DayOfWeek.SUNDAY) {
            showError("Solo giorni lavorativi");
            return false;
        }

        if(d.compareTo(LocalDate.now()) < 0) {
            showError("Giorno non disponibile");
            return false;
        }

        // almeno due giorni dopo
        if(delivery_interval_combobox.getValue() == null) {
            showError("Ora Obbligatoria");
            return false;
        }

        if(payment_type_combobox == null) {
            showError("Pagamento Obbligatorio");
            return false;
        }

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
}
