# LimitOrderBook

This is a very simple prototype for a limit order book. Self-practice use only.

## Description
Files contain:
- **Orderbook.java**
  The main class of an order book
  - processOrder(order): process on limit order or market order.
  - processMarketOrder(order): process through the bid/ask tree when no quantity left for this order.
  - processLimitOrder(order): process throught the bid/ask tree when no quantity left or the existing min/max price is bigger/smaller than limit price.
  - processOrderList(orderList,quantity,order): order process on given order list and order, start from the head of the given order list, remove all order until the quantity is zero or the given order list is empty.
  
- **OrderTree.java**
  The main class of an order tree, which is a basic component of an order book.
  - getOrderList(price): return the orderList by given price.
  - getOrder(orderId): return the order by given orderId.
  - createPrice(price): create a price level and add to the treemap and hashmap.
  - removePrice(price): remove the price level from treemap and hashmap.
  - priceExist(price): check whether the price level is inside the map.
  - orderExist(orderId): check whether the orderId is inside the map.
  - insertOrder(order): insert an order into the orderTree. If current tree contains this order, remove it. If the orderTree doesn't contain this price level, add it. Update the order's parent level and priceMap, priceTreeand orderMap.
  - updateOrderQuantity(orderId, quantity): update the quantity of a given orderId, and update the total quantity of the tree.
  - updateOrder(order): update the order if the price and quantity has changed.
  - removeOrderById(orderId): remove an order from the tree by given order id.
  - maxPrice, minPrice, maxPriceList, minPriceList
  
- **OrderList.java**
  The main class of an order list, which is the node of an order tree.
  - appendOrder(order): append a new order into the order list.
  - removeOrder(order): remove the order from the order list.
  - moveTail(order): move the order to the end of order list.

- **Order.java**
  The main class of an order, which is the basic component of an order list.
  - updateQuantity(quantity): update the quantity of the order and move it the end of its price order list.


More detailed and completed documentation can be refered to: https://github.com/DrAshBooth/JavaLOB/
