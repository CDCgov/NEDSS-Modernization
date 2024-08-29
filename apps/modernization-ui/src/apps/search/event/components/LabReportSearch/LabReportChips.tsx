import { LabReportFilter } from 'generated/graphql/schema';
import { SearchCriteriaContext } from 'providers/SearchCriteriaContext';
import Chip from 'apps/search/advancedSearch/components/chips/Chip';

type LabReportChipsProps = {
    filter: LabReportFilter;
    onChange: (labReportFilter: LabReportFilter) => void;
};
export const LabReportChips = ({ filter, onChange }: LabReportChipsProps) => {
    return (
        <SearchCriteriaContext.Consumer>
            {({ searchCriteria }) => (
                <>
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
                    {filter.pregnancyStatus ? (
                        <Chip
                            name="PREGNANCY STATUS"
                            value={filter.pregnancyStatus}
                            handleClose={() => onChange({ ...filter, pregnancyStatus: undefined })}
                        />
                    ) : null}
                    {filter.eventId?.labEventType ? (
                        <Chip
                            name="INVESTIGATION EVENT TYPE"
                            value={filter.eventId.labEventType.replaceAll('_', ' ')}
                            handleClose={() => onChange({ ...filter, eventId: undefined })}
                        />
                    ) : null}
                    {filter.eventId?.labEventId ? (
                        <Chip
                            name="EVENT ID"
                            value={filter.eventId.labEventId}
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
                    {filter.entryMethods?.map((e, index) => (
                        <Chip
                            name="ENTRY METHOD"
                            key={index}
                            value={e ?? ''}
                            handleClose={() =>
                                onChange({
                                    ...filter,
                                    entryMethods: filter.entryMethods?.filter((entry) => entry !== e)
                                })
                            }
                        />
                    ))}
                    {filter.enteredBy?.map((e, index) => (
                        <Chip
                            name="ENTERED BY"
                            key={index}
                            value={e ?? ''}
                            handleClose={() =>
                                onChange({
                                    ...filter,
                                    enteredBy: filter.enteredBy?.filter((enteredBy) => enteredBy !== e)
                                })
                            }
                        />
                    ))}
                    {filter.eventStatus?.map((e, index) => (
                        <Chip
                            name="EVENT STATUS"
                            key={index}
                            value={e ?? ''}
                            handleClose={() =>
                                onChange({
                                    ...filter,
                                    eventStatus: filter.eventStatus?.filter((status) => status !== e)
                                })
                            }
                        />
                    ))}
                    {filter.processingStatus?.map((e, index) => (
                        <Chip
                            name="PROCESSING STATUS"
                            key={index}
                            value={(e ?? '').replaceAll('_', ' ')}
                            handleClose={() =>
                                onChange({
                                    ...filter,
                                    processingStatus: filter.processingStatus?.filter((status) => status !== e)
                                })
                            }
                        />
                    ))}
                    {filter.providerSearch?.providerType ? (
                        <Chip
                            name="ENTITY TYPE"
                            value={filter.providerSearch.providerType}
                            handleClose={() =>
                                onChange({
                                    ...filter,
                                    providerSearch: undefined
                                })
                            }
                        />
                    ) : null}
                    {filter.providerSearch?.providerId ? (
                        <Chip
                            name="ENTITY ID"
                            value={filter.providerSearch.providerId}
                            handleClose={() =>
                                onChange({
                                    ...filter,
                                    providerSearch: undefined
                                })
                            }
                        />
                    ) : null}
                    {filter.resultedTest ? (
                        <Chip
                            name="RESULTED TEST"
                            value={filter.resultedTest}
                            handleClose={() =>
                                onChange({
                                    ...filter,
                                    resultedTest: undefined
                                })
                            }
                        />
                    ) : null}
                    {filter.codedResult ? (
                        <Chip
                            name="CODED RESULT"
                            value={filter.codedResult}
                            handleClose={() =>
                                onChange({
                                    ...filter,
                                    codedResult: undefined
                                })
                            }
                        />
                    ) : null}
                </>
            )}
        </SearchCriteriaContext.Consumer>
    );
};
