package pl.baranowski.dev.deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.baranowski.dev.model.Ingredient;
import pl.baranowski.dev.model.RecipeCard;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ObjectMapper.class)
class RecipesCardDeserializerTest {
    @Autowired
    ObjectMapper underTest;

    @Test
    void deserialize() throws JsonProcessingException {
        //given
        String json ="{\"id\":645479,\"usedIngredientCount\":2,\"missedIngredientCount\":4,\"missedIngredients\":[{\"id\":93607,\"amount\":1.5,\"unit\":\"cup\",\"unitLong\":\"cups\",\"unitShort\":\"cup\",\"aisle\":\"Milk,Eggs,OtherDairy\",\"name\":\"almond milk\",\"original\":\"11/2cupunsweetenedalmondmilk\",\"originalName\":\"unsweetenedalmondmilk\",\"meta\":[\"unsweetened\"],\"extendedName\":\"unsweetenedalmondmilk\",\"image\":\"https://spoonacular.com/cdn/ingredients_100x100/almond-milk.png\"},{\"id\":11457,\"amount\":2.0,\"unit\":\"cups\",\"unitLong\":\"cups\",\"unitShort\":\"cup\",\"aisle\":\"Produce\",\"name\":\"baby spinach\",\"original\":\"2cupsbabyspinach\",\"originalName\":\"babyspinach\",\"meta\":[],\"image\":\"https://spoonacular.com/cdn/ingredients_100x100/spinach.jpg\"},{\"id\":19296,\"amount\":1.0,\"unit\":\"tablespoon\",\"unitLong\":\"tablespoon\",\"unitShort\":\"Tbsp\",\"aisle\":\"Nutbutters,Jams,andHoney\",\"name\":\"honey\",\"original\":\"1tablespoonhoney\",\"originalName\":\"honey\",\"meta\":[],\"image\":\"https://spoonacular.com/cdn/ingredients_100x100/honey.png\"},{\"id\":9176,\"amount\":1.0,\"unit\":\"cup\",\"unitLong\":\"cup\",\"unitShort\":\"cup\",\"aisle\":\"Produce\",\"name\":\"mango\",\"original\":\"1cupmango,freshorfrozen\",\"originalName\":\"mango,freshorfrozen\",\"meta\":[\"fresh\"],\"extendedName\":\"freshmango\",\"image\":\"https://spoonacular.com/cdn/ingredients_100x100/mango.jpg\"}],\"usedIngredients\":[{\"id\":9037,\"amount\":1.0,\"unit\":\"\",\"unitLong\":\"\",\"unitShort\":\"\",\"aisle\":\"Produce\",\"name\":\"avocado\",\"original\":\"1avocado\",\"originalName\":\"avocado\",\"meta\":[],\"image\":\"https://spoonacular.com/cdn/ingredients_100x100/avocado.jpg\"},{\"id\":9040,\"amount\":1.0,\"unit\":\"\",\"unitLong\":\"\",\"unitShort\":\"\",\"aisle\":\"Produce\",\"name\":\"banana\",\"original\":\"1banana\",\"originalName\":\"banana\",\"meta\":[],\"image\":\"https://spoonacular.com/cdn/ingredients_100x100/bananas.jpg\"}],\"unusedIngredients\":[],\"likes\":0,\"title\":\"GreenMonsterIcePops\",\"image\":\"https://spoonacular.com/recipeImages/645479-312x231.jpg\",\"imageType\":\"jpg\"}\n";
        //when
        RecipeCard result = underTest.readValue(json, RecipeCard.class);
        //then
        List<Ingredient> expectedUnusedIngredients = Arrays.asList(new Ingredient("almond milk", 1.5D, "cup"),
                                                             new Ingredient("baby spinach", 2.0D, "cups"),
                                                             new Ingredient("honey", 1.5D, "tablespoon"),
                                                             new Ingredient("mango", 1.0, "cup"));
        RecipeCard expected = new RecipeCard(645479L, "Green Monster Ice Pops", "https://spoonacular.com/recipeImages/645479-312x231.jpg", expectedUnusedIngredients);
    }
}