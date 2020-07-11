package shoppyme.model;

import shoppyme.controller.Controller;
import shoppyme.model.customenum.PaymentType;
import shoppyme.model.customenum.Status;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {
    public final int id;
    private LocalDate deliveryDate;
    private int[] deliveryInterval;
    private Map<Product, Integer> products = new HashMap<>();
    private Map<Product, Float> oldProductsPrice = new HashMap<>();
    private PaymentType payment_type;
    private float totalPrice;
    private Status status;
    private int userID;

    public Order(User user) {
        Stock s = Stock.getInstance();
        this.id = s.getNewOrderID();
        this.userID = user.id;
        this.totalPrice = 0;
    }

    public Order(int id, LocalDate deliveryDate, int[] deliveryInterval, Map<Product, Integer> products, Map<Product, Float> oldProductsPrice, PaymentType payment_type, float totalPrice, Status status, int userID) {
        this.id = id;
        this.deliveryDate = deliveryDate;
        this.deliveryInterval = deliveryInterval;
        this.products = products;
        this.oldProductsPrice = oldProductsPrice;
        this.payment_type = payment_type;
        this.totalPrice = totalPrice;
        this.status = status;
        this.userID = userID;
    }

    public void setDeliveryDate(LocalDate delivery_date) {
        this.deliveryDate = delivery_date;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public String getDeliveryInterval() {
        return String.format("%d - %d", deliveryInterval[0], deliveryInterval[1]);
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public void setDeliveryInterval(int[] delivery_interval) {
        this.deliveryInterval = delivery_interval;
    }

    public boolean addProduct(Product p) {

        if (products.containsKey(p)) {
            int q = products.get(p);
            if(Stock.isAvailable(p, q + 1))
                products.put(p, q + 1);
            else return false;
        } else {
            if(Stock.isAvailable(p, 1))
                products.put(p, 1);
            else return false;
        }
        oldProductsPrice.put(p, p.getPrice());
        totalPrice += p.getPrice();
        return true;
    }

    public boolean removeProduct(Product p) {

        Integer q = products.get(p);
        totalPrice -= p.getPrice();

        if(q != null){
            if(q == 1){
                products.remove(p);
                oldProductsPrice.remove(p);
            }
            else{
                products.put(p, q - 1);
            }
            return true;
        }
        return false;
    }

    public PaymentType getPaymentType() {
        return payment_type;
    }

    public void setPaymentType(PaymentType payment_type) {
        this.payment_type = payment_type;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public int getUserID() {
        return userID;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public Map<Product, Float> getOldProductsPrice() {
        return oldProductsPrice;
    }

    @Override
    public String toString() {
        String tmp = String.format("\t{\n\t\t\"id\": %d," +
                        "\n\t\t\"deliveryDate\": %d," +
                        "\n\t\t\"deliveryInterval\": [%d, %d]," +
                        "\n\t\t\"products\": [",
                id, deliveryDate.toEpochDay(), deliveryInterval[0], deliveryInterval[1]);

        for(Product p : oldProductsPrice.keySet()){
            tmp += String.format(
                    "\n\t\t\t{" +
                    "\n\t\t\t\t\"id_product\": %d," +
                    "\n\t\t\t\t\"quantity\": %d," +
                    "\n\t\t\t\t\"oldPrice\": %s" +
                    "\n\t\t\t},",
                    p.id, products.get(p), String.valueOf(oldProductsPrice.get(p)).replace(",",".")
            );

        };

        tmp = tmp.substring(0, tmp.length()-1);

        tmp += String.format(
                        "\n\t\t]," +
                        "\n\t\t\"total_price\": %s," +
                        "\n\t\t\"payment_type\": \"%s\"," +
                        "\n\t\t\"status\": \"%s\"," +
                        "\n\t\t\"user_id\": %d\n\t},\n",
                String.valueOf(totalPrice).replace(",","."),payment_type.toString(), status, userID);

        return tmp;
    }

    public void completeOrder() {
        this.status = Status.PROCESSING;
        User u = Controller.getCurrentUser();
        FidelityCard f = u.getCard();
        if(f != null) {
            f.addPoints(Math.round(totalPrice));
            u.setCard(f);
            Controller.setCurrentUser(u);
        }
        Stock.addOrder(this);
        Controller.setCurrentOrder(null);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Order)
            if(this.id == ((Order) obj).id)
                return true;
            else
                return false;
        return false;
    }
}
