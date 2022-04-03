package pl.baranowski.dev.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RecipeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Long originId;
    private String originURL;
    private String imageURL;
    private String title;
    private String summary;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "recipe_ingredient_mapping", joinColumns = @JoinColumn(name="recipe_id"), inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private List<IngredientEntity> ingredients;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "recipe_step_mapping", joinColumns = @JoinColumn(name="recipe_id"), inverseJoinColumns = @JoinColumn(name = "step_id"))
    private List<StepEntity> steps;
}
