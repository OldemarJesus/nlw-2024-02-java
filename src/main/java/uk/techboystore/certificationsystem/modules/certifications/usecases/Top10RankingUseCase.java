package uk.techboystore.certificationsystem.modules.certifications.usecases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.techboystore.certificationsystem.modules.students.entities.CertificationStudentEntity;
import uk.techboystore.certificationsystem.modules.students.repositories.CertificationStudentRepository;

@Service
public class Top10RankingUseCase {
    @Autowired
    private CertificationStudentRepository certificationStudentRepository;

    public List<CertificationStudentEntity> execute() {
        return certificationStudentRepository.findTop10ByOrderByGradeDesc();
    }
}
