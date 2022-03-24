package pl.baranowski.dev.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.baranowski.dev.dto.StepDTO;

import java.io.IOException;

public class StepDTODeserializer extends JsonDeserializer<StepDTO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StepDTO.class);

    @Override
    public StepDTO deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectCodec codec = p.getCodec();
        JsonNode node = codec.readTree(p);
        LOGGER.debug("Starting deserializer for node: {}", node);

        int number = node.get("number").asInt();
        String description = node.get("step").asText();

        LOGGER.debug("Parsed values: number={}, description={}", number, description);
        return new StepDTO(number, description);
    }
}
