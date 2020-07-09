package shoppyme.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import shoppyme.model.Order;
import shoppyme.model.Stock;
import shoppyme.model.User;

import java.io.IOException;

public class Controller {

    private static Controller instance = new Controller();

    private Controller(){

    }

    public static Controller getInstance(){
        return instance;
    }

    public static LoginController loginController;
    public static ProfileController profileController;
    public static ShoppingController shoppingController;
    public static SigninController signinController;

    public static Stage stage;
    public static Scene loginScene;
    public static Scene shoppingScene;
    public static Scene profileScene;
    public static Scene signinScene;

    private static User currentUser;
    private static Order currentOrder;
    private static Order selectedOrder;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static Order getCurrentOrder() {
        return currentOrder;
    }

    public static Order getSelectedOrder() {
        return selectedOrder;
    }

    public static void setCurrentUser(User currentUser) {
        Controller.currentUser = currentUser;
        Stock.updateUser(currentUser);
    }

    public static void setCurrentOrder(Order currentOrder) {
        Controller.currentOrder = currentOrder;
    }

    public static void setSelectedOrder(Order selectedOrder) {
        Controller.selectedOrder = selectedOrder;
    }

    public void goToShoppingScene() throws IOException {

        if(shoppingScene == null){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/shopping.fxml"));
            Parent shoppingViewParent = loader.load();
            shoppingController = loader.getController();
            shoppingScene = new Scene(shoppingViewParent);
        }

        stage.setScene(shoppingScene);
//        window.setScene(shoppingViewScene);
//        window.show();
    }

    public void goToProfileScene() throws IOException {

        if(profileScene == null){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/profile.fxml"));
            Parent profileViewParent = loader.load();
            profileController = loader.getController();
            profileScene = new Scene(profileViewParent);
        }

        stage.setScene(profileScene);
    }

    public void goToSigninScene() throws IOException {

        if(signinScene == null){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/signin.fxml"));
            Parent signinViewParent = loader.load();
            signinController = loader.getController();
            signinScene = new Scene(signinViewParent);
        }

        stage.setScene(signinScene);
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
}
