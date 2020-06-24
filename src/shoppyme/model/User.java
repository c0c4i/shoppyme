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
    private PaymentType payment_type;
    FidelityCard card;

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
        this.payment_type = payment_type;
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

    public String getPassword() {
        return password;
    }

    public PaymentType getPayment_type() {
        return payment_type;
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
        this.payment_type = payment_type;
    }

    public void setCard(FidelityCard card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", cap='" + cap + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", password='" + password + '\'' +
                ", payment_type='" + payment_type + '\'' +
                ", card=" + card +
                '}';
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
