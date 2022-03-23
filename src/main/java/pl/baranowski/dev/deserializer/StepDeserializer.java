package pl.baranowski.dev.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import pl.baranowski.dev.model.Step;

import java.io.IOException;

public class StepDeserializer extends JsonDeserializer<Step> {
    @Override
    public Step deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectCodec codec = p.getCodec();
        JsonNode node = codec.readTree(p);

        int number = node.get("number").asInt();
        String description = node.get("step").asText();

        return new Step(number, description);
    }
}
