package gov.cdc.nbs.search.redirect.simple;

//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gov.cdc.nbs.option.Option;
import gov.cdc.nbs.search.criteria.date.DateCriteria;
//import gov.cdc.nbs.time.json.FormattedLocalDateJsonSerializer;

//import java.time.LocalDate;

record SimplePatientSearchCriteria(
        SimplePatientSearchNameCriteria name,
        //@JsonSerialize(using = FormattedLocalDateJsonSerializer.class) LocalDate dateOfBirth,
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
