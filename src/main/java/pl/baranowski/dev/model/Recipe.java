package pl.baranowski.dev.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.baranowski.dev.dto.IngredientDTO;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Long originId;
    private String originURL;
    private String imageURL;
    private String title;
    private String summary;
    @CollectionTable
    private List<IngredientDTO> ingredientDTOS;
    @CollectionTable
    private List<StepDTO> instructions;
}
