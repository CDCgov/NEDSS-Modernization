package gov.cdc.nbs.dibbs.nbs_deduplication.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import gov.cdc.nbs.dibbs.nbs_deduplication.model.person.PersonDto;
import gov.cdc.nbs.dibbs.nbs_deduplication.model.person.PersonEthnicGroupDto;
import gov.cdc.nbs.dibbs.nbs_deduplication.model.person.PersonNameDto;
import gov.cdc.nbs.dibbs.nbs_deduplication.model.person.PersonRaceDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Setter
@Getter

public class PersonContainer extends LdfBaseContainer implements Serializable {

    public PersonDto thePersonDto = new PersonDto();
    public Collection<PersonNameDto> thePersonNameDtoCollection = new ArrayList<>();
    public Collection<EntityLocatorParticipationDto> theEntityLocatorParticipationDtoCollection = new ArrayList<>();
    @JsonIgnore
    public Collection<PersonRaceDto> thePersonRaceDtoCollection = new ArrayList<>();

    @JsonIgnore
    public Collection<PersonEthnicGroupDto> thePersonEthnicGroupDtoCollection = new ArrayList<>();

    @JsonIgnore
    public Collection<EntityIdDto> theEntityIdDtoCollection = new ArrayList<>();

    @JsonIgnore
    public Collection<ParticipationDto> theParticipationDtoCollection = new ArrayList<>();

    @JsonIgnore
    public Collection<RoleDto> theRoleDtoCollection = new ArrayList<>();

    @JsonIgnore
    private String defaultJurisdictionCd;

    @JsonIgnore
    private boolean isExt = false;

    @JsonIgnore
    private boolean isMPRUpdateValid = true;

    @JsonIgnore
    private String localIdentifier;

    @JsonIgnore
    private String role;

    @JsonIgnore
    private String addReasonCode;

    /**
     * NEW VARIABLE
     * */

    private Boolean patientMatchedFound;

}
