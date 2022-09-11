public class Order {
    private int orderId;
    private boolean buyOrSell;
    private boolean isLimit;
    private int quantity;
    private double price;
    private Order nextOrder;
    private Order prevOrder;
    private OrderList parentLimit;

    public void updateQuantity(int quantity){
        if (quantity > this.quantity){
            this.parentLimit.moveTail(this);
            this.parentLimit.setTotalCount(this.parentLimit.getTotalCount()-this.quantity+quantity);
            this.quantity = quantity;
        }
    }
    public Order getNextOrder(){
        return this.nextOrder;
    }
    public void setNextOrder(Order nextOrder){
        this.nextOrder = nextOrder;
    }
    public Order getPrevOrder() {
        return this.prevOrder;
    }
    public void setPrevOrder(Order prevOrder) {
        this.prevOrder = prevOrder;
    }
    public int getQuantity() {
        return this.quantity;
    }
    public int getOrderId(){
        return this.orderId;
    }
    public double getPrice(){
        return this.price;
    }
    public void setParentLimit(OrderList orderList){
        this.parentLimit = orderList;
    }

    public OrderList getParentLimit() {
        return parentLimit;
    }

    public boolean isLimit() {
        return isLimit;
    }

    public boolean isBuyOrSell() {
        return buyOrSell;
    }
}
