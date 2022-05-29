package aiss.model.consuming;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "averagePopularity",
    "availability",
    "totalStock",
    "category",
    "averagePrice"
})
@Generated("jsonschema2pojo")
public class Product {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("averagePopularity")
    private Double averagePopularity;
    @JsonProperty("availability")
    private Integer availability;
    @JsonProperty("totalStock")
    private Integer totalStock;
    @JsonProperty("category")
    private String category;
    @JsonProperty("averagePrice")
    private Double averagePrice;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Product() {}
    
    public Product(String name, String type) {
    	this.name = name;
    	this.category = type;
    }
    
    public Product(String id, String name, String averagePopularity, String availability, String totalStock, String category, String averagePrice) {
    	this.id = id;
    	this.name = name;
    	this.averagePopularity = Double.valueOf(averagePopularity);
    	this.availability = Integer.valueOf(availability);
    	this.totalStock = Integer.valueOf(totalStock);
    	this.category = category;
    	this.averagePrice = Double.valueOf(averagePrice);
    }
    
    public Product(String name, String averagePopularity, String availability, String totalStock, String category, String averagePrice) {
    	this.name = name;
    	this.averagePopularity = Double.valueOf(averagePopularity);
    	this.availability = Integer.valueOf(availability);
    	this.totalStock = Integer.valueOf(totalStock);
    	this.category = category;
    	this.averagePrice = Double.valueOf(averagePrice);
    }
    
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("averagePopularity")
    public Double getAveragePopularity() {
        return averagePopularity;
    }

    @JsonProperty("averagePopularity")
    public void setAveragePopularity(Double averagePopularity) {
        this.averagePopularity = averagePopularity;
    }

    @JsonProperty("availability")
    public Integer getAvailability() {
        return availability;
    }

    @JsonProperty("availability")
    public void setAvailability(Integer availability) {
        this.availability = availability;
    }

    @JsonProperty("totalStock")
    public Integer getTotalStock() {
        return totalStock;
    }

    @JsonProperty("totalStock")
    public void setTotalStock(Integer totalStock) {
        this.totalStock = totalStock;
    }

    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    @JsonProperty("category")
    public void setCategory(String category) {
        this.category = category;
    }

    @JsonProperty("averagePrice")
    public Double getAveragePrice() {
        return averagePrice;
    }

    @JsonProperty("averagePrice")
    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
