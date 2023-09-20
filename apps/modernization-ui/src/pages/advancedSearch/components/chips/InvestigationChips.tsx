import { InvestigationFilter } from 'generated/graphql/schema';
import { SearchCriteria, SearchCriteriaContext } from 'providers/SearchCriteriaContext';
import Chip from './Chip';

type InvestigationChipsProps = {
    filter: InvestigationFilter;
    onChange: (investigationFilter: InvestigationFilter) => void;
};
export const InvestigationChips = ({ filter, onChange }: InvestigationChipsProps) => {
    function lookupUserName(searchCriteria: SearchCriteria, createdBy: string): string {
        const user = searchCriteria.userResults.find((u) => u.nedssEntryId === createdBy);
        return user ? `${user.userLastNm}, ${user.userFirstNm}` : '';
    }

    return (
        <SearchCriteriaContext.Consumer>
            {({ searchCriteria }) => (
                <>
                    {filter.conditions?.map((condition, index) => (
                        <Chip
                            name="CONDITION"
                            value={searchCriteria.conditions.find((c) => c.id === condition)?.conditionDescTxt ?? ''}
                            key={`condition-${index}`}
                            handleClose={() =>
                                onChange({
                                    ...filter,
                                    conditions: filter.conditions?.filter((c) => c !== condition) ?? []
                                })
                            }
                        />
                    ))}
                    {filter.programAreas?.map((programArea, index) => (
                        <Chip
                            name="PROGRAM AREA"
                            value={searchCriteria.programAreas.find((p) => p.id === programArea)?.progAreaDescTxt ?? ''}
                            key={`programArea-${index}`}
                            handleClose={() =>
                                onChange({
                                    ...filter,
                                    programAreas: filter.programAreas?.filter((p) => p !== programArea) ?? []
                                })
                            }
                        />
                    ))}
                    {filter.jurisdictions?.map((jurisdiction, index) => (
                        <Chip
                            name="JURISDICTION"
                            value={searchCriteria.jurisdictions.find((j) => j.id === jurisdiction)?.codeDescTxt ?? ''}
                            key={`jurisdiction${index}`}
                            handleClose={() =>
                                onChange({
                                    ...filter,
                                    jurisdictions: filter.jurisdictions?.filter((j) => j !== jurisdiction) ?? []
                                })
                            }
                        />
                    ))}
                    {filter.pregnancyStatus ? (
                        <Chip
                            name="PREGNANCY STATUS"
                            value={filter.pregnancyStatus}
                            handleClose={() => onChange({ ...filter, pregnancyStatus: undefined })}
                        />
                    ) : null}
                    {filter.eventId?.investigationEventType ? (
                        <Chip
                            name="INVESTIGATION EVENT TYPE"
                            value={filter.eventId.investigationEventType.replaceAll('_', ' ')}
                            handleClose={() => onChange({ ...filter, eventId: undefined })}
                        />
                    ) : null}
                    {filter.eventId?.id ? (
                        <Chip
                            name="EVENT ID"
                            value={filter.eventId.id}
                            handleClose={() => onChange({ ...filter, eventId: undefined })}
                        />
                    ) : null}
                    {filter.eventDate?.type ? (
                        <Chip
                            name="DATE TYPE"
                            value={filter.eventDate.type.replaceAll('_', ' ')}
                            handleClose={() => onChange({ ...filter, eventDate: undefined })}
                        />
                    ) : null}
                    {filter.eventDate?.from ? (
                        <Chip
                            name="FROM"
                            value={filter.eventDate.from}
                            handleClose={() => onChange({ ...filter, eventDate: undefined })}
                        />
                    ) : null}
                    {filter.eventDate?.to ? (
                        <Chip
                            name="TO"
                            value={filter.eventDate.to}
                            handleClose={() => onChange({ ...filter, eventDate: undefined })}
                        />
                    ) : null}
                    {filter.createdBy ? (
                        <Chip
                            name="CREATED BY"
                            value={lookupUserName(searchCriteria, filter.createdBy)}
                            handleClose={() => onChange({ ...filter, createdBy: undefined })}
                        />
                    ) : null}
                    {filter.lastUpdatedBy ? (
                        <Chip
                            name="LAST UPDATED BY"
                            value={lookupUserName(searchCriteria, filter.lastUpdatedBy)}
                            handleClose={() => onChange({ ...filter, lastUpdatedBy: undefined })}
                        />
                    ) : null}
                    {filter.providerFacilitySearch?.entityType ? (
                        <Chip
                            name="ENTITY TYPE"
                            value={filter.providerFacilitySearch.entityType}
                            handleClose={() =>
                                onChange({
                                    ...filter,
                                    providerFacilitySearch: undefined
                                })
                            }
                        />
                    ) : null}
                    {filter.providerFacilitySearch?.id ? (
                        <Chip
                            name="ENTITY ID"
                            value={filter.providerFacilitySearch.id}
                            handleClose={() =>
                                onChange({
                                    ...filter,
                                    providerFacilitySearch: undefined
                                })
                            }
                        />
                    ) : null}
                    {filter.investigationStatus ? (
                        <Chip
                            name="INVESTIGATION STATUS"
                            value={filter.investigationStatus}
                            handleClose={() =>
                                onChange({
                                    ...filter,
                                    investigationStatus: undefined
                                })
                            }
                        />
                    ) : null}
                    {filter.outbreakNames?.map((outbreak, index) => (
                        <Chip
                            name="OUTBREAK NAME"
                            value={searchCriteria.outbreaks.find((o) => o.id.code === outbreak)?.codeShortDescTxt ?? ''}
                            key={`outbreak-${index}`}
                            handleClose={() =>
                                onChange({
                                    ...filter,
                                    outbreakNames: filter.outbreakNames?.filter((c) => c !== outbreak) ?? []
                                })
                            }
                        />
                    ))}
                    {filter.caseStatuses?.map((caseStatus, index) => (
                        <Chip
                            name="CASE STATUS"
                            value={caseStatus}
                            key={`case-status-${index}`}
                            handleClose={() =>
                                onChange({
                                    ...filter,
                                    caseStatuses: filter.caseStatuses?.filter((c) => c !== caseStatus) ?? []
                                })
                            }
                        />
                    ))}
                    {filter.processingStatuses?.map((processingStatus, index) => (
                        <Chip
                            name="PROCESSING STATUS"
                            value={(processingStatus ?? '').replaceAll('_', ' ')}
                            key={`processing-status-${index}`}
                            handleClose={() =>
                                onChange({
                                    ...filter,
                                    processingStatuses:
                                        filter.processingStatuses?.filter((c) => c !== processingStatus) ?? []
                                })
                            }
                        />
                    ))}
                    {filter.notificationStatuses?.map((notificationStatus, index) => (
                        <Chip
                            name="NOTIFICATION STATUS"
                            value={notificationStatus ?? ''}
                            key={`notification-status-${index}`}
                            handleClose={() =>
                                onChange({
                                    ...filter,
                                    notificationStatuses:
                                        filter.notificationStatuses?.filter((c) => c !== notificationStatus) ?? []
                                })
                            }
                        />
                    ))}
                </>
            )}
        </SearchCriteriaContext.Consumer>
    );
};
