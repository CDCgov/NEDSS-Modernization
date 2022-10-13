import { Accordion, Button, Form, Grid } from '@trussworks/react-uswds';
import { useForm } from 'react-hook-form';
import { CaseStatus, EventFilter, EventType, useFindPatientsByEventLazyQuery } from '../../generated/graphql/schema';
import { AccordionItemProps } from '@trussworks/react-uswds/lib/components/Accordion/Accordion';
import { EventTypes } from './EventType';
import { useState } from 'react';
import { GeneralSearch } from './generalSearch';

export const EventSearch = () => {
    // const [getFilteredData] = useFindPatientsByFilterLazyQuery();
    const [getEventSearch] = useFindPatientsByEventLazyQuery();

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
        console.log('body:', body);
        const filterData: EventFilter = {
            eventType: eventSearchType
        };
        eventSearchType === EventType.Investigation &&
            (filterData.investigationFilter = {
                includeUnasignedCaseStatus: true,
                caseStatuses: [CaseStatus.Confirmed]
            });

        // getFilteredData({
        //     variables: {
        //         filter: rowData
        //     }
        // });
        getEventSearch({
            variables: {
                filter: filterData,
                page: {
                    pageNumber: 1,
                    pageSize: 50
                }
            }
        }).then((re) => {
            console.log('re:', re);
        });
    };

    return (
        <Form onSubmit={handleSubmit(onSubmit)} className="width-full maxw-full">
            <Accordion items={eventSearchItems} multiselectable={true} />
            {eventSearchType && eventSearchType !== '- Select -' && (
                <Accordion items={eventSearchFilteredItem} multiselectable={true} />
            )}
            <Grid col={12} className="margin-top-5 padding-x-3">
                <Button className="width-full" type={'submit'}>
                    Search
                </Button>
            </Grid>
            <Grid col={12} className="padding-x-3">
                <Button
                    className="width-full"
                    type={'button'}
                    onClick={() =>
                        reset({
                            lastName: '',
                            firstName: '',
                            city: '',
                            state: '',
                            zip: '',
                            patientId: ''
                        })
                    }
                    outline>
                    Clear
                </Button>
            </Grid>
        </Form>
    );
};
