package shoppyme.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.shape.Rectangle;
import shoppyme.model.Order;
import shoppyme.model.Stock;
import shoppyme.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML private Label signin_label;
    @FXML private TextField username_field;
    @FXML private PasswordField password_field;
    @FXML private Label error_field;
    @FXML private Rectangle error_rectangle;

    public void loginButtonClick(ActionEvent event) throws IOException {
        String usr = username_field.getText();
        String psw = password_field.getText();

        User user = Stock.userAuthentication(usr, psw);

        if(user == null){
            error_field.setVisible(true);
            error_rectangle.setVisible(true);
        } else {
            Controller.setCurrentUser(user);
            Controller.setCurrentOrder(new Order(user));
            username_field.clear();
            password_field.clear();
            Controller.getInstance().goToShoppingScene();
        }
    }

    public void signinUserPageButton() throws IOException {
        Controller.getInstance().goToSigninScene();
    }

    public void reservedAreaButtonClick() throws IOException {
        Controller.getInstance().goToReservedLoginAreaScene();
    }

    public void clearMessageError(){
        error_field.setVisible(false);
        error_rectangle.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
