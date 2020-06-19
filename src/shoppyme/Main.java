package shoppyme;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        //Stock m = Stock.getInstance();
//        Map<Product, Integer> inventory = Stock.getInventory();
//        inventory.forEach((product, quantity) -> {
//            System.out.println(product);
//            System.out.println(String.format("Quantity: %d\n", quantity));
//        });
//
//        List<FidelityCard> fidelity_cards = Stock.getFidelityCards();
//        fidelity_cards.forEach((fidelity) -> {
//            System.out.println(fidelity);
//        });

//        List<User> users = Stock.getUsers();
//        users.forEach((user) -> {
//            System.out.println(user);
//        });
//
//        List<Order> users = Stock.getOrders();
//        users.forEach((order) -> {
//            System.out.println(order);
//        });

        List<Supervisor> supervisors = Stock.getSupervisors();
        supervisors.forEach(System.out::println);
    }
}
