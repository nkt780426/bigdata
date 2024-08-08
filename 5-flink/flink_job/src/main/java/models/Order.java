package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonCreator;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
    private Long orderId;
    private String orderDate;
    private Long customerID;

    @JsonCreator
    public Order() {
    }

    public Order(Long orderId, String orderDate, Long customerID) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerID = customerID;
    }

    // Getters and Setters
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    @Override
    public String toString() {
        return "Order{" +
               "orderId=" + orderId +
               ", orderDate='" + orderDate + '\'' +
               ", customerID=" + customerID +
               '}';
    }
}
