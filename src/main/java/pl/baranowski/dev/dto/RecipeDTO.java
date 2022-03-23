package pl.baranowski.dev.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.baranowski.dev.deserializer.RecipeDTODeserializer;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = RecipeDTODeserializer.class)
public class RecipeDTO {
    private Long originId;
    private String originURL;
    private String imageURL;
    private String title;
    private String summary;
    private List<IngredientDTO> ingredientDTOS;
    private List<StepDTO> instructions;
}
