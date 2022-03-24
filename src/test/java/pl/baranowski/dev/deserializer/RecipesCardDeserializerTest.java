package pl.baranowski.dev.deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.baranowski.dev.dto.IngredientDTO;
import pl.baranowski.dev.model.RecipeCard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(classes = ObjectMapper.class)
class RecipesCardDeserializerTest {
    @Autowired
    ObjectMapper underTest;

    @Test
    void deserialize() throws JsonProcessingException {
        //given
        String json ="{\n" +
                "            \"id\": 645479,\n" +
                "            \"usedIngredientCount\": 2,\n" +
                "            \"missedIngredientCount\": 4,\n" +
                "            \"missedIngredients\": [\n" +
                "                {\n" +
                "                    \"id\": 93607,\n" +
                "                    \"amount\": 1.5,\n" +
                "                    \"unit\": \"cup\",\n" +
                "                    \"unitLong\": \"cups\",\n" +
                "                    \"unitShort\": \"cup\",\n" +
                "                    \"aisle\": \"Milk, Eggs, Other Dairy\",\n" +
                "                    \"name\": \"almond milk\",\n" +
                "                    \"original\": \"1 1/2 cup unsweetened almond milk\",\n" +
                "                    \"originalName\": \"unsweetened almond milk\",\n" +
                "                    \"meta\": [\n" +
                "                        \"unsweetened\"\n" +
                "                    ],\n" +
                "                    \"extendedName\": \"unsweetened almond milk\",\n" +
                "                    \"image\": \"https://spoonacular.com/cdn/ingredients_100x100/almond-milk.png\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"id\": 11457,\n" +
                "                    \"amount\": 2.0,\n" +
                "                    \"unit\": \"cups\",\n" +
                "                    \"unitLong\": \"cups\",\n" +
                "                    \"unitShort\": \"cup\",\n" +
                "                    \"aisle\": \"Produce\",\n" +
                "                    \"name\": \"baby spinach\",\n" +
                "                    \"original\": \"2 cups baby spinach\",\n" +
                "                    \"originalName\": \"baby spinach\",\n" +
                "                    \"meta\": [],\n" +
                "                    \"image\": \"https://spoonacular.com/cdn/ingredients_100x100/spinach.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"id\": 19296,\n" +
                "                    \"amount\": 1.0,\n" +
                "                    \"unit\": \"tablespoon\",\n" +
                "                    \"unitLong\": \"tablespoon\",\n" +
                "                    \"unitShort\": \"Tbsp\",\n" +
                "                    \"aisle\": \"Nut butters, Jams, and Honey\",\n" +
                "                    \"name\": \"honey\",\n" +
                "                    \"original\": \"1 tablespoon honey\",\n" +
                "                    \"originalName\": \"honey\",\n" +
                "                    \"meta\": [],\n" +
                "                    \"image\": \"https://spoonacular.com/cdn/ingredients_100x100/honey.png\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"id\": 9176,\n" +
                "                    \"amount\": 1.0,\n" +
                "                    \"unit\": \"cup\",\n" +
                "                    \"unitLong\": \"cup\",\n" +
                "                    \"unitShort\": \"cup\",\n" +
                "                    \"aisle\": \"Produce\",\n" +
                "                    \"name\": \"mango\",\n" +
                "                    \"original\": \"1 cup mango, fresh or frozen\",\n" +
                "                    \"originalName\": \"mango, fresh or frozen\",\n" +
                "                    \"meta\": [\n" +
                "                        \"fresh\"\n" +
                "                    ],\n" +
                "                    \"extendedName\": \"fresh mango\",\n" +
                "                    \"image\": \"https://spoonacular.com/cdn/ingredients_100x100/mango.jpg\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"usedIngredients\": [\n" +
                "                {\n" +
                "                    \"id\": 9037,\n" +
                "                    \"amount\": 1.0,\n" +
                "                    \"unit\": \"\",\n" +
                "                    \"unitLong\": \"\",\n" +
                "                    \"unitShort\": \"\",\n" +
                "                    \"aisle\": \"Produce\",\n" +
                "                    \"name\": \"avocado\",\n" +
                "                    \"original\": \"1 avocado\",\n" +
                "                    \"originalName\": \"avocado\",\n" +
                "                    \"meta\": [],\n" +
                "                    \"image\": \"https://spoonacular.com/cdn/ingredients_100x100/avocado.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"id\": 9040,\n" +
                "                    \"amount\": 1.0,\n" +
                "                    \"unit\": \"\",\n" +
                "                    \"unitLong\": \"\",\n" +
                "                    \"unitShort\": \"\",\n" +
                "                    \"aisle\": \"Produce\",\n" +
                "                    \"name\": \"banana\",\n" +
                "                    \"original\": \"1 banana\",\n" +
                "                    \"originalName\": \"banana\",\n" +
                "                    \"meta\": [],\n" +
                "                    \"image\": \"https://spoonacular.com/cdn/ingredients_100x100/bananas.jpg\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"unusedIngredients\": [],\n" +
                "            \"likes\": 0,\n" +
                "            \"title\": \"Green Monster Ice Pops\",\n" +
                "            \"image\": \"https://spoonacular.com/recipeImages/645479-312x231.jpg\",\n" +
                "            \"imageType\": \"jpg\"\n" +
                "        }";
        //when
        RecipeCard result = underTest.readValue(json, RecipeCard.class);
        //then
        List<IngredientDTO> expectedUnusedIngredientDTOS = Arrays.asList(new IngredientDTO("almond milk", 1.5D, "cup"),
                                                                         new IngredientDTO("baby spinach", 2.0D, "cups"),
                                                                         new IngredientDTO("honey", 1.0D, "tablespoon"),
                                                                         new IngredientDTO("mango", 1.0, "cup"));
        RecipeCard expected = new RecipeCard(645479L, "Green Monster Ice Pops", "https://spoonacular.com/recipeImages/645479-312x231.jpg",
                                             expectedUnusedIngredientDTOS);
        assertEquals(expected, result);

    }
}