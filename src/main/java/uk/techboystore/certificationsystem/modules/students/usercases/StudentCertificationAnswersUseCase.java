package uk.techboystore.certificationsystem.modules.students.usercases;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.techboystore.certificationsystem.modules.questions.dto.QuestionAnswerDTO;
import uk.techboystore.certificationsystem.modules.questions.dto.StudentCertificationAnswerDTO;
import uk.techboystore.certificationsystem.modules.questions.entities.QuestionEntity;
import uk.techboystore.certificationsystem.modules.questions.repositories.QuestionRepository;
import uk.techboystore.certificationsystem.modules.students.dto.VerifyCertificationDto;
import uk.techboystore.certificationsystem.modules.students.entities.AnswersCertificationsEntity;
import uk.techboystore.certificationsystem.modules.students.entities.CertificationStudentEntity;
import uk.techboystore.certificationsystem.modules.students.entities.StudentEntity;
import uk.techboystore.certificationsystem.modules.students.repositories.CertificationStudentRepository;
import uk.techboystore.certificationsystem.modules.students.repositories.StudentRepostitory;

@Service
public class StudentCertificationAnswersUseCase {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private StudentRepostitory studentRepostitory;

    @Autowired
    private CertificationStudentRepository certificationsRepository;

    @Autowired
    private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;

    public CertificationStudentEntity execute(StudentCertificationAnswerDTO dto) throws Exception {
        var hasCertificaiton = verifyIfHasCertificationUseCase
                .execute(new VerifyCertificationDto(dto.getEmail(), dto.getTechnology()));
                
        if (hasCertificaiton) {
            throw new Exception("Certificação já realizada");
        }
        // check alternative questions
        List<QuestionEntity> questionsEntity = questionRepository.findByTechnology(dto.getTechnology());
        List<AnswersCertificationsEntity> answersCertifications = new ArrayList<>();
        int correctAnswerTotal = 0;

        // chekc of correct alternative
        for (QuestionAnswerDTO questionAnswers : dto.getQuestionsAnswers()) {
            var questionFromDB = questionsEntity.stream()
                    .filter(q -> q.getId().equals(questionAnswers.getQuestionID()))
                    .findFirst()
                    .get();

            var correctAlternativeFromDB = questionFromDB.getAlternatives().stream()
                    .filter(alternative -> alternative.isCorrect())
                    .findFirst()
                    .get();

            var isCorrectAnswer = correctAlternativeFromDB.getId().equals(questionAnswers.getAlternativeID());
            questionAnswers.setCorrect(isCorrectAnswer);

            if (isCorrectAnswer) {
                correctAnswerTotal++;
            }

            answersCertifications.add(AnswersCertificationsEntity.builder()
                    .anwerID(questionAnswers.getAlternativeID())
                    .questionID(questionAnswers.getQuestionID())
                    .isCorrect(questionAnswers.isCorrect())
                    .build());
        }

        // check student
        var student = studentRepostitory.findByEmail(dto.getEmail());
        UUID studentID;
        if (student.isEmpty()) {
            var studentCreated = StudentEntity.builder().email(dto.getEmail()).build();
            studentRepostitory.save(studentCreated);
            studentID = studentCreated.getId();
        } else {
            studentID = student.get().getId();
        }

        // prepare certification
        CertificationStudentEntity certificationStudentEntity = CertificationStudentEntity.builder()
                .technology(dto.getTechnology())
                .studentID(studentID)
                .grade(correctAnswerTotal)
                .build();

        var certificationStudentCreated = certificationsRepository.save(certificationStudentEntity);

        answersCertifications.stream()
                .forEach(ac -> {
                    ac.setCertificationID(certificationStudentEntity.getId());
                    ac.setCertificationStudentEntity(certificationStudentEntity);
                });

        // store certifications information
        certificationStudentEntity.setAnswersCertificationsEntity(answersCertifications);
        certificationsRepository.save(certificationStudentEntity);

        return certificationStudentCreated;
    }
}
