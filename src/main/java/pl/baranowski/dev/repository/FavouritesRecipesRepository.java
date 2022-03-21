package pl.baranowski.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.baranowski.dev.model.Recipe;

public interface FavouritesRecipesRepository extends JpaRepository<Recipe, Long> {
}
