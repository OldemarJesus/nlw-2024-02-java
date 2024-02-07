package uk.techboystore.certificationsystem.modules.questions.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlterantivesResultDTO {
    private UUID id;
    private String description;
}
