package gov.cdc.nbs.search.redirect.simple;

import gov.cdc.nbs.option.Option;
import gov.cdc.nbs.search.criteria.date.DateCriteria;

record SimplePatientSearchCriteria(
        SimplePatientSearchNameCriteria name,
        DateCriteria bornOn,
        Option gender,
        String id,
        String morbidity,
        String document,
        String stateCase,
        String abcCase,
        String cityCountyCase,
        String notification,
        String labReport,
        String accessionNumber,
        String investigation,
        String treatment,
        String vaccination) {
}
