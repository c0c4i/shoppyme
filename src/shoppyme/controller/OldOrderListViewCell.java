package shoppyme.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import shoppyme.model.Order;
import shoppyme.model.Product;

import javax.swing.*;
import java.io.IOException;

public class OldOrderListViewCell extends ListCell<Order> {

    @FXML private GridPane old_order_gridpane;
    @FXML private Label order_id_label;
    @FXML private Label order_date_label;
    @FXML private Label order_time_label;
    @FXML private Label order_payment_label;
    @FXML private Label order_total_price_label;
    @FXML private Label order_status_label;
    @FXML private Button order_info_button = new Button();
    @FXML private Label selected_delivery_date_label;

    private FXMLLoader mLLoader;
    private EventHandler<ActionEvent> event;

    @Override
    protected void updateItem(Order order, boolean empty) {
        super.updateItem(order, empty);

        if(empty || order == null) {
            setText(null);
            setGraphic(null);

        } else {

            mLLoader = new FXMLLoader(getClass().getResource("../view/old_order_cell.fxml"));
            mLLoader.setController(this);

            try {
                mLLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            order_id_label.setText(String.valueOf(order.id));
            order_date_label.setText(order.getDeliveryDate().toString());
            order_time_label.setText(order.getDeliveryInterval().toString());
            order_payment_label.setText(order.getPaymentType().toString());
            order_total_price_label.setText(String.valueOf(order.getTotalPrice()));
            order_status_label.setText(order.getStatus().toString());

            setText(null);
            setGraphic(old_order_gridpane);
        }
    }

    public void setButtonEvent(EventHandler<ActionEvent> e) {
        event = e;
    }
}
