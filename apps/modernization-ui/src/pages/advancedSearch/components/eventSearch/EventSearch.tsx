import { Button, Form, Grid } from '@trussworks/react-uswds';
import { InvestigationFilter, LabReportFilter } from 'generated/graphql/schema';
import { SEARCH_TYPE } from 'pages/advancedSearch/AdvancedSearch';
import { useEffect, useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { EventTypeAccordion, SearchType } from './EventTypeAccordion';
import { InvestigationAccordion } from './InvestigationSearch/InvestigationAccordion';
import { LabReportAccordion } from './labReportSearch/LabReportAccordion';

type EventSearchProps = {
    onSearch: (filter: InvestigationFilter | LabReportFilter, type: SEARCH_TYPE) => void;
    investigationFilter?: InvestigationFilter;
    labReportFilter?: LabReportFilter;
};
export const EventSearch = ({ investigationFilter, labReportFilter, onSearch }: EventSearchProps) => {
    const [searchType, setSearchType] = useState<SearchType>();
    const investigationForm = useForm<InvestigationFilter>({ defaultValues: {} });
    const labReportForm = useForm<LabReportFilter>({
        defaultValues: { entryMethods: [], enteredBy: [], eventStatus: [], processingStatus: [] }
    });

    // on page load, if an event search was performed set the selected event type
    useEffect(() => {
        if (investigationFilter) {
            setSearchType('investigation');
            investigationForm.reset(investigationFilter, { keepDefaultValues: true });
        } else {
            setSearchType('labReport');
            labReportForm.reset(labReportFilter, { keepDefaultValues: true });
        }
    }, [investigationFilter, labReportFilter]);

    const handleClearAll = () => {
        investigationForm.reset();
        labReportForm.reset();
        setSearchType(undefined);
    };

    /**
     * Removes empty objects from the filter.
     * @example
     * { "eventId" : {}, "anotherField": "sample"} -> {"anotherField": "sample"}
     *
     * @param {InvestigationFilter} investigationFilter
     * @return {InvestigationFilter} InvestigationFilter
     */
    const cleanInvestigationFilter = (investigationFilter: InvestigationFilter): InvestigationFilter => {
        const eventId = investigationFilter.eventId;
        if (eventId === undefined || eventId?.id == undefined || eventId.investigationEventType === undefined) {
            investigationFilter.eventId = undefined;
        }
        const eventDate = investigationFilter.eventDate;
        if (eventDate === undefined || eventDate?.to == undefined || eventDate.from === undefined) {
            investigationFilter.eventDate = undefined;
        }
        const providerFacilitySearch = investigationFilter.providerFacilitySearch;
        if (
            providerFacilitySearch === undefined ||
            providerFacilitySearch?.entityType == undefined ||
            providerFacilitySearch.id === undefined
        ) {
            investigationFilter.providerFacilitySearch = undefined;
        }
        return investigationFilter;
    };

    const handleSubmitInvestigation = (investigationFilter: InvestigationFilter) => {
        onSearch(cleanInvestigationFilter(investigationFilter), SEARCH_TYPE.INVESTIGATION);
    };

    const handleSubmitLabReport = (labReportFilter: LabReportFilter) => {
        onSearch(labReportFilter, SEARCH_TYPE.LAB_REPORT);
    };

    return (
        <>
            <div style={{ height: `calc(100vh - 405px)`, overflowY: 'auto' }}>
                <EventTypeAccordion searchType={searchType} setSearchType={setSearchType} />
                <FormProvider {...investigationForm}>
                    <Form
                        onSubmit={
                            searchType === 'investigation'
                                ? investigationForm.handleSubmit(handleSubmitInvestigation)
                                : labReportForm.handleSubmit(handleSubmitLabReport)
                        }
                        className="width-full maxw-full">
                        {searchType === 'investigation' ? <InvestigationAccordion form={investigationForm} /> : null}
                        {searchType === 'labReport' ? <LabReportAccordion form={labReportForm} /> : null}
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
                                    onClick={handleClearAll}
                                    outline>
                                    Clear all
                                </Button>
                            </Grid>
                        </Grid>
                    </Form>
                </FormProvider>
            </div>
        </>
    );
};
