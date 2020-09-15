package shoppyme.model;

import shoppyme.model.customenum.PaymentType;

public class User {
    public final int id;
    private String name;
    private String surname;
    private String address;
    private String cap;
    private String phone;
    private String email;
    private String city;
    private String password;
    private PaymentType paymentType;
    private FidelityCard card;

    public User(int id, String name, String surname, String address, String cap, String phone, String email, String city, String password, PaymentType payment_type, FidelityCard card) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.cap = cap;
        this.phone = phone;
        this.email = email;
        this.city = city;
        this.password = password;
        this.paymentType = payment_type;
        this.card = card;
    }

    public String getName() { return name; }

    public String getSurname() { return surname; }

    public String getAddress() { return address; }

    public String getCap() { return cap; }

    public String getPhone() { return phone; }

    public String getEmail() { return email; }

    public String getCity() {
        return city;
    }

    public boolean passwordMatch(String password) {
        return password.equals(this.password);
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public FidelityCard getCard() {
        return card;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPaymentType(PaymentType payment_type) {
        this.paymentType = payment_type;
    }

    public void setCard(FidelityCard card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return String.format("\t{\n\t\t\"id\": %d, \n\t\t\"name\": \"%s\", \n\t\t\"surname\": \"%s\", \n\t\t\"address\": \"%s\", \n\t\t\"cap\": \"%s\", \n\t\t\"phone\": \"%s\", \n\t\t\"email\": \"%s\", \n\t\t\"city\": \"%s\", \n\t\t\"password\": \"%s\", \n\t\t\"payment_type\": \"%s\", \n\t\t\"card_id\": %d\n\t},\n",
                id, name, surname, address, cap, phone, email, city, password, paymentType == null ? "NOT_SET" : paymentType.toString(), card == null ? -1 : card.id);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User)
            if(this.id == ((User) obj).id)
                return true;
            else
                return false;
        return false;
    }
}
