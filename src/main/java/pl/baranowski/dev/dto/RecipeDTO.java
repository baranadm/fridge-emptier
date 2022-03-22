package pl.baranowski.dev.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.baranowski.dev.deserializer.RecipeDeserializer;
import pl.baranowski.dev.model.Ingredient;
import pl.baranowski.dev.model.Step;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = RecipeDeserializer.class)
public class RecipeDTO {
    private Long originId;
    private String originURL;
    private String imageURL;
    private String title;
    private String summary;
    private List<Ingredient> ingredients;
    private List<Step> instructions;
}
