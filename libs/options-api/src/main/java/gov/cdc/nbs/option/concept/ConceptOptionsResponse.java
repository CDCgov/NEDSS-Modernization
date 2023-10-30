package gov.cdc.nbs.option.concept;

import gov.cdc.nbs.option.Option;

import java.util.Collection;

public record ConceptOptionsResponse(String valueSet, Collection<Option> options) {

}
