import { LabReport } from 'generated/graphql/schema';
import { Column, DataTable } from 'design-system/table';
import { ColumnPreference, useColumnPreferences } from 'design-system/table/preferences';
import { internalizeDate } from 'date';
import { ClassicLink } from 'classic';
import { Selectable } from 'options';
import {
    getPatient,
    getOrderingProviderName,
    getReportingFacility,
    getDescription,
    getAssociatedInvestigations,
    getPatientName
} from 'apps/search/laboratory-report/result';

const LEGAL_NAME = { id: 'lastNm', name: 'Legal name' };
const DATE_OF_BIRTH = { id: 'birthTime', name: 'Date of birth' };
const SEX = { id: 'sex', name: 'Sex' };
const PATIENT_ID = { id: 'id', name: 'Patient ID' };

const DOCUMENT_TYPE = { id: 'documentType', name: 'Document type' };
const DATE_RECEIVED = { id: 'dateReceived', name: 'Date received' };
const DESCRIPTION = { id: 'description', name: 'Description' };
const REPORTING_FACILITY = { id: 'reportingFacility', name: 'Reporting facility' };
const ORDERING_PROVIDER = { id: 'orderingProvider', name: 'Ordering provider' };
const JURSIDICTION = { id: 'jurisdiction', name: 'Jurisdiction' };
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
    { ...JURSIDICTION, moveable: true, toggleable: true },
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
            render: getPatientName
        },
        {
            ...DATE_OF_BIRTH,
            sortable: true,
            render: (row) => {
                const patient = getPatient(row);
                return internalizeDate(patient?.birthTime);
            }
        },
        {
            ...SEX,
            sortable: true,
            render: (row) => {
                const patient = getPatient(row);
                return patient?.currSexCd;
            }
        },
        {
            ...PATIENT_ID,
            sortable: true,
            render: (row) => {
                const patient = getPatient(row);
                return patient?.shortId;
            }
        },
        {
            ...DOCUMENT_TYPE,
            render: (row) => {
                const patient = getPatient(row);
                return (
                    <ClassicLink
                        id="condition"
                        url={`/nbs/api/profile/${patient?.personParentUid}/report/lab/${row.id}`}>
                        Lab report
                    </ClassicLink>
                );
            }
        },
        {
            ...DATE_RECEIVED,
            render: (row) => {
                return internalizeDate(row.addTime);
            }
        },
        {
            ...DESCRIPTION,
            render: (row) => {
                return getDescription(row);
            }
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
            ...JURSIDICTION,
            render: (row) => jurisdictionResolver(String(row.jurisdictionCd))?.name
        },
        {
            ...ASSOCIATED_WITH,
            render: getAssociatedInvestigations
        },
        {
            ...LOCAL_ID,
            render: (row) => {
                return row.localId;
            }
        }
    ];

    return <DataTable<LabReport> id="laboratory-report-search-results" columns={apply(columns)} data={results} />;
};

export { LaboratoryReportSearchResultsTable, preferences };
