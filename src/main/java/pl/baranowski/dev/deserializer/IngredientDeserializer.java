package pl.baranowski.dev.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.baranowski.dev.dto.IngredientDTO;

import java.io.IOException;

public class IngredientDeserializer extends JsonDeserializer<IngredientDTO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(IngredientDeserializer.class);

    @Override
    public IngredientDTO deserialize(JsonParser p,
                                     DeserializationContext ctxt) throws IOException, JacksonException {
        LOGGER.debug("Starting deserializer");
        ObjectCodec codec = p.getCodec();
        JsonNode node = codec.readTree(p);

        String name = node.get("name").asText();
        LOGGER.debug("Parsed name={}", name);
        Double amount = node.get("amount").asDouble();
        LOGGER.debug("Parsed amount={}", amount);
        String unit = node.get("unit").asText();
        LOGGER.debug("Parsed unit={}", unit);
        IngredientDTO ingredientDTO = new IngredientDTO(name, amount, unit);

        LOGGER.debug("Returning result: {}", ingredientDTO);
        return ingredientDTO;
    }
}
