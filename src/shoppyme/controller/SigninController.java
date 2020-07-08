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
import javafx.stage.Stage;
import shoppyme.model.FidelityCard;
import shoppyme.model.Stock;
import shoppyme.model.User;
import shoppyme.model.customenum.PaymentType;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SigninController implements Initializable {

    private static ObservableList<PaymentType> paymentTypeObservableList;

    @FXML private RadioButton card_true_radiobutton;
    @FXML private RadioButton card_false_radiobutton;
    @FXML private Label form_error_label;

    @FXML private ComboBox payment_type_combobox;
    @FXML private PasswordField password_field;
    @FXML private TextField name_field;
    @FXML private TextField surname_field;
    @FXML private TextField phone_field;
    @FXML private TextField email_field;
    @FXML private TextField address_field;
    @FXML private TextField cap_field;
    @FXML private TextField city_field;

    public SigninController(){
        paymentTypeObservableList = FXCollections.observableArrayList();
        paymentTypeObservableList.addAll(PaymentType.values());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final ToggleGroup group = new ToggleGroup();

        payment_type_combobox.setItems(paymentTypeObservableList);

        card_true_radiobutton.setToggleGroup(group);
        card_false_radiobutton.setToggleGroup(group);
        card_true_radiobutton.setSelected(true);
    }

    public void signinUserButton(ActionEvent event) throws IOException{
//        if(!formValidation()){
//            //I dati non sono formattati correttamente
//        }

        if(Stock.userAlreadyExists(email_field.getText())){
            showError("Email già in uso");
        }
        else {
            int id = Stock.getNewUserID();
            String name = name_field.getText();
            String surname = surname_field.getText();
            String address = address_field.getText();
            String email = email_field.getText();
            String phone = phone_field.getText();
            String cap = cap_field.getText();
            String city = city_field.getText();
            String password = password_field.getText();
            PaymentType paymentType = (PaymentType) payment_type_combobox.getValue();
            FidelityCard fidelityCard;

            if(card_true_radiobutton.isArmed()) {
                fidelityCard = new FidelityCard();
            }
            else{
                fidelityCard = null;
            }

            User user = new User(id, name, surname, address, cap, phone, email, city, password, paymentType, fidelityCard);
            Stock.addUser(user);

            Controller.setCurrentUser(user);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/shopping.fxml"));
            Parent shoppingViewParent = loader.load();

            Scene shoppingViewScene = new Scene(shoppingViewParent);

            //This line gets the Stage information
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(shoppingViewScene);
            window.show();
        }

    }

    private boolean formValidation(){
        return true;
    }

    private void showError(String error){
        form_error_label.setText(error);
        form_error_label.setVisible(true);
    }

    public void clearErrorMessage(){
        form_error_label.setVisible(false);
    }
}
