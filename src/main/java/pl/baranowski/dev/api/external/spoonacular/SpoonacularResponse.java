
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
    "vegetarian",
    "vegan",
    "glutenFree",
    "dairyFree",
    "veryHealthy",
    "cheap",
    "veryPopular",
    "sustainable",
    "weightWatcherSmartPoints",
    "gaps",
    "lowFodmap",
    "aggregateLikes",
    "spoonacularScore",
    "healthScore",
    "creditsText",
    "license",
    "sourceName",
    "pricePerServing",
    "extendedIngredients",
    "id",
    "title",
    "readyInMinutes",
    "servings",
    "sourceUrl",
    "image",
    "imageType",
    "summary",
    "cuisines",
    "dishTypes",
    "diets",
    "occasions",
    "winePairing",
    "instructions",
    "analyzedInstructions",
    "originalId",
    "spoonacularSourceUrl"
})
@Generated("jsonschema2pojo")
public class SpoonacularResponse {

    @JsonProperty("vegetarian")
    private Boolean vegetarian;
    @JsonProperty("vegan")
    private Boolean vegan;
    @JsonProperty("glutenFree")
    private Boolean glutenFree;
    @JsonProperty("dairyFree")
    private Boolean dairyFree;
    @JsonProperty("veryHealthy")
    private Boolean veryHealthy;
    @JsonProperty("cheap")
    private Boolean cheap;
    @JsonProperty("veryPopular")
    private Boolean veryPopular;
    @JsonProperty("sustainable")
    private Boolean sustainable;
    @JsonProperty("weightWatcherSmartPoints")
    private Integer weightWatcherSmartPoints;
    @JsonProperty("gaps")
    private String gaps;
    @JsonProperty("lowFodmap")
    private Boolean lowFodmap;
    @JsonProperty("aggregateLikes")
    private Integer aggregateLikes;
    @JsonProperty("spoonacularScore")
    private Double spoonacularScore;
    @JsonProperty("healthScore")
    private Double healthScore;
    @JsonProperty("creditsText")
    private String creditsText;
    @JsonProperty("license")
    private String license;
    @JsonProperty("sourceName")
    private String sourceName;
    @JsonProperty("pricePerServing")
    private Double pricePerServing;
    @JsonProperty("extendedIngredients")
    private List<ExtendedIngredient> extendedIngredients = null;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("readyInMinutes")
    private Integer readyInMinutes;
    @JsonProperty("servings")
    private Integer servings;
    @JsonProperty("sourceUrl")
    private String sourceUrl;
    @JsonProperty("image")
    private String image;
    @JsonProperty("imageType")
    private String imageType;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("cuisines")
    private List<Object> cuisines = null;
    @JsonProperty("dishTypes")
    private List<String> dishTypes = null;
    @JsonProperty("diets")
    private List<String> diets = null;
    @JsonProperty("occasions")
    private List<String> occasions = null;
    @JsonProperty("winePairing")
    private WinePairing winePairing;
    @JsonProperty("instructions")
    private String instructions;
    @JsonProperty("analyzedInstructions")
    private List<AnalyzedInstruction> analyzedInstructions = null;
    @JsonProperty("originalId")
    private Object originalId;
    @JsonProperty("spoonacularSourceUrl")
    private String spoonacularSourceUrl;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("vegetarian")
    public Boolean getVegetarian() {
        return vegetarian;
    }

    @JsonProperty("vegetarian")
    public void setVegetarian(Boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    @JsonProperty("vegan")
    public Boolean getVegan() {
        return vegan;
    }

    @JsonProperty("vegan")
    public void setVegan(Boolean vegan) {
        this.vegan = vegan;
    }

    @JsonProperty("glutenFree")
    public Boolean getGlutenFree() {
        return glutenFree;
    }

    @JsonProperty("glutenFree")
    public void setGlutenFree(Boolean glutenFree) {
        this.glutenFree = glutenFree;
    }

    @JsonProperty("dairyFree")
    public Boolean getDairyFree() {
        return dairyFree;
    }

    @JsonProperty("dairyFree")
    public void setDairyFree(Boolean dairyFree) {
        this.dairyFree = dairyFree;
    }

    @JsonProperty("veryHealthy")
    public Boolean getVeryHealthy() {
        return veryHealthy;
    }

    @JsonProperty("veryHealthy")
    public void setVeryHealthy(Boolean veryHealthy) {
        this.veryHealthy = veryHealthy;
    }

    @JsonProperty("cheap")
    public Boolean getCheap() {
        return cheap;
    }

    @JsonProperty("cheap")
    public void setCheap(Boolean cheap) {
        this.cheap = cheap;
    }

    @JsonProperty("veryPopular")
    public Boolean getVeryPopular() {
        return veryPopular;
    }

    @JsonProperty("veryPopular")
    public void setVeryPopular(Boolean veryPopular) {
        this.veryPopular = veryPopular;
    }

    @JsonProperty("sustainable")
    public Boolean getSustainable() {
        return sustainable;
    }

    @JsonProperty("sustainable")
    public void setSustainable(Boolean sustainable) {
        this.sustainable = sustainable;
    }

    @JsonProperty("weightWatcherSmartPoints")
    public Integer getWeightWatcherSmartPoints() {
        return weightWatcherSmartPoints;
    }

    @JsonProperty("weightWatcherSmartPoints")
    public void setWeightWatcherSmartPoints(Integer weightWatcherSmartPoints) {
        this.weightWatcherSmartPoints = weightWatcherSmartPoints;
    }

    @JsonProperty("gaps")
    public String getGaps() {
        return gaps;
    }

    @JsonProperty("gaps")
    public void setGaps(String gaps) {
        this.gaps = gaps;
    }

    @JsonProperty("lowFodmap")
    public Boolean getLowFodmap() {
        return lowFodmap;
    }

    @JsonProperty("lowFodmap")
    public void setLowFodmap(Boolean lowFodmap) {
        this.lowFodmap = lowFodmap;
    }

    @JsonProperty("aggregateLikes")
    public Integer getAggregateLikes() {
        return aggregateLikes;
    }

    @JsonProperty("aggregateLikes")
    public void setAggregateLikes(Integer aggregateLikes) {
        this.aggregateLikes = aggregateLikes;
    }

    @JsonProperty("spoonacularScore")
    public Double getSpoonacularScore() {
        return spoonacularScore;
    }

    @JsonProperty("spoonacularScore")
    public void setSpoonacularScore(Double spoonacularScore) {
        this.spoonacularScore = spoonacularScore;
    }

    @JsonProperty("healthScore")
    public Double getHealthScore() {
        return healthScore;
    }

    @JsonProperty("healthScore")
    public void setHealthScore(Double healthScore) {
        this.healthScore = healthScore;
    }

    @JsonProperty("creditsText")
    public String getCreditsText() {
        return creditsText;
    }

    @JsonProperty("creditsText")
    public void setCreditsText(String creditsText) {
        this.creditsText = creditsText;
    }

    @JsonProperty("license")
    public String getLicense() {
        return license;
    }

    @JsonProperty("license")
    public void setLicense(String license) {
        this.license = license;
    }

    @JsonProperty("sourceName")
    public String getSourceName() {
        return sourceName;
    }

    @JsonProperty("sourceName")
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    @JsonProperty("pricePerServing")
    public Double getPricePerServing() {
        return pricePerServing;
    }

    @JsonProperty("pricePerServing")
    public void setPricePerServing(Double pricePerServing) {
        this.pricePerServing = pricePerServing;
    }

    @JsonProperty("extendedIngredients")
    public List<ExtendedIngredient> getExtendedIngredients() {
        return extendedIngredients;
    }

    @JsonProperty("extendedIngredients")
    public void setExtendedIngredients(List<ExtendedIngredient> extendedIngredients) {
        this.extendedIngredients = extendedIngredients;
    }

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

    @JsonProperty("readyInMinutes")
    public Integer getReadyInMinutes() {
        return readyInMinutes;
    }

    @JsonProperty("readyInMinutes")
    public void setReadyInMinutes(Integer readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }

    @JsonProperty("servings")
    public Integer getServings() {
        return servings;
    }

    @JsonProperty("servings")
    public void setServings(Integer servings) {
        this.servings = servings;
    }

    @JsonProperty("sourceUrl")
    public String getSourceUrl() {
        return sourceUrl;
    }

    @JsonProperty("sourceUrl")
    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
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

    @JsonProperty("summary")
    public String getSummary() {
        return summary;
    }

    @JsonProperty("summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    @JsonProperty("cuisines")
    public List<Object> getCuisines() {
        return cuisines;
    }

    @JsonProperty("cuisines")
    public void setCuisines(List<Object> cuisines) {
        this.cuisines = cuisines;
    }

    @JsonProperty("dishTypes")
    public List<String> getDishTypes() {
        return dishTypes;
    }

    @JsonProperty("dishTypes")
    public void setDishTypes(List<String> dishTypes) {
        this.dishTypes = dishTypes;
    }

    @JsonProperty("diets")
    public List<String> getDiets() {
        return diets;
    }

    @JsonProperty("diets")
    public void setDiets(List<String> diets) {
        this.diets = diets;
    }

    @JsonProperty("occasions")
    public List<String> getOccasions() {
        return occasions;
    }

    @JsonProperty("occasions")
    public void setOccasions(List<String> occasions) {
        this.occasions = occasions;
    }

    @JsonProperty("winePairing")
    public WinePairing getWinePairing() {
        return winePairing;
    }

    @JsonProperty("winePairing")
    public void setWinePairing(WinePairing winePairing) {
        this.winePairing = winePairing;
    }

    @JsonProperty("instructions")
    public String getInstructions() {
        return instructions;
    }

    @JsonProperty("instructions")
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @JsonProperty("analyzedInstructions")
    public List<AnalyzedInstruction> getAnalyzedInstructions() {
        return analyzedInstructions;
    }

    @JsonProperty("analyzedInstructions")
    public void setAnalyzedInstructions(List<AnalyzedInstruction> analyzedInstructions) {
        this.analyzedInstructions = analyzedInstructions;
    }

    @JsonProperty("originalId")
    public Object getOriginalId() {
        return originalId;
    }

    @JsonProperty("originalId")
    public void setOriginalId(Object originalId) {
        this.originalId = originalId;
    }

    @JsonProperty("spoonacularSourceUrl")
    public String getSpoonacularSourceUrl() {
        return spoonacularSourceUrl;
    }

    @JsonProperty("spoonacularSourceUrl")
    public void setSpoonacularSourceUrl(String spoonacularSourceUrl) {
        this.spoonacularSourceUrl = spoonacularSourceUrl;
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
