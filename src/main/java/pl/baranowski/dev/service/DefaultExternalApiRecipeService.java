package pl.baranowski.dev.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.baranowski.dev.client.ExternalApiClient;
import pl.baranowski.dev.dto.RecipeDTO;
import pl.baranowski.dev.exception.ApiException;
import pl.baranowski.dev.model.RecipeCard;

import java.util.Arrays;
import java.util.List;

public class DefaultExternalApiRecipeService implements RecipeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultExternalApiRecipeService.class);
    private final ExternalApiClient spoonacularApiClient;
    private final ObjectMapper objectMapper;

    public DefaultExternalApiRecipeService(ExternalApiClient spoonacularApiClient,
                                           ObjectMapper objectMapper) {
        this.spoonacularApiClient = spoonacularApiClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public RecipeDTO get(long id) throws ApiException {
        LOGGER.debug("get(id='{}')", id);
        String jsonRecipe = spoonacularApiClient.get(id);
        LOGGER.debug("Received json: {}", jsonRecipe);
        try {
            RecipeDTO result = objectMapper.readValue(jsonRecipe, RecipeDTO.class);
            LOGGER.debug("Returning: {}", result);
            return result;
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApiException(e.getMessage());
        }
    }

    @Override
    public List<RecipeCard> find(List<String> include, List<String> exclude) throws ApiException {
        LOGGER.debug("find(include='{}', exclude='{}')", include, exclude);
        String jsonList = spoonacularApiClient.find(include, exclude);
        LOGGER.debug("Received json: {}", jsonList);
        try {
            //TODO pytanie: czy jest to eleganckie rozwiązanie? -> get().toString() , a później readValue z tego
            String results = objectMapper.readTree(jsonList).get("results").toString();

            LOGGER.debug("Starting to read array from string: {}", results);
            RecipeCard[] cards = objectMapper.readValue(results, RecipeCard[].class);

            List<RecipeCard> result = Arrays.asList(cards);
            LOGGER.debug("Returning: {}", result);
            return result;
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApiException(e.getMessage());
        }
    }
}
