package com.driver;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    OrderRepository orderRepository = new OrderRepository();

    public void addOrder(Order order){                                                                  // 1st API
        orderRepository.addOrder(order);
    }

    public void addPartner(String partnerId){                                                           // 2nd API
        orderRepository.addPartner(partnerId);
    }

    public void addOrderPartnerPair(String orderId,String partnerId){                                    // 3rd API
        orderRepository.addOrderPartnerPair(orderId,partnerId);
    }

    public Order getOrderById(String orderId){                                                          // 4th API
        Order order = orderRepository.getOrderById(orderId);
        return order;
    }

    public DeliveryPartner getPartnerById(String partnerId){                                            // 5th API
        DeliveryPartner deliveryPartner = orderRepository.getPartnerById(partnerId);
        return deliveryPartner;
    }

    public Integer getOrderCountByPartnerId(String partnerId){                                          // 6th API
        int orderCount = orderRepository.getOrderCountByPartnerId(partnerId);
        return orderCount;
    }

    public List<String> getOrdersByPartnerId(String partnerId){                                         // 7th API
        List<String> orders = orderRepository.getOrdersByPartnerId(partnerId);
        return orders;
    }

    public List<String> getAllOrders(){                                                                  // 8th API
        List<String> orders = orderRepository.getAllOrders();
        return orders;
    }

    public Integer getCountOfUnassignedOrders(){                                                         // 9th API
        Integer countOfOrders = orderRepository.getCountOfUnassignedOrders();
        return countOfOrders;
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time,String partnerId){                // 10th API
        int countOfOrders = orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(time,partnerId);
        return countOfOrders;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {                                    // 11th API
        String time = orderRepository.getLastDeliveryTimeByPartnerId(partnerId);
        return time;
    }

    public void deletePartnerById(String partnerId){                                                    // 12th API                                                                                  // 12th API
        orderRepository.deletePartnerById(partnerId);
    }

    public void deleteOrderById(String orderId){                                                        // 13th API                                                                                            // 13th API
        orderRepository.deleteOrderById(orderId);
    }

}
