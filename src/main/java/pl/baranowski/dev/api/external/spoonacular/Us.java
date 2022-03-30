
package pl.baranowski.dev.api.external.spoonacular;

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
    "amount",
    "unitShort",
    "unitLong"
})
@Generated("jsonschema2pojo")
public class Us {

    @JsonProperty("amount")
    private Double amount;
    @JsonProperty("unitShort")
    private String unitShort;
    @JsonProperty("unitLong")
    private String unitLong;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("amount")
    public Double getAmount() {
        return amount;
    }

    @JsonProperty("amount")
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @JsonProperty("unitShort")
    public String getUnitShort() {
        return unitShort;
    }

    @JsonProperty("unitShort")
    public void setUnitShort(String unitShort) {
        this.unitShort = unitShort;
    }

    @JsonProperty("unitLong")
    public String getUnitLong() {
        return unitLong;
    }

    @JsonProperty("unitLong")
    public void setUnitLong(String unitLong) {
        this.unitLong = unitLong;
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
