import { SearchLayout } from 'apps/search/layout';

const InvestigationSearch = () => {
    return (
        <SearchLayout
            criteria={() => <div>criteria</div>}
            resultsAsList={() => <div>result list</div>}
            resultsAsTable={() => <div>result table</div>}
            onSearch={() => {}}
            onClear={() => {}}
        />
    );
};

export { InvestigationSearch };
