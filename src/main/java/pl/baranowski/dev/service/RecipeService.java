package pl.baranowski.dev.service;

import pl.baranowski.dev.model.Recipe;
import pl.baranowski.dev.model.RecipeCard;

import java.util.List;

public interface RecipeService {
    Recipe get(long id);
    List<RecipeCard> find(List<String> include, List<String> exclude);
}