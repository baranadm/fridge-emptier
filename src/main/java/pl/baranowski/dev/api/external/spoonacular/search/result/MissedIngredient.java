
package pl.baranowski.dev.api.external.spoonacular.search.result;

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
    "amount",
    "unit",
    "unitLong",
    "unitShort",
    "aisle",
    "name",
    "original",
    "originalName",
    "meta",
    "image"
})
@Generated("jsonschema2pojo")
public class MissedIngredient {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("amount")
    private Double amount;
    @JsonProperty("unit")
    private String unit;
    @JsonProperty("unitLong")
    private String unitLong;
    @JsonProperty("unitShort")
    private String unitShort;
    @JsonProperty("aisle")
    private String aisle;
    @JsonProperty("name")
    private String name;
    @JsonProperty("original")
    private String original;
    @JsonProperty("originalName")
    private String originalName;
    @JsonProperty("meta")
    private List<String> meta = null;
    @JsonProperty("image")
    private String image;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("amount")
    public Double getAmount() {
        return amount;
    }

    @JsonProperty("amount")
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @JsonProperty("unit")
    public String getUnit() {
        return unit;
    }

    @JsonProperty("unit")
    public void setUnit(String unit) {
        this.unit = unit;
    }

    @JsonProperty("unitLong")
    public String getUnitLong() {
        return unitLong;
    }

    @JsonProperty("unitLong")
    public void setUnitLong(String unitLong) {
        this.unitLong = unitLong;
    }

    @JsonProperty("unitShort")
    public String getUnitShort() {
        return unitShort;
    }

    @JsonProperty("unitShort")
    public void setUnitShort(String unitShort) {
        this.unitShort = unitShort;
    }

    @JsonProperty("aisle")
    public String getAisle() {
        return aisle;
    }

    @JsonProperty("aisle")
    public void setAisle(String aisle) {
        this.aisle = aisle;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("original")
    public String getOriginal() {
        return original;
    }

    @JsonProperty("original")
    public void setOriginal(String original) {
        this.original = original;
    }

    @JsonProperty("originalName")
    public String getOriginalName() {
        return originalName;
    }

    @JsonProperty("originalName")
    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    @JsonProperty("meta")
    public List<String> getMeta() {
        return meta;
    }

    @JsonProperty("meta")
    public void setMeta(List<String> meta) {
        this.meta = meta;
    }

    @JsonProperty("image")
    public String getImage() {
        return image;
    }

    @JsonProperty("image")
    public void setImage(String image) {
        this.image = image;
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
