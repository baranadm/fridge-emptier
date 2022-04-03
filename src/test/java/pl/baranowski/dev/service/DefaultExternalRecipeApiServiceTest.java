package pl.baranowski.dev.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.baranowski.dev.api.external.spoonacular.AnalyzedInstruction;
import pl.baranowski.dev.api.external.spoonacular.ExtendedIngredient;
import pl.baranowski.dev.api.external.spoonacular.Recipe;
import pl.baranowski.dev.api.external.spoonacular.Step;
import pl.baranowski.dev.api.external.spoonacular.search.result.MissedIngredient;
import pl.baranowski.dev.api.external.spoonacular.search.result.Result;
import pl.baranowski.dev.client.SpoonacularApiClient;
import pl.baranowski.dev.dto.IngredientDTO;
import pl.baranowski.dev.dto.RecipeCardDTO;
import pl.baranowski.dev.dto.RecipeDTO;
import pl.baranowski.dev.dto.StepDTO;
import pl.baranowski.dev.exception.ApiException;
import pl.baranowski.dev.exception.ExternalApiException;
import pl.baranowski.dev.exception.ResourceParsingException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {DefaultExternalRecipeApiService.class, ObjectMapper.class, ModelMapper.class})
class DefaultExternalRecipeApiServiceTest {
    @Autowired
    DefaultExternalRecipeApiService underTest;
    @MockBean
    SpoonacularApiClient spoonacularApiClient;


    @Test
    void get_whenApiClientReturnsRecipe_returnsListOfRecipeCards() throws ExternalApiException, ResourceParsingException {
        //given
        ExtendedIngredient onion = new ExtendedIngredient();
        onion.setName("Onion");
        onion.setAmount(4.0);
        onion.setUnit("quarters");

        Step step = new Step();
        step.setNumber(1);
        step.setStep("Eat every quarter");

        AnalyzedInstruction instructions = new AnalyzedInstruction();
        instructions.setSteps(List.of(step));

        Recipe recipe = new Recipe();
        recipe.setId(34);
        recipe.setTitle("Tasty 30min recipe");
        recipe.setSummary("30 minut recipe (+2h of washing dishes)");
        recipe.setImage("filtered.image.url");
        recipe.setSourceUrl("source.url");
        recipe.setExtendedIngredients(List.of(onion));
        recipe.setAnalyzedInstructions(List.of(instructions));
        when(spoonacularApiClient.get(34L)).thenReturn(recipe);
        //when
        RecipeDTO result = underTest.get(34L);
        //then
        RecipeDTO expected = new RecipeDTO(34L,
                                           recipe.getSourceUrl(),
                                           recipe.getImage(),
                                           recipe.getTitle(),
                                           recipe.getSummary(),
                                           List.of(new IngredientDTO("Onion", 4.0, "quarters")),
                                           List.of(new StepDTO(
                                                   step.getNumber(), step.getStep())));
        assertEquals(expected, result);
    }

    @Test
    void get_whenClientThrowsResourceParsingException_thenThrowsSame() throws ExternalApiException, ResourceParsingException {
        when(spoonacularApiClient.get(45L)).thenThrow(ResourceParsingException.class);

        assertThrows(ResourceParsingException.class, () -> underTest.get(45L));
    }

    @Test
    void get_whenExternalApiThrows_thenThrows() throws ExternalApiException, ResourceParsingException {
        //given
        //when
        when(spoonacularApiClient.get(1L)).thenThrow(ExternalApiException.class);
        //then
        assertThrows(ExternalApiException.class, () -> underTest.get(1L));
    }

    @Test
    void find_whenApiClientReturnsResults_returnsListOfRecipeCards() throws ApiException {
        //given
        List<String> include = List.of("tomato");
        List<String> exclude = List.of("onion", "strawberry");

        MissedIngredient tomato = new MissedIngredient();
        tomato.setId(1);
        tomato.setName("Tomato");
        tomato.setAmount(3.0);
        tomato.setUnit("-");

        Result searchResult = new Result();
        searchResult.setId(1234);
        searchResult.setImage("image.url");
        searchResult.setTitle("Title title");
        searchResult.setMissedIngredients(List.of(tomato));

        List<Result> results = Collections.nCopies(3, searchResult);

        //when
        when(spoonacularApiClient.find(include, exclude)).thenReturn(results);
        List<RecipeCardDTO> result = underTest.find(include, exclude);

        //then
        List<RecipeCardDTO> expected = Collections.nCopies(3,
                                                           new RecipeCardDTO(1234L,
                                                                             "Title title",
                                                                             "image.url",
                                                                             List.of(new IngredientDTO("Tomato",
                                                                                                       3.0,
                                                                                                       "-"))));
        assertEquals(expected, result);
    }

    @Test
    void find_whenClientThrowsResourceParsingException_thenThrowsSame() throws ExternalApiException, ResourceParsingException {
        //given
        List<String> include = Collections.singletonList("banana");
        List<String> exclude = Collections.singletonList("montana");
        //when
        when(spoonacularApiClient.find(include, exclude)).thenThrow(ResourceParsingException.class);
        //then
        assertThrows(ResourceParsingException.class, () -> underTest.find(include, exclude));
    }

    @Test
    void find_whenExternalApiThrows_thenThrows() throws ApiException {
        //given
        List<String> include = Collections.singletonList("banana");
        List<String> exclude = Collections.singletonList("montana");
        //when
        when(spoonacularApiClient.find(include, exclude)).thenThrow(ExternalApiException.class);
        //then
        assertThrows(ExternalApiException.class, () -> underTest.find(include, exclude));
    }
}