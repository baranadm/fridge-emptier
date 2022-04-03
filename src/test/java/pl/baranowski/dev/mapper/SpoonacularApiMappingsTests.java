package pl.baranowski.dev.mapper;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.baranowski.dev.api.external.spoonacular.AnalyzedInstruction;
import pl.baranowski.dev.api.external.spoonacular.ExtendedIngredient;
import pl.baranowski.dev.api.external.spoonacular.SpoonacularResponse;
import pl.baranowski.dev.api.external.spoonacular.Step;
import pl.baranowski.dev.api.external.spoonacular.search.result.MissedIngredient;
import pl.baranowski.dev.api.external.spoonacular.search.result.SearhResultEntry;
import pl.baranowski.dev.dto.IngredientDTO;
import pl.baranowski.dev.dto.RecipeCardDTO;
import pl.baranowski.dev.dto.RecipeDTO;
import pl.baranowski.dev.dto.StepDTO;
import pl.baranowski.dev.entity.IngredientEntity;
import pl.baranowski.dev.entity.RecipeEntity;
import pl.baranowski.dev.entity.StepEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SpoonacularApiMappingsTests {
    @Autowired
    ModelMapper underTest;

    @Test
    void extendedIngredient_to_ingredientEntity() {
        //given
        ExtendedIngredient extendedIngredient = new ExtendedIngredient();
        extendedIngredient.setName("Broccoli");
        extendedIngredient.setAmount(2.0);
        extendedIngredient.setUnit("lbs");

        IngredientEntity expected = new IngredientEntity();
        expected.setName("Broccoli");
        expected.setAmount(2.0);
        expected.setUnit("lbs");

        //when
        IngredientEntity result = underTest.map(extendedIngredient, IngredientEntity.class);

        //then
        assertEquals(expected, result);
    }

    @Test
    void spoonacularStep_to_stepEntity() {
        //given
        Step spoonacularStep = new Step();
        spoonacularStep.setNumber(12);
        spoonacularStep.setStep("do whatever you want");

        StepEntity expected = new StepEntity();
        expected.setNumber(12);
        expected.setDescription(spoonacularStep.getStep());

        //when
        StepEntity result = underTest.map(spoonacularStep, StepEntity.class);

        //then
        assertEquals(expected, result);
    }

    @Test
    void spoonacularResponse_to_responseEntity() {
        //given
        ExtendedIngredient spoonacularIngredient = new ExtendedIngredient();
        spoonacularIngredient.setName("Cabbage");
        spoonacularIngredient.setAmount(1.5);
        spoonacularIngredient.setUnit("UK ton");
        List<ExtendedIngredient> spoonacularIngredients = new ArrayList<>(Collections.nCopies(2,
                                                                                              spoonacularIngredient));

        Step spoonacularStep = new Step();
        spoonacularStep.setNumber(1);
        spoonacularStep.setStep("Cut whole cabbage in a half and put it on a plate");

        AnalyzedInstruction spoonacularInstruction = new AnalyzedInstruction();
        spoonacularInstruction.setSteps(Collections.singletonList(spoonacularStep));

        SpoonacularResponse spoonacularResponse = new SpoonacularResponse();
        spoonacularResponse.setId(1234);
        spoonacularResponse.setSourceUrl("source.url");
        spoonacularResponse.setImage("image.url");
        spoonacularResponse.setTitle("Cabbage snack");
        spoonacularResponse.setSummary("Enormously big plate of cabbage.");
        spoonacularResponse.setExtendedIngredients(spoonacularIngredients);
        spoonacularResponse.setAnalyzedInstructions(Collections.singletonList(spoonacularInstruction));

        RecipeEntity expected = new RecipeEntity(null,
                                                 spoonacularResponse.getId().longValue(),
                                                 spoonacularResponse.getSourceUrl(),
                                                 spoonacularResponse.getImage(),
                                                 spoonacularResponse.getTitle(),
                                                 spoonacularResponse.getSummary(),
                                                 Collections.nCopies(2,
                                                                     new IngredientEntity(0,
                                                                                          spoonacularIngredient.getName(),
                                                                                          spoonacularIngredient.getAmount(),
                                                                                          spoonacularIngredient.getUnit())),
                                                 Collections.singletonList(new StepEntity(0,
                                                                                          spoonacularStep.getNumber(),
                                                                                          spoonacularStep.getStep())));
        //when
        RecipeEntity result = underTest.map(spoonacularResponse, RecipeEntity.class);

        //then
        assertEquals(expected, result);
    }

    @Test
    void ingredientEntity_to_ingredientDTO() {
        //given
        IngredientEntity ingredientEntity = new IngredientEntity(15L, "Coffee", 4.0, "tbsp");
        IngredientDTO expected = new IngredientDTO("Coffee", 4.0, "tbsp");
        //when
        IngredientDTO result = underTest.map(ingredientEntity, IngredientDTO.class);
        //then
        assertEquals(expected, result);
    }

    @Test
    void spoonacularSearchResultEntry_to_RecipeCardDTO() {
        //given
        SearhResultEntry searchResultEntry = new SearhResultEntry();
        searchResultEntry.setId(45);
        searchResultEntry.setTitle("Pretty spicy chicken with french fries");
        searchResultEntry.setImage("image.url");
        MissedIngredient chicken = new MissedIngredient();
        chicken.setName("Chicken");
        chicken.setAmount(2.0);
        chicken.setUnit("lbs");
        MissedIngredient chilli = new MissedIngredient();
        chilli.setName("Chilli");
        chilli.setAmount(10.0);
        chilli.setUnit("tbsp");
        searchResultEntry.setMissedIngredients(Arrays.asList(chicken, chilli));

        RecipeCardDTO expected = new RecipeCardDTO(45L, searchResultEntry.getTitle(), searchResultEntry.getImage(),
                                                   Arrays.asList(new IngredientDTO(chicken.getName(),
                                                                                   chicken.getAmount(),
                                                                                   chicken.getUnit()),
                                                                 new IngredientDTO(chilli.getName(),
                                                                                   chilli.getAmount(),
                                                                                   chilli.getUnit())));
        //when
        RecipeCardDTO result = underTest.map(searchResultEntry, RecipeCardDTO.class);

        //then
        assertEquals(expected, result);
    }

    @Test
    void stepEntity_to_stepDTO() {
        //given
        StepEntity stepEntity = new StepEntity(145L, 11, "Mix everything");
        StepDTO expected = new StepDTO(stepEntity.getNumber(), stepEntity.getDescription());
        //when
        StepDTO result = underTest.map(stepEntity, StepDTO.class);
        //then
        assertEquals(expected, result);
    }

    @Test
    void recipeEntity_to_recipeDTO() {
        //given
        IngredientEntity sliceOfBread = new IngredientEntity(56L, "Slice of bread", 1.0, "-");
        IngredientEntity breadRoll = new IngredientEntity(57L, "Bread roll", 1.0, "-");
        StepEntity stepOne = new StepEntity(777L,
                                            1,
                                            "Put a bread roll on top of a slice of bread.");
        StepEntity stepTwo = new StepEntity(778L, 2, "Eat.");
        RecipeEntity recipeEntity
                = new RecipeEntity(43L,
                                   3300L,
                                   "origin.url",
                                   "image.url",
                                   "Students sandwich",
                                   "Two-ingredients simple recipe to fulfill your belly.",
                                   Arrays.asList(sliceOfBread, breadRoll),
                                   Arrays.asList(stepOne, stepTwo));
        RecipeDTO expected = new RecipeDTO(recipeEntity.getOriginId(),
                                           recipeEntity.getOriginURL(),
                                           recipeEntity.getImageURL(),
                                           recipeEntity.getTitle(),
                                           recipeEntity.getSummary(),
                                           Arrays.asList(new IngredientDTO(sliceOfBread.getName(),
                                                                           sliceOfBread.getAmount(),
                                                                           sliceOfBread.getUnit()),
                                                         new IngredientDTO(breadRoll.getName(),
                                                                           breadRoll.getAmount(),
                                                                           breadRoll.getUnit())),
                                           Arrays.asList(new StepDTO(stepOne.getNumber(), stepOne.getDescription()),
                                                         new StepDTO(stepTwo.getNumber(), stepTwo.getDescription())));
        //when
        RecipeDTO result = underTest.map(recipeEntity, RecipeDTO.class);
        //then
        assertEquals(expected, result);
    }
}
