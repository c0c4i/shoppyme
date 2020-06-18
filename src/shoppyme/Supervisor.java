package shoppyme;

public class Supervisor {
    public final int id;
    private String username;
    private String password;
    private String rule;
    private String name;
    private String surname;
    private String address;
    private String cap;
    private String city;
    private String phone;
    private String email;

    public Supervisor(int id, String username, String password, String rule, String name, String surname, String address, String cap, String city, String phone, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.rule = rule;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.cap = cap;
        this.city = city;
        this.phone = phone;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getRule() {
        return rule;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public String getCap() {
        return cap;
    }

    public String getCity() {
        return city;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRule(String rule) {
        this.rule = rule;
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

    public void setCity(String city) {
        this.city = city;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
