package pl.baranowski.dev.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.baranowski.dev.client.ExternalApiClient;
import pl.baranowski.dev.dto.RecipeDTO;
import pl.baranowski.dev.exception.ExternalApiException;
import pl.baranowski.dev.exception.ResourceParsingException;
import pl.baranowski.dev.model.RecipeCard;

import java.util.Arrays;
import java.util.List;

@Service
public class DefaultExternalRecipeApiService implements RecipeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultExternalRecipeApiService.class);
    private final ExternalApiClient spoonacularApiClient;
    private final ObjectMapper objectMapper;

    public DefaultExternalRecipeApiService(ExternalApiClient spoonacularApiClient,
                                           ObjectMapper objectMapper) {
        this.spoonacularApiClient = spoonacularApiClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public RecipeDTO get(long id) throws ExternalApiException, ResourceParsingException {
        LOGGER.debug("get(id='{}')", id);

        String jsonRecipe = spoonacularApiClient.get(id);
        LOGGER.debug("Received json: {}", jsonRecipe);

        RecipeDTO result;
        result = parse(jsonRecipe, RecipeDTO.class);

        LOGGER.debug("Returning result: {}", result);
        return result;
    }

    @Override
    public List<RecipeCard> find(List<String> include,
                                 List<String> exclude) throws ExternalApiException, ResourceParsingException {
        LOGGER.debug("find(include='{}', exclude='{}')", include, exclude);

        String jsonList = spoonacularApiClient.find(include, exclude);
        LOGGER.debug("Received json: {}", jsonList);

        String results = getResults(jsonList);

        //TODO pytanie: czy jest to eleganckie rozwiązanie? -> get().toString() , a później readValue z tego
        RecipeCard[] cards = parse(results, RecipeCard[].class);

        List<RecipeCard> result = Arrays.asList(cards);
        LOGGER.debug("Returning: {}", result);
        return result;
    }

    private <T> T parse(String json, Class<T> clazz) throws ResourceParsingException {
        LOGGER.debug("Trying to extract {} from: {}", clazz, json);
        T result;
        try {
            result = objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResourceParsingException(e.getMessage());
        }
        LOGGER.debug("Returning: {}", result);
        return result;
    }

    //TODO pytanie: czy nie lepiej zwracać pustą listę? i załatwić to Optionalem?
    private String getResults(String jsonList) throws ResourceParsingException {
        LOGGER.debug("Trying to find results in {}", jsonList);
        try {
            String results = objectMapper.readTree(jsonList).get("results").toString();
            LOGGER.debug("Returning: {}", results);
            return results;
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResourceParsingException("No results.");
        }
    }
}
