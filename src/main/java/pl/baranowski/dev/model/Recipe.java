package pl.baranowski.dev.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.baranowski.dev.deserializer.RecipeDeserializer;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Long originId;
    private String originURL;
    private String imageURL;
    private String title;
    private String summary;
    private List<Ingredient> ingredients;
    private List<Step> instructions;
}
