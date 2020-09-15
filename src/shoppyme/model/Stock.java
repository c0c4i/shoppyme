package shoppyme.model;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import org.json.*;
import shoppyme.model.customenum.*;

public class Stock {
    private static HashMap<Product, Integer> inventory = new HashMap<>();
    private static List<FidelityCard> fidelityCards = new ArrayList<>();
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

    private JSONArray fetchJsonFile(String filename) {
        try (FileReader reader = new FileReader(filename)) {
            JSONTokener tokener = new JSONTokener(reader);
            Object obj = tokener.nextValue();
            return (JSONArray) obj;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void initInventory() {
        JSONArray productsList = fetchJsonFile("src/shoppyme/model/db/products.json");
        productsList.forEach( product -> parseProductObject((JSONObject) product));
    }

    private static void parseProductObject(JSONObject product)
    {
        int quantity = product.getInt("quantity");

        JSONObject productObj = product.getJSONObject("product");
        int id = productObj.getInt("id");
        String name = productObj.getString("name");
        String brand = productObj.getString("brand");
        ProductType type = ProductType.valueOf(productObj.getString("type"));

        JSONArray propertiesList = productObj.getJSONArray("properties");
        List<ProductProperty> properties = new ArrayList<>();
        propertiesList.forEach( prop -> {
            String j = (String) prop;
            ProductProperty e = ProductProperty.valueOf(j);
            properties.add(e);
        });

        int package_quantity = productObj.getInt("package_quantity");
        float price = productObj.getFloat("price");
        String image = productObj.getString("image");

        Product p = new Product(id, name, brand, type, properties, package_quantity, price, image);

        inventory.put(p, quantity);
    }

    private void initFidelity() {
        JSONArray fidelityList = fetchJsonFile("src/shoppyme/model/db/fidelitycard.json");
        fidelityList.forEach( fidelity -> parseFidelityObject((JSONObject) fidelity));
    }

    private static void parseFidelityObject(JSONObject fidelity)
    {
        int id = fidelity.getInt("id");
        LocalDate emission_date = LocalDate.ofEpochDay(fidelity.getLong("emission_date"));
        int points = fidelity.getInt("points");

        FidelityCard c = new FidelityCard(id, emission_date, points);
        fidelityCards.add(c);
    }

    private void initUsers() {
        JSONArray usersList = fetchJsonFile("src/shoppyme/model/db/users.json");
        usersList.forEach( user -> parseUserObject((JSONObject) user));
    }

    private static void parseUserObject(JSONObject user)
    {
        int id = user.getInt("id");
        String name = user.getString("name");
        String surname = user.getString("surname");
        String address = user.getString("address");
        String cap = user.getString("cap");
        String phone = user.getString("phone");
        String email = user.getString("email");
        String city = user.getString("city");
        String password = user.getString("password");
        String payment_type_string = user.getString("payment_type");
        PaymentType payment_type;

        if(payment_type_string.equals("NOT_SET")){
            payment_type = null;
        } else {
            payment_type = PaymentType.valueOf(payment_type_string);
        }

        int card_id = user.getInt("card_id");
        FidelityCard card = null;

        for(FidelityCard f : fidelityCards) {
            if(f.id == card_id){
                card = f;
                break;
            }
        }

        User u = new User(id, name, surname, address, cap, phone, email, city, password, payment_type, card);
        users.add(u);
    }

    private void initOrders() {
        JSONArray ordersList = fetchJsonFile("src/shoppyme/model/db/orders.json");
        ordersList.forEach( order -> parseOrderObject((JSONObject) order));
    }

    private static void parseOrderObject(JSONObject order)
    {
        int id = order.getInt("id");
        LocalDate deliveryDate = LocalDate.ofEpochDay(order.getInt("deliveryDate"));

        JSONArray deliveryIntervalObj = order.getJSONArray("deliveryInterval");
        int[] deliveryInterval = new int[2];

        int i= 0;
        for(Object o : deliveryIntervalObj){
            Integer time = (Integer)o;
            deliveryInterval[i] = time;
            i++;
        }

        JSONArray productsList = order.getJSONArray("products");
        Map<Product, Integer> products = new HashMap<>();
        Map<Product, Float> productsPrice = new HashMap<>();
        productsList.forEach( prod -> {
            JSONObject j = (JSONObject) prod;
            int id_product = j.getInt("id_product");
            int quantity = j.getInt("quantity");
            float oldPrice = j.getFloat("oldPrice");
            for(Product p : inventory.keySet()) {
                if(p.id == id_product){
                    products.put(p, quantity);
                    productsPrice.put(p, oldPrice);
                    break;
                }
            }
        });

        PaymentType payment_type = PaymentType.valueOf(order.getString("payment_type"));
        float totalPrice = order.getFloat("total_price");
        Status status = Status.valueOf(order.getString("status"));
        int user_id = order.getInt("user_id");

        Order o = new Order(id, deliveryDate, deliveryInterval, products, productsPrice, payment_type, totalPrice, status, user_id);
        orders.add(o);
    }

    private void initSupervisors() {
        JSONArray supervisorsList = fetchJsonFile("src/shoppyme/model/db/supervisors.json");
        supervisorsList.forEach( supervisor -> parseSupervisorObject((JSONObject) supervisor));
    }

    private static void parseSupervisorObject(JSONObject supervisor)
    {
        int id = supervisor.getInt("id");
        String username = supervisor.getString("username");
        String password = supervisor.getString("password");
        Role role = Role.valueOf(supervisor.getString("role"));
        String name = supervisor.getString("name");
        String surname = supervisor.getString("surname");
        String address = supervisor.getString("address");
        String cap = supervisor.getString("cap");
        String city = supervisor.getString("city");
        String phone = supervisor.getString("phone");
        String email = supervisor.getString("email");

        Supervisor s = new Supervisor(id, username, password, role, name, surname, address, cap, city, phone, email);
        supervisors.add(s);
    }
    
    public static int getNewUserID(){
        return users.size() + 1;
    }

    public static int getNewOrderID() {
        return orders.size() + 1;
    }

    public static int getNewFidelityID() {
        return fidelityCards.size() + 1;
    }

    public static Map<Product, Integer> getInventory() {
        return inventory;
    }

    public static List<Product> getInventoryBy(String name, SearchType type){
        List<Product> filteredList = new ArrayList<>();

        if(type == null){
            filteredList.addAll(inventory.keySet());
            return filteredList;
        }

        switch(type){
            case NAME : for(Product p : inventory.keySet()){
                            if(p.getName().matches("(?i:.*"+ name + ".*)"))
                                filteredList.add(p);
                        }
                        break;

            case BRAND : for(Product p : inventory.keySet()) {
                            if (p.getBrand().matches("(?i:.*"+ name + ".*)"))
                                filteredList.add(p);
                        }
                        break;

            case TYPE : for(Product p : inventory.keySet()) {
                            if (p.getType().toString().matches("(?i:.*"+ name + ".*)"))
                                filteredList.add(p);
                        }
                break;
        }

        return filteredList;
    }

    public static List<Product> getOrderedInventory(OrderType orderType, List<Product> productList){
        List<Product> orderedList = new ArrayList<>();
        Comparator<Product> comparator;
        orderedList.addAll(productList);

        if(orderType == null){
            return  orderedList;
        }

        switch(orderType){
            case AZ_BRAND:
                comparator = new Comparator<Product>() {
                    @Override
                    public int compare(Product o1, Product o2) {
                        return (o1.getBrand().compareTo(o2.getBrand()));
                    }
                };
                break;

            case ZA_BRAND:
                comparator = new Comparator<Product>() {
                    @Override
                    public int compare(Product o1, Product o2) {
                        return (o2.getBrand().compareTo(o1.getBrand()));
                    }
                };
                break;

            case LOWER_PRICE:
                comparator = new Comparator<Product>() {
                    @Override
                    public int compare(Product o1, Product o2) {
                        float diff = o1.getPrice() - o2.getPrice();
                        if(diff > 0)
                            return 1;
                        else if(diff < 0)
                            return -1;
                        else
                            return 0;
                    }
                };
                break;

            case HIGHER_PRICE:
                comparator = new Comparator<Product>() {
                    @Override
                    public int compare(Product o1, Product o2) {
                        float diff = o1.getPrice() - o2.getPrice();
                        if(diff > 0)
                            return -1;
                        else if(diff < 0)
                            return 1;
                        else
                            return 0;
                    }
                };
                break;

            default: return orderedList;
        }

        orderedList.sort(comparator);

        return orderedList;
    }

    public static List<User> getUsers() {
        return users;
    }

    public static List<Order> getOrders() {
        return orders;
    }

    public static void addOrder(Order o) {
        orders.add(o);
    }

    public static void updateOrder(Order o) {
        orders.remove(o);
        orders.add(o);
    }

    public static User userAuthentication (String email, String password) {
        for (User u : users) {
            if(u.getEmail().equals(email) && u.passwordMatch(password))
                return u;
        }
        return null;
    }

    public static void addUser(User newUser) {
        users.add(newUser);
    }

    public static void updateUser(User updatedUser) {
        users.remove(updatedUser);
        users.add(updatedUser);
    }

    public static List<Order> getUserOrder(User u) {
        List<Order> userOrder = new ArrayList<>();
        orders.forEach(order -> {
            if(order.getUserID() == u.id)
                userOrder.add(order);
        });
        return userOrder;
    }

    public static void addFidelityCard(FidelityCard f) {
        fidelityCards.add(f);
    }

    public static void updateFidelityCard(FidelityCard f) {
        fidelityCards.remove(f);
        fidelityCards.add(f);
    }

    public static boolean isAvailable(Product p, int q) {
        int i = inventory.get(p) - q;
        return (i >= 0);
    }

    public static void onClose() {
        onCloseInventoryUpdate();
        onCloseFidelityUpdate();
        onCloseUsersUpdate();
        onCloseOrderUpdate();
        //onCloseSupervisorUpdate();
    }

    private static void onCloseInventoryUpdate() {
        File file = new File("src/shoppyme/model/db/products.json");
        String json = "[\n";
        for(Product p : inventory.keySet()){
            json += String.format("\t{\n\t\t\"quantity\": %d,",inventory.get(p));
            json += p.toString();
            json += String.format("\n\t},\n");
        }

        String result = json.substring(0, json.length()-2);
        result += "\n]";

        try {
            FileWriter fw = new FileWriter(file, false);
            fw.write(result);
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void onCloseFidelityUpdate() {
        Comparator<FidelityCard> comparator = new Comparator<FidelityCard>() {
            @Override
            public int compare(FidelityCard f1, FidelityCard f2) {
                return (f1.id - f2.id);
            }
        };

        fidelityCards.sort(comparator);

        File file = new File("src/shoppyme/model/db/fidelitycard.json");
        String json = "[\n";
        for(FidelityCard f: fidelityCards) {
            json += f.toString();
        }
        String result = json.substring(0, json.length()-2);
        result += "\n]";

        try {
            FileWriter fw = new FileWriter(file, false);
            fw.write(result);
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void onCloseUsersUpdate() {
        Comparator<User> comparator = new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return (u1.id - u2.id);
            }
        };

        users.sort(comparator);

        File file = new File("src/shoppyme/model/db/users.json");
        String json = "[\n";
        for(User user: users) {
            json += user.toString();
        }
        String result = json.substring(0, json.length()-2);
        result += "\n]";

        try {
            FileWriter fw = new FileWriter(file, false);
            fw.write(result);
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void onCloseOrderUpdate() {
        Comparator<Order> comparator = new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return (o1.id - o2.id);
            }
        };

        orders.sort(comparator);

        File file = new File("src/shoppyme/model/db/orders.json");
        String json = "[\n";
        for(Order o: orders) {
            json += o.toString();
        }

        String result = json.substring(0, json.length()-2);
        result += "\n]";

        try {
            FileWriter fw = new FileWriter(file, false);
            fw.write(result);
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean userAlreadyExists(String email){
        for(User u : users){
            if(u.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

//    ################# SUPERVISOR METHOD ####################

    public static Supervisor supervisorAuth(String username, String password) {
        for (Supervisor s : supervisors) {
            if(s.getUsername().equals(username) && s.passwordMatch(password))
                return s;
        }
        return null;
    }

    public static void updateProduct(Product p, int q) {
        inventory.remove(p);
        inventory.put(p, q);
    }

    public static int getQuantityOf(Product p) {
        return inventory.get(p);
    }

    public static List<Product> getSearchedProductBy(int id) {
        List<Product> searched = new ArrayList<>();
        for(Product p : inventory.keySet()) {
            if(p.id == id) {
                searched.add(p);
                return searched;
            }
        }
        return searched;
    }

    public static List<Order> getSearchedOrderBy(int id) {
        List<Order> searched = new ArrayList<>();
        for(Order p : orders) {
            if(p.id == id) {
                searched.add(p);
                return searched;
            }
        }
        return searched;
    }
}
