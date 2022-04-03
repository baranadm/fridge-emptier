
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
    "title",
    "image",
    "imageType",
    "usedIngredientCount",
    "missedIngredientCount",
    "missedIngredients",
    "usedIngredients",
    "unusedIngredients",
    "likes"
})
@Generated("jsonschema2pojo")
public class SearhResultEntry {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("image")
    private String image;
    @JsonProperty("imageType")
    private String imageType;
    @JsonProperty("usedIngredientCount")
    private Integer usedIngredientCount;
    @JsonProperty("missedIngredientCount")
    private Integer missedIngredientCount;
    @JsonProperty("missedIngredients")
    private List<MissedIngredient> missedIngredients = null;
    @JsonProperty("usedIngredients")
    private List<UsedIngredient> usedIngredients = null;
    @JsonProperty("unusedIngredients")
    private List<Object> unusedIngredients = null;
    @JsonProperty("likes")
    private Integer likes;
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

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("image")
    public String getImage() {
        return image;
    }

    @JsonProperty("image")
    public void setImage(String image) {
        this.image = image;
    }

    @JsonProperty("imageType")
    public String getImageType() {
        return imageType;
    }

    @JsonProperty("imageType")
    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    @JsonProperty("usedIngredientCount")
    public Integer getUsedIngredientCount() {
        return usedIngredientCount;
    }

    @JsonProperty("usedIngredientCount")
    public void setUsedIngredientCount(Integer usedIngredientCount) {
        this.usedIngredientCount = usedIngredientCount;
    }

    @JsonProperty("missedIngredientCount")
    public Integer getMissedIngredientCount() {
        return missedIngredientCount;
    }

    @JsonProperty("missedIngredientCount")
    public void setMissedIngredientCount(Integer missedIngredientCount) {
        this.missedIngredientCount = missedIngredientCount;
    }

    @JsonProperty("missedIngredients")
    public List<MissedIngredient> getMissedIngredients() {
        return missedIngredients;
    }

    @JsonProperty("missedIngredients")
    public void setMissedIngredients(List<MissedIngredient> missedIngredients) {
        this.missedIngredients = missedIngredients;
    }

    @JsonProperty("usedIngredients")
    public List<UsedIngredient> getUsedIngredients() {
        return usedIngredients;
    }

    @JsonProperty("usedIngredients")
    public void setUsedIngredients(List<UsedIngredient> usedIngredients) {
        this.usedIngredients = usedIngredients;
    }

    @JsonProperty("unusedIngredients")
    public List<Object> getUnusedIngredients() {
        return unusedIngredients;
    }

    @JsonProperty("unusedIngredients")
    public void setUnusedIngredients(List<Object> unusedIngredients) {
        this.unusedIngredients = unusedIngredients;
    }

    @JsonProperty("likes")
    public Integer getLikes() {
        return likes;
    }

    @JsonProperty("likes")
    public void setLikes(Integer likes) {
        this.likes = likes;
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
