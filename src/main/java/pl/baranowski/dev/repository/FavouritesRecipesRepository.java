package pl.baranowski.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.baranowski.dev.entity.RecipeEntity;

public interface FavouritesRecipesRepository extends JpaRepository<RecipeEntity, Long> {
}
