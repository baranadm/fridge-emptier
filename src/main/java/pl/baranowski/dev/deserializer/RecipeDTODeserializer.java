package pl.baranowski.dev.deserializer;

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
import pl.baranowski.dev.dto.RecipeDTO;
import pl.baranowski.dev.dto.IngredientDTO;
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

        long originId = node.get("id").asLong();
        String originURL = node.get("sourceUrl").asText();
        String imageURL = node.get("image").asText();
        String title = node.get("title").asText();
        String summary = node.get("summary").asText("no description");
        List<IngredientDTO> ingredientDTOS = getIngredients(node.get("extendedIngredients"));
        List<StepDTO> stepDTOS = getSteps(node.get("analyzedInstructions").elements().next().get("steps"));

        RecipeDTO result = new RecipeDTO(originId, originURL, imageURL, title, summary, ingredientDTOS, stepDTOS);
        LOGGER.debug("Returning result: {}", result);
        return result;
    }

    private List<IngredientDTO> getIngredients(JsonNode ingredientsNode) throws JsonProcessingException {
        String ingredientsJsonArray = ingredientsNode.toString();
        ObjectMapper mapper = createMapper(IngredientDTO.class, new IngredientDTODeserializer());
        List<IngredientDTO> result = Arrays.asList(mapper.readValue(ingredientsJsonArray, IngredientDTO[].class));
        return result;
    }

    private List<StepDTO> getSteps(JsonNode stepsNode) throws JsonProcessingException {
        String stepsJsonArray = stepsNode.toString();
        ObjectMapper mapper = createMapper(StepDTO.class, new StepDTODeserializer());
        List<StepDTO> result = Arrays.asList(mapper.readValue(stepsJsonArray, StepDTO[].class));
        return result;
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
