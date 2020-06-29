package shoppyme.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import shoppyme.model.Stock;
import shoppyme.model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    private User currentUser;

    @FXML
    private TextField name_field;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setCurrentUser(User user){
        currentUser = user;
        name_field.setText(currentUser.getName());
    }

}
