package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonCreator;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {
    private Long id;
    private String name;
    private String segment;
    private String country;
    private String region;
    private String stateOrProvince;
    private String city;
    private Long postalCode;

    @JsonCreator
    public Customer() {
    }

    public Customer(Long id, String name, String segment, String country, String region, String stateOrProvince, String city, Long postalCode) {
        this.id = id;
        this.name = name;
        this.segment = segment;
        this.country = country;
        this.region = region;
        this.stateOrProvince = stateOrProvince;
        this.city = city;
        this.postalCode = postalCode;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getStateOrProvince() {
        return stateOrProvince;
    }

    public void setStateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Long postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return "Customer{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", segment='" + segment + '\'' +
               ", country='" + country + '\'' +
               ", region='" + region + '\'' +
               ", stateOrProvince='" + stateOrProvince + '\'' +
               ", city='" + city + '\'' +
               ", postalCode=" + postalCode +
               '}';
    }
}
