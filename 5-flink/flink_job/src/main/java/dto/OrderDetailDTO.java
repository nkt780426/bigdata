package dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDetailDTO {
    private Long rowId;
    private String orderPriority;
    private float discount;
    private float unitPrice;
    private float shippingCost;
    private String shipMode;
    private ProductDTO productDTO;
    private String shipDate;
    private float profit;
    private Long quantityOrderedNew;
    private float sales;

    @JsonCreator
    public OrderDetailDTO() {
    }

    public OrderDetailDTO(Long rowId, String orderPriority, float discount, float unitPrice, float shippingCost, String shipMode, ProductDTO productDTO, String shipDate, float profit, Long quantityOrderedNew, float sales) {
        this.rowId = rowId;
        this.orderPriority = orderPriority;
        this.discount = discount;
        this.unitPrice = unitPrice;
        this.shippingCost = shippingCost;
        this.shipMode = shipMode;
        this.productDTO = productDTO;
        this.shipDate = shipDate;
        this.profit = profit;
        this.quantityOrderedNew = quantityOrderedNew;
        this.sales = sales;
    }

    // Getters and Setters
    public Long getRowId() {
        return rowId;
    }

    public void setRowId(Long rowId) {
        this.rowId = rowId;
    }

    public String getOrderPriority() {
        return orderPriority;
    }

    public void setOrderPriority(String orderPriority) {
        this.orderPriority = orderPriority;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public float getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(float shippingCost) {
        this.shippingCost = shippingCost;
    }

    public String getShipMode() {
        return shipMode;
    }

    public void setShipMode(String shipMode) {
        this.shipMode = shipMode;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProduct(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

    public String getShipDate() {
        return shipDate;
    }

    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }

    public float getProfit() {
        return profit;
    }

    public void setProfit(float profit) {
        this.profit = profit;
    }

    public Long getQuantityOrderedNew() {
        return quantityOrderedNew;
    }

    public void setQuantityOrderedNew(Long quantityOrderedNew) {
        this.quantityOrderedNew = quantityOrderedNew;
    }

    public float getSales() {
        return sales;
    }

    public void setSales(float sales) {
        this.sales = sales;
    }

    @Override
    public String toString() {
        return "OrderDetailDTO{" +
            "rowId=" + rowId +
            ", orderPriority='" + orderPriority + '\'' +
            ", discount=" + discount +
            ", unitPrice=" + unitPrice +
            ", shippingCost=" + shippingCost +
            ", shipMode='" + shipMode + '\'' +
            ", product=" + productDTO +
            ", shipDate='" + shipDate + '\'' +
            ", profit=" + profit +
            ", quantityOrderedNew=" + quantityOrderedNew +
            ", sales=" + sales +
            '}';
    }

}
