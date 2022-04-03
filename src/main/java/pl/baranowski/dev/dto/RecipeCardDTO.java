package pl.baranowski.dev.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.baranowski.dev.deserializer.RecipesCardDeserializer;
import pl.baranowski.dev.dto.IngredientDTO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = RecipesCardDeserializer.class)
public class RecipeCardDTO {
    private long originId;
    private String title;
    private String imageURL;
    private List<IngredientDTO> ingredientsToBuy;
}
