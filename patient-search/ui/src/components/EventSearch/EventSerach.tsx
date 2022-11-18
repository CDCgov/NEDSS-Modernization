import { Accordion, Button, Form, Grid } from '@trussworks/react-uswds';
import { AccordionItemProps } from '@trussworks/react-uswds/lib/components/Accordion/Accordion';
import { useState } from 'react';
import { useForm } from 'react-hook-form';
import { EventFilter, EventType, useFindPatientsByEventLazyQuery } from '../../generated/graphql/schema';
import { EventTypes } from './EventType';
import { GeneralSearch } from './generalSearch';

type EventSearchProps = {
    onSearch: (data: any) => void;
};

export const EventSearch = ({ onSearch }: EventSearchProps) => {
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
        const filterData: EventFilter = {
            eventType: eventSearchType
        };
        if (eventSearchType === EventType.Investigation) {
            filterData.investigationFilter = {
                // TODO inject actual values
                // conditions: ['Bacterial Vaginosis'],
                // caseStatuses: {
                //     includeUnassigned: true,
                //     statusList: [
                //         CaseStatus.Confirmed,
                //         CaseStatus.NotACase,
                //         CaseStatus.Probable,
                //         CaseStatus.Suspect,
                //         CaseStatus.Unknown
                //     ]
                // }
            };
        } else if (eventSearchType === EventType.LaboratoryReport) {
            filterData.laboratoryReportFilter = {
                // TODO inject actual values
                // resultedTest: 'Acid-Fast Stain'
            };
        } else {
            // no search type selected
            return;
        }

        // getFilteredData({
        //     variables: {
        //         filter: rowData
        //     }
        // });
        // const investigationFilter = {
        //     conditions: ['Chancroid'],
        //     programAreas: ['STD'],
        //     jurisdictions: ['130006'], // Clayton County
        //     pregnancyStatus: 'YES',
        //     eventIdType: 'INVESTIGATION_ID',
        //     eventId: 'CAS10001003GA01',
        //     eventDateSearch: {
        //         eventDateType: 'INVESTIGATION_CREATE_DATE',
        //         from: '1970-01-01T00:00:00Z',
        //         to: '2023-01-01T00:00:00Z'
        //     },
        //     createdBy: '10054490', // Id of user
        //     lastUpdatedBy: '10054490', // Id of user
        //     providerFacilitySearch: {
        //         entityType: 'FACILITY',
        //         id: '10003001'
        //     }, // investigatorId: "10003004" - investigatorId is mutually exclusive with providerFacilitySearch
        //     investigationStatus: 'CLOSED',
        //     outbreakNames: ['WHS'],
        //     caseStatuses: {
        //         includeUnassigned: true,
        //         statusList: ['UNKNOWN']
        //     }
        //     // notificationStatuses: {
        //     // includeUnassigned:true
        //     // statusList: [APPROVED]
        //     // }
        //     // processingStatuses: {
        //     // includeUnassigned:true
        //     // statusList: [AWAITING_INTERVIEW]
        //     // }
        // };
        getEventSearch({
            variables: {
                filter: filterData,
                page: {
                    pageNumber: 0,
                    pageSize: 50
                }
            }
        }).then((re) => {
            onSearch(re.data);
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
