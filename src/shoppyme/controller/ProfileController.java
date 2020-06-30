package shoppyme.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import shoppyme.model.Stock;
import shoppyme.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    private User currentUser = Controller.getCurrentUser();

    @FXML private TextField name_field;
    @FXML private TextField surname_field;
    @FXML private TextField phone_field;
    @FXML private TextField email_field;
    @FXML private TextField address_field;
    @FXML private TextField cap_field;
    @FXML private TextField city_field;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name_field.setText(currentUser.getName());
        surname_field.setText(currentUser.getSurname());
        phone_field.setText(currentUser.getPhone());
        email_field.setText(currentUser.getEmail());
        address_field.setText(currentUser.getAddress());
        cap_field.setText(currentUser.getCap());
        city_field.setText(currentUser.getCity());
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
