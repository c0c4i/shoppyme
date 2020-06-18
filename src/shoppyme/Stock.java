package shoppyme;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Stock {
    private static HashMap<Product, Integer> inventory = new HashMap<>();
    private static List<FidelityCard> fidelity_cards = new ArrayList<>();
    private static List<User> users = new ArrayList<>();
    private static List<Order> orders = new ArrayList<>();
    private static List<Supervisor> supervisors = new ArrayList<>();
    private static Stock instance = new Stock();

    private Stock(){
        init();
    }

    public static Stock getInstance(){
        return instance;
    }

    private void init() {
        // read file and create java object
        initInventory();
        initFidelity();
        initUsers();
        initOrders();
        initSupervisors();
    }

    private void initInventory() {
        JSONParser jsonParser = new JSONParser();
        String filename = "src/shoppyme/db/products.json";
        try (FileReader reader = new FileReader(filename)) {
            Object obj = jsonParser.parse(reader);
            JSONArray productsList = (JSONArray) obj;
            productsList.forEach( product -> parseProductObject((JSONObject) product));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void parseProductObject(JSONObject product)
    {
        int quantity = ((Long) product.get("quantity")).intValue();

        JSONObject productObj = (JSONObject) product.get("product");
        int id = ((Long) productObj.get("id")).intValue();
        String name = (String) productObj.get("name");
        String brand = (String) productObj.get("brand");
        ProductType type = ProductType.valueOf((String) productObj.get("type"));

        JSONArray propertiesList = (JSONArray) productObj.get("properties");
        List<ProductProperty> properties = new ArrayList<ProductProperty>();
        propertiesList.forEach( prop -> {
            JSONObject j = (JSONObject) prop;
            String p = (String) j.get("name");
            ProductProperty e = ProductProperty.valueOf(p);
            properties.add(e);
        });

        int package_quantity = ((Long) productObj.get("package_quantity")).intValue();
        float price = ((Double)productObj.get("price")).floatValue();
        String image = (String) productObj.get("image");

        Product p = new Product(id, name, brand, type, properties, package_quantity, price, image);

        inventory.put(p, quantity);
    }

    private void initFidelity() {
        JSONParser jsonParser = new JSONParser();
        String filename = "src/shoppyme/db/fidelitycard.json";
        try (FileReader reader = new FileReader(filename)) {
            Object obj = jsonParser.parse(reader);
            JSONArray fidelityList = (JSONArray) obj;
            fidelityList.forEach( fidelity -> parseFidelityObject((JSONObject) fidelity));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void parseFidelityObject(JSONObject fidelity)
    {
        int id = ((Long) fidelity.get("id")).intValue();
        LocalDate emission_date = LocalDate.ofEpochDay((Long) fidelity.get("emission_date"));
        int points = ((Long) fidelity.get("id")).intValue();

        FidelityCard c = new FidelityCard(id, emission_date, points);
        fidelity_cards.add(c);
    }

    private void initUsers() {
        JSONParser jsonParser = new JSONParser();
        String filename = "src/shoppyme/db/users.json";
        try (FileReader reader = new FileReader(filename)) {
            Object obj = jsonParser.parse(reader);
            JSONArray usersList = (JSONArray) obj;
            usersList.forEach( user -> parseUserObject((JSONObject) user));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void parseUserObject(JSONObject user)
    {
        int id = ((Long) user.get("id")).intValue();
        String name = (String) user.get("name");
        String surname = (String) user.get("surname");
        String address = (String) user.get("address");
        String cap = (String) user.get("cap");
        String phone = (String) user.get("phone");
        String email = (String) user.get("email");
        String city = (String) user.get("city");
        String password = (String) user.get("password");
        PaymentType payment_type = PaymentType.valueOf((String) user.get("payment_type"));
        int card_id = ((Long) user.get("card_id")).intValue();
        FidelityCard card = null;

        for(FidelityCard f : fidelity_cards) {
            if(f.getID() == card_id){
                card = f;
                break;
            }
        }

        User u = new User(id, name, surname, address, cap, phone, email, city, password, payment_type, card);
        users.add(u);
    }

    private void initOrders() {
        JSONParser jsonParser = new JSONParser();
        String filename = "src/shoppyme/db/orders.json";
        try (FileReader reader = new FileReader(filename)) {
            Object obj = jsonParser.parse(reader);
            JSONArray usersList = (JSONArray) obj;
            usersList.forEach( user -> parseOrderObject((JSONObject) user));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void parseOrderObject(JSONObject order)
    {
        int id = ((Long) order.get("id")).intValue();
        LocalDate deliveryDate = LocalDate.ofEpochDay((Long) order.get("deliveryDate"));

        JSONArray deliveryIntervalObj = (JSONArray) order.get("deliveryInterval");
        ArrayList<Integer> deliveryInterval = deliveryIntervalObj;

        JSONArray productsList = (JSONArray) order.get("products");
        Map<Product, Integer> products = new HashMap<>();
        productsList.forEach( prod -> {
            JSONObject j = (JSONObject) prod;
            int id_product = ((Long) j.get("id_product")).intValue();
            int quantity = ((Long) j.get("quantity")).intValue();
            for(Product p : inventory.keySet()) {
                if(p.id == id_product){
                    products.put(p, quantity);
                    break;
                }
            }
        });

        PaymentType payment_type = PaymentType.valueOf((String) order.get("payment_type"));
        Status status = Status.valueOf((String) order.get("status"));
        int user_id = ((Long) order.get("user_id")).intValue();

        Order o = new Order(id, deliveryDate, deliveryInterval, products, payment_type, status, user_id);
        orders.add(o);
    }

    private void initSupervisors() {

    }

    public int getNewOrderID() {
        return orders.size() + 1;
    }

    public int getNewFidelityID() {
        return fidelity_cards.size() + 1;
    }

    public Map<Product, Integer> getInventory() {
        return inventory;
    }

    public static List<FidelityCard> getFidelityCards() {
        return fidelity_cards;
    }

    public static List<User> getUsers() {
        return users;
    }

    public static List<Order> getOrders() {
        return orders;
    }

    public static List<Supervisor> getSupervisors() {
        return supervisors;
    }
}
