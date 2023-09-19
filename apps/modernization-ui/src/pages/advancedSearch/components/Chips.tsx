import { InvestigationFilter, LabReportFilter, PersonFilter } from 'generated/graphql/schema';
import { SEARCH_TYPE } from '../AdvancedSearch';

type ChipProps = {
    lastSearchType: SEARCH_TYPE | undefined;
    patientFilter?: PersonFilter;
    setPersonFilter: (personFilter: PersonFilter) => void;
    investigationFilter?: InvestigationFilter;
    setInvestigationFilter: (investigationFilter: InvestigationFilter) => void;
    labReportFilter?: LabReportFilter;
    setLabReportFilter: (labReportFilter: LabReportFilter) => void;
};
export const Chips = ({
    lastSearchType,
    patientFilter,
    // setPersonFilter,
    investigationFilter,
    // setInvestigationFilter,
    labReportFilter
}: // setLabReportFilter
ChipProps) => {
    return (
        <div>
            Chips -{' '}
            {lastSearchType === SEARCH_TYPE.PERSON ? (
                <>Person Chips {patientFilter === undefined ? 'undefined' : 'defined'}</>
            ) : null}
            {lastSearchType === SEARCH_TYPE.INVESTIGATION ? (
                <>Investigation Chips {investigationFilter === undefined ? 'undefined' : 'defined'}</>
            ) : null}
            {lastSearchType === SEARCH_TYPE.LAB_REPORT ? (
                <>Lab Report Chips {labReportFilter === undefined ? 'undefined' : 'defined'}</>
            ) : null}
        </div>
    );
};
