package shoppyme.controller;

import shoppyme.model.Order;
import shoppyme.model.Stock;
import shoppyme.model.User;

import java.util.List;

public class Controller {
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
}
