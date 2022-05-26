
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
    "product",
    "price",
    "stock",
    "popularity"
})
@Generated("jsonschema2pojo")
public class MarketProduct {

    @JsonProperty("product")
    private Product product;
    @JsonProperty("price")
    private Double price;
    @JsonProperty("stock")
    private Integer stock;
    @JsonProperty("popularity")
    private Integer popularity;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public MarketProduct() {}
    public MarketProduct(Product gp, String price, String stock, String popularity) {
    	this.product = gp;
    	this.price = Double.valueOf(price);
    	this.stock = Integer.valueOf(stock);
    	this.popularity = Integer.valueOf(popularity);
    }
    
    @JsonProperty("product")
    public Product getProduct() {
        return product;
    }

    @JsonProperty("product")
    public void setProduct(Product product) {
        this.product = product;
    }

    @JsonProperty("price")
    public Double getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(Double price) {
        this.price = price;
    }

    @JsonProperty("stock")
    public Integer getStock() {
        return stock;
    }

    @JsonProperty("stock")
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @JsonProperty("popularity")
    public Integer getPopularity() {
        return popularity;
    }

    @JsonProperty("popularity")
    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
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
