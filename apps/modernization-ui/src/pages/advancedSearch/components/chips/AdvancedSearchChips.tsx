import { InvestigationFilter, LabReportFilter, PersonFilter } from 'generated/graphql/schema';

import { SEARCH_TYPE } from 'pages/advancedSearch/AdvancedSearch';
import { InvestigationChips } from './InvestigationChips';
import { PatientChips } from './PatientChips';
import { LabReportChips } from './LabReportChips';

type ChipProps = {
    lastSearchType: SEARCH_TYPE | undefined;
    personFilter?: PersonFilter;
    handlePersonFilterChange: (personFilter: PersonFilter) => void;
    investigationFilter?: InvestigationFilter;
    handleInvestigationFilterChange: (investigationFilter: InvestigationFilter) => void;
    labReportFilter?: LabReportFilter;
    handleLabReportFilterChange: (labReportFilter: LabReportFilter) => void;
};
export const AdvancedSearchChips = ({
    lastSearchType,
    personFilter,
    handlePersonFilterChange,
    investigationFilter,
    handleInvestigationFilterChange,
    labReportFilter,
    handleLabReportFilterChange
}: ChipProps) => {
    return (
        <>
            {lastSearchType === SEARCH_TYPE.PERSON && personFilter ? (
                <PatientChips filter={personFilter} handlePersonFilterChange={handlePersonFilterChange} />
            ) : null}
            {lastSearchType === SEARCH_TYPE.INVESTIGATION && investigationFilter ? (
                <InvestigationChips
                    filter={investigationFilter}
                    handleInvestigationFilterChange={handleInvestigationFilterChange}
                />
            ) : null}
            {lastSearchType === SEARCH_TYPE.LAB_REPORT && labReportFilter ? (
                <LabReportChips filter={labReportFilter} handleLabReportFilterChange={handleLabReportFilterChange} />
            ) : null}
        </>
    );
};
