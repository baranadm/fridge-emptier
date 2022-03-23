package pl.baranowski.dev.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.baranowski.dev.dto.IngredientDTO;
import pl.baranowski.dev.model.RecipeCard;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class RecipesCardDeserializer extends JsonDeserializer<RecipeCard> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecipesCardDeserializer.class);
    @Override
    public RecipeCard deserialize(JsonParser p,
                                        DeserializationContext ctxt) throws IOException, JacksonException {
        LOGGER.debug("Starting deserializer");
        ObjectCodec codec = p.getCodec();
        JsonNode node = ctxt.readTree(p);

        long originId = node.get("id").asLong();
        String title = node.get("title").asText();
        String imageURL = node.get("image").asText();
        List<IngredientDTO> unusedIngredientDTOS = getIngredients(node.get("unusedIngredients").toString());

        RecipeCard result = new RecipeCard(originId, title, imageURL, unusedIngredientDTOS);
        return result;
    }

    private List<IngredientDTO> getIngredients(String ingredientsJson) throws JsonProcessingException {
        //TODO pytanie: czy mogę to zrobić przez DI?
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(IngredientDTO.class, new IngredientDeserializer());
        mapper.registerModule(module);

        List<IngredientDTO> ingredientDTOS = Arrays.asList(mapper.readValue(ingredientsJson, IngredientDTO[].class));
        return ingredientDTOS;
    }
}
