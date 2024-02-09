package uk.techboystore.certificationsystem.modules.certifications.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.techboystore.certificationsystem.modules.certifications.usecases.Top10RankingUseCase;
import uk.techboystore.certificationsystem.modules.students.entities.CertificationStudentEntity;

@RestController
@RequestMapping("/ranking")
public class RankingController {
    @Autowired
    private Top10RankingUseCase top10RankingUseCase;

    @GetMapping("/top-ten")
    public List<CertificationStudentEntity> topTen() {
        return top10RankingUseCase.execute();
    }
}
