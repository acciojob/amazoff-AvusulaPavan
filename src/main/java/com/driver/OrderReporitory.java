package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Repository
public class OrderReporitory {

    HashMap<String, Order> orderHashMap=new HashMap<>();
    HashMap<String, DeliveryPartner> deliveryPartnerHashMap=new HashMap<>();
    HashMap<String, String> orderPartnerHashMap=new HashMap<>();
    HashMap<String, HashSet<String>> partnerOrderHashMap=new HashMap<>();

    public void addOrder(Order order) {
        String id= order.getId();
        orderHashMap.put(id,order);
    }

    public void addPartner(String partnerId) {

        deliveryPartnerHashMap.put(partnerId,new DeliveryPartner(partnerId));

    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        if(orderHashMap.containsKey(orderId) && deliveryPartnerHashMap.containsKey(partnerId)){

            HashSet<String> currentOrders = new HashSet<String>();
            if(partnerOrderHashMap.containsKey(partnerId))
                currentOrders = partnerOrderHashMap.get(partnerId);
            currentOrders.add(orderId);
            partnerOrderHashMap.put(partnerId, currentOrders);

            DeliveryPartner partner = deliveryPartnerHashMap.get(partnerId);
            partner.setNumberOfOrders(currentOrders.size());
            orderPartnerHashMap.put(orderId, partnerId);
        }
    }
    public Order getOrderById(String orderId) {

        return orderHashMap.get(orderId);

    }
    public DeliveryPartner getPartnerById(String partnerId) {
        return deliveryPartnerHashMap.get(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        Integer count=0;
        if(deliveryPartnerHashMap.containsKey(partnerId)){
            count=deliveryPartnerHashMap.get(partnerId).getNumberOfOrders();
        }
        return count;
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        HashSet<String> orderList = new HashSet<>();
        if(partnerOrderHashMap.containsKey(partnerId)) orderList = partnerOrderHashMap.get(partnerId);
        return new ArrayList<>(orderList);
    }

    public List<String> getAllOrders() {
        return new ArrayList<>(orderHashMap.keySet());
    }

    public Integer getCountOfUnassignedOrders() {
        Integer countOfOrders = 0;
        List<String> orders =  new ArrayList<>(orderHashMap.keySet());
        for(String orderId: orders){
            if(!orderPartnerHashMap.containsKey(orderId)){
                countOfOrders += 1;
            }
        }
        return countOfOrders;
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String timeS, String partnerId) {

        Integer hour = Integer.valueOf(timeS.substring(0, 2));
        Integer minutes = Integer.valueOf(timeS.substring(3));
        Integer time = hour*60 + minutes;

        Integer countOfOrders = 0;
        if(partnerOrderHashMap.containsKey(partnerId)){
            HashSet<String> orders = partnerOrderHashMap.get(partnerId);
            for(String order: orders){
                if(orderHashMap.containsKey(order)){
                    Order currOrder = orderHashMap.get(order);
                    if(time < currOrder.getDeliveryTime()){
                        countOfOrders += 1;
                    }
                }
            }
        }
        return countOfOrders;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {

        Integer time = 0;

        if(partnerOrderHashMap.containsKey(partnerId)){
            HashSet<String> orders = partnerOrderHashMap.get(partnerId);
            for(String order: orders){
                if(orderHashMap.containsKey(order)){
                    Order currOrder = orderHashMap.get(order);
                    time = Math.max(time, currOrder.getDeliveryTime());
                }
            }
        }

        Integer hour = time/60;
        Integer minutes = time%60;

        String hourInString = String.valueOf(hour);
        String minInString = String.valueOf(minutes);
        if(hourInString.length() == 1){
            hourInString = "0" + hourInString;
        }
        if(minInString.length() == 1){
            minInString = "0" + minInString;
        }

        return  hourInString + ":" + minInString;
    }

    public void deletePartnerById(String partnerId) {

        HashSet<String> orders = new HashSet<>();
        if(partnerOrderHashMap.containsKey(partnerId)){
            orders = partnerOrderHashMap.get(partnerId);
            for(String order: orders){
                if(orderPartnerHashMap.containsKey(order)){

                    orderPartnerHashMap.remove(order);
                }
            }
            partnerOrderHashMap.remove(partnerId);
        }

        if(deliveryPartnerHashMap.containsKey(partnerId)){
            deliveryPartnerHashMap.remove(partnerId);
        }
    }

    public void deleteOrderById(String orderId) {

        if(orderPartnerHashMap.containsKey(orderId)){
            String partnerId = orderPartnerHashMap.get(orderId);
            HashSet<String> orders = partnerOrderHashMap.get(partnerId);
            orders.remove(orderId);
            partnerOrderHashMap.put(partnerId, orders);

            //change order count of partner
            DeliveryPartner partner = deliveryPartnerHashMap.get(partnerId);
            partner.setNumberOfOrders(orders.size());
        }

        if(orderHashMap.containsKey(orderId)){
            orderHashMap.remove(orderId);
        }
    }


}
