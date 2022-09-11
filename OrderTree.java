import java.util.HashMap;
import java.util.TreeMap;

public class OrderTree {
    private TreeMap<Double, OrderList> priceTree = new TreeMap<>();
    private HashMap<Double, OrderList> priceMap = new HashMap<>();
    private HashMap<Integer, Order> orderMap = new HashMap<>();
    private int size;
    private int totalOrder;
    private int depth;

    public int getLength(){
        return orderMap.size();
    }
    public OrderList getOrderList(double price){
        return priceMap.get(price);
    }

    public Order getOrder(int orderId) {
        return orderMap.get(orderId);
    }

    public void createPrice(double price){
        depth += 1;
        OrderList newList = new OrderList();
        priceTree.put(price, newList);
        priceMap.put(price, newList);
    }
    public void removePrice(double price){
        depth -= 1;
        priceTree.remove(price);
        priceMap.remove(price);
    }
    public boolean priceExist(double price){
        return priceMap.containsKey(price);
    }
    public boolean orderExist(int orderId){
        return orderMap.containsKey(orderId);
    }
    public void insertOrder(Order order){
        int orderId = order.getOrderId();
        double orderPrice = order.getPrice();
        if (orderExist(orderId)) {}
        this.totalOrder += 1;
        if (!priceExist(orderPrice)){
            createPrice(orderPrice);
        }
        order.setParentLimit(priceMap.get(orderPrice));
        priceMap.get(orderPrice).appendOrder(order);
        orderMap.put(orderId, order);
        size += order.getQuantity();
    }
    public void updateOrderQuantity(int orderId, int quantity){
        Order order = this.orderMap.get(orderId);
        double price = order.getPrice();
        int origQuantity = order.getQuantity();
        order.updateQuantity(quantity);
        this.size += order.getQuantity() - origQuantity;
    }
    public void updateOrder(Order orderUpdated){
        int orderUpdatedId = orderUpdated.getOrderId();
        double price = orderUpdated.getPrice();
        Order order = this.orderMap.get(orderUpdatedId);
        int origQuantity = order.getQuantity();
        if (price != order.getPrice()){
            OrderList tmpOrderList = this.priceMap.get(order.getPrice());
            tmpOrderList.removeOrder(order);
            if (tmpOrderList.getTotalNumber() == 0){
                this.removePrice(order.getPrice());
            }
            insertOrder(orderUpdated);
        }
        else{
            order.updateQuantity(orderUpdated.getQuantity());
        }
        this.totalOrder += order.getQuantity() - origQuantity;
    }
    public void removeOrderById(int orderId){
        this.size -= 1;
        Order order = orderMap.get(orderId);
        this.totalOrder -= order.getQuantity();
        order.getParentLimit().removeOrder(order);
        if (order.getParentLimit().getTotalNumber() == 0){
            this.removePrice(order.getPrice());
        }
        orderMap.remove(orderId);
    }
    public Double maxPrice(){
        if (this.depth>0){
            return this.priceTree.lastKey();
        }
        else{
            return null;
        }
    }
    public Double minPrice(){
        if (this.depth>0){
            return this.priceTree.firstKey();
        }
        else{
            return null;
        }
    }
    public OrderList maxPriceList(){
        if (this.depth > 0){
            return this.getOrderList(maxPrice());
        }
        else{
            return null;
        }
    }
    public OrderList minPriceList(){
        if (this.depth > 0){
            return this.getOrderList(minPrice());
        }
        else{
            return null;
        }
    }

    public int getSize() {
        return size;
    }

    public int getDepth() {
        return depth;
    }

    public int getTotalOrder() {
        return totalOrder;
    }
}
