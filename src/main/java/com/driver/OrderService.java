package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderReporitory orderReporitory;


    public void addOrder(Order order) {
        orderReporitory.addOrder(order);
    }

    public void addPartner(String partnerId) {
        orderReporitory.addPartner(partnerId);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        orderReporitory.addOrderPartnerPair(orderId,partnerId);
    }

    public Order getOrderById(String orderId) {
        return orderReporitory.getOrderById(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return orderReporitory.getPartnerById(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return orderReporitory.getOrderCountByPartnerId(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return orderReporitory.getOrdersByPartnerId(partnerId);
    }

    public List<String> getAllOrders() {
        return orderReporitory.getAllOrders();
    }

    public Integer getCountOfUnassignedOrders() {
        return orderReporitory.getCountOfUnassignedOrders();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        return orderReporitory.getOrdersLeftAfterGivenTimeByPartnerId(time,partnerId);
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        return orderReporitory.getLastDeliveryTimeByPartnerId(partnerId);
    }

    public void deletePartnerById(String partnerId) {
        orderReporitory.deletePartnerById(partnerId);
    }

    public void deleteOrderById(String orderId) {
        orderReporitory.deleteOrderById(orderId);
    }
}
