import { Accordion, Button, Form, Grid } from '@trussworks/react-uswds';
import { AccordionItemProps } from '@trussworks/react-uswds/lib/components/Accordion/Accordion';
import { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
// import { useNavigate } from 'react-router-dom';
import {
    EntryMethod,
    EventStatus,
    InvestigationFilter,
    LabReportFilter,
    LaboratoryReportStatus,
    UserType,
    useFindLocalCodedResultsLazyQuery,
    useFindLocalLabTestLazyQuery
} from '../../../../generated/graphql/schema';
import { SEARCH_TYPE } from '../../AdvancedSearch';
import { EventTypes } from './EventType';
import { GeneralSearch } from './GeneralSearch';
import { LabGeneralSearch } from './LabGeneralSearch';
import { SearchCriteria } from './SearchCriteria';
import { LabSearchCriteria } from './LabSearchCriteria';
import { setInvestigationFilters, setLabReportFilters } from '../../../../utils/util';
import debounce from 'lodash.debounce';

type EventSearchProps = {
    onSearch: (filter: InvestigationFilter | LabReportFilter, type: SEARCH_TYPE) => void;
    investigationFilter?: InvestigationFilter;
    labReportFilter?: LabReportFilter;
    clearAll: () => void;
};

export const EventSearch = ({ onSearch, investigationFilter, labReportFilter, clearAll }: EventSearchProps) => {
    // const navigate = useNavigate();
    const methods = useForm();
    const [eventSearchType, setEventSearchType] = useState<SEARCH_TYPE | ''>();
    const [lastEventSearchType, setLastEventSearchType] = useState<SEARCH_TYPE | ''>();
    const { handleSubmit, control, reset } = methods;

    const [getLocalResultedTests] = useFindLocalLabTestLazyQuery();
    const [getCodedResultedTests] = useFindLocalCodedResultsLazyQuery();

    // on page load, if an event search was performed set the selected event type
    useEffect(() => {
        if (investigationFilter) {
            setEventSearchType(SEARCH_TYPE.INVESTIGATION);
            methods.reset(setInvestigationFilters(investigationFilter));
        } else if (labReportFilter) {
            setEventSearchType(SEARCH_TYPE.LAB_REPORT);
            methods.reset(setLabReportFilters(labReportFilter));
        }
    }, [investigationFilter, labReportFilter]);

    const [toBottom, setToBottom] = useState(false);
    useEffect(() => {
        if (lastEventSearchType && !eventSearchType) {
            setEventSearchType(lastEventSearchType);
        }

        if (eventSearchType) {
            const element = document.getElementsByClassName('usa-accordion__heading accordian-item');

            if (element) {
                element?.[2]?.addEventListener('click', function () {
                    setToBottom(true);
                });
            }
        }
    }, [eventSearchType]);

    useEffect(() => {
        if (toBottom) {
            const divElement = document.getElementById('criteria');
            if (divElement) {
                setTimeout(() => {
                    divElement.scrollIntoView({ behavior: 'smooth', block: 'end' });
                    setToBottom(false);
                });
            }
        }
    }, [toBottom]);

    const eventSearchItems: AccordionItemProps[] = [
        {
            title: 'Event type',
            content: (
                <EventTypes
                    defaultValue={eventSearchType}
                    onChangeMethod={(e) => {
                        setEventSearchType(e);
                        setLastEventSearchType(e);
                        if (e === SEARCH_TYPE.LAB_REPORT) {
                            methods.reset(
                                setLabReportFilters({
                                    entryMethods: [EntryMethod.Electronic],
                                    enteredBy: [UserType.Internal, UserType.External],
                                    eventStatus: [EventStatus.New],
                                    processingStatus: [LaboratoryReportStatus.Unprocessed]
                                })
                            );
                        }
                    }}
                    control={control}
                    name="eventType"
                />
            ),
            expanded: true,
            id: '1',
            headingLevel: 'h4',
            className: 'accordian-item'
        }
    ];

    const investigationSearchFilteredItem: AccordionItemProps[] = [
        {
            title: 'General search',
            content: <GeneralSearch control={control} filter={investigationFilter} />,
            expanded: true,
            id: '2',
            headingLevel: 'h4',
            className: 'accordian-item'
        },
        {
            title: 'Investigation criteria',
            content: <SearchCriteria control={control} filter={investigationFilter} />,
            expanded: false,
            id: '3',
            headingLevel: 'h4',
            className: 'accordian-item'
        }
    ];

    const removeDuplicates = (items: any) => {
        const newArray: any = [];
        const uniqueObject: any = {};
        for (const i in items) {
            if (items[i]['value']) {
                uniqueObject[items[i]['value']] = items[i];
            }
        }
        for (const i in uniqueObject) {
            if (uniqueObject[i]) {
                newArray.push(uniqueObject[i]);
            }
        }
        return newArray;
    };

    const [resultData, setResultsData] = useState<{ label: string; value: string }[]>([]);
    const debouncedSearchResults = debounce(async (criteria) => {
        getLocalResultedTests({ variables: { searchText: criteria } }).then((re) => {
            const tempArr: { label: string; value: string }[] = [];
            re.data?.findLocalLabTest.content.map((res) => {
                tempArr.push({ label: res?.labTestDescTxt || '', value: res?.labTestDescTxt || '' });
            });
            setResultsData(removeDuplicates(tempArr));
        });
    }, 300);

    const handleResultChange = (e: any) => {
        debouncedSearchResults(e.target.value);
    };

    const [codedResults, setCodedResults] = useState<{ label: string; value: string }[]>([]);
    const debouncedCodedSearchResults = debounce(async (criteria) => {
        getCodedResultedTests({ variables: { searchText: criteria } }).then((re) => {
            const tempArr: { label: string; value: string }[] = [];
            re.data?.findLocalCodedResults.content.map((res) => {
                tempArr.push({ label: res?.labResultDescTxt || '', value: res?.labResultDescTxt || '' });
            });
            setCodedResults(removeDuplicates(tempArr));
        });
    }, 300);

    const handleCodedResultChange = (e: any) => {
        debouncedCodedSearchResults(e.target.value);
    };

    const labReportSearchItem: AccordionItemProps[] = [
        {
            title: 'General search',
            content: <LabGeneralSearch control={control} filter={labReportFilter} />,
            expanded: true,
            id: '2',
            headingLevel: 'h4',
            className: 'accordian-item'
        },
        {
            title: 'Lab report criteria',
            content: (
                <LabSearchCriteria
                    resultsTestOptions={resultData}
                    codedResults={codedResults}
                    codedResultsChange={handleCodedResultChange}
                    resultChanges={handleResultChange}
                    control={control}
                    filter={labReportFilter}
                />
            ),
            expanded: false,
            id: '3',
            headingLevel: 'h4',
            className: 'accordian-item'
        }
    ];

    const onSubmit: any = (body: any) => {
        let filterData: InvestigationFilter | LabReportFilter = {};
        if (eventSearchType === SEARCH_TYPE.INVESTIGATION) {
            filterData = {
                conditions: body.condition?.length > 0 ? body.condition : undefined,
                jurisdictions: body.jurisdiction?.length > 0 ? body.jurisdiction : undefined,
                pregnancyStatus:
                    body.pregnancyTest && body.pregnancyTest !== '- Select -' ? body.pregnancyTest : undefined,
                programAreas: body.programArea?.length > 0 ? body.programArea : undefined,
                createdBy: body.createdBy && body.createdBy !== '- Select -' ? body.createdBy : undefined,
                lastUpdatedBy:
                    body.lastUpdatedBy && body.lastUpdatedBy !== '- Select -' ? body.lastUpdatedBy : undefined
            } as InvestigationFilter;
        } else if (eventSearchType === SEARCH_TYPE.LAB_REPORT) {
            filterData = {
                jurisdictions: body.labjurisdiction?.length > 0 ? body.labjurisdiction : undefined,
                pregnancyStatus:
                    body.labpregnancyTest && body.labpregnancyTest !== '- Select -' ? body.labpregnancyTest : undefined,
                programAreas: body.labprogramArea?.length > 0 ? body.labprogramArea : undefined,
                createdBy: body.labcreatedBy && body.labcreatedBy !== '- Select -' ? body.labcreatedBy : undefined,
                lastUpdatedBy:
                    body.lablastUpdatedBy && body.lablastUpdatedBy !== '- Select -' ? body.lablastUpdatedBy : undefined,
                entryMethods: body.entryMethod?.length > 0 ? body.entryMethod : undefined,
                enteredBy: body.enteredBy?.length > 0 ? body.enteredBy : undefined,
                eventStatus: body.eventStatus?.length > 0 ? body.eventStatus : undefined,
                processingStatus: body.processingStatus?.length > 0 ? body.processingStatus : undefined
            } as LabReportFilter;
        } else {
            return;
        }
        // Lab Event Id
        if (body.labeventIdType && body.labeventIdType !== '- Select -' && body.labeventId) {
            if (eventSearchType === SEARCH_TYPE.LAB_REPORT) {
                const eventIdSearch = {
                    labEventType: body.labeventIdType,
                    labEventId: body.labeventId
                };
                (filterData as LabReportFilter).eventId = eventIdSearch;
            }
        }

        // Investigation Event Id
        if (body.eventIdType && body.eventIdType !== '- Select -' && body.eventId) {
            if (eventSearchType === SEARCH_TYPE.INVESTIGATION) {
                const eventIdSearch = {
                    investigationEventType: body.eventIdType,
                    id: body.eventId
                };
                (filterData as InvestigationFilter).eventId = eventIdSearch;
            }
        }

        // Investigation Event Date
        if (body.eventDateType && body.eventDateType !== '- Select -' && body.from && body.to) {
            if (eventSearchType === SEARCH_TYPE.INVESTIGATION) {
                const eventDateSearch = {
                    type: body.eventDateType,
                    from: body.from,
                    to: body.to
                };
                (filterData as InvestigationFilter).eventDate = eventDateSearch;
            }
        }

        if (body.labeventDateType && body.labeventDateType !== '- Select -' && body.labfrom && body.labto) {
            if (eventSearchType === SEARCH_TYPE.LAB_REPORT) {
                const eventDateSearch = {
                    type: body.labeventDateType,
                    from: body.labfrom,
                    to: body.labto
                };
                (filterData as LabReportFilter).eventDate = eventDateSearch;
            }
        }

        // Provider/Facility Type Filter
        if (body.entityType && body.entityType !== '- Select -' && body.id) {
            if (eventSearchType === SEARCH_TYPE.INVESTIGATION && (filterData as InvestigationFilter)) {
                filterData = filterData as InvestigationFilter;
                filterData.providerFacilitySearch = {
                    entityType: body.entityType,
                    id: body.id
                };
            }
        }

        if (body.labentityType && body.labentityType !== '- Select -' && body.labid) {
            if (eventSearchType === SEARCH_TYPE.LAB_REPORT && (filterData as LabReportFilter)) {
                filterData = filterData as LabReportFilter;
                filterData.providerSearch = {
                    providerType: body.labentityType,
                    providerId: body.labid
                };
            }
        }

        // Investigation Criteria
        if (eventSearchType === SEARCH_TYPE.INVESTIGATION) {
            filterData = filterData as InvestigationFilter;
            body.investigationStatus && body.investigationStatus !== '- Select -'
                ? (filterData.investigationStatus = body.investigationStatus)
                : undefined;

            body.outbreakNames && body.outbreakNames !== '- Select -'
                ? (filterData.outbreakNames = body.outbreakNames)
                : undefined;

            if (body.statusList && body.statusList !== '- Select -') {
                filterData.caseStatuses = body.statusList;
            }

            if (body.processingStatus && body.processingStatus !== '- Select -') {
                filterData.processingStatuses = {
                    statusList: body.processingStatus,
                    includeUnassigned: body.processing || false
                };
            }

            if (body.notificationStatus && body.notificationStatus !== '- Select -') {
                filterData.notificationStatuses = {
                    statusList: body.notificationStatus,
                    includeUnassigned: body.notification || false
                };
            }
        }

        if (eventSearchType === SEARCH_TYPE.LAB_REPORT) {
            filterData = filterData as LabReportFilter;
            body.resultedTest && (filterData.resultedTest = body.resultedTest);
            body.codedResult && (filterData.codedResult = body.codedResult);
        }

        onSearch(filterData, eventSearchType);
    };

    return (
        <Form onSubmit={handleSubmit(onSubmit)} className="width-full maxw-full">
            <div style={{ height: `calc(100vh - 405px)`, overflowY: 'auto' }}>
                <Accordion items={eventSearchItems} multiselectable={true} />
                {eventSearchType === SEARCH_TYPE.INVESTIGATION && (
                    <Accordion items={investigationSearchFilteredItem} multiselectable={true} />
                )}

                {eventSearchType === SEARCH_TYPE.LAB_REPORT && (
                    <Accordion items={labReportSearchItem} multiselectable={true} />
                )}
            </div>
            <Grid row className="bottom-search">
                <Grid col={12} className="padding-x-2">
                    <Button className="width-full clear-btn" type={'submit'}>
                        Search
                    </Button>
                </Grid>
                <Grid col={12} className="padding-x-2">
                    <Button
                        className="width-full clear-btn"
                        type={'button'}
                        onClick={() => {
                            reset({});
                            clearAll();
                            setEventSearchType('');
                        }}
                        outline>
                        Clear all
                    </Button>
                </Grid>
            </Grid>
        </Form>
    );
};
