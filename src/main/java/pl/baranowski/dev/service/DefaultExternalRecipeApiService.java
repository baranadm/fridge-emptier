package pl.baranowski.dev.service;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.baranowski.dev.api.external.spoonacular.AnalyzedInstruction;
import pl.baranowski.dev.api.external.spoonacular.Recipe;
import pl.baranowski.dev.api.external.spoonacular.Step;
import pl.baranowski.dev.api.external.spoonacular.search.result.Result;
import pl.baranowski.dev.client.SpoonacularApiClient;
import pl.baranowski.dev.dto.RecipeCardDTO;
import pl.baranowski.dev.dto.RecipeDTO;
import pl.baranowski.dev.dto.StepDTO;
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
    private final SpoonacularApiClient spoonacularApiClient;
    private final ModelMapper modelMapper;

    public DefaultExternalRecipeApiService(SpoonacularApiClient spoonacularApiClient, ModelMapper modelMapper) {
        this.spoonacularApiClient = spoonacularApiClient;
        this.modelMapper = modelMapper;
        configureModelMapper();
    }

    private void configureModelMapper() {
        this.modelMapper.typeMap(Step.class, StepEntity.class).addMapping(Step::getStep, StepEntity::setDescription);
        this.modelMapper.typeMap(Step.class, StepDTO.class).addMapping(Step::getStep, StepDTO::setDescription);
        this.modelMapper.typeMap(Recipe.class, RecipeEntity.class).addMappings(modelMapper -> {
            modelMapper.skip(RecipeEntity::setId);
            modelMapper.map(Recipe::getId, RecipeEntity::setOriginId);
            modelMapper.map(Recipe::getSourceUrl, RecipeEntity::setOriginURL);
            modelMapper.map(Recipe::getImage, RecipeEntity::setImageURL);
            modelMapper.map(Recipe::getExtendedIngredients, RecipeEntity::setIngredients);
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
            modelMapper.using(stepsConverter).map(Recipe::getAnalyzedInstructions, RecipeEntity::setSteps);
        });
        this.modelMapper.typeMap(Recipe.class, RecipeDTO.class).addMappings(modelMapper -> {
            modelMapper.map(Recipe::getId, RecipeDTO::setOriginId);
            modelMapper.map(Recipe::getSourceUrl, RecipeDTO::setOriginURL);
            modelMapper.map(Recipe::getImage, RecipeDTO::setImageURL);
            Converter<List<AnalyzedInstruction>, List<StepDTO>> stepsConverter =
                    mappingContext -> mappingContext.getSource()
                                                    .get(0) == null ? null : mappingContext.getSource()
                                                                                           .get(0)
                                                                                           .getSteps()
                                                                                           .stream()
                                                                                           .map(step -> this.modelMapper.map(
                                                                                                   step,
                                                                                                   StepDTO.class))
                                                                                           .collect(
                                                                                                   Collectors.toList());
            modelMapper.using(stepsConverter).map(Recipe::getAnalyzedInstructions, RecipeDTO::setSteps);
        });
        this.modelMapper.typeMap(Result.class, RecipeCardDTO.class).addMappings(modelMapper -> {
            modelMapper.map(Result::getId, RecipeCardDTO::setOriginId);
            modelMapper.map(Result::getImage, RecipeCardDTO::setImageURL);
            modelMapper.map(Result::getMissedIngredients, RecipeCardDTO::setIngredientsToBuy);
        });
    }

    @Override
    public RecipeDTO get(long id) throws ExternalApiException, ResourceParsingException {
        LOGGER.debug("get(id='{}')", id);

        Recipe recipe = spoonacularApiClient.get(id);
        LOGGER.debug("Received recipe: {}", recipe);

        RecipeDTO result = modelMapper.map(recipe, RecipeDTO.class);
        LOGGER.debug("Returning result: {}", result);
        return result;
    }

    @Override
    public List<RecipeCardDTO> find(List<String> include,
                                    List<String> exclude) throws ExternalApiException, ResourceParsingException {
        LOGGER.debug("find(include='{}', exclude='{}')", include, exclude);

        List<Result> searchResult = spoonacularApiClient.find(include, exclude);
        LOGGER.debug("Received search result: {}", searchResult);

        List<RecipeCardDTO> result = Arrays.asList(modelMapper.map(searchResult, RecipeCardDTO[].class));
        LOGGER.debug("Returning list of RecipeCards: {}", result);
        return result;
    }

}
