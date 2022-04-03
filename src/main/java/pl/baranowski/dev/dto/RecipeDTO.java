package pl.baranowski.dev.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO {
    private Long originId;
    private String originURL;
    private String imageURL;
    private String title;
    private String summary;
    private List<IngredientDTO> ingredients;
    private List<StepDTO> steps;
}
