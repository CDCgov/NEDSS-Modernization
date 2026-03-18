package gov.cdc.nbs.search.redirect.simple;

import gov.cdc.nbs.search.criteria.text.TextCriteria;

record SimplePatientSearchNameCriteria(TextCriteria first, TextCriteria last) {}
