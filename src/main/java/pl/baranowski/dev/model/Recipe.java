package pl.baranowski.dev.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.baranowski.dev.entity.Ingredient;
import pl.baranowski.dev.entity.Step;

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
    @OneToMany(mappedBy = "recipe")
    private List<Ingredient> ingredient;
    @OneToMany(mappedBy = "recipe")
    private List<Step> steps;
}
