package shoppyme.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import shoppyme.model.Order;
import shoppyme.model.Stock;
import shoppyme.model.Supervisor;
import shoppyme.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SupervisorLoginController implements Initializable {

    @FXML private TextField username_field;
    @FXML private PasswordField password_field;
    @FXML private Label error_field;
    @FXML private Rectangle error_rectangle;

    public void loginButtonClick(ActionEvent event) throws IOException {
        String usr = username_field.getText();
        String psw = password_field.getText();

        Supervisor supervisor = Stock.supervisorAuth(usr, psw);

        if(supervisor == null){
            error_field.setVisible(true);
            error_rectangle.setVisible(true);
        } else {
            Controller.setCurrentSupervisor(supervisor);
            username_field.clear();
            password_field.clear();
            Controller.getInstance().goToShoppingScene();
        }
    }

    public void publicAreaButtonClick() throws IOException {
        Controller.getInstance().goToLoginScene();
    }

    public void clearMessageError(){
        error_field.setVisible(false);
        error_rectangle.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
