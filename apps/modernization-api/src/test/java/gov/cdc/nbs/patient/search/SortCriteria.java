package gov.cdc.nbs.patient.search;

import org.springframework.data.domain.Sort;

record SortCriteria(Sort.Direction direction, String field) {
}
