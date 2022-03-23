package pl.baranowski.dev.deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.baranowski.dev.dto.RecipeDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ObjectMapper.class)
class RecipeDeserializerTest {
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testDeserialization() throws JsonProcessingException {
        String json = "{\"vegetarian\":false,\"vegan\":false,\"glutenFree\":true,\"dairyFree\":true,\"veryHealthy\":false,\"cheap\":false,\"veryPopular\":false,\"sustainable\":false,\"weightWatcherSmartPoints\":2,\"gaps\":\"no\",\"lowFodmap\":false,\"aggregateLikes\":19,\"spoonacularScore\":85.0,\"healthScore\":26.0,\"creditsText\":\"Foodista.com – The Cooking Encyclopedia Everyone Can Edit\",\"license\":\"CC BY 3.0\",\"sourceName\":\"Foodista\",\"pricePerServing\":76.32,\"extendedIngredients\":[{\"id\":93607,\"aisle\":\"Milk, Eggs, Other Dairy\",\"image\":\"almond-milk.png\",\"consistency\":\"liquid\",\"name\":\"almond milk\",\"nameClean\":\"almond milk\",\"original\":\"1 1/2 cup unsweetened almond milk\",\"originalName\":\"unsweetened almond milk\",\"amount\":1.5,\"unit\":\"cup\",\"meta\":[\"unsweetened\"],\"measures\":{\"us\":{\"amount\":1.5,\"unitShort\":\"cups\",\"unitLong\":\"cups\"},\"metric\":{\"amount\":354.882,\"unitShort\":\"ml\",\"unitLong\":\"milliliters\"}}},{\"id\":9037,\"aisle\":\"Produce\",\"image\":\"avocado.jpg\",\"consistency\":\"solid\",\"name\":\"avocado\",\"nameClean\":\"avocado\",\"original\":\"1 avocado\",\"originalName\":\"avocado\",\"amount\":1.0,\"unit\":\"\",\"meta\":[],\"measures\":{\"us\":{\"amount\":1.0,\"unitShort\":\"\",\"unitLong\":\"\"},\"metric\":{\"amount\":1.0,\"unitShort\":\"\",\"unitLong\":\"\"}}},{\"id\":11457,\"aisle\":\"Produce\",\"image\":\"spinach.jpg\",\"consistency\":\"solid\",\"name\":\"baby spinach\",\"nameClean\":\"baby spinach\",\"original\":\"2 cups baby spinach\",\"originalName\":\"baby spinach\",\"amount\":2.0,\"unit\":\"cups\",\"meta\":[],\"measures\":{\"us\":{\"amount\":2.0,\"unitShort\":\"cups\",\"unitLong\":\"cups\"},\"metric\":{\"amount\":473.176,\"unitShort\":\"ml\",\"unitLong\":\"milliliters\"}}},{\"id\":9040,\"aisle\":\"Produce\",\"image\":\"bananas.jpg\",\"consistency\":\"solid\",\"name\":\"banana\",\"nameClean\":\"ripe banana\",\"original\":\"1 banana\",\"originalName\":\"banana\",\"amount\":1.0,\"unit\":\"\",\"meta\":[],\"measures\":{\"us\":{\"amount\":1.0,\"unitShort\":\"\",\"unitLong\":\"\"},\"metric\":{\"amount\":1.0,\"unitShort\":\"\",\"unitLong\":\"\"}}},{\"id\":19296,\"aisle\":\"Nut butters, Jams, and Honey\",\"image\":\"honey.png\",\"consistency\":\"liquid\",\"name\":\"honey\",\"nameClean\":\"honey\",\"original\":\"1 tablespoon honey\",\"originalName\":\"honey\",\"amount\":1.0,\"unit\":\"tablespoon\",\"meta\":[],\"measures\":{\"us\":{\"amount\":1.0,\"unitShort\":\"Tbsp\",\"unitLong\":\"Tbsp\"},\"metric\":{\"amount\":1.0,\"unitShort\":\"Tbsp\",\"unitLong\":\"Tbsp\"}}},{\"id\":9176,\"aisle\":\"Produce\",\"image\":\"mango.jpg\",\"consistency\":\"solid\",\"name\":\"mango\",\"nameClean\":\"mango\",\"original\":\"1 cup mango, fresh or frozen\",\"originalName\":\"mango, fresh or frozen\",\"amount\":1.0,\"unit\":\"cup\",\"meta\":[\"fresh\"],\"measures\":{\"us\":{\"amount\":1.0,\"unitShort\":\"cup\",\"unitLong\":\"cup\"},\"metric\":{\"amount\":236.588,\"unitShort\":\"ml\",\"unitLong\":\"milliliters\"}}}],\"id\":645479,\"title\":\"Green Monster Ice Pops\",\"readyInMinutes\":180,\"servings\":6,\"sourceUrl\":\"http://www.foodista.com/recipe/3584LCSQ/green-monster-ice-pops\",\"image\":\"https://spoonacular.com/recipeImages/645479-556x370.jpg\",\"imageType\":\"jpg\",\"summary\":\"This recipe serves 6 and costs 76 cents per serving. One serving contains <b>109 calories</b>, <b>2g of protein</b>, and <b>6g of fat</b>. 19 people have tried and liked this recipe. A mixture of almond milk, avocado, honey, and a handful of other ingredients are all it takes to make this recipe so yummy. It can be enjoyed any time, but it is especially good for <b>Halloween</b>. It is a good option if you're following a <b>gluten free and dairy free</b> diet. From preparation to the plate, this recipe takes around <b>3 hours</b>. All things considered, we decided this recipe <b>deserves a spoonacular score of 86%</b>. This score is amazing. Try <a href=\\\"https://spoonacular.com/recipes/cold-brew-coffee-latte-ice-pops-912354\\\">Cold Brew Coffee Latte Ice Pops</a>, <a href=\\\"https://spoonacular.com/recipes/monster-pops-266266\\\">Monster Pops</a>, and <a href=\\\"https://spoonacular.com/recipes/monster-cookie-pops-177420\\\">Monster Cookie Pops</a> for similar recipes.\",\"cuisines\":[],\"dishTypes\":[\"side dish\"],\"diets\":[\"gluten free\",\"dairy free\"],\"occasions\":[\"halloween\"],\"winePairing\":{},\"instructions\":\"<ol><li>Place all ingredients into a blender and mix well.</li><li>Taste the smoothie. If you find that it is not sweet enough, add 1 to 2 tablespoons of maple syrup or honey into the smoothie and blend.</li><li>Pour smoothie into the ice pop molds. Insert a wooden popsicle stick into the mold, leaving about a quarter of the stick above the mold.</li><li>Place the molds into the freezer for 2-3 hours to let freeze before serving.</li></ol>\",\"analyzedInstructions\":[{\"name\":\"\",\"steps\":[{\"number\":1,\"step\":\"Place all ingredients into a blender and mix well.Taste the smoothie. If you find that it is not sweet enough, add 1 to 2 tablespoons of maple syrup or honey into the smoothie and blend.\",\"ingredients\":[{\"id\":19911,\"name\":\"maple syrup\",\"localizedName\":\"maple syrup\",\"image\":\"maple-syrup.png\"},{\"id\":0,\"name\":\"smoothie\",\"localizedName\":\"smoothie\",\"image\":\"\"},{\"id\":19296,\"name\":\"honey\",\"localizedName\":\"honey\",\"image\":\"honey.png\"}],\"equipment\":[{\"id\":404726,\"name\":\"blender\",\"localizedName\":\"blender\",\"image\":\"blender.png\"}]},{\"number\":2,\"step\":\"Pour smoothie into the ice pop molds. Insert a wooden popsicle stick into the mold, leaving about a quarter of the stick above the mold.\",\"ingredients\":[{\"id\":0,\"name\":\"smoothie\",\"localizedName\":\"smoothie\",\"image\":\"\"},{\"id\":10014412,\"name\":\"ice\",\"localizedName\":\"ice\",\"image\":\"ice-cubes.png\"},{\"id\":0,\"name\":\"pop\",\"localizedName\":\"soft drink\",\"image\":\"\"}],\"equipment\":[{\"id\":408704,\"name\":\"popsicle sticks\",\"localizedName\":\"popsicle sticks\",\"image\":\"popsicle-sticks.jpg\"}]},{\"number\":3,\"step\":\"Place the molds into the freezer for 2-3 hours to let freeze before serving.\",\"ingredients\":[],\"equipment\":[],\"length\":{\"number\":180,\"unit\":\"minutes\"}}]}],\"originalId\":null,\"spoonacularSourceUrl\":\"https://spoonacular.com/green-monster-ice-pops-645479\"}";
        RecipeDTO recipeDTO = objectMapper.readValue(json, RecipeDTO.class);
        assertEquals(645479, recipeDTO.getOriginId());
        assertEquals("http://www.foodista.com/recipe/3584LCSQ/green-monster-ice-pops", recipeDTO.getOriginURL());
        assertEquals("https://spoonacular.com/recipeImages/645479-556x370.jpg", recipeDTO.getImageURL());
        assertEquals("Green Monster Ice Pops", recipeDTO.getTitle());
        assertEquals(
                "This recipe serves 6 and costs 76 cents per serving. One serving contains <b>109 calories</b>, <b>2g of protein</b>, and <b>6g of fat</b>. 19 people have tried and liked this recipe. A mixture of almond milk, avocado, honey, and a handful of other ingredients are all it takes to make this recipe so yummy. It can be enjoyed any time, but it is especially good for <b>Halloween</b>. It is a good option if you're following a <b>gluten free and dairy free</b> diet. From preparation to the plate, this recipe takes around <b>3 hours</b>. All things considered, we decided this recipe <b>deserves a spoonacular score of 86%</b>. This score is amazing. Try <a href=\"https://spoonacular.com/recipes/cold-brew-coffee-latte-ice-pops-912354\">Cold Brew Coffee Latte Ice Pops</a>, <a href=\"https://spoonacular.com/recipes/monster-pops-266266\">Monster Pops</a>, and <a href=\"https://spoonacular.com/recipes/monster-cookie-pops-177420\">Monster Cookie Pops</a> for similar recipes.",
                recipeDTO.getSummary());
        assertEquals(6, recipeDTO.getIngredientDTOS().size());
        assertEquals(3, recipeDTO.getInstructions().size());
    }

}