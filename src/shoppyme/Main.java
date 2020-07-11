package shoppyme;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import shoppyme.controller.Controller;
import shoppyme.model.Stock;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/public/login.fxml"));

        Controller.getInstance().setStage(primaryStage);

        primaryStage.setTitle("Shoppyme");
        Controller.loginScene = new Scene(root);
        primaryStage.setScene(Controller.loginScene);
        primaryStage.show();
    }

    @Override
    public void stop(){
        Stock.onClose();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
