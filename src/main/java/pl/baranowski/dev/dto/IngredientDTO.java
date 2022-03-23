package pl.baranowski.dev.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.baranowski.dev.deserializer.IngredientDeserializer;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = IngredientDeserializer.class)
public class IngredientDTO {
    private String name;
    private Double amount;
    private String unit;
}