package uk.techboystore.certificationsystem.modules.questions.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.techboystore.certificationsystem.modules.questions.dto.AlterantivesResultDTO;
import uk.techboystore.certificationsystem.modules.questions.dto.QuestionResultDTO;
import uk.techboystore.certificationsystem.modules.questions.entities.AlternativesEntity;
import uk.techboystore.certificationsystem.modules.questions.entities.QuestionEntity;
import uk.techboystore.certificationsystem.modules.questions.repositories.QuestionRepository;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepository repository;

    @GetMapping("/technology/{technology}")
    public List<QuestionResultDTO> findByTechnology(@PathVariable String technology) {
        var result = repository.findByTechnology(technology);

        var toMap = result.stream().map(question -> mapQuestionToDTO(question))
                .collect(Collectors.toList());

        return toMap;
    }

    static QuestionResultDTO mapQuestionToDTO(QuestionEntity questions) {
        var questionResultDTO = QuestionResultDTO.builder()
                .id(questions.getId())
                .technology(questions.getTechnology())
                .description(questions.getDescription()).build();

        List<AlterantivesResultDTO> alterantivesResultDTOs = questions.getAlternatives()
                .stream().map(alterantive -> mapAlterantivesResultDTO(alterantive))
                .collect(Collectors.toList());

        questionResultDTO.setAlternatives(alterantivesResultDTOs);
        return questionResultDTO;
    }

    static AlterantivesResultDTO mapAlterantivesResultDTO(AlternativesEntity alternativesEntity) {
        return AlterantivesResultDTO.builder()
                .id(alternativesEntity.getId())
                .description(alternativesEntity.getDescription()).build();
    }
}
