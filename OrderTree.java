import java.util.HashMap;
import java.util.TreeMap;

public class OrderTree {
    // key:value, price:orderList
    private TreeMap<Double, OrderList> priceTree = new TreeMap<>();
    // key:value, price:orderList
    private HashMap<Double, OrderList> priceMap = new HashMap<>();
    // key:value, orderId:order
    private HashMap<Integer, Order> orderMap = new HashMap<>();
    // total number of quantity
    private int size;
    // total number of order in the tree
    private int totalOrder;
    // total number of price level
    private int depth;

    public int getLength(){
        return orderMap.size();
    }
    // return orderList by given price
    public OrderList getOrderList(double price){
        return priceMap.get(price);
    }
    // return order by given orderId
    public Order getOrder(int orderId) {
        return orderMap.get(orderId);
    }
    // create a price level
    public void createPrice(double price){
        depth += 1;
        OrderList newList = new OrderList();
        priceTree.put(price, newList);
        priceMap.put(price, newList);
    }
    // delete a price level
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
    // insert an order into the tree
    public void insertOrder(Order order){
        int orderId = order.getOrderId();
        double orderPrice = order.getPrice();
        // if current tree contains the orderId, remove it
        if (orderExist(orderId)) {removeOrderById(orderId);}
        this.totalOrder += 1;
        // if current tree does not contain the price limit, add it
        if (!priceExist(orderPrice)){
            createPrice(orderPrice);
        }
        // update the order's parent limit, and the priceMap
        order.setParentLimit(priceMap.get(orderPrice));
        priceMap.get(orderPrice).appendOrder(order);
        orderMap.put(orderId, order);
        priceTree.get(orderPrice).appendOrder(order);
        size += order.getQuantity();
    }
    public void updateOrderQuantity(int orderId, int quantity){
        // retrieve the corresponding order object and update the total quantity
        Order order = this.orderMap.get(orderId);
        int origQuantity = order.getQuantity();
        order.updateQuantity(quantity);
        this.size += order.getQuantity() - origQuantity;
    }
    public void updateOrder(Order orderUpdated){
        // basic information
        int orderUpdatedId = orderUpdated.getOrderId();
        double price = orderUpdated.getPrice();
        Order order = this.orderMap.get(orderUpdatedId);
        // update the order if price or quantity changed
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
        this.totalOrder -= 1;
        Order order = orderMap.get(orderId);
        this.size -= order.getQuantity();
        // remove the order from its parent limit
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
