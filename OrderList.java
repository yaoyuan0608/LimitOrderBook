public class OrderList {
    private int limitPrice;
    // total number of order
    private int totalNumber = 0;
    // total number of quantity
    private int totalCount = 0;
    private Order headOrder = null;
    private Order tailOrder = null;

    public void appendOrder(Order incomingOrder) {
        // append the new order into the order list
        // if the order list is empty, let it be the first one
        if (totalNumber == 0) {
            incomingOrder.setNextOrder(null);
            incomingOrder.setPrevOrder(null);
            headOrder = incomingOrder;
            tailOrder = incomingOrder;
        }
        // append it at the tail
        else{
            incomingOrder.setNextOrder(null);
            incomingOrder.setPrevOrder(tailOrder);
            tailOrder.setNextOrder(incomingOrder);
            tailOrder = incomingOrder;
        }
        totalNumber += 1;
        totalCount += incomingOrder.getQuantity();
    }

    public void removeOrder(Order order){
        totalCount -= order.getQuantity();
        totalNumber -= 1;
        // if the order is the last order, do nothing
        if (totalNumber == 0){
            return;
        }
        // connect the head and tail of the deleted order
        else{
            Order tmpNextOrder = order.getNextOrder();
            Order tmpPrevOrder = order.getPrevOrder();
            if ((tmpPrevOrder != null) && (tmpNextOrder != null)){
                tmpNextOrder.setPrevOrder(tmpPrevOrder);
                tmpPrevOrder.setNextOrder(tmpNextOrder);
            }
            else if (tmpNextOrder != null){
                tmpNextOrder.setPrevOrder(null);
                headOrder = tmpNextOrder;
            }
            else if (tmpPrevOrder != null){
                tmpPrevOrder.setNextOrder(null);
                tailOrder = tmpPrevOrder;
            }
        }
    }
    public void moveTail(Order order){
        // connect prev and next order of the given order
        order.getNextOrder().setPrevOrder(order.getPrevOrder());
        if (order.getPrevOrder() != null){
            order.getPrevOrder().setNextOrder(order.getNextOrder());
        }
        else{
            this.headOrder = order.getNextOrder();
        }
        // update tail order
        this.tailOrder.setNextOrder((order));
        order.setPrevOrder(this.tailOrder);
        order.setNextOrder(null);
    }

    public int getLimitPrice(){
        return limitPrice;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public Order getHeadOrder() {
        return headOrder;
    }

    public Order getTailOrder() {
        return tailOrder;
    }

    // update total quantity
    public void setTotalCount(int quantity) {
        this.totalCount += quantity;
    }
}
