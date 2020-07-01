package shoppyme.controller;

import shoppyme.model.Order;
import shoppyme.model.Stock;
import shoppyme.model.User;

import java.util.List;

public class Controller {
    private static User currentUser;
    private static Order currentOrder;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static Order getCurrentOrder() {
        return currentOrder;
    }

    public static void setCurrentUser(User currentUser) {
        Controller.currentUser = currentUser;
        Stock.updateUser(currentUser);
    }

    public static void setCurrentOrder(Order currentOrder) {
        Controller.currentOrder = currentOrder;
    }
}
