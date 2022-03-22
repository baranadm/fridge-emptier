package pl.baranowski.dev.service;

import pl.baranowski.dev.dto.RecipeDTO;
import pl.baranowski.dev.exception.ApiException;
import pl.baranowski.dev.exception.ExternalApiException;
import pl.baranowski.dev.model.Recipe;
import pl.baranowski.dev.model.RecipeCard;

import java.util.List;

public interface RecipeService {
    RecipeDTO get(long id) throws ApiException;
    List<RecipeCard> find(List<String> include, List<String> exclude) throws ApiException;
}