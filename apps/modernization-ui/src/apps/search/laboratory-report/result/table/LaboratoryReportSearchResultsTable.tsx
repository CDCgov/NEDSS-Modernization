import { useEffect } from 'react';
import { LabReport, LabReportPersonParticipation, LabReportOrganizationParticipation } from 'generated/graphql/schema';
import { Column, DataTable } from 'design-system/table';
import { ColumnPreference, useColumnPreferences } from 'design-system/table/preferences';
import { internalizeDate } from 'date';
import { ClassicLink } from 'classic';
import { useJurisdictionOptions } from 'options/jurisdictions';

const getPatient = (labReport: LabReport): LabReportPersonParticipation | undefined | null => {
    return labReport.personParticipations?.find((p) => p?.typeCd === 'PATSBJ');
};

const getOrderingProviderName = (labReport: LabReport): string | undefined => {
    const provider = labReport.personParticipations.find((p) => p.typeCd === 'ORD' && p.personCd === 'PRV');
    if (provider) {
        return `${provider.firstName} ${provider.lastName}`;
    } else {
        return undefined;
    }
};

const getReportingFacility = (labReport: LabReport): LabReportOrganizationParticipation | undefined => {
    return labReport.organizationParticipations.find((o) => o?.typeCd === 'AUT');
};

const getDescription = (labReport: LabReport): string | undefined => {
    const observation = labReport.observations?.find((o) => o?.altCd && o?.displayName && o?.cdDescTxt);

    return observation && `${observation.cdDescTxt} = ${observation.displayName}`;
};

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
};

const LaboratoryReportSearchResultsTable = ({ results }: Props) => {
    const { apply, register } = useColumnPreferences();
    const { resolve: findById } = useJurisdictionOptions();
    const columns: Column<LabReport>[] = [
        {
            ...LEGAL_NAME,
            fixed: true,
            sortable: true,
            render: (row) => {
                const patient = getPatient(row);
                return patient ? `${patient.firstName ?? ''} ${patient.lastName ?? ''}`.trim() || 'N/A' : 'N/A';
            }
        },
        {
            ...DATE_OF_BIRTH,
            sortable: true,
            render: (row) => {
                const patient = getPatient(row);
                console.log('PATIENT', patient);
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
                        url={`/nbs/api/profile/${patient?.personParentUid}/investigation/${row.id}`}>
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
            render: (row) => {
                return getReportingFacility(row)?.name;
            }
        },
        {
            ...ORDERING_PROVIDER,
            render: (row) => {
                return getOrderingProviderName(row);
            }
        },
        {
            ...JURSIDICTION,
            render: (row) => {
                return findById(String(row.jurisdictionCd))?.name;
            }
        },
        {
            ...ASSOCIATED_WITH,
            render: (row) => {
                return row.associatedInvestigations
                    ?.map((investigation) => `${investigation?.localId}\n${investigation?.cdDescTxt}\n`)
                    .join('\n');
            }
        },
        {
            ...LOCAL_ID,
            render: (row) => {
                return row.localId;
            }
        }
    ];

    useEffect(() => {
        register(preferences);
    }, []);

    return <DataTable<LabReport> id="laboratory-report-search-results" columns={apply(columns)} data={results} />;
};

export { LaboratoryReportSearchResultsTable };
