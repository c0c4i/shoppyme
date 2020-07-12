package shoppyme.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import shoppyme.model.Order;

import java.io.IOException;

public class SupervisorOrderListViewCell extends ListCell<Order> {

    @FXML private GridPane order_gridpane;
    @FXML private Label order_id_label;
    @FXML private Label order_date_label;
    @FXML private Label order_time_label;
    @FXML private Label order_status_label;

    private FXMLLoader mLLoader;
    private EventHandler<ActionEvent> event;

    @Override
    protected void updateItem(Order order, boolean empty) {
        super.updateItem(order, empty);

        if(empty || order == null) {
            setText(null);
            setGraphic(null);

        } else {

            mLLoader = new FXMLLoader(getClass().getResource("../view/private/supervisor_order_cell.fxml"));
            mLLoader.setController(this);

            try {
                mLLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            order_id_label.setText(String.valueOf(order.id));
            order_date_label.setText(order.getDeliveryDate().toString());
            order_time_label.setText(order.getDeliveryInterval());
            order_status_label.setText(order.getStatus().toString());

            setText(null);
            setGraphic(order_gridpane);
        }
    }
}
