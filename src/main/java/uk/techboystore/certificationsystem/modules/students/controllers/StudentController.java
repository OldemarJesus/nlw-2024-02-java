package uk.techboystore.certificationsystem.modules.students.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.techboystore.certificationsystem.modules.students.dto.VerifyCertificationDto;
import uk.techboystore.certificationsystem.modules.students.usercases.VerifyIfHasCertificationUseCase;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;
    
    @PostMapping("/verify-certification")
    public String verifyCertificaiton(@RequestBody VerifyCertificationDto verifyCertification) {
        System.out.println(verifyIfHasCertificationUseCase.execute(verifyCertification));
        return "test";
    }
}
