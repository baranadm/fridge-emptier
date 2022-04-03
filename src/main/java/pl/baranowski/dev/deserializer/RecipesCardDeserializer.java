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
import pl.baranowski.dev.dto.RecipeCardDTO;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class RecipesCardDeserializer extends JsonDeserializer<RecipeCardDTO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecipesCardDeserializer.class);
    @Override
    public RecipeCardDTO deserialize(JsonParser p,
                                     DeserializationContext ctxt) throws IOException, JacksonException {
        ObjectCodec codec = p.getCodec();
        JsonNode node = codec.readTree(p);
        LOGGER.debug("Starting RecipeCard deserializer for node: {}", node);

        long originId = node.get("id").asLong();
        String title = node.get("title").asText();
        String imageURL = node.get("image").asText();
        List<IngredientDTO> unusedIngredientDTOS = getIngredients(node.get("missedIngredients").toString());

        LOGGER.debug("Parsed values: originId={}, title={}, imageURL={}, unusedIngredients={}", originId, title, imageURL, unusedIngredientDTOS);
        RecipeCardDTO result = new RecipeCardDTO(originId, title, imageURL, unusedIngredientDTOS);

        return result;
    }

    private List<IngredientDTO> getIngredients(String ingredientsJson) throws JsonProcessingException {
        //TODO pytanie: czy mogę to zrobić przez DI?
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(IngredientDTO.class, new IngredientDTODeserializer());
        mapper.registerModule(module);

        List<IngredientDTO> ingredientDTOS = Arrays.asList(mapper.readValue(ingredientsJson, IngredientDTO[].class));
        return ingredientDTOS;
    }
}
