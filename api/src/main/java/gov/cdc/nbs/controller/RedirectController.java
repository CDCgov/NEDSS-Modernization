package gov.cdc.nbs.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RedirectController {
    // NBS parameter keys
    private static final String NBS_LAST_NAME = "patientSearchVO.lastName";
    private static final String NBS_FIRST_NAME = "patientSearchVO.firstName";
    private static final String NBS_BIRTH_TIME = "patientSearchVO.birthTime";// MM/dd/YYYY format
    private static final String NBS_CURRENT_SEX = "patientSearchVO.currentSex";// M, F, or U
    private static final String NBS_EVENT_ID_TYPE = "patientSearchVO.actType";
    private static final String NBS_EVENT_ID = "patientSearchVO.actId";
    private static final String NBS_PATIENT_ID = "patientSearchVO.localID";
    // Expected keys
    private static final String LAST_NAME = "lastName";
    private static final String FIRST_NAME = "firstName";
    private static final String BIRTH_TIME = "dob";// MM/dd/YYYY format
    private static final String CURRENT_SEX = "sex";// M, F, or U
    private static final String EVENT_ID_TYPE = "eventType";
    private static final String EVENT_ID = "eventId";
    private static final String PATIENT_ID = "patientId";

    @GetMapping("/nbs/MyTaskList1.do")
    public RedirectView redirectAdvancedSearch(HttpServletRequest request, RedirectAttributes attributes) {
        return new RedirectView("/search");
    }

    @PostMapping("/nbs/HomePage.do") // proxy verifies path contains: ?method=patientSearchSubmit
    public RedirectView redirectSimpleSearch(
            HttpServletRequest request,
            RedirectAttributes attributes,
            @RequestParam Map<String, String> incomingParams) {
        attributes.addAllAttributes(getSearchAttributes(incomingParams));
        return new RedirectView("/");
    }

    /**
     * Maps the www-form-urlencoded search parameters to our search params
     */
    private Map<String, String> getSearchAttributes(Map<String, String> map) {

        var searchFields = new HashMap<String, String>();
        if (StringUtils.hasText(map.get(NBS_LAST_NAME))) {
            searchFields.put(LAST_NAME, map.get(NBS_LAST_NAME));
        }
        if (StringUtils.hasText(map.get(NBS_FIRST_NAME))) {
            searchFields.put(FIRST_NAME, map.get(NBS_FIRST_NAME));
        }
        if (StringUtils.hasText(map.get(NBS_BIRTH_TIME))) {
            searchFields.put(BIRTH_TIME, map.get(NBS_BIRTH_TIME));
        }
        if (StringUtils.hasText(map.get(NBS_CURRENT_SEX))) {
            searchFields.put(CURRENT_SEX, map.get(NBS_CURRENT_SEX));
        }
        if (StringUtils.hasText(map.get(NBS_EVENT_ID_TYPE))) {
            searchFields.put(EVENT_ID_TYPE, map.get(NBS_EVENT_ID_TYPE));
        }
        if (StringUtils.hasText(map.get(NBS_EVENT_ID))) {
            searchFields.put(EVENT_ID, map.get(NBS_EVENT_ID));
        }
        if (StringUtils.hasText(map.get(NBS_PATIENT_ID))) {
            searchFields.put(PATIENT_ID, map.get(NBS_PATIENT_ID));
        }
        return searchFields;
    }
}
