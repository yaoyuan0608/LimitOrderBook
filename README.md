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
  - dd
- **OrderList.java**
  The main class of an order list, which is the node of an order tree.
  
- **Order.java**
  The main class of an order, which is the basic component of an order list.





Reference: https://github.com/DrAshBooth/JavaLOB/
