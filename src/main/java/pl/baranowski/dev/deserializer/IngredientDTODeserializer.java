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

public class IngredientDTODeserializer extends JsonDeserializer<IngredientDTO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(IngredientDTODeserializer.class);

    @Override
    public IngredientDTO deserialize(JsonParser p,
                                     DeserializationContext ctxt) throws IOException, JacksonException {
        ObjectCodec codec = p.getCodec();
        JsonNode node = codec.readTree(p);
        LOGGER.debug("Starting Ingredient deserializer for node: {}", node);

        String name = node.get("name").asText();
        Double amount = node.get("amount").asDouble();
        String unit = node.get("unit").asText();
        LOGGER.debug("Parsed values: name={}, amount={}, unit={}", name, amount, unit);

        IngredientDTO ingredientDTO = new IngredientDTO(name, amount, unit);
        return ingredientDTO;
    }
}
