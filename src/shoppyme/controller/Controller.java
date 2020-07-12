package shoppyme.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import shoppyme.model.Order;
import shoppyme.model.Stock;
import shoppyme.model.Supervisor;
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
    public static CheckoutController checkoutController;
    public static SupervisorLoginController supervisorLoginController;
    public static SupervisorAreaController supervisorAreaController;

    public static Stage stage;
    public static Scene loginScene;
    public static Scene shoppingScene;
    public static Scene profileScene;
    public static Scene signinScene;
    public static Scene checkoutScene;
    public static Scene supervisorLoginScene;
    public static Scene supervisorAreaScene;

    private static User currentUser;
    private static Order currentOrder;
    private static Order selectedOrder;
    private static Supervisor currentSupervisor;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static Order getCurrentOrder() {
        return currentOrder;
    }

    public static Order getSelectedOrder() {
        return selectedOrder;
    }

    public static Supervisor getCurrentSupervisor() {
        return currentSupervisor;
    }

    public static void setCurrentUser(User currentUser) {
        Controller.currentUser = currentUser;
        Stock.updateUser(currentUser);
    }

    public static void setCurrentSupervisor(Supervisor supervisor) {
        Controller.currentSupervisor = supervisor;
    }

    public static void setCurrentOrder(Order currentOrder) {
        Controller.currentOrder = currentOrder;
    }

    public static void setSelectedOrder(Order selectedOrder) {
        Controller.selectedOrder = selectedOrder;
    }

    public void goToShoppingScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/public/shopping.fxml"));
        Parent shoppingViewParent = loader.load();
        shoppingController = loader.getController();
        shoppingScene = new Scene(shoppingViewParent);

        stage.setScene(shoppingScene);
    }

    public void goToProfileScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/public/profile.fxml"));
        Parent profileViewParent = loader.load();
        profileController = loader.getController();
        profileScene = new Scene(profileViewParent);

        stage.setScene(profileScene);
    }

    public void goToCheckoutScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/public/checkout.fxml"));
        Parent checkoutViewParent = loader.load();
        checkoutController = loader.getController();
        checkoutScene = new Scene(checkoutViewParent);

        stage.setScene(checkoutScene);
    }

    public void goToSigninScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/public/signin.fxml"));
        Parent signinViewParent = loader.load();
        signinController = loader.getController();
        signinScene = new Scene(signinViewParent);

        stage.setScene(signinScene);
    }

    public void goToReservedLoginAreaScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/private/supervisor_login.fxml"));
        Parent supervisorLoginView = loader.load();
        supervisorLoginController = loader.getController();
        supervisorLoginScene = new Scene(supervisorLoginView);

        stage.setScene(supervisorLoginScene);
    }

    public void goToReservedAreaScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/private/supervisor_area.fxml"));
        Parent supervisorAreaView = loader.load();
        supervisorAreaController = loader.getController();
        supervisorAreaScene = new Scene(supervisorAreaView);

        stage.setScene(supervisorAreaScene);
    }

    public void goToLoginScene() {
        clearSession();
        stage.setScene(loginScene);
    }

    public void clearSession() {
        currentUser = null;
        currentOrder = null;
        selectedOrder = null;
        currentSupervisor = null;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
}
