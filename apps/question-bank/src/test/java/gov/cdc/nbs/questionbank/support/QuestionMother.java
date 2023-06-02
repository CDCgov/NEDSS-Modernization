package gov.cdc.nbs.questionbank.support;

import java.time.Instant;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.entities.DateQuestionEntity;
import gov.cdc.nbs.questionbank.entities.DropdownQuestionEntity;
import gov.cdc.nbs.questionbank.entities.NumericQuestionEntity;
import gov.cdc.nbs.questionbank.entities.TextQuestionEntity;
import gov.cdc.nbs.questionbank.entities.enums.CodeSet;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import gov.cdc.nbs.questionbank.question.repository.QuestionRepository;

@Component
public class QuestionMother {
    private final QuestionRepository repository;

    public QuestionMother(QuestionRepository repository) {
        this.repository = repository;
    }

    public void clean() {
        repository.deleteAll();
    }

    public TextQuestionEntity textQuestion(String label) {
        return repository.save(new TextQuestionEntity(addTextQuestion(label)));
    }

    public DateQuestionEntity dateQuestion(String label) {
        return repository.save(new DateQuestionEntity(addDateQuestion(label)));
    }

    public NumericQuestionEntity numericQuestion(String label) {
        return repository.save(new NumericQuestionEntity(addNumericQuestion(label)));
    }

    public DropdownQuestionEntity dropdownQuestion(String label) {
        return repository.save(new DropdownQuestionEntity(addDropdownQuestion(label)));
    }

    private QuestionCommand.AddTextQuestion addTextQuestion(String label) {
        return new QuestionCommand.AddTextQuestion(
                null,
                123L,
                Instant.now(),
                label,
                "text tooltip",
                100,
                "text placeholder",
                "text default",
                CodeSet.LOCAL);
    }

    private QuestionCommand.AddDateQuestion addDateQuestion(String label) {
        return new QuestionCommand.AddDateQuestion(
                null,
                123L,
                Instant.now(),
                label,
                "date tooltip",
                false,
                CodeSet.PHIN);
    }

    private QuestionCommand.AddNumericQuestion addNumericQuestion(String label) {
        return new QuestionCommand.AddNumericQuestion(
                null,
                123L,
                Instant.now(),
                label,
                "numeric tooltip",
                0,
                100,
                50,
                null,
                CodeSet.LOCAL);
    }

    private QuestionCommand.AddDropDownQuestion addDropdownQuestion(String label) {
        return new QuestionCommand.AddDropDownQuestion(
                null,
                123L,
                Instant.now(),
                label,
                "dropdown tooltip",
                null,
                null,
                false,
                CodeSet.LOCAL);
    }
}
