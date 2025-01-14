import { LabReport } from 'generated/graphql/schema';
import { Column, DataTable } from 'design-system/table';
import { ColumnPreference, useColumnPreferences } from 'design-system/table/preferences';
import { internalizeDate } from 'date';
import { ClassicLink } from 'classic';
import { Selectable } from 'options';
import { withPatient, displayProfileLink, displayGender } from 'apps/search/basic';
import {
    getPatient,
    getOrderingProviderName,
    getReportingFacility,
    getDescription,
    getAssociatedInvestigations
} from 'apps/search/laboratory-report/result';

const LEGAL_NAME = { id: 'lastNm', name: 'Legal name' };
const DATE_OF_BIRTH = { id: 'birthTime', name: 'Date of birth' };
const SEX = { id: 'sex', name: 'Current sex' };
const PATIENT_ID = { id: 'patientid', name: 'Patient ID' };

const DOCUMENT_TYPE = { id: 'documentType', name: 'Document type' };
const DATE_RECEIVED = { id: 'dateReceived', name: 'Date received' };
const DESCRIPTION = { id: 'description', name: 'Description' };
const REPORTING_FACILITY = { id: 'reportingFacility', name: 'Reporting facility' };
const ORDERING_PROVIDER = { id: 'orderingProvider', name: 'Ordering provider' };
const JURISDICTION = { id: 'jurisdiction', name: 'Jurisdiction' };
const ASSOCIATED_WITH = { id: 'associatedWith', name: 'Associated with' };
const LOCAL_ID = { id: 'localId', name: 'Local ID' };

const preferences: ColumnPreference[] = [
    { ...LEGAL_NAME },
    { ...DATE_OF_BIRTH },
    { ...SEX },
    { ...PATIENT_ID },
    { ...DOCUMENT_TYPE, moveable: true, toggleable: true },
    { ...DATE_RECEIVED, moveable: true, toggleable: true },
    { ...DESCRIPTION, moveable: true, toggleable: true },
    { ...REPORTING_FACILITY, moveable: true, toggleable: true },
    { ...ORDERING_PROVIDER, moveable: true, toggleable: true },
    { ...JURISDICTION, moveable: true, toggleable: true },
    { ...ASSOCIATED_WITH, moveable: true, toggleable: true },
    { ...LOCAL_ID, moveable: true, toggleable: true }
];

type Props = {
    results: LabReport[];
    jurisdictionResolver: (value: string) => Selectable | undefined;
};

const LaboratoryReportSearchResultsTable = ({ results, jurisdictionResolver }: Props) => {
    const { apply } = useColumnPreferences();
    const columns: Column<LabReport>[] = [
        {
            ...LEGAL_NAME,
            fixed: true,
            sortable: true,
            render: withPatient(getPatient, displayProfileLink)
        },
        {
            ...DATE_OF_BIRTH,
            sortable: true,
            render: (row) => internalizeDate(getPatient(row)?.birthTime)
        },
        {
            ...SEX,
            sortable: true,
            render: withPatient(getPatient, displayGender)
        },
        {
            ...PATIENT_ID,
            sortable: true,
            render: (row) => getPatient(row)?.shortId
        },
        {
            ...DOCUMENT_TYPE,
            render: (row) => (
                <ClassicLink
                    id="condition"
                    url={`/nbs/api/profile/${getPatient(row)?.personParentUid}/report/lab/${row.id}`}>
                    Lab report
                </ClassicLink>
            )
        },
        {
            ...DATE_RECEIVED,
            render: (row) => internalizeDate(row.addTime)
        },
        {
            ...DESCRIPTION,
            render: getDescription
        },
        {
            ...REPORTING_FACILITY,
            render: getReportingFacility
        },
        {
            ...ORDERING_PROVIDER,
            render: getOrderingProviderName
        },
        {
            ...JURISDICTION,
            render: (row) => jurisdictionResolver(String(row.jurisdictionCd))?.name
        },
        {
            ...ASSOCIATED_WITH,
            render: getAssociatedInvestigations
        },
        {
            ...LOCAL_ID,
            render: (row) => row.localId
        }
    ];

    return <DataTable<LabReport> id="laboratory-report-search-results" columns={apply(columns)} data={results} />;
};

export { LaboratoryReportSearchResultsTable, preferences };
