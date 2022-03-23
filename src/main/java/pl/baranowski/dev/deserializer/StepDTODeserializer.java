package pl.baranowski.dev.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import pl.baranowski.dev.model.StepDTO;

import java.io.IOException;

public class StepDTODeserializer extends JsonDeserializer<StepDTO> {
    @Override
    public StepDTO deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectCodec codec = p.getCodec();
        JsonNode node = codec.readTree(p);

        int number = node.get("number").asInt();
        String description = node.get("step").asText();

        return new StepDTO(number, description);
    }
}
