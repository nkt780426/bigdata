package dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import models.Product;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDTO {
    private String category;
    private String subCategory;
    private String name;
    private String container;
    private float baseMargin;

    @JsonCreator
    public ProductDTO() {
    }

    public ProductDTO(String category, String subCategory, String name, String container, float baseMargin) {
        this.category = category;
        this.subCategory = subCategory;
        this.name = name;
        this.container = container;
        this.baseMargin = baseMargin;
    }

    // Getters and Setters
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public float getBaseMargin() {
        return baseMargin;
    }

    public void setBaseMargin(float baseMargin) {
        this.baseMargin = baseMargin;
    }

    @Override
    public String toString() {
        return "ProductDTO {" +
            "category='" + category + '\'' +
            ", subCategory='" + subCategory + '\'' +
            ", name='" + name + '\'' +
            ", container='" + container + '\'' +
            ", baseMargin=" + baseMargin +
            '}';
    }

    public Product toProduct() {
        return new Product(category, subCategory, name, container, baseMargin);
    }
}
