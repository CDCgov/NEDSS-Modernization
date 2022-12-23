import { Accordion, Button, Form, Grid } from '@trussworks/react-uswds';
import { AccordionItemProps } from '@trussworks/react-uswds/lib/components/Accordion/Accordion';
import { useState } from 'react';
import { useForm } from 'react-hook-form';
import { EventFilter, EventType } from '../../generated/graphql/schema';
import { EventTypes } from './EventType';
import { GeneralSearch } from './generalSearch';

type EventSearchProps = {
    onSearch: (filter: EventFilter) => void;
    data?: EventFilter;
};

export const EventSearch = ({ onSearch, data }: EventSearchProps) => {
    const [eventSearchType, setEventSearchType] = useState<any>('');

    const methods = useForm();

    const { handleSubmit, control, reset } = methods;

    const eventSearchItems: AccordionItemProps[] = [
        {
            title: 'Event Type',
            content: (
                <EventTypes
                    defaultValue={data?.eventType}
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
            content: <GeneralSearch searchType={data?.eventType || eventSearchType} control={control} data={data} />,
            expanded: true,
            id: '2',
            headingLevel: 'h4',
            className: 'accordian-item'
        }
    ];

    const onSubmit: any = (body: any) => {
        const filterData: EventFilter = {
            eventType: eventSearchType || data?.eventType
        };
        console.log(body);
        if (eventSearchType === EventType.Investigation) {
            filterData.eventType = EventType.Investigation;
            filterData.investigationFilter = {
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
        } else if (eventSearchType === EventType.LaboratoryReport) {
            filterData.eventType = EventType.LaboratoryReport;
            filterData.laboratoryReportFilter = {
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
            if ((filterData.eventType = EventType.LaboratoryReport)) {
                filterData?.investigationFilter && (filterData.investigationFilter.eventDateSearch = eventDateSearch);
            } else {
                filterData?.laboratoryReportFilter &&
                    (filterData.laboratoryReportFilter.eventDateSearch = eventDateSearch);
            }
        }

        // Provider/Facility Type Filter
        if (body.entityType && body.entityType !== '- Select -' && body.id) {
            if ((filterData.eventType = EventType.LaboratoryReport)) {
                filterData?.investigationFilter &&
                    (filterData.investigationFilter.providerFacilitySearch = {
                        entityType: body.entityType,
                        id: body.id
                    });
            } else {
                filterData?.laboratoryReportFilter &&
                    (filterData.laboratoryReportFilter.providerSearch = {
                        providerType: body.entityType,
                        providerId: body.id
                    });
            }
        }
        console.log(filterData, 'filterData');
        onSearch(filterData);
    };

    return (
        <Form onSubmit={handleSubmit(onSubmit)} className="width-full maxw-full">
            <div style={{ height: `calc(100vh - 405px)`, overflowY: 'auto' }}>
                <Accordion items={eventSearchItems} multiselectable={true} />
                {(data?.eventType || (eventSearchType && eventSearchType !== '- Select -')) && (
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
                        }}
                        outline>
                        Clear all
                    </Button>
                </Grid>
            </Grid>
        </Form>
    );
};
