package uk.techboystore.certificationsystem.modules.students.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyCertificationDto {
    private String email;
    private String technology;
}
