package gov.cdc.nbs.questionbank.question;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

class QuestionPageTest {

    @Test
    void should_create_pageable() {
        QuestionPage page = new QuestionPage(10, 0);
        Pageable pageable = QuestionPage.toPageable(page);
        assertNotNull(pageable);
    }

    @Test
    void shouldnt_throw_exception() {
        QuestionPage page = null;
        Pageable pageable = QuestionPage.toPageable(page);
        assertNull(pageable);
    }
}
