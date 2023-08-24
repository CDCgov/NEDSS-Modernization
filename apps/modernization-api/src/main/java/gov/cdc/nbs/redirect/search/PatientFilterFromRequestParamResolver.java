package gov.cdc.nbs.redirect.search;

import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.exception.RedirectionException;
import gov.cdc.nbs.graphql.filter.PatientFilter;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.time.FlexibleLocalDateConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.format.DateTimeParseException;
import java.util.Map;

@Component
public class PatientFilterFromRequestParamResolver {

    private static final String NBS_LAST_NAME = "patientSearchVO.lastName";
    private static final String NBS_FIRST_NAME = "patientSearchVO.firstName";
    private static final String NBS_DATE_OF_BIRTH = "patientSearchVO.birthTime";
    private static final String NBS_SEX = "patientSearchVO.currentSex";
    private static final String NBS_ID = "patientSearchVO.localID";
    private static final String NBS_EVENT_TYPE = "patientSearchVO.actType";
    private static final String NBS_EVENT_ID = "patientSearchVO.actId";
    private static final String VACCINE_TYPE = "P10006";
    private static final String TREATMENT_TYPE = "P10005";


    public PatientFilter resolve(final Map<String, String> map) {
        var filter = new PatientFilter(RecordStatus.ACTIVE);
        if (StringUtils.hasText(map.get(NBS_LAST_NAME))) {
            filter.setLastName(map.get(NBS_LAST_NAME));
        }
        if (StringUtils.hasText(map.get(NBS_FIRST_NAME))) {
            filter.setFirstName(map.get(NBS_FIRST_NAME));
        }

        try {
            if (StringUtils.hasText(map.get(NBS_DATE_OF_BIRTH))) {
                filter.setDateOfBirth(FlexibleLocalDateConverter.fromString(map.get(NBS_DATE_OF_BIRTH)));
            }
            if (StringUtils.hasText(map.get(NBS_SEX))) {
                filter.setGender(Gender.valueOf(map.get(NBS_SEX)));
            }
            if (StringUtils.hasText(map.get(NBS_ID))) {
                filter.setId(map.get(NBS_ID));
            }
            if (StringUtils.hasText(map.get(NBS_EVENT_TYPE)) && StringUtils.hasText(map.get(NBS_EVENT_ID))) {
                var type = map.get(NBS_EVENT_TYPE);
                if (type.equals(VACCINE_TYPE)) {
                    filter.setVaccinationId(map.get(NBS_EVENT_ID));
                } else if (type.equals(TREATMENT_TYPE)) {
                    filter.setTreatmentId(map.get(NBS_EVENT_ID));
                }
            }
        } catch (DateTimeParseException | IllegalArgumentException e) {
            throw new RedirectionException("Failed to generate patient filter from search params");
        }

        if (filterIsEmpty(filter)) {
            return null;
        }
        return filter;
    }

    private boolean filterIsEmpty(PatientFilter filter) {
        return filter.getLastName() == null
                && filter.getFirstName() == null
                && filter.getDateOfBirth() == null
                && filter.getGender() == null
                && filter.getId() == null
                && filter.getVaccinationId() == null
                && filter.getTreatmentId() == null;
    }
}
