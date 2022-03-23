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
import pl.baranowski.dev.model.StepDTO;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class RecipeDeserializer extends JsonDeserializer<RecipeDTO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeDeserializer.class);

    @Override
    public RecipeDTO deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        LOGGER.debug("Starting deserializer");
        ObjectCodec oc = p.getCodec();
        JsonNode node = oc.readTree(p);

        long originId = node.get("id").asLong();
        LOGGER.debug("Parsed originId={}", originId);

        String originURL = node.get("sourceUrl").asText();
        LOGGER.debug("Parsed originURL={}", originURL);

        String imageURL = node.get("image").asText();
        LOGGER.debug("Parsed imageURL={}", imageURL);

        String title = node.get("title").asText();
        LOGGER.debug("Parsed title={}", title);

        String summary = node.get("summary").asText();
        LOGGER.debug("Parsed summary={}", summary);

        List<IngredientDTO> ingredientDTOS = getIngredients(node.get("extendedIngredients"));
        LOGGER.debug("Parsed ingredients={}", ingredientDTOS);

        List<StepDTO> stepDTOS = getSteps(node.get("analyzedInstructions").elements().next().get("steps"));
        LOGGER.debug("Parsed steps={}", stepDTOS);

        RecipeDTO result = new RecipeDTO(originId, originURL, imageURL, title, summary, ingredientDTOS, stepDTOS);
        LOGGER.debug("Returning result: {}", result);
        return result;
    }

    private List<IngredientDTO> getIngredients(JsonNode ingredientsNode) throws JsonProcessingException {
        LOGGER.debug("getIngredients(JsonNode={})", ingredientsNode);

        String ingredientsJsonArray = ingredientsNode.toString();
        LOGGER.debug("ingredientsArray: {}", ingredientsJsonArray);

        ObjectMapper mapper = createMapper(IngredientDTO.class, new IngredientDeserializer());
        List<IngredientDTO> result = Arrays.asList(mapper.readValue(ingredientsJsonArray, IngredientDTO[].class));

        LOGGER.debug("Returning result: {}", result);
        return result;
    }

    private List<StepDTO> getSteps(JsonNode stepsNode) throws JsonProcessingException {
        LOGGER.debug("getSteps(JsonNode={})", stepsNode);

        String stepsJsonArray = stepsNode.toString();
        LOGGER.debug("stepsArray: {}", stepsJsonArray);

        ObjectMapper mapper = createMapper(StepDTO.class, new StepDTODeserializer());
        List<StepDTO> result = Arrays.asList(mapper.readValue(stepsJsonArray, StepDTO[].class));

        LOGGER.debug("Returning result: {}", result);
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
