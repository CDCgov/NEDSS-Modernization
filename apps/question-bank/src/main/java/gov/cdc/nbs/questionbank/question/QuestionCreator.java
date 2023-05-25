package gov.cdc.nbs.questionbank.question;

import java.time.Instant;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.entities.DateQuestionEntity;
import gov.cdc.nbs.questionbank.entities.DropdownQuestionEntity;
import gov.cdc.nbs.questionbank.entities.NumericQuestionEntity;
import gov.cdc.nbs.questionbank.entities.TextQuestionEntity;
import gov.cdc.nbs.questionbank.entities.ValueEntity;
import gov.cdc.nbs.questionbank.entities.ValueSet;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest.TextQuestionData;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.AddDateQuestion;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.AddDropDownQuestion;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.AddNumericQuestion;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.AddTextQuestion;
import gov.cdc.nbs.questionbank.question.repository.DisplayElementRepository;

@Component
class QuestionCreator {

    private final DisplayElementRepository displayElementRepository;
    private final EntityManager entityManager;

    public QuestionCreator(
            DisplayElementRepository displayElementRepository,
            EntityManager entityManager) {
        this.displayElementRepository = displayElementRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    TextQuestionEntity create(QuestionRequest.TextQuestionData data, long userId) {
        TextQuestionEntity entity = new TextQuestionEntity(asAdd(userId, data));
        return displayElementRepository.save(entity);
    }

    @Transactional
    DateQuestionEntity create(QuestionRequest.DateQuestionData data, long userId) {
        DateQuestionEntity entity = new DateQuestionEntity(asAdd(userId, data));
        return displayElementRepository.save(entity);
    }

    @Transactional
    DropdownQuestionEntity create(QuestionRequest.DropdownQuestionData data, long userId) {
        DropdownQuestionEntity entity = new DropdownQuestionEntity(adAdd(userId, data));
        return displayElementRepository.save(entity);
    }

    @Transactional
    NumericQuestionEntity create(QuestionRequest.NumericQuestionData data, long userId) {
        NumericQuestionEntity entity = new NumericQuestionEntity(asAdd(userId, data));
        return displayElementRepository.save(entity);
    }

    private AddTextQuestion asAdd(long userId, TextQuestionData data) {
        Instant now = Instant.now();
        return new AddTextQuestion(
                null,
                userId,
                now,
                data.label(),
                data.tooltip(),
                data.maxLength(),
                data.placeholder());
    }

    private AddDateQuestion asAdd(long userId, QuestionRequest.DateQuestionData data) {
        Instant now = Instant.now();
        return new AddDateQuestion(
                null,
                userId,
                now,
                data.label(),
                data.tooltip(),
                data.allowFutureDates());
    }

    private AddDropDownQuestion adAdd(long userId, QuestionRequest.DropdownQuestionData data) {
        Instant now = Instant.now();
        return new AddDropDownQuestion(
                null,
                userId,
                now,
                data.label(),
                data.tooltip(),
                getReference(ValueSet.class, data.valueSet()),
                getReference(ValueEntity.class, data.defaultValue()),
                data.isMultiSelect());
    }

    private AddNumericQuestion asAdd(long userId, QuestionRequest.NumericQuestionData data) {
        Instant now = Instant.now();
        return new AddNumericQuestion(
                null,
                userId,
                now,
                data.label(),
                data.tooltip(),
                data.minValue(),
                data.maxValue(),
                getReference(ValueSet.class, data.unitValueSet()));
    }

    private <T> T getReference(Class<T> clazz, Object primaryKey) {
        if (primaryKey == null) {
            return null;
        }
        return entityManager.getReference(clazz, primaryKey);
    }

}
