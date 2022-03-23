package pl.baranowski.dev.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.baranowski.dev.deserializer.StepDeserializer;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = StepDeserializer.class)
public class Step {
    private int number;
    private String description;
}
