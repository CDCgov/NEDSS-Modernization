import { useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { SearchLayout, SearchResultList } from 'apps/search/layout';
import { LabReport } from 'generated/graphql/schema';

import { useLaboratoryReportSearch } from './useLaboratoryReportSearch';
import { LabReportFilterEntry, initial } from './labReportFormTypes';
import { LaboratoryReportSearchResultListItem } from './result/list';

const LaboratoryReportSearch = () => {
    const { handleSubmit, reset: resetForm } = useForm<LabReportFilterEntry, Partial<LabReportFilterEntry>>({
        defaultValues: initial,
        mode: 'onBlur'
    });

    const { status, search, reset, results } = useLaboratoryReportSearch();

    useEffect(() => {
        if (status === 'waiting') {
            resetForm();
        }
    }, [resetForm, status]);

    return (
        <SearchLayout
            criteria={() => <div>criteria</div>}
            resultsAsList={() => (
                <SearchResultList<LabReport>
                    results={results?.content ?? []}
                    render={(result) => <LaboratoryReportSearchResultListItem result={result} />}
                />
            )}
            resultsAsTable={() => <div>result table</div>}
            onSearch={handleSubmit(search)}
            onClear={reset}
        />
    );
};

export { LaboratoryReportSearch };
