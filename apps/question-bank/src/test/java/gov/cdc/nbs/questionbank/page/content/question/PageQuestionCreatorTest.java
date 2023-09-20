package gov.cdc.nbs.questionbank.page.content.question;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;

import gov.cdc.nbs.questionbank.page.content.question.exception.OrderQuestionException;
import gov.cdc.nbs.questionbank.page.content.question.request.OrderQuestionRequest;
import gov.cdc.nbs.questionbank.page.content.question.response.OrderQuestionResponse;
import gov.cdc.nbs.questionbank.page.content.tab.repository.WaUiMetaDataRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.question.TextQuestionEntity;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.content.question.request.AddQuestionRequest;
import gov.cdc.nbs.questionbank.page.exception.AddQuestionException;
import gov.cdc.nbs.questionbank.question.exception.QuestionNotFoundException;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;
import gov.cdc.nbs.questionbank.support.QuestionEntityMother;

@ExtendWith(MockitoExtension.class)
class PageQuestionCreatorTest {

    @Mock
    private WaQuestionRepository questionRepository;

    @Mock
    private WaUiMetadataRepository uiMetadatumRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private WaUiMetaDataRepository waUiMetaDataRepository;

    @InjectMocks
    private PageQuestionCreator contentManager;

    @Test
    void should_add_question_to_page() {
        // given a request to add a question to a page
        var request = new AddQuestionRequest(123L, 12);

        // and a valid page id
        Long pageId = 321L;
        when(entityManager.getReference(WaTemplate.class, pageId)).thenReturn(new WaTemplate());

        // and a valid question
        TextQuestionEntity textQuestion = QuestionEntityMother.textQuestion();
        when(questionRepository.findById(request.questionId())).thenReturn(Optional.of(textQuestion));

        // and a valid userId
        Long userId = 999L;

        // and the question is not already associated with the page
        when(uiMetadatumRepository.countByPageAndQuestionIdentifier(pageId, textQuestion.getQuestionIdentifier()))
                .thenReturn(0L);

        // when an add question request is processed
        ArgumentCaptor<WaUiMetadata> captor = ArgumentCaptor.forClass(WaUiMetadata.class);
        when(uiMetadatumRepository.save(captor.capture())).thenAnswer(m -> {
            WaUiMetadata savedMetadatum = m.getArgument(0);
            savedMetadatum.setId(77L);
            return savedMetadatum;
        });
        Long newId = contentManager.addQuestion(pageId, request, userId);

        // then the question is added to the page
        verify(uiMetadatumRepository, times(1)).save(Mockito.any());
        assertNotNull(newId);
    }

    @Test
    void should_not_allow_duplicate_question() {
        // given a request to add a question to a page
        var request = new AddQuestionRequest(123L, 12);

        // and a valid page id
        Long pageId = 321L;
        when(entityManager.getReference(WaTemplate.class, pageId)).thenReturn(new WaTemplate());

        // and a valid question
        TextQuestionEntity textQuestion = QuestionEntityMother.textQuestion();
        when(questionRepository.findById(request.questionId())).thenReturn(Optional.of(textQuestion));

        // and a valid userId
        Long userId = 999L;

        // and the question already exists on the page
        when(uiMetadatumRepository.countByPageAndQuestionIdentifier(pageId, textQuestion.getQuestionIdentifier()))
                .thenReturn(1L);

        // when an add question request is processed 
        // then an exception is thrown
        assertThrows(AddQuestionException.class, () -> contentManager.addQuestion(pageId, request, userId));
        verify(uiMetadatumRepository, times(0)).save(Mockito.any());
    }

    @Test
    void should_throw_exception_null_page() {
        // given a request to add a question to a page
        var request = new AddQuestionRequest(123L, 12);

        // and a null page id
        Long pageId = null;

        // when an add question request is processed 
        // then an exception is thrown
        assertThrows(AddQuestionException.class, () -> contentManager.addQuestion(pageId, request, 1L));
        verify(uiMetadatumRepository, times(0)).save(Mockito.any());
    }

    @Test
    void should_throw_exception_null_question() {
        // given a request to add a question to a page with a null questionId
        var request = new AddQuestionRequest(null, 12);

        // and a valid page id
        Long pageId = 321L;
        when(entityManager.getReference(WaTemplate.class, pageId)).thenReturn(new WaTemplate());

        // when an add question request is processed 
        // then an exception is thrown
        assertThrows(QuestionNotFoundException.class, () -> contentManager.addQuestion(pageId, request, 1L));
        verify(uiMetadatumRepository, times(0)).save(Mockito.any());
    }

    @Test
    void should_limit_order_nbr_to_max_plus_one() {
        // given a request to add a question to a page
        var request = new AddQuestionRequest(123L, 99);

        // and a valid page id
        Long pageId = 321L;
        when(entityManager.getReference(WaTemplate.class, pageId)).thenReturn(new WaTemplate());

        // and a valid question
        TextQuestionEntity textQuestion = QuestionEntityMother.textQuestion();
        when(questionRepository.findById(request.questionId())).thenReturn(Optional.of(textQuestion));

        // and a valid userId
        Long userId = 999L;

        // and the question is not already associated with the page
        when(uiMetadatumRepository.countByPageAndQuestionIdentifier(pageId, textQuestion.getQuestionIdentifier()))
                .thenReturn(0L);

        // and the current max order nbr is 3
        when(uiMetadatumRepository.findMaxOrderNbrForPage(pageId)).thenReturn(3);

        // when an add question request is processed
        ArgumentCaptor<WaUiMetadata> captor = ArgumentCaptor.forClass(WaUiMetadata.class);
        when(uiMetadatumRepository.save(captor.capture())).thenAnswer(m -> {
            WaUiMetadata savedMetadatum = m.getArgument(0);
            savedMetadatum.setId(77L);
            return savedMetadatum;
        });
        Long newId = contentManager.addQuestion(pageId, request, userId);

        // then the question is added to the page
        verify(uiMetadatumRepository, times(1)).save(Mockito.any());
        assertNotNull(newId);
        assertEquals(4, captor.getValue().getOrderNbr().intValue());
    }

    @Test
    void should_not_limit_order_nbr_if_less_than_max() {
        // given a request to add a question to a page
        var request = new AddQuestionRequest(123L, 99);

        // and a valid page id
        Long pageId = 321L;
        when(entityManager.getReference(WaTemplate.class, pageId)).thenReturn(new WaTemplate());

        // and a valid question
        TextQuestionEntity textQuestion = QuestionEntityMother.textQuestion();
        when(questionRepository.findById(request.questionId())).thenReturn(Optional.of(textQuestion));

        // and a valid userId
        Long userId = 999L;

        // and the question is not already associated with the page
        when(uiMetadatumRepository.countByPageAndQuestionIdentifier(pageId, textQuestion.getQuestionIdentifier()))
                .thenReturn(0L);

        // and the current max order nbr is 100
        when(uiMetadatumRepository.findMaxOrderNbrForPage(pageId)).thenReturn(100);

        // when an add question request is processed
        ArgumentCaptor<WaUiMetadata> captor = ArgumentCaptor.forClass(WaUiMetadata.class);
        when(uiMetadatumRepository.save(captor.capture())).thenAnswer(m -> {
            WaUiMetadata savedMetadatum = m.getArgument(0);
            savedMetadatum.setId(77L);
            return savedMetadatum;
        });
        Long newId = contentManager.addQuestion(pageId, request, userId);

        // then the question is added to the page
        verify(uiMetadatumRepository, times(1)).save(Mockito.any());
        assertNotNull(newId);
        assertEquals(99, captor.getValue().getOrderNbr().intValue());
    }



    @Test
    void orderQuestionForwardOrdering() throws OrderQuestionException {
        // Create the OrderQuestionRequest
        OrderQuestionRequest orderQuestionRequest = new OrderQuestionRequest(
                123L, 2, 1, 1, 1, 3);

        // Create a list for order numbers
        List<Integer> orderNumberList = new ArrayList<>();
        orderNumberList.add(3);
        orderNumberList.add(58);
        orderNumberList.add(116);
        orderNumberList.add(157);

        // Mock the behavior of waUiMetaDataRepository methods
        when(waUiMetaDataRepository.getOrderNumberList(100L))
                .thenReturn(orderNumberList);

        when(waUiMetaDataRepository.getSectionOrderNumberList(100L, 58, 116))
                .thenReturn(orderNumberList);

        when(waUiMetaDataRepository.getSubSectionOrderNumberList(100L, 3, 58))
                .thenReturn(orderNumberList);

        when(waUiMetaDataRepository.getQuestionOrderNumberList(100L, 3, 58))
                .thenReturn(orderNumberList);

        when(waUiMetaDataRepository.getMaxOrderNumber(100L))
                .thenReturn(250);

        // Initialize the contentManager
        PageQuestionCreator contentManager = new PageQuestionCreator(
                questionRepository,
                uiMetadatumRepository,
                entityManager,
                 waUiMetaDataRepository
        );

        // Call the method under test
        OrderQuestionResponse orderQuestionResponse =
                contentManager.orderQuestion(100L, orderQuestionRequest);

        // Assertions
        Assertions.assertEquals("The question moved from 1 to 3 successfully", orderQuestionResponse.message());
    }


    @Test
    void orderQuestionReverseOrdering() throws OrderQuestionException {
        // Create the OrderQuestionRequest
        OrderQuestionRequest orderQuestionRequest = new OrderQuestionRequest(
                123L, 2, 1, 1, 3, 1);

        // Create a list for order numbers
        List<Integer> orderNumberList = new ArrayList<>();
        orderNumberList.add(3);
        orderNumberList.add(58);
        orderNumberList.add(116);
        orderNumberList.add(157);

        // Mock the behavior of waUiMetaDataRepository methods
        when(waUiMetaDataRepository.getOrderNumberList(100L))
                .thenReturn(orderNumberList);

        when(waUiMetaDataRepository.getSectionOrderNumberList(100L, 58, 116))
                .thenReturn(orderNumberList);

        when(waUiMetaDataRepository.getSubSectionOrderNumberList(100L, 3, 58))
                .thenReturn(orderNumberList);

        when(waUiMetaDataRepository.getQuestionOrderNumberList(100L, 3, 58))
                .thenReturn(orderNumberList);

        when(waUiMetaDataRepository.getMaxOrderNumber(100L))
                .thenReturn(250);

        // Initialize the contentManager
        PageQuestionCreator contentManager = new PageQuestionCreator(
                questionRepository,
                uiMetadatumRepository,
                entityManager,
                waUiMetaDataRepository
        );

        // Call the method under test
        OrderQuestionResponse orderQuestionResponse =
                contentManager.orderQuestion(100L, orderQuestionRequest);

        // Assertions
        Assertions.assertEquals("The question moved from 3 to 1 successfully", orderQuestionResponse.message());
    }


    @Test
    void orderQuestionEqualException() throws OrderQuestionException {
        // Create the OrderQuestionRequest
        OrderQuestionRequest orderQuestionRequest = new OrderQuestionRequest(
                123L, 2, 1, 1, 3, 3);

        // Create a list for order numbers
        List<Integer> orderNumberList = new ArrayList<>();
        orderNumberList.add(3);
        orderNumberList.add(58);
        orderNumberList.add(116);
        orderNumberList.add(157);

        // Mock the behavior of waUiMetaDataRepository methods
        when(waUiMetaDataRepository.getOrderNumberList(100L))
                .thenReturn(orderNumberList);

        when(waUiMetaDataRepository.getSectionOrderNumberList(100L, 58, 116))
                .thenReturn(orderNumberList);

        when(waUiMetaDataRepository.getSubSectionOrderNumberList(100L, 3, 58))
                .thenReturn(orderNumberList);

        when(waUiMetaDataRepository.getQuestionOrderNumberList(100L, 3, 58))
                .thenReturn(orderNumberList);

        when(waUiMetaDataRepository.getMaxOrderNumber(100L))
                .thenReturn(250);

        // Initialize the contentManager
        PageQuestionCreator contentManager = new PageQuestionCreator(
                questionRepository,
                uiMetadatumRepository,
                entityManager,
                waUiMetaDataRepository
        );

        Assertions.assertThrows(OrderQuestionException.class, () -> contentManager.orderQuestion(100L,
                orderQuestionRequest));

    }


    @Test
    void orderQuestionInvalidQuestionException() throws OrderQuestionException {
        // Create the OrderQuestionRequest
        OrderQuestionRequest orderQuestionRequest = new OrderQuestionRequest(
                123L, 2, 1, 1, 53, 3);

        // Create a list for order numbers
        List<Integer> orderNumberList = new ArrayList<>();
        orderNumberList.add(3);
        orderNumberList.add(58);
        orderNumberList.add(116);
        orderNumberList.add(157);

        // Mock the behavior of waUiMetaDataRepository methods
        when(waUiMetaDataRepository.getOrderNumberList(100L))
                .thenReturn(orderNumberList);

        when(waUiMetaDataRepository.getSectionOrderNumberList(100L, 58, 116))
                .thenReturn(orderNumberList);

        when(waUiMetaDataRepository.getSubSectionOrderNumberList(100L, 3, 58))
                .thenReturn(orderNumberList);

        when(waUiMetaDataRepository.getQuestionOrderNumberList(100L, 3, 58))
                .thenReturn(orderNumberList);

        when(waUiMetaDataRepository.getMaxOrderNumber(100L))
                .thenReturn(250);

        // Initialize the contentManager
        PageQuestionCreator contentManager = new PageQuestionCreator(
                questionRepository,
                uiMetadatumRepository,
                entityManager,
                waUiMetaDataRepository
        );

        Assertions.assertThrows(OrderQuestionException.class, () -> contentManager.orderQuestion(100L,
                orderQuestionRequest));

    }



    @Test
    void orderQuestionInvalidSubSectionException() throws OrderQuestionException {
        // Create the OrderQuestionRequest
        OrderQuestionRequest orderQuestionRequest = new OrderQuestionRequest(
                123L, 2, 1, 5, 3, 3);

        // Create a list for order numbers
        List<Integer> orderNumberList = new ArrayList<>();
        orderNumberList.add(3);
        orderNumberList.add(58);
        orderNumberList.add(116);
        orderNumberList.add(157);

        // Mock the behavior of waUiMetaDataRepository methods
        when(waUiMetaDataRepository.getOrderNumberList(100L))
                .thenReturn(orderNumberList);

        when(waUiMetaDataRepository.getSectionOrderNumberList(100L, 58, 116))
                .thenReturn(orderNumberList);

        when(waUiMetaDataRepository.getSubSectionOrderNumberList(100L, 3, 58))
                .thenReturn(orderNumberList);

        when(waUiMetaDataRepository.getMaxOrderNumber(100L))
                .thenReturn(250);

        // Initialize the contentManager
        PageQuestionCreator contentManager = new PageQuestionCreator(
                questionRepository,
                uiMetadatumRepository,
                entityManager,
                waUiMetaDataRepository
        );

        Assertions.assertThrows(OrderQuestionException.class, () -> contentManager.orderQuestion(100L,
                orderQuestionRequest));

    }


    @Test
    void orderQuestionSectionPositionException() throws OrderQuestionException {
        // Create the OrderQuestionRequest
        OrderQuestionRequest orderQuestionRequest = new OrderQuestionRequest(
                123L, 2, 5, 1, 3, 3);

        // Create a list for order numbers
        List<Integer> orderNumberList = new ArrayList<>();
        orderNumberList.add(3);
        orderNumberList.add(58);
        orderNumberList.add(116);
        orderNumberList.add(157);

        // Mock the behavior of waUiMetaDataRepository methods
        when(waUiMetaDataRepository.getOrderNumberList(100L))
                .thenReturn(orderNumberList);

        when(waUiMetaDataRepository.getSectionOrderNumberList(100L, 58, 116))
                .thenReturn(orderNumberList);

        when(waUiMetaDataRepository.getMaxOrderNumber(100L))
                .thenReturn(250);

        // Initialize the contentManager
        PageQuestionCreator contentManager = new PageQuestionCreator(
                questionRepository,
                uiMetadatumRepository,
                entityManager,
                waUiMetaDataRepository
        );

        Assertions.assertThrows(OrderQuestionException.class, () -> contentManager.orderQuestion(100L,
                orderQuestionRequest));

    }

    @Test
    void orderQuestionInvalidTabPosition() throws OrderQuestionException {
        // Create the OrderQuestionRequest
        OrderQuestionRequest orderQuestionRequest = new OrderQuestionRequest(
                123L, 5, 1, 1, 3, 3);

        // Create a list for order numbers
        List<Integer> orderNumberList = new ArrayList<>();
        orderNumberList.add(3);
        orderNumberList.add(58);
        orderNumberList.add(116);
        orderNumberList.add(157);

        // Mock the behavior of waUiMetaDataRepository methods
        when(waUiMetaDataRepository.getOrderNumberList(100L))
                .thenReturn(orderNumberList);

        when(waUiMetaDataRepository.getMaxOrderNumber(100L))
                .thenReturn(250);

        // Initialize the contentManager
        PageQuestionCreator contentManager = new PageQuestionCreator(
                questionRepository,
                uiMetadatumRepository,
                entityManager,
                waUiMetaDataRepository
        );

        Assertions.assertThrows(OrderQuestionException.class, () -> contentManager.orderQuestion(100L,
                orderQuestionRequest));

    }



}

