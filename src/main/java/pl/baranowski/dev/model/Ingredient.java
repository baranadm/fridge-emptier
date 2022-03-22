package pl.baranowski.dev.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.baranowski.dev.deserializer.IngredientDeserializer;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = IngredientDeserializer.class)
public class Ingredient {
    private String name;
    private Double amount;
    private String unit;
}
