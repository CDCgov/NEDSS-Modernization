import { SearchLayout, SearchNavigation } from 'apps/search/layout';

const LaboratoryReportSearch = () => {
    return (
        <SearchLayout
            navigation={() => <SearchNavigation />}
            criteria={() => <div>criteria</div>}
            renderAsList={() => <div>result list</div>}
            renderAsTable={() => <div>result table</div>}
            onSearch={() => {}}
            onClear={() => {}}
        />
    );
};

export { LaboratoryReportSearch };
