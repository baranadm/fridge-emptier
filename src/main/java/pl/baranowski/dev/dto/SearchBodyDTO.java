package pl.baranowski.dev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchBodyDTO {
    @NotBlank(message = "Please provide at least one ingredient.")
    private String include;
    private String exclude;

    public List<String> getIncludeAsList() {
        return toList(include);
    }

    public List<String> getExcludeAsList() {
        return toList(exclude);
    }

    private List<String> toList(String line) {
        return Arrays.stream(line.split(",")).map(String::trim).collect(Collectors.toList());
    }
}
