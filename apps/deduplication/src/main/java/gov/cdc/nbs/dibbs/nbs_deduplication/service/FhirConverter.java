package gov.cdc.nbs.dibbs.nbs_deduplication.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gov.cdc.nbs.dibbs.nbs_deduplication.model.EntityLocatorParticipationDto;
import gov.cdc.nbs.dibbs.nbs_deduplication.model.PersonContainer;
import gov.cdc.nbs.dibbs.nbs_deduplication.model.person.PersonDto;
import gov.cdc.nbs.dibbs.nbs_deduplication.model.person.PersonNameDto;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Setter
public class FhirConverter {

  private final ObjectMapper mapper;

  public String convertPersonToFhirFormat(PersonContainer personContainer) {
    PersonDto personDto = personContainer.getThePersonDto();
    ObjectNode patient = mapper.createObjectNode();
    patient.put("resourceType", "Patient");
    Collection<PersonNameDto> nameDtos = personContainer.getThePersonNameDtoCollection();
    setPatientName(patient, nameDtos);
    Collection<EntityLocatorParticipationDto> locatorDtos =
        personContainer.getTheEntityLocatorParticipationDtoCollection();
    setPatientAddress(patient, locatorDtos);
    setPatientBirthDate(patient, personDto);
    return getFinalResponse(patient).toString();
  }

  private void setPatientName(ObjectNode patient, Collection<PersonNameDto> nameDtos) {
    if (nameDtos != null) {
      ArrayNode names = mapper.createArrayNode();
      nameDtos.stream()
          .map(nameDto -> {
            ObjectNode name = mapper.createObjectNode();
            name.set("family", mapper.valueToTree(nameDto.getLastNm()));
            ArrayNode givenNames = mapper.createArrayNode();
            givenNames.add(nameDto.getFirstNm());
            name.set("given", givenNames);
            return name;
          })
          .forEach(names::add);
      patient.set("name", names);
    }
  }

  private void setPatientAddress(ObjectNode patient, Collection<EntityLocatorParticipationDto> locatorDtos) {
    if (locatorDtos != null) {
      ArrayNode addresses = mapper.createArrayNode();
      locatorDtos.stream()
          .map(EntityLocatorParticipationDto::getThePostalLocatorDto)
          .filter(Objects::nonNull)
          .forEach(postalLocatorDto -> {
            ObjectNode address = mapper.createObjectNode();
            ArrayNode lines = mapper.createArrayNode();
            lines.add(postalLocatorDto.getStreetAddr1());
            lines.add(postalLocatorDto.getStreetAddr2());

            address.set("line", lines);
            address.set("city", mapper.valueToTree(postalLocatorDto.getCityDescTxt()));
            address.set("state", mapper.valueToTree(postalLocatorDto.getStateCd()));
            address.set("postalCode", mapper.valueToTree(postalLocatorDto.getZipCd()));
            address.set("country", mapper.valueToTree(postalLocatorDto.getCntryDescTxt()));
            addresses.add(address);
          });
      patient.set("address", addresses);
    }
  }

  private void setPatientBirthDate(ObjectNode patient, PersonDto personDto) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String formattedDate = sdf.format(personDto.getBirthTime());
    patient.set("birthDate", mapper.valueToTree(formattedDate));
  }

  private ObjectNode getFinalResponse(ObjectNode patient) {
    ObjectNode entry = mapper.createObjectNode();
    entry.set("resource", patient);
    ObjectNode bundle = mapper.createObjectNode();
    bundle.put("resourceType", "Bundle");

    ArrayNode entries = mapper.createArrayNode();
    entries.add(entry);
    bundle.set("entry", entries);
    ObjectNode finalJson = mapper.createObjectNode();
    finalJson.set("bundle", bundle);
    finalJson.put("use_enhanced", true);
    return finalJson;
  }

}
