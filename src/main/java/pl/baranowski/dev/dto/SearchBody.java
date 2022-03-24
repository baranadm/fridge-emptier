package pl.baranowski.dev.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchBody {
    private String include;
    private String exclude;
}
