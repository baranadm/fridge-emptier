package pl.baranowski.dev.service;

import pl.baranowski.dev.dto.RecipeDTO;
import pl.baranowski.dev.exception.ExternalApiException;
import pl.baranowski.dev.exception.ResourceParsingException;
import pl.baranowski.dev.dto.RecipeCardDTO;

import java.util.List;

public interface RecipeService {
    RecipeDTO get(long id) throws ExternalApiException, ResourceParsingException;
    List<RecipeCardDTO> find(List<String> include, List<String> exclude) throws ExternalApiException, ResourceParsingException;
}