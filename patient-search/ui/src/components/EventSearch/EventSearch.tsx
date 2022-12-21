import { Accordion, Button, Form, Grid } from '@trussworks/react-uswds';
import { AccordionItemProps } from '@trussworks/react-uswds/lib/components/Accordion/Accordion';
import { useState } from 'react';
import { useForm } from 'react-hook-form';
import { EventFilter, EventType } from '../../generated/graphql/schema';
import { EventTypes } from './EventType';
import { GeneralSearch } from './generalSearch';

type EventSearchProps = {
    onSearch: (filter: EventFilter) => void;
};

export const EventSearch = ({ onSearch }: EventSearchProps) => {
    const [eventSearchType, setEventSearchType] = useState<any>('');

    const methods = useForm();

    const { handleSubmit, control, reset } = methods;

    const eventSearchItems: AccordionItemProps[] = [
        {
            title: 'Event Type',
            content: (
                <EventTypes
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
            content: <GeneralSearch searchType={eventSearchType} control={control} />,
            expanded: true,
            id: '2',
            headingLevel: 'h4',
            className: 'accordian-item'
        }
    ];

    const onSubmit: any = (body: any) => {
        const filterData: EventFilter = {
            eventType: eventSearchType
        };
        console.log(body);
        if (eventSearchType === EventType.Investigation) {
            filterData.eventType = EventType.Investigation;
            filterData.investigationFilter = {
                conditions: body.condition && body.condition !== '- Select -' ? [body.condition] : undefined,
                jurisdictions:
                    body.jurisdiction && body.jurisdiction !== '- Select -' ? [body.jurisdiction] : undefined,
                pregnancyStatus:
                    body.pregnancyTest && body.pregnancyTest !== '- Select -' ? body.pregnancyTest : undefined,
                programAreas: body.programArea !== '- Select -' ? [body.programArea] : undefined
            };
        } else if (eventSearchType === EventType.LaboratoryReport) {
            filterData.eventType = EventType.LaboratoryReport;
            filterData.laboratoryReportFilter = {
                programAreas: body.programArea !== '- Select -' ? [body.programArea] : undefined,
                jurisdictions: body.jurisdiction !== '- Select -' ? [body.jurisdiction] : undefined,
                pregnancyStatus: body.pregnancyTest !== '- Select -' ? body.pregnancyTest : undefined
            };
        } else {
            // no search type selected
            return;
        }
        onSearch(filterData);
    };

    return (
        <Form onSubmit={handleSubmit(onSubmit)} className="width-full maxw-full">
            <div style={{ height: `calc(100vh - 405px)`, overflowY: 'auto' }}>
                <Accordion items={eventSearchItems} multiselectable={true} />
                {eventSearchType && eventSearchType !== '- Select -' && (
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
