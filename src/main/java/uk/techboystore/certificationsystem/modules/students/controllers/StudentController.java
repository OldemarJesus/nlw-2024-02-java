package uk.techboystore.certificationsystem.modules.students.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.techboystore.certificationsystem.modules.questions.dto.StudentCertificationAnswerDTO;
import uk.techboystore.certificationsystem.modules.students.dto.VerifyCertificationDto;
import uk.techboystore.certificationsystem.modules.students.entities.CertificationStudentEntity;
import uk.techboystore.certificationsystem.modules.students.usercases.StudentCertificationAnswersUseCase;
import uk.techboystore.certificationsystem.modules.students.usercases.VerifyIfHasCertificationUseCase;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;

    @Autowired
    private StudentCertificationAnswersUseCase studentCertificationAnswersUseCase;

    @PostMapping("/verify-certification")
    public String verifyCertificaiton(@RequestBody VerifyCertificationDto verifyCertification) {
        var result = verifyIfHasCertificationUseCase.execute(verifyCertification);

        if (result) {
            return "Ja fez a prova";
        }
        return "Pode fazer a prova";
    }

    @PostMapping("/certification/answer")
    public ResponseEntity<Object> StudentCertificationAnswer(
            @RequestBody StudentCertificationAnswerDTO studentCertificationAnswerDTO) {
        try {
            var result = studentCertificationAnswersUseCase.execute(studentCertificationAnswerDTO);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
