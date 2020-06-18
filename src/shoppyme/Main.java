package shoppyme;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Stock m = Stock.getInstance();
//        Map<Product, Integer> inventory = m.getInventory();
//        inventory.forEach((product, quantity) -> {
//            System.out.println(product);
//            System.out.println(String.format("Quantity: %d\n", quantity));
//        });
//
//        List<FidelityCard> fidelity_cards = m.getFidelityCards();
//        fidelity_cards.forEach((fidelity) -> {
//            System.out.println(fidelity);
//        });

//        List<User> users = m.getUsers();
//        users.forEach((user) -> {
//            System.out.println(user);
//        });

        List<Order> users = m.getOrders();
        users.forEach((order) -> {
            System.out.println(order);
        });
    }
}
