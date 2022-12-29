import { Accordion, Button, Form, Grid } from '@trussworks/react-uswds';
import { AccordionItemProps } from '@trussworks/react-uswds/lib/components/Accordion/Accordion';
import { useState } from 'react';
import { useForm } from 'react-hook-form';
import { InvestigationFilter, LabReportFilter } from '../../generated/graphql/schema';
import { EventTypes } from './EventType';
import { GeneralSearch } from './generalSearch';
import { useNavigate } from 'react-router-dom';
import { SearchCriteria } from './SearchCriteria';

type EventSearchProps = {
    onSearch: (filter: InvestigationFilter | LabReportFilter, type: string) => void;
    data?: InvestigationFilter | LabReportFilter;
    searchType?: string;
};

export const EventSearch = ({ onSearch, data, searchType }: EventSearchProps) => {
    const navigate = useNavigate();
    const methods = useForm();

    const [eventSearchType, setEventSearchType] = useState<any>('');

    const { handleSubmit, control, reset } = methods;

    const eventSearchItems: AccordionItemProps[] = [
        {
            title: 'Event Type',
            content: (
                <EventTypes
                    defaultValue={searchType || eventSearchType}
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

    const eventSearchFilteredItem: AccordionItemProps[] = [
        {
            title: 'General Search',
            content: <GeneralSearch searchType={searchType || eventSearchType} control={control} data={data} />,
            expanded: true,
            id: '2',
            headingLevel: 'h4',
            className: 'accordian-item'
        },
        {
            title: 'Investigation Criteria',
            content: <SearchCriteria searchType={searchType || eventSearchType} control={control} data={data} />,
            expanded: false,
            id: '3',
            headingLevel: 'h4',
            className: 'accordian-item'
        }
    ];

    const onSubmit: any = (body: any) => {
        let filterData: InvestigationFilter | LabReportFilter = {};
        console.log(body);
        if (eventSearchType === 'investigation') {
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
        } else if (eventSearchType === 'labReport') {
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
            if (eventSearchType === 'investigation' && (filterData as InvestigationFilter)) {
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
        console.log(filterData, 'filterData');
        onSearch(filterData, eventSearchType);
    };

    return (
        <Form onSubmit={handleSubmit(onSubmit)} className="width-full maxw-full">
            <div style={{ height: `calc(100vh - 405px)`, overflowY: 'auto' }}>
                <Accordion items={eventSearchItems} multiselectable={true} />
                {(searchType || (eventSearchType && eventSearchType !== '- Select -')) && (
                    <Accordion items={eventSearchFilteredItem} multiselectable={true} />
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
