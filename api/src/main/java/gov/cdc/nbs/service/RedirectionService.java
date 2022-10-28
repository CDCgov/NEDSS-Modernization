package gov.cdc.nbs.service;

import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RedirectionService {

    private static final Map<String, String> fieldMappings = Map.ofEntries(
            entry("patientSearchVO.lastName", "lastName"),
            entry("patientSearchVO.firstName", "firstName"),
            entry("patientSearchVO.birthTime", "DateOfBirth"),
            entry("patientSearchVO.currentSex", "sex"),
            entry("patientSearchVO.actType", "eventType"),
            entry("patientSearchVO.actId", "eventId"),
            entry("patientSearchVO.localID", "id")

    );

    /**
     * Maps the www-form-urlencoded search parameters to our search params
     */
    public Map<String, String> getSearchAttributes(Map<String, String> map) {
        var searchFields = new HashMap<String, String>();
        fieldMappings.forEach((NBSKey, Key) -> {
            if (StringUtils.hasText(map.get(NBSKey))) {
                searchFields.put(Key, map.get(NBSKey));
            }
        });
        return searchFields;
    }
}
