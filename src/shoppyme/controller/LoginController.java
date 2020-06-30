package shoppyme.controller;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import shoppyme.model.Product;
import shoppyme.model.Stock;
import shoppyme.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField username_field;
    @FXML
    private TextField password_field;
    @FXML
    private Label error_field;

    public void loginButtonClick(ActionEvent event) throws IOException {
        String usr = username_field.getText();
        String psw = password_field.getText();

        User user = Stock.userAuthentication(usr, psw);

        if(user == null){
            error_field.setVisible(true);
        } else {
            Controller.setCurrentUser(user);
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

    public void clearMessageError(){
        error_field.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
