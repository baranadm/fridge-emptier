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
import pl.baranowski.dev.model.Ingredient;
import pl.baranowski.dev.model.Step;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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

        List<Ingredient> ingredients = getIngredients(node.get("extendedIngredients").toString());

        List<Step> instructions = getInstructions(node);

        RecipeDTO result = new RecipeDTO(originId, originURL, imageURL, title, summary, ingredients, instructions);
        LOGGER.debug("Returning result: {}", result);
        return result;
    }

    private List<Ingredient> getIngredients(String ingredientsJson) throws JsonProcessingException {
        //TODO pytanie: czy mogę to zrobić przez DI?
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Ingredient.class, new IngredientDeserializer());
        mapper.registerModule(module);

        List<Ingredient> ingredients = Arrays.asList(mapper.readValue(ingredientsJson, Ingredient[].class));
        return ingredients;
    }

    private List<Step> getInstructions(JsonNode node) {
        List<Step> instructions = new ArrayList<>();
        Iterator<JsonNode> analyzedInstructions = node.get("analyzedInstructions").elements();
        if (analyzedInstructions.hasNext()) {
            JsonNode instruction = analyzedInstructions.next();
            Iterator<JsonNode> steps = instruction.get("steps").elements();
            steps.forEachRemaining(step -> {
                int number = step.get("number").asInt();
                String description = step.get("step").asText();
                instructions.add(new Step(number, description));
            });
        }
        LOGGER.debug("Parsed instructions={}", instructions);
        return instructions;
    }
}
