package shoppyme;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import shoppyme.controller.Controller;
import shoppyme.model.Stock;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Controller.getInstance().setStage(primaryStage);

        primaryStage.setTitle("Shoppyme");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("view/assets/shoppyme.png")));

        Controller.getInstance().goToLoginScene();

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
