import { Button, Form, Grid } from '@trussworks/react-uswds';
import { InvestigationFilter, LabReportFilter } from 'generated/graphql/schema';
import { SEARCH_TYPE } from 'pages/advancedSearch/AdvancedSearch';
import { useEffect, useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { EventTypeAccordion, SearchType } from './EventTypeAccordion';
import { InvestigationAccordion } from './InvestigationSearch/InvestigationAccordion';
import { LabReportAccordion } from './labReportSearch/LabReportAccordion';
import { initialLabForm } from './labReportSearch/DefaultLabReportForm';

type EventSearchProps = {
    onSearch: (filter: InvestigationFilter | LabReportFilter, type: SEARCH_TYPE) => void;
    investigationFilter?: InvestigationFilter;
    labReportFilter?: LabReportFilter;
};
export const EventSearch = ({ investigationFilter, labReportFilter, onSearch }: EventSearchProps) => {
    const [searchType, setSearchType] = useState<SearchType>();
    const investigationForm = useForm<InvestigationFilter>({ defaultValues: {} });
    const labReportForm = useForm<LabReportFilter>({
        defaultValues: initialLabForm()
    });

    // Auto scroll the 'criteria' section into view when expanding the accordion
    useEffect(() => {
        if (searchType) {
            const element = document.getElementsByClassName('usa-accordion__heading accordian-item');
            if (element) {
                element?.[2]?.addEventListener('click', function () {
                    const divElement = document.getElementById('criteria');
                    if (divElement) {
                        setTimeout(() => {
                            divElement.scrollIntoView({ behavior: 'smooth', block: 'end' });
                        }, 10);
                    }
                });
            }
        }
    }, [searchType]);

    // on page load, if an event search was performed set the selected event type
    useEffect(() => {
        if (investigationFilter) {
            setSearchType('investigation');
            investigationForm.reset(investigationFilter, { keepDefaultValues: true });
        } else if (labReportFilter) {
            setSearchType('labReport');
            labReportForm.reset({ ...initialLabForm(), ...labReportFilter }, { keepDefaultValues: true });
        } else {
            investigationForm.reset({}, { keepDefaultValues: true });
            labReportForm.reset(initialLabForm(), { keepDefaultValues: true });
        }
    }, [investigationFilter, labReportFilter]);

    const handleClearAll = () => {
        investigationForm.reset({}, { keepDefaultValues: true });
        labReportForm.reset(initialLabForm());
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
        investigationFilter.eventId = orUndefined(investigationFilter.eventId);
        investigationFilter.eventDate = orUndefined(investigationFilter.eventDate);
        investigationFilter.providerFacilitySearch = orUndefined(investigationFilter.providerFacilitySearch);
        return investigationFilter;
    };

    /**
     * Removes empty objects from the filter.
     * @example
     * { "eventId" : {}, "anotherField": "sample"} -> {"anotherField": "sample"}
     *
     * @param {LabReportFilter} labReportFilter
     * @return {LabReportFilter} LabReportFilter
     */
    const cleanLabFilter = (labReportFilter: LabReportFilter): LabReportFilter => {
        labReportFilter.eventId = orUndefined(labReportFilter.eventId);
        labReportFilter.eventDate = orUndefined(labReportFilter.eventDate);
        labReportFilter.providerSearch = orUndefined(labReportFilter.providerSearch);
        return labReportFilter;
    };

    const orUndefined = (object: any | undefined) => {
        return Object.values(object).find((e) => e !== undefined) ? object : undefined;
    };

    const handleSubmitInvestigation = (investigationFilter: InvestigationFilter) => {
        onSearch(cleanInvestigationFilter(investigationFilter), SEARCH_TYPE.INVESTIGATION);
    };

    const handleSubmitLabReport = (labReportFilter: LabReportFilter) => {
        onSearch(cleanLabFilter(labReportFilter), SEARCH_TYPE.LAB_REPORT);
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
