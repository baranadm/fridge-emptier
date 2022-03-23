package pl.baranowski.dev.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.baranowski.dev.deserializer.StepDTODeserializer;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = StepDTODeserializer.class)
public class StepDTO {
    private int number;
    private String description;
}
