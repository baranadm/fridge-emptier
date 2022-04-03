package pl.baranowski.dev.mapper;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.baranowski.dev.api.external.spoonacular.AnalyzedInstruction;
import pl.baranowski.dev.api.external.spoonacular.ExtendedIngredient;
import pl.baranowski.dev.api.external.spoonacular.SpoonacularResponse;
import pl.baranowski.dev.api.external.spoonacular.Step;
import pl.baranowski.dev.dto.IngredientDTO;
import pl.baranowski.dev.dto.RecipeDTO;
import pl.baranowski.dev.entity.IngredientEntity;
import pl.baranowski.dev.entity.RecipeEntity;
import pl.baranowski.dev.entity.StepEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MappingsTests {
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
        spoonacularResponse.setAnalyzedInstructions(Arrays.asList(spoonacularInstruction));

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
}
