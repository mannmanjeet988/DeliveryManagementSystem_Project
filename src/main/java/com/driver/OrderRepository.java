package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Repository
public class OrderRepository {

    HashMap<String,Order> orderDb = new HashMap<>();
    HashMap<String,DeliveryPartner> partnerDb = new HashMap<>();
    HashMap<String, String> orderToPartnerMap = new HashMap<>();    // key : orderId, value : partnerId

    HashMap<String, HashSet<String>> partnerToOrderMap = new HashMap<>();  // key :orderId, value : hashSet<orderId>


   public void addOrder(Order order){
       String key = order.getId();
       if(key != null)
           orderDb.put(key,order);
   }

   public void  addPartner(String partnerId){
       DeliveryPartner deliveryPartner = new DeliveryPartner(partnerId);
       String key = deliveryPartner.getId();
       partnerDb.put(key, deliveryPartner);
   }

   public void addOrderPartnerPair(String orderId, String partnerId){
    if(orderDb.containsKey(orderId) && partnerDb.containsKey(partnerId)){

        HashSet<String> orders = new HashSet<>();
        if(partnerToOrderMap.containsKey(partnerId)){
            orders = partnerToOrderMap.get(partnerId);
        }
        orders.add(orderId);
        partnerToOrderMap.put(partnerId, orders);

        DeliveryPartner partner = partnerDb.get(partnerId);
        partner.setNumberOfOrders(orders.size());

        orderToPartnerMap.put(orderId,partnerId);
    }
   }

   public Order getOrderById(String orderId){
       Order order = orderDb.get(orderId);
       return order;
  }

    public DeliveryPartner getPartnerById(String partnerId){
       DeliveryPartner deliveryPartner = null;
       if(partnerDb.containsKey(partnerId))
           deliveryPartner = partnerDb.get(partnerId);

       return deliveryPartner;
    }

public Integer getOrderCountByPartnerId(String partnerId){
       int orderCount = 0;
       if(partnerToOrderMap.containsKey(partnerId))
       orderCount = partnerToOrderMap.get(partnerId).size();

       return orderCount;
}

    public List<String> getOrdersByPartnerId(String partnerId) {


        HashSet<String> orderList = null;

        if (partnerToOrderMap.containsKey(partnerId))
            orderList = partnerToOrderMap.get(partnerId);

        return new ArrayList<>(orderList);
    }

    public List<String> getAllOrders(){
       List<String> orders = new ArrayList<>(orderDb.keySet());
       return orders;
    }

    public Integer getCountOfUnassignedOrders(){
       Integer orderCount =0;
        List<String> list = new ArrayList<>(orderDb.keySet());

        for(String st : list){
            if(!orderToPartnerMap.containsKey(st))
                orderCount++;
        }
        return orderCount;
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId){
       int orderCount = 0;
        int hr = Integer.valueOf(time.substring(0,2));
        int min = Integer.valueOf(time.substring(3));
        int total = hr*60 + min;

        if(partnerToOrderMap.containsKey(partnerId)){
            HashSet<String> set = partnerToOrderMap.get(partnerId);

            for(String st : set){
                if(orderDb.containsKey(st))
                {
                    Order order = orderDb.get(st);
                    if(order.getDeliveryTime() > total) {
                        orderCount++;
                    }
                }
            }


        }
       return orderCount;
    }

    public String  getLastDeliveryTimeByPartnerId(String partnerId){
        String time = null;
        int delivery_time = 0;

        if(partnerDb.containsKey(partnerId))
        {
            HashSet<String> list = partnerToOrderMap.get(partnerId);

            for(String st : list)
            {
                if(orderDb.containsKey(st))
                {
                    Order order = orderDb.get(st);

                    if(delivery_time < order.getDeliveryTime())
                        delivery_time = order.getDeliveryTime();
                }
            }
        }
        StringBuilder str = new StringBuilder();

        int hr = delivery_time / 60;                 // calculate hour
        if(hr < 10)
            str.append(0).append(hr);
        else
            str.append(hr);

        str.append(":");

        int min = delivery_time - (hr*60);          // calculate minutes
        if(min < 10)
            str.append(0).append(min);
        else
            str.append(min);

        return str.toString();
    }

    public void deletePartnerById(String partnerId){
        HashSet<String> list = new HashSet<>();

        if(partnerToOrderMap.containsKey(partnerId))
        {
            list = partnerToOrderMap.get(partnerId);

            for (String st : list) {
//                orderMap.remove(st);

                if (orderToPartnerMap.containsKey(st))
                    orderToPartnerMap.remove(st);
            }

            partnerToOrderMap.remove(partnerId);
        }

        if(partnerDb.containsKey(partnerId)) {
            partnerDb.remove(partnerId);
        }
    }

    public void deleteOrderById(String orderId){
        if(orderToPartnerMap.containsKey(orderId))
        {
            String partnerId = orderToPartnerMap.get(orderId);

            HashSet<String> list = partnerToOrderMap.get(partnerId);
            list.remove(orderId);
            partnerToOrderMap.put(partnerId,list);

            DeliveryPartner deliveryPartner = partnerDb.get(partnerId);
            deliveryPartner.setNumberOfOrders(list.size());
        }

        if(orderDb.containsKey(orderId)) {
            orderDb.remove(orderId);
        }
    }
}

