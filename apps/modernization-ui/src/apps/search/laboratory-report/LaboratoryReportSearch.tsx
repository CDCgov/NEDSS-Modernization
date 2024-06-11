import { SearchLayout } from 'apps/search/layout';

const LaboratoryReportSearch = () => {
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

export { LaboratoryReportSearch };
