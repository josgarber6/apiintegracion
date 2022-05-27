
package aiss.model.consuming;

import java.util.HashMap;
import java.util.List;
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
    "url",
    "rating",
    "description",
    "products"
})
@Generated("jsonschema2pojo")
public class OnlineShop {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("url")
    private String url;
    @JsonProperty("rating")
    private Integer rating;
    @JsonProperty("description")
    private String description;
    @JsonProperty("products")
    private List<MarketProduct> products = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public OnlineShop() {}
    public OnlineShop(String name, String url, String rating, String description, List<MarketProduct> ls) {
    	
    	this.name = name;
    	this.url = url;
    	this.rating = Integer.valueOf(rating);
    	this.description = description;
    	this.products = ls;
    	
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

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("rating")
    public Integer getRating() {
        return rating;
    }

    @JsonProperty("rating")
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("products")
    public List<MarketProduct> getProducts() {
        return products;
    }

    @JsonProperty("products")
    public void setProducts(List<MarketProduct> products) {
        this.products = products;
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
