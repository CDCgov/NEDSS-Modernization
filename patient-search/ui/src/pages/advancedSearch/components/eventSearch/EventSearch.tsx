import { Accordion, Button, Form, Grid } from '@trussworks/react-uswds';
import { AccordionItemProps } from '@trussworks/react-uswds/lib/components/Accordion/Accordion';
import { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import { InvestigationFilter, LabReportFilter } from '../../../../generated/graphql/schema';
import { SEARCH_TYPE } from '../../AdvancedSearch';
import { EventTypes } from './EventType';
import { GeneralSearch } from './GeneralSearch';
import { LabGeneralSearch } from './LabGeneralSearch';
import { SearchCriteria } from './SearchCriteria';
import { LabSearchCriteria } from './LabSearchCriteria';

type EventSearchProps = {
    onSearch: (filter: InvestigationFilter | LabReportFilter, type: SEARCH_TYPE) => void;
    investigationFilter?: InvestigationFilter;
    labReportFilter?: LabReportFilter;
};

export const EventSearch = ({ onSearch, investigationFilter, labReportFilter }: EventSearchProps) => {
    const navigate = useNavigate();
    const methods = useForm();
    const [eventSearchType, setEventSearchType] = useState<SEARCH_TYPE | ''>();
    const { handleSubmit, control, reset } = methods;

    // on page load, if an event search was performed set the selected event type
    useEffect(() => {
        if (investigationFilter) {
            setEventSearchType(SEARCH_TYPE.INVESTIGATION);
        } else if (labReportFilter) {
            setEventSearchType(SEARCH_TYPE.LAB_REPORT);
        }
    }, [investigationFilter, labReportFilter]);

    const eventSearchItems: AccordionItemProps[] = [
        {
            title: 'Event Type',
            content: (
                <EventTypes
                    defaultValue={eventSearchType}
                    onChangeMethod={(e) => {
                        setEventSearchType(e);
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
            title: 'General Search',
            content: <GeneralSearch control={control} filter={investigationFilter} />,
            expanded: true,
            id: '2',
            headingLevel: 'h4',
            className: 'accordian-item'
        },
        {
            title: 'Investigation Criteria',
            content: <SearchCriteria control={control} filter={investigationFilter} />,
            expanded: false,
            id: '3',
            headingLevel: 'h4',
            className: 'accordian-item'
        }
    ];

    const labReportSearchItem: AccordionItemProps[] = [
        {
            title: 'General Search',
            content: <LabGeneralSearch control={control} filter={labReportFilter} />,
            expanded: true,
            id: '2',
            headingLevel: 'h4',
            className: 'accordian-item'
        },
        {
            title: 'Lab Report Criteria',
            content: <LabSearchCriteria control={control} filter={labReportFilter} />,
            expanded: false,
            id: '3',
            headingLevel: 'h4',
            className: 'accordian-item'
        }
    ];

    const onSubmit: any = (body: any) => {
        let filterData: InvestigationFilter | LabReportFilter = {};
        if (eventSearchType === SEARCH_TYPE.INVESTIGATION) {
            // filterData.eventType = EventType.Investigation;
            filterData = {
                conditions: body.conditon?.length > 0 ? body.conditon : undefined,
                jurisdictions: body.jurisdiction?.length > 0 ? body.jurisdiction : undefined,
                pregnancyStatus:
                    body.pregnancyTest && body.pregnancyTest !== '- Select -' ? body.pregnancyTest : undefined,
                programAreas: body.programArea?.length > 0 ? body.programArea : undefined,
                eventIdType: body.eventIdType && body.eventIdType !== '- Select -' ? body.eventIdType : undefined,
                eventId: body.eventId || undefined,
                createdBy: body.createdBy && body.createdBy !== '- Select -' ? body.createdBy : undefined,
                lastUpdatedBy:
                    body.lastUpdatedBy && body.lastUpdatedBy !== '- Select -' ? body.lastUpdatedBy : undefined
            };
        } else if (eventSearchType === SEARCH_TYPE.LAB_REPORT) {
            // filterData.eventType = EventType.LaboratoryReport;
            filterData = {
                jurisdictions: body.jurisdiction?.length > 0 ? body.jurisdiction : undefined,
                pregnancyStatus:
                    body.pregnancyTest && body.pregnancyTest !== '- Select -' ? body.pregnancyTest : undefined,
                programAreas: body.programArea?.length > 0 ? body.programArea : undefined,
                eventIdType: body.eventIdType && body.eventIdType !== '- Select -' ? body.eventIdType : undefined,
                eventId: body.eventId || undefined,
                createdBy: body.createdBy && body.createdBy !== '- Select -' ? body.createdBy : undefined,
                lastUpdatedBy:
                    body.lastUpdatedBy && body.lastUpdatedBy !== '- Select -' ? body.lastUpdatedBy : undefined
            };
        } else {
            return;
        }

        // Event Date Filters
        if (body.eventDateType && body.eventDateType !== '- Select -' && body.from && body.to) {
            const eventDateSearch = {
                eventDateType: body.eventDateType,
                from: body.from,
                to: body.to
            };
            filterData.eventDateSearch = eventDateSearch;
        }

        // Provider/Facility Type Filter
        if (body.entityType && body.entityType !== '- Select -' && body.id) {
            if (eventSearchType === SEARCH_TYPE.INVESTIGATION && (filterData as InvestigationFilter)) {
                filterData = filterData as InvestigationFilter;
                filterData.providerFacilitySearch = {
                    entityType: body.entityType,
                    id: body.id
                };
            } else {
                filterData = filterData as LabReportFilter;
                filterData.providerSearch = {
                    providerType: body.entityType,
                    providerId: body.id
                };
            }
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
                            reset({
                                firstName: '',
                                lastName: '',
                                address: '',
                                city: '',
                                state: '-Select-',
                                zip: '',
                                patientId: '',
                                dob: '',
                                gender: '-Select-',
                                phoneNumber: '',
                                email: '',
                                identificationNumber: '',
                                identificationType: '-Select-',
                                ethnicity: '-Select-',
                                race: '-Select-'
                            });
                            navigate({ pathname: '/INVESTIGATION' });
                        }}
                        outline>
                        Clear all
                    </Button>
                </Grid>
            </Grid>
        </Form>
    );
};
