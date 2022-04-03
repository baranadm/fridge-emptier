package pl.baranowski.dev.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.baranowski.dev.api.external.spoonacular.AnalyzedInstruction;
import pl.baranowski.dev.api.external.spoonacular.SpoonacularResponse;
import pl.baranowski.dev.api.external.spoonacular.Step;
import pl.baranowski.dev.client.ExternalApiClient;
import pl.baranowski.dev.dto.RecipeCardDTO;
import pl.baranowski.dev.dto.RecipeDTO;
import pl.baranowski.dev.entity.RecipeEntity;
import pl.baranowski.dev.entity.StepEntity;
import pl.baranowski.dev.exception.ExternalApiException;
import pl.baranowski.dev.exception.ResourceParsingException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultExternalRecipeApiService implements RecipeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultExternalRecipeApiService.class);
    private final ExternalApiClient spoonacularApiClient;
    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;

    public DefaultExternalRecipeApiService(ExternalApiClient spoonacularApiClient,
                                           ObjectMapper objectMapper, ModelMapper modelMapper) {
        this.spoonacularApiClient = spoonacularApiClient;
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
        configureModelMapper();
    }

    private void configureModelMapper() {
        this.modelMapper.typeMap(Step.class, StepEntity.class).addMapping(Step::getStep, StepEntity::setDescription);
        this.modelMapper.typeMap(SpoonacularResponse.class, RecipeEntity.class).addMappings(modelMapper -> {
            modelMapper.skip(RecipeEntity::setId);
            modelMapper.map(SpoonacularResponse::getId, RecipeEntity::setOriginId);
            modelMapper.map(SpoonacularResponse::getSourceUrl, RecipeEntity::setOriginURL);
            modelMapper.map(SpoonacularResponse::getImage, RecipeEntity::setImageURL);
            modelMapper.map(SpoonacularResponse::getExtendedIngredients, RecipeEntity::setIngredients);
            Converter<List<AnalyzedInstruction>, List<StepEntity>> stepsConverter =
                    mappingContext -> mappingContext.getSource()
                                                    .get(0) == null ? null : mappingContext.getSource()
                                                                                           .get(0)
                                                                                           .getSteps()
                                                                                           .stream()
                                                                                           .map(step -> this.modelMapper.map(
                                                                                                   step,
                                                                                                   StepEntity.class))
                                                                                           .collect(
                                                                                                   Collectors.toList());
            modelMapper.using(stepsConverter).map(SpoonacularResponse::getAnalyzedInstructions, RecipeEntity::setSteps);
        });
    }

    @Override
    public RecipeDTO get(long id) throws ExternalApiException, ResourceParsingException {
        LOGGER.debug("get(id='{}')", id);

        String jsonRecipe = spoonacularApiClient.get(id);
        LOGGER.debug("Received json: {}", jsonRecipe);

        SpoonacularResponse response = processResponse(jsonRecipe, SpoonacularResponse.class);

        RecipeDTO result = modelMapper.map(response, RecipeDTO.class);
        LOGGER.debug("Returning result: {}", result);
        return result;
    }

    @Override
    public List<RecipeCardDTO> find(List<String> include,
                                    List<String> exclude) throws ExternalApiException, ResourceParsingException {
        LOGGER.debug("find(include='{}', exclude='{}')", include, exclude);

        String jsonList = spoonacularApiClient.find(include, exclude);
        LOGGER.debug("Received json: {}", jsonList);

        String results = getResults(jsonList);

        //TODO pytanie: czy jest to eleganckie rozwiązanie? -> get().toString() , a później readValue z tego
        RecipeCardDTO[] cards = processResponse(results, RecipeCardDTO[].class);

        List<RecipeCardDTO> result = Arrays.asList(cards);
        LOGGER.debug("Returning: {}", result);
        return result;
    }

    private <T> T processResponse(String json, Class<T> clazz) throws ResourceParsingException {
        return parse(json, clazz);
    }

    private <T> T parse(String json, Class<T> clazz) throws ResourceParsingException {
        LOGGER.debug("parse(): Trying to extract {} from: {}", clazz, json);
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
