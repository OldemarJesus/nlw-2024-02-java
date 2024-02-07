package uk.techboystore.certificationsystem.modules.students.usercases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.techboystore.certificationsystem.modules.students.dto.VerifyCertificationDto;
import uk.techboystore.certificationsystem.modules.students.repositories.CertificationStudentRepository;

@Service
public class VerifyIfHasCertificationUseCase {
    @Autowired
    private CertificationStudentRepository repository;

    public boolean execute(VerifyCertificationDto dto) {
        var result = repository.findByStudentEmailAndTechnology(dto.getEmail(), dto.getTechnology());
        if (!result.isEmpty()) {
            return true;
        }
        return false;
    }
}
