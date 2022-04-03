package pl.baranowski.dev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeCardDTO {
    private long originId;
    private String title;
    private String imageURL;
    private List<IngredientDTO> ingredientsToBuy;
}
