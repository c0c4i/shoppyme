package shoppyme;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {
    public final int id;
    private LocalDate deliveryDate;
    private List<Integer> deliveryInterval;
    private Map<Product, Integer> products = new HashMap<>();
    private PaymentType payment_type;
    private Status status;
    private int userID;

    public Order(User user) {
        Stock s = Stock.getInstance();
        this.id = s.getNewOrderID();
        this.userID = user.id;
    }

    public Order(int id, LocalDate deliveryDate, List<Integer> deliveryInterval, Map<Product, Integer> products, PaymentType payment_type, Status status, int userID) {
        this.id = id;
        this.deliveryDate = deliveryDate;
        this.deliveryInterval = deliveryInterval;
        this.products = products;
        this.payment_type = payment_type;
        this.status = status;
        this.userID = userID;
    }

    public void setDeliveryDate(LocalDate delivery_date) {
        this.deliveryDate = delivery_date;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public List<Integer> getDeliveryInterval() {
        return deliveryInterval;
    }

    public void setDeliveryInterval(List<Integer> delivery_interval) {
        this.deliveryInterval = delivery_interval;
    }

    public void addProduct(Product p) {
        Integer q = products.get(p);
        if (q == null)
            products.put(p, 1);
        else
            products.put(p, q+1);
    }

    // ritorna true/false a seconda della buona riuscita
    public boolean removeProduct(Product p) {
        if(products.get(p) != null) {
            products.remove(p);
            return true;
        } else return false;
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

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", deliveryDate=" + deliveryDate +
                ", deliveryInterval=" + deliveryInterval +
                ", products=" + products +
                ", payment_type=" + payment_type +
                ", status=" + status +
                ", userID=" + userID +
                '}';
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
