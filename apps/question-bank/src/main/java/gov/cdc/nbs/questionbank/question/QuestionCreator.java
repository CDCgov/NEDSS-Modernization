package gov.cdc.nbs.questionbank.question;

import java.time.Clock;
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
import gov.cdc.nbs.questionbank.kafka.producer.QuestionCreatedEventProducer;
import gov.cdc.nbs.questionbank.question.QuestionRequest.CreateTextQuestion;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.AddDateQuestion;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.AddDropDownQuestion;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.AddNumericQuestion;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.AddTextQuestion;
import gov.cdc.nbs.questionbank.question.repository.DisplayElementRepository;

@Component
class QuestionCreator {

    private final DisplayElementRepository displayElementRepository;
    private final EntityManager entityManager;
    private final QuestionCreatedEventProducer createEvent;
    private final Clock clock;

    public QuestionCreator(
            DisplayElementRepository displayElementRepository,
            EntityManager entityManager,
            QuestionCreatedEventProducer createEvent,
            Clock clock) {
        this.displayElementRepository = displayElementRepository;
        this.entityManager = entityManager;
        this.createEvent = createEvent;
        this.clock = clock;
    }

    @Transactional
    TextQuestionEntity create(QuestionRequest.CreateTextQuestion data, long userId) {
        TextQuestionEntity entity = new TextQuestionEntity(asAdd(userId, data));
        entity = displayElementRepository.save(entity);
        createEvent.send(entity);
        return entity;
    }

    @Transactional
    DateQuestionEntity create(QuestionRequest.CreateDateQuestion data, long userId) {
        DateQuestionEntity entity = new DateQuestionEntity(asAdd(userId, data));
        entity = displayElementRepository.save(entity);
        createEvent.send(entity);
        return entity;
    }

    @Transactional
    DropdownQuestionEntity create(QuestionRequest.CreateDropdownQuestion data, long userId) {
        DropdownQuestionEntity entity = new DropdownQuestionEntity(adAdd(userId, data));
        entity = displayElementRepository.save(entity);
        createEvent.send(entity);
        return entity;
    }

    @Transactional
    NumericQuestionEntity create(QuestionRequest.CreateNumericQuestion data, long userId) {
        NumericQuestionEntity entity = new NumericQuestionEntity(asAdd(userId, data));
        entity = displayElementRepository.save(entity);
        createEvent.send(entity);
        return entity;
    }

    private AddTextQuestion asAdd(long userId, CreateTextQuestion data) {
        return new AddTextQuestion(
                null,
                userId,
                Instant.now(clock),
                data.label(),
                data.tooltip(),
                data.maxLength(),
                data.placeholder(),
                data.defaultValue(),
                data.codeSet());
    }

    private AddDateQuestion asAdd(long userId, QuestionRequest.CreateDateQuestion data) {
        return new AddDateQuestion(
                null,
                userId,
                Instant.now(clock),
                data.label(),
                data.tooltip(),
                data.allowFutureDates(),
                data.codeSet());
    }

    private AddDropDownQuestion adAdd(long userId, QuestionRequest.CreateDropdownQuestion data) {
        return new AddDropDownQuestion(
                null,
                userId,
                Instant.now(clock),
                data.label(),
                data.tooltip(),
                getReference(ValueSet.class, data.valueSet()),
                getReference(ValueEntity.class, data.defaultValue()),
                data.isMultiSelect(),
                data.codeSet());
    }

    private AddNumericQuestion asAdd(long userId, QuestionRequest.CreateNumericQuestion data) {
        return new AddNumericQuestion(
                null,
                userId,
                Instant.now(clock),
                data.label(),
                data.tooltip(),
                data.minValue(),
                data.maxValue(),
                data.defaultValue(),
                getReference(ValueSet.class, data.unitValueSet()),
                data.codeSet());
    }

    private <T> T getReference(Class<T> clazz, Object primaryKey) {
        if (primaryKey == null) {
            return null;
        }
        return entityManager.getReference(clazz, primaryKey);
    }

}
