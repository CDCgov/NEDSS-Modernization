import { SearchLayout } from 'apps/search/layout';
import InvestigationSearchForm from './InvestigationSearchForm';
import { SearchCriteriaProvider } from 'providers/SearchCriteriaContext';

const InvestigationSearch = () => {
    return (
        <SearchCriteriaProvider>
            <SearchLayout
                criteria={() => <InvestigationSearchForm />}
                resultsAsList={() => <div>result list</div>}
                resultsAsTable={() => <div>result table</div>}
                onSearch={() => {}}
                onClear={() => {}}
            />
        </SearchCriteriaProvider>
    );
};

export { InvestigationSearch };
