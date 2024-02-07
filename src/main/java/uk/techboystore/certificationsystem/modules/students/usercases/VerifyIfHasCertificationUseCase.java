package uk.techboystore.certificationsystem.modules.students.usercases;

import org.springframework.stereotype.Service;

import uk.techboystore.certificationsystem.modules.students.dto.VerifyCertificationDto;

@Service
public class VerifyIfHasCertificationUseCase {
    public boolean execute(VerifyCertificationDto dto) {
        if (dto.getEmail().equals("oldemego@gmail.com") && dto.getTechnology().equals("Java")) {
            return true;
        }
        return false;
    }
}
