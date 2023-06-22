package gov.cdc.nbs.questionbank.question;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort.Direction;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;
import gov.cdc.nbs.questionbank.question.request.FindQuestionRequest.PageData;

@ExtendWith(MockitoExtension.class)
class QuestionFinderTest {

    @Mock
    private WaQuestionRepository repository;

    @Mock
    private QuestionMapper mapper;

    private QuestionFinder finder = new QuestionFinder(repository, mapper, 25);

    @Test
    void should_create_pageable() {
        var pageable = finder.toPageable(null);
        assertNotNull(pageable);
        assertEquals(25, pageable.getPageSize());
    }

    @Test
    void should_limit_size() {
        PageData pageData = new PageData(0, 100, Direction.ASC, "id");
        var pageable = finder.toPageable(pageData);
        assertNotNull(pageable);
        assertEquals(25, pageable.getPageSize());
        var sort = pageable.getSort().get().findFirst().get();
        assertEquals(Direction.ASC, sort.getDirection());
        assertEquals("id", sort.getProperty());
    }

    @Test
    void should_use_size() {
        PageData pageData = new PageData(0, 3, Direction.DESC, "prop");
        var pageable = finder.toPageable(pageData);
        assertNotNull(pageable);
        assertEquals(3, pageable.getPageSize());
        var sort = pageable.getSort().get().findFirst().get();
        assertEquals(Direction.DESC, sort.getDirection());
        assertEquals("prop", sort.getProperty());
    }
}
