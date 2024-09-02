package gov.cdc.nbs.dibbs.nbs_deduplication.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gov.cdc.nbs.dibbs.nbs_deduplication.model.EntityLocatorParticipationDto;
import gov.cdc.nbs.dibbs.nbs_deduplication.model.PersonContainer;
import gov.cdc.nbs.dibbs.nbs_deduplication.model.person.PersonDto;
import gov.cdc.nbs.dibbs.nbs_deduplication.model.person.PersonNameDto;
import gov.cdc.nbs.dibbs.nbs_deduplication.model.PostalLocatorDto;
import gov.cdc.nbs.dibbs.nbs_deduplication.service.FhirConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class FhirConverterTest {

  private ObjectMapper objectMapper;
  private FhirConverter fhirConverter;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
    fhirConverter = new FhirConverter(objectMapper);
  }

  @Test
  void testConvertPersonToFhirFormat() {
    PersonDto personDto = new PersonDto();
    personDto.setBirthTime(new Timestamp(new Date().getTime()));
    PersonNameDto personNameDto = getPersonNameDto();
    PostalLocatorDto postalLocatorDto = getPostalLocatorDto();

    EntityLocatorParticipationDto locatorDto = new EntityLocatorParticipationDto();
    locatorDto.setThePostalLocatorDto(postalLocatorDto);

    PersonContainer personContainer = new PersonContainer();
    personContainer.setThePersonDto(personDto);
    personContainer.setThePersonNameDtoCollection(Collections.singletonList(personNameDto));
    personContainer.setTheEntityLocatorParticipationDtoCollection(Collections.singletonList(locatorDto));

    String fhirJson = fhirConverter.convertPersonToFhirFormat(personContainer);
    String finalJson = getFinalJson(personDto).toString();
    assertEquals(finalJson, fhirJson);
  }

  private PersonNameDto getPersonNameDto() {
    PersonNameDto personNameDto = new PersonNameDto();
    personNameDto.setFirstNm("John");
    personNameDto.setLastNm("Doe");
    return personNameDto;
  }

  private PostalLocatorDto getPostalLocatorDto() {
    PostalLocatorDto postalLocatorDto = new PostalLocatorDto();
    postalLocatorDto.setStreetAddr1("123 Main St");
    postalLocatorDto.setStreetAddr2("Apt 4B");
    postalLocatorDto.setCityDescTxt("Springfield");
    postalLocatorDto.setStateCd("IL");
    postalLocatorDto.setZipCd("1234");
    postalLocatorDto.setCntryDescTxt("USA");
    return postalLocatorDto;
  }

  private ObjectNode getFinalJson(PersonDto personDto) {
    ObjectNode fhirObject = objectMapper.createObjectNode();
    fhirObject.put("resourceType", "Patient");

    ObjectNode nameObject = objectMapper.createObjectNode();
    nameObject.put("family", "Doe");
    nameObject.putArray("given").add("John");

    fhirObject.putArray("name").add(nameObject);

    ObjectNode addressObject = objectMapper.createObjectNode();
    addressObject.putArray("line").add("123 Main St").add("Apt 4B");
    addressObject.put("city", "Springfield");
    addressObject.put("state", "IL");
    addressObject.put("postalCode", "1234");
    addressObject.put("country", "USA");

    fhirObject.putArray("address").add(addressObject);

    String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(personDto.getBirthTime());
    fhirObject.put("birthDate", formattedDate);

    ObjectNode entryNode = objectMapper.createObjectNode();
    entryNode.set("resource", fhirObject);

    ObjectNode bundleNode = objectMapper.createObjectNode();
    bundleNode.put("resourceType", "Bundle");
    bundleNode.putArray("entry").add(entryNode);

    ObjectNode finalJson = objectMapper.createObjectNode();
    finalJson.set("bundle", bundleNode);
    finalJson.put("use_enhanced", true);
    return finalJson;
  }
}
