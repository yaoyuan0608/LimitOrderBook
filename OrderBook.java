public class OrderBook {
    private OrderTree bids = new OrderTree();
    private OrderTree asks = new OrderTree();

    public void processOrder(Order order){
        // determine if it is a limit order or market order
        boolean limit = order.isLimit();
        if (order.getQuantity() <= 0){
            throw new IllegalArgumentException();
        }
        if (limit){
            processLimitOrder(order);
        }
        else{
            processMarketOrder(order);
        }
    }
    private int processOrderList(OrderList orderAtBest, int quantity, Order order){
        // order information
        boolean side = order.isBuyOrSell();
        int orderId = order.getOrderId();
        // process when the current order list has order left and total quantity is not 0
        while ((orderAtBest.getTotalCount() > 0) && quantity > 0){
            int qtyTraded = 0;
            // start from the header order
            Order headOrder = orderAtBest.getHeadOrder();
            // if current header order has more quantity than needed
            if (quantity < headOrder.getQuantity()){
                qtyTraded = quantity;
                // update order by its orderId and new quantity
                if (side){
                    this.bids.updateOrderQuantity(headOrder.getOrderId(), headOrder.getQuantity()-quantity);
                }
                else{
                    this.asks.updateOrderQuantity(headOrder.getOrderId(), headOrder.getQuantity()-quantity);
                }
            }
            // remove current header order
            else{
                qtyTraded = headOrder.getQuantity();
                if (side){
                    this.bids.removeOrderById(headOrder.getOrderId());
                }
                else{
                    this.asks.removeOrderById(headOrder.getOrderId());
                }
            }
            quantity -= qtyTraded;
        }
        return quantity;
    }
    private void processMarketOrder(Order order){
        // order information
        boolean side = order.isBuyOrSell();
        int quantityRemain = order.getQuantity();
        if (side){
            // process when quantity left and the tree has order left
            while ((quantityRemain > 0) && (this.asks.getTotalOrder() > 0)){
                // process on the order list with minimum price (sell side)
                OrderList orderAtBest = this.asks.minPriceList();
                quantityRemain = processOrderList(orderAtBest, quantityRemain, order);
            }
        }
        else{
            // process when quantity left and the tree has order left
            while ((quantityRemain > 0) && (this.bids.getTotalOrder() > 0)){
                // process on the order list with maximum price (buy side)
                OrderList orderAtBest = this.bids.maxPriceList();
                quantityRemain = processOrderList(orderAtBest, quantityRemain, order);
            }
        }
    }
    private void processLimitOrder(Order order){
        // order information
        boolean side = order.isBuyOrSell();
        int quantityRemain = order.getQuantity();
        if (side){
            // process when quantity left and the tree has order left and the minimum price is bigger than required
            while ((quantityRemain > 0) && (this.asks.getTotalOrder() > 0) &&
                    (this.asks.minPrice() <= order.getPrice())){
                OrderList orderAtBest = this.asks.maxPriceList();
                quantityRemain = processOrderList(orderAtBest, quantityRemain, order);
            }
            // if any quantity left, update this order and add to the tree
            if (quantityRemain > 0){
                order.updateQuantity(quantityRemain);
                this.bids.insertOrder(order);
            }
        }
        else{
            // process when quantity left and the tree has order left and the maximum price is smaller than required
            while ((quantityRemain > 0) && (this.bids.getTotalOrder() > 0) &&
                    (this.bids.maxPrice() >= order.getPrice())){
                OrderList orderAtBest = this.bids.maxPriceList();
                quantityRemain = processOrderList(orderAtBest, quantityRemain, order);
            }
            // if any quantity left, update this order and add to the tree
            if (quantityRemain > 0){
                order.updateQuantity(quantityRemain);
                this.asks.insertOrder(order);
            }
        }
    }

    public void cancelOrder(Order order){
        // basic information, can replace order by orderId
        boolean side = order.isBuyOrSell();
        int orderId = order.getOrderId();
        if (side){
            // if the orderId is in the tree, remove it
            if (bids.orderExist(orderId)){
                bids.removeOrderById(orderId);
            }
        }
        else{
            if (asks.orderExist(orderId)){
                asks.removeOrderById(orderId);
            }
        }
    }
}
