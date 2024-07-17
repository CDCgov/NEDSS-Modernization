import { useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { SearchLayout, SearchResultList } from 'apps/search/layout';
import { LabReport } from 'generated/graphql/schema';
import { useLaboratoryReportSearch } from './useLaboratoryReportSearch';
import { LabReportFilterEntry, initial } from './labReportFormTypes';
import { LaboratoryReportSearchResultListItem } from './result/list';
import { FormAccordion } from './FormAccordion';
import { SearchCriteriaProvider } from 'providers/SearchCriteriaContext';
import { NoInputBanner } from '../NoInputBanner';
import { NoResultsBanner } from '../NoResultsBanner';

const LaboratoryReportSearch = () => {
    const formMethods = useForm<LabReportFilterEntry, Partial<LabReportFilterEntry>>({
        defaultValues: initial,
        mode: 'onBlur'
    });

    const { status, search, reset, results } = useLaboratoryReportSearch();

    useEffect(() => {
        if (status === 'waiting') {
            formMethods.reset();
        }
    }, [formMethods.reset, status]);

    return (
        <SearchCriteriaProvider>
            <SearchLayout
                criteria={() => <FormAccordion form={formMethods} />}
                resultsAsList={() => (
                    <SearchResultList<LabReport>
                        results={results?.content || []}
                        render={(result) => <LaboratoryReportSearchResultListItem result={result} />}
                    />
                )}
                resultsAsTable={() => <div>result table</div>}
                onSearch={formMethods.handleSubmit(search)}
                noInputResults={() => <NoInputBanner />}
                noResults={() => <NoResultsBanner />}
                onClear={reset}
            />
        </SearchCriteriaProvider>
    );
};

export { LaboratoryReportSearch };
