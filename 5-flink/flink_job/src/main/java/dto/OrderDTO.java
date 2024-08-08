package dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import models.Order;
import models.OrderDetail;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDTO {
    private Long orderId;
    private String orderDate;
    @JsonProperty("customer")
    private CustomerDTO customerDTO;
    @JsonProperty("orderDetails")
    private List<OrderDetailDTO> orderDetailsDTO;

    @JsonCreator
    public OrderDTO() {
    }

    public OrderDTO(Long orderId, String orderDate, CustomerDTO customerDTO, List<OrderDetailDTO> orderDetailsDTO) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerDTO = customerDTO;
        this.orderDetailsDTO = orderDetailsDTO != null ? orderDetailsDTO : Collections.emptyList();
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

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    public List<OrderDetailDTO> getOrderDetailsDTO() {
        return orderDetailsDTO;
    }

    public void setOrderDetailsDTO(List<OrderDetailDTO> orderDetailsDTO) {
        this.orderDetailsDTO = orderDetailsDTO;
    }

    @Override
    public String toString() {
        return "Order {" +
               "orderId=" + orderId +
               ", orderDate='" + orderDate + '\'' +
               ", customer=" + customerDTO +
               ", orderDetails=" + orderDetailsDTO +
               '}';
    }

    public Order toOrder() {
        Order order = new Order(orderId, orderDate, customerDTO.getId());
        return order;
    }

    public List<OrderDetail> toOrderDetails() {
        OrderDetail orderdetail = new OrderDetail();
        orderdetail.setOrderId(this.orderId);
        List<OrderDetail> collections = new ArrayList<OrderDetail>();
        for (OrderDetailDTO element : orderDetailsDTO){
            orderdetail.setOrderId(this.orderId);
            orderdetail.setRowId(element.getRowId());
            orderdetail.setOrderPriority(element.getOrderPriority());
            orderdetail.setDiscount(element.getDiscount());
            orderdetail.setUnitPrice(element.getUnitPrice());
            orderdetail.setShippingCost(element.getShippingCost());
            orderdetail.setShipMode(element.getShipMode());
            orderdetail.setShipDate(element.getShipDate());
            orderdetail.setProductName(element.getProductDTO().getName());
            orderdetail.setProfit(element.getProfit());
            orderdetail.setQuantityOrderedNew(element.getQuantityOrderedNew());
            orderdetail.setSales(element.getSales());
            collections.add(orderdetail);
        }
        return collections;
    }
}
