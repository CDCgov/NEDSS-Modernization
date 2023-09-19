import { InvestigationFilter, LabReportFilter, PersonFilter } from 'generated/graphql/schema';
import { SearchCriteriaContext } from 'providers/SearchCriteriaContext';
import { ReactElement } from 'react';
import { SEARCH_TYPE } from '../AdvancedSearch';
import Chip from './Chip';

type ChipProps = {
    lastSearchType: SEARCH_TYPE | undefined;
    personFilter?: PersonFilter;
    handlePersonFilterChange: (personFilter: PersonFilter) => void;
    investigationFilter?: InvestigationFilter;
    handleInvestigationFilterChange: (investigationFilter: InvestigationFilter) => void;
    labReportFilter?: LabReportFilter;
    handleLabReportFilterChange: (labReportFilter: LabReportFilter) => void;
};
export const Chips = ({
    lastSearchType,
    personFilter: patientFilter,
    handlePersonFilterChange,
    investigationFilter,
    handleInvestigationFilterChange,
    labReportFilter
}: // setLabReportFilter
ChipProps) => {
    const renderPatientChips = (filter: PersonFilter): ReactElement => {
        return (
            <>
                <SearchCriteriaContext.Consumer>
                    {({ searchCriteria }) => (
                        <>
                            {filter.lastName ? (
                                <Chip
                                    name="LAST"
                                    value={filter.lastName}
                                    handleClose={() => handlePersonFilterChange({ ...filter, lastName: undefined })}
                                />
                            ) : null}
                            {filter.firstName ? (
                                <Chip
                                    name="FIRST"
                                    value={filter.firstName}
                                    handleClose={() => handlePersonFilterChange({ ...filter, firstName: undefined })}
                                />
                            ) : null}
                            {filter.dateOfBirth ? (
                                <Chip
                                    name="DOB"
                                    value={filter.dateOfBirth}
                                    handleClose={() => handlePersonFilterChange({ ...filter, dateOfBirth: undefined })}
                                />
                            ) : null}
                            {filter.gender ? (
                                <Chip
                                    name="SEX"
                                    value={filter.gender}
                                    handleClose={() => handlePersonFilterChange({ ...filter, gender: undefined })}
                                />
                            ) : null}
                            {filter.id ? (
                                <Chip
                                    name="SEX"
                                    value={filter.id}
                                    handleClose={() => handlePersonFilterChange({ ...filter, id: undefined })}
                                />
                            ) : null}
                            {filter.address ? (
                                <Chip
                                    name="ADDRESS"
                                    value={filter.address}
                                    handleClose={() => handlePersonFilterChange({ ...filter, address: undefined })}
                                />
                            ) : null}
                            {filter.city ? (
                                <Chip
                                    name="CITY"
                                    value={filter.city}
                                    handleClose={() => handlePersonFilterChange({ ...filter, city: undefined })}
                                />
                            ) : null}
                            {filter.state ? (
                                <Chip
                                    name="STATE"
                                    value={searchCriteria.states.find((s) => s.value === filter.state)?.name ?? ''}
                                    handleClose={() => handlePersonFilterChange({ ...filter, state: undefined })}
                                />
                            ) : null}
                            {filter.zip ? (
                                <Chip
                                    name="ZIP"
                                    value={filter.zip}
                                    handleClose={() => handlePersonFilterChange({ ...filter, zip: undefined })}
                                />
                            ) : null}
                            {filter.phoneNumber ? (
                                <Chip
                                    name="PHONENUMBER"
                                    value={filter.phoneNumber}
                                    handleClose={() => handlePersonFilterChange({ ...filter, phoneNumber: undefined })}
                                />
                            ) : null}
                            {filter.email ? (
                                <Chip
                                    name="EMAIL"
                                    value={filter.email}
                                    handleClose={() => handlePersonFilterChange({ ...filter, email: undefined })}
                                />
                            ) : null}
                            {filter.identification?.identificationType ? (
                                <Chip
                                    name="ID TYPE"
                                    value={
                                        searchCriteria.identificationTypes.find(
                                            (i) => i.id.code === filter.identification?.identificationType
                                        )?.codeDescTxt ?? ''
                                    }
                                    handleClose={() =>
                                        handlePersonFilterChange({ ...filter, identification: undefined })
                                    }
                                />
                            ) : null}
                            {filter.identification?.identificationNumber ? (
                                <Chip
                                    name="ID NUMBER"
                                    value={filter.identification.identificationNumber}
                                    handleClose={() =>
                                        handlePersonFilterChange({ ...filter, identification: undefined })
                                    }
                                />
                            ) : null}
                            {filter.ethnicity ? (
                                <Chip
                                    name="ETHNICITY"
                                    value={
                                        searchCriteria.ethnicities.find((e) => e.id.code === filter.ethnicity)
                                            ?.codeDescTxt ?? ''
                                    }
                                    handleClose={() => handlePersonFilterChange({ ...filter, ethnicity: undefined })}
                                />
                            ) : null}
                            {filter.race ? (
                                <Chip
                                    name="RACE"
                                    value={
                                        searchCriteria.races.find((r) => r.id.code === filter.race)?.codeDescTxt ?? ''
                                    }
                                    handleClose={() => handlePersonFilterChange({ ...filter, race: undefined })}
                                />
                            ) : null}
                        </>
                    )}
                </SearchCriteriaContext.Consumer>
            </>
        );
    };

    const renderInvestigationChips = (filter: InvestigationFilter): ReactElement => {
        return (
            <>
                <SearchCriteriaContext.Consumer>
                    {({ searchCriteria }) => (
                        <>
                            {filter.conditions?.map((condition, index) => (
                                <>
                                    <Chip
                                        name="CONDITION"
                                        value={
                                            searchCriteria.conditions.find((c) => c.id === condition)
                                                ?.conditionDescTxt ?? ''
                                        }
                                        key={index}
                                        handleClose={() =>
                                            handleInvestigationFilterChange({
                                                ...filter,
                                                conditions: filter.conditions?.filter((c) => c !== condition) ?? []
                                            })
                                        }
                                    />
                                </>
                            ))}
                            {filter.programAreas?.map((programArea, index) => (
                                <>
                                    <Chip
                                        name="PROGRAM AREA"
                                        value={
                                            searchCriteria.programAreas.find((p) => p.id === programArea)
                                                ?.progAreaDescTxt ?? ''
                                        }
                                        key={index}
                                        handleClose={() =>
                                            handleInvestigationFilterChange({
                                                ...filter,
                                                programAreas:
                                                    filter.programAreas?.filter((p) => p !== programArea) ?? []
                                            })
                                        }
                                    />
                                </>
                            ))}
                            {filter.jurisdictions?.map((jurisdiction, index) => (
                                <>
                                    <Chip
                                        name="JURISDICTION"
                                        value={
                                            searchCriteria.jurisdictions.find((j) => j.id === jurisdiction)
                                                ?.codeDescTxt ?? ''
                                        }
                                        key={index}
                                        handleClose={() =>
                                            handleInvestigationFilterChange({
                                                ...filter,
                                                jurisdictions:
                                                    filter.jurisdictions?.filter((j) => j !== jurisdiction) ?? []
                                            })
                                        }
                                    />
                                </>
                            ))}
                            {filter.pregnancyStatus ? (
                                <Chip
                                    name="PREGNANCY STATUS"
                                    value={filter.pregnancyStatus}
                                    handleClose={() =>
                                        handleInvestigationFilterChange({ ...filter, pregnancyStatus: undefined })
                                    }
                                />
                            ) : null}
                            {filter.eventId?.investigationEventType ? (
                                <Chip
                                    name="INVESTIGATION EVENT TYPE"
                                    value={filter.eventId.investigationEventType}
                                    handleClose={() =>
                                        handleInvestigationFilterChange({ ...filter, eventId: undefined })
                                    }
                                />
                            ) : null}
                            {filter.eventId?.id ? (
                                <Chip
                                    name="EVENT ID"
                                    value={filter.eventId.id}
                                    handleClose={() =>
                                        handleInvestigationFilterChange({ ...filter, eventId: undefined })
                                    }
                                />
                            ) : null}
                        </>
                    )}
                </SearchCriteriaContext.Consumer>
            </>
        );
    };

    return (
        <>
            {lastSearchType === SEARCH_TYPE.PERSON && patientFilter ? renderPatientChips(patientFilter) : null}
            {lastSearchType === SEARCH_TYPE.INVESTIGATION && investigationFilter
                ? renderInvestigationChips(investigationFilter)
                : null}
            {lastSearchType === SEARCH_TYPE.LAB_REPORT ? (
                <>Lab Report Chips {labReportFilter === undefined ? 'undefined' : 'defined'}</>
            ) : null}
        </>
    );
};
