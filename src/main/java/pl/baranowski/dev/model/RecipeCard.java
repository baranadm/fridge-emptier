package pl.baranowski.dev.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.baranowski.dev.deserializer.RecipesCardDeserializer;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = RecipesCardDeserializer.class)
public class RecipeCard {
    private long originId;
    private String title;
    private String imageURL;
    private List<Ingredient> ingredientsToBuy;
}
