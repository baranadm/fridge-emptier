
package pl.baranowski.dev.api.external.spoonacular;

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
    "number",
    "step",
    "ingredients",
    "equipment",
    "length"
})
@Generated("jsonschema2pojo")
public class Step {

    @JsonProperty("number")
    private Integer number;
    @JsonProperty("step")
    private String step;
    @JsonProperty("ingredients")
    private List<Ingredient> ingredients = null;
    @JsonProperty("equipment")
    private List<Equipment> equipment = null;
    @JsonProperty("length")
    private Length length;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("number")
    public Integer getNumber() {
        return number;
    }

    @JsonProperty("number")
    public void setNumber(Integer number) {
        this.number = number;
    }

    @JsonProperty("step")
    public String getStep() {
        return step;
    }

    @JsonProperty("step")
    public void setStep(String step) {
        this.step = step;
    }

    @JsonProperty("ingredients")
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    @JsonProperty("ingredients")
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @JsonProperty("equipment")
    public List<Equipment> getEquipment() {
        return equipment;
    }

    @JsonProperty("equipment")
    public void setEquipment(List<Equipment> equipment) {
        this.equipment = equipment;
    }

    @JsonProperty("length")
    public Length getLength() {
        return length;
    }

    @JsonProperty("length")
    public void setLength(Length length) {
        this.length = length;
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
