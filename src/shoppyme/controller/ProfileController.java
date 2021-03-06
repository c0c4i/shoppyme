package shoppyme.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import shoppyme.model.*;
import shoppyme.model.customenum.PaymentType;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileController implements Initializable {

    private User currentUser = Controller.getCurrentUser();

    private static ObservableList<Order> oldOrderObservableList;
    private static ObservableList<Product> selectedOrderObservableList;
    private static ObservableList<PaymentType> paymentTypeObservableList;

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

    @FXML private ComboBox payment_type_combobox;

    @FXML private Label selected_delivery_date_label;
    @FXML private Label selected_delivery_time_label;
    @FXML private Label selected_payment_label;
    @FXML private Label selected_total_price_label;

    @FXML private Label form_error_label;
    @FXML private Rectangle form_error_rectangle;

    @FXML private Button fidelity_card_request_button;
    @FXML private Pane fidelity_card_pane;
    @FXML private Label fidelity_card_release_date;
    @FXML private Label fidelity_card_points;
    @FXML private Label fidelity_card_id;

    public ProfileController(){
        oldOrderObservableList = FXCollections.observableArrayList();
        oldOrderObservableList.addAll(Stock.getUserOrder(Controller.getCurrentUser()));

        Comparator<Order> comparator = new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return (o2.getDeliveryDate().compareTo(o1.getDeliveryDate()));
            }
        };

        oldOrderObservableList.sort(comparator);

        selectedOrderObservableList = FXCollections.observableArrayList();
        paymentTypeObservableList = FXCollections.observableArrayList();
        paymentTypeObservableList.addAll(PaymentType.values());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        oldOrderList.setItems(oldOrderObservableList);
        oldOrderList.setCellFactory(oldOrderListView -> new OldOrderListViewCell());

        if(oldOrderObservableList.size() > 0) {
            oldOrderList.getSelectionModel().select(0);
            Controller.setSelectedOrder(oldOrderList.getSelectionModel().getSelectedItem());
            loadSelectedOrderList();
        }

        selectedOrderList.setFocusTraversable(false);

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

        loadFidelityCardArea();

        payment_type_combobox.setItems(paymentTypeObservableList);
        payment_type_combobox.getSelectionModel().select(currentUser.getPaymentType());

    }

    private void loadFidelityCardArea() {
        FidelityCard c = currentUser.getCard();
        if(c != null) {
            fidelity_card_request_button.setVisible(false);
            fidelity_card_release_date.setText(c.getEmissionDate().toString());
            fidelity_card_id.setText(String.valueOf(c.id));
            fidelity_card_points.setText(String.valueOf(c.getPoints()));
            fidelity_card_pane.setVisible(true);
        } else {
            fidelity_card_request_button.setVisible(true);
        }
    }

    public void loadSelectedOrderList() {
        Order o = Controller.getSelectedOrder();
        selected_delivery_date_label.setText(o.getDeliveryDate().toString());
        selected_delivery_time_label.setText(o.getDeliveryInterval().toString());
        selected_payment_label.setText(o.getPaymentType().toString());
        selected_total_price_label.setText(String.format("€ %.2f", o.getTotalPrice()));

        selectedOrderObservableList.clear();        // rivedere come ho fatto nella spesa, per ora funziona
        selectedOrderObservableList.addAll(o.getProducts().keySet());
        selectedOrderList.setItems(selectedOrderObservableList);
        selectedOrderList.setCellFactory(oldOrderListView -> new SelectedOrderListViewCell());
    }

    public void saveUserInfoButton() {
        if(!formValidation()){
            return;
        }

        clearErrorMessage();

        currentUser.setName(name_field.getText());
        currentUser.setSurname(surname_field.getText());
        currentUser.setPhone(phone_field.getText());
        currentUser.setEmail(email_field.getText());
        currentUser.setAddress(address_field.getText());
        currentUser.setCap(cap_field.getText());
        currentUser.setCity(city_field.getText());
        currentUser.setPaymentType((PaymentType)payment_type_combobox.getValue());
        Controller.setCurrentUser(currentUser);
    }

    public void getNewFidelityCardButton() {
        FidelityCard f = new FidelityCard();
        currentUser.setCard(f);
        Controller.setCurrentUser(currentUser);
        loadFidelityCardArea();
    }

    public void newShoppingButton(ActionEvent event) throws IOException {
        Controller.getInstance().goToShoppingScene();
        Controller.setSelectedOrder(null);
    }

    private boolean formValidation(){
        if( name_field.getText().length() == 0 ){
            showError("Nome obbligatorio");
            return false;
        }
        if( name_field.getText().matches(".*\\d.*") ){
            showError("Nome non valido");
            return false;
        }
        if( surname_field.getText().length() == 0 ){
            showError("Cognome obbligatorio");
            return false;
        }
        if( surname_field.getText().matches(".*\\d.*") ){
            showError("Cognome non valido");
            return false;
        }
        if( phone_field.getText().length() == 0 ){
            showError("Telefono obbligatorio");
            return false;
        }
        if( !phone_field.getText().matches("[0-9]+")){
            showError("Numero di telefono non valido");
            return false;
        }
        if( email_field.getText().length() == 0 ){
            showError("Email obbligatoria");
            return false;
        }

        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email_field.getText());

        if( !mat.matches() ){
            showError("Email non valida");
            return false;
        }
        if( address_field.getText().length() == 0 ){
            showError("Indirizzo obbligatorio");
            return false;
        }
        if( cap_field.getText().length() == 0 ){
            showError("CAP obbligatorio");
            return false;
        }
        if( !cap_field.getText().matches("[0-9]+")){
            showError("CAP non valido");
            return false;
        }
        if( city_field.getText().length() == 0 ){
            showError("Città obbligatoria");
            return false;
        }
        if( city_field.getText().matches(".*\\d.*") ){
            showError("Città non valida");
            return false;
        }

        return true;
    }

    private void showError(String error){
        form_error_label.setText(error);
        form_error_label.setVisible(true);
        form_error_rectangle.setVisible(true);
    }

    public void clearErrorMessage(){
        form_error_label.setVisible(false);
        form_error_rectangle.setVisible(false);
    }

    public void Logout() throws IOException {
        Controller.getInstance().goToLoginScene();
    }
}
