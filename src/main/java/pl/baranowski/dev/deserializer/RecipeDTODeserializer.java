package pl.baranowski.dev.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.baranowski.dev.dto.IngredientDTO;
import pl.baranowski.dev.dto.RecipeDTO;
import pl.baranowski.dev.dto.StepDTO;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class RecipeDTODeserializer extends JsonDeserializer<RecipeDTO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeDTODeserializer.class);

    @Override
    public RecipeDTO deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectCodec oc = p.getCodec();
        JsonNode node = oc.readTree(p);
        LOGGER.debug("Starting deserializer for node: {}", node);

        RecipeDTO result = parseFields(node);
        LOGGER.debug("Returning result: {}", result);
        return result;
    }

    @NotNull
    private RecipeDTO parseFields(JsonNode node) throws JsonProcessingException {
        long originId = node.get("id").asLong();
        String originURL = node.get("sourceUrl").asText();
        String imageURL = node.get("image").asText();
        String title = node.get("title").asText();
        String summary = node.get("summary").asText("no description");

        List<IngredientDTO> ingredientDTOS = parseIngredients(node.get("extendedIngredients"));
        List<StepDTO> stepDTOS = parseSteps(node.get("analyzedInstructions").elements().next().get("steps"));

        return new RecipeDTO(originId, originURL, imageURL, title, summary, ingredientDTOS, stepDTOS);
    }

    private List<IngredientDTO> parseIngredients(JsonNode ingredientsNode) throws JsonProcessingException {
        String ingredientsJsonArray = ingredientsNode.toString();
        ObjectMapper mapper = createMapper(IngredientDTO.class, new IngredientDTODeserializer());
        IngredientDTO[] ingredients = mapper.readValue(ingredientsJsonArray, IngredientDTO[].class);
        return Arrays.asList(ingredients);
    }

    private List<StepDTO> parseSteps(JsonNode stepsNode) throws JsonProcessingException {
        String stepsJsonArray = stepsNode.toString();
        ObjectMapper mapper = createMapper(StepDTO.class, new StepDTODeserializer());
        StepDTO[] steps = mapper.readValue(stepsJsonArray, StepDTO[].class);
        return Arrays.asList(steps);
    }

    private <T> ObjectMapper createMapper(Class<T> clazz, JsonDeserializer<T> deserializer) {
        //TODO pytanie: czy mogę to zrobić przez DI?
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(clazz, deserializer);
        mapper.registerModule(module);
        return mapper;
    }
}
