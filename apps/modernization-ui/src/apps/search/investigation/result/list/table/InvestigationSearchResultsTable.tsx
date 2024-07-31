import { useEffect } from 'react';
import { Investigation, InvestigationPersonParticipation } from 'generated/graphql/schema';
import { Column, DataTable } from 'design-system/table';
import { ColumnPreference, useColumnPreferences } from 'design-system/table/preferences';
import { internalizeDate } from 'date';
import { ClassicLink } from 'classic';
import { Link } from 'react-router-dom';
import styles from './investigation-search-results-table.module.scss';

const getPatient = (investigation: Investigation): InvestigationPersonParticipation | undefined | null => {
    return investigation.personParticipations?.find((p) => p?.typeCd === 'SubjOfPHC');
};

const getInvestigatorName = (investigation: Investigation): string | undefined => {
    const provider = investigation.personParticipations?.find((p) => p?.typeCd === 'InvestgrOfPHC');
    if (provider) {
        return `${provider.firstName} ${provider.lastName}`;
    } else {
        return undefined;
    }
};

const getInvestigationStatusString = (investigation: Investigation): string =>
    investigation.investigationStatusCd === 'C' ? 'CLOSED' : 'OPEN';

const LEGAL_NAME = { id: 'lastNm', name: 'Legal name' };
const DATE_OF_BIRTH = { id: 'birthTime', name: 'Date of birth' };
const SEX = { id: 'sex', name: 'Sex' };
const PATIENT_ID = { id: 'id', name: 'Patient ID' };
const CONDITION = { id: 'condition', name: 'Condition' };
const START_DATE = { id: 'startDate', name: 'Start date' };
const JURSIDICTION = { id: 'jurisdition', name: 'Jurisdiction' };
const INVESTIGATOR = { id: 'investigator', name: 'Investigator' };
const INVESTIGATION_ID = { id: 'investigationId', name: 'Investigation ID' };
const STATUS = { id: 'status', name: 'Status' };
const NOTIFICATION = { id: 'notificaiton', name: 'Notification' };

const columns: Column<Investigation>[] = [
    {
        ...LEGAL_NAME,
        fixed: true,
        sortable: true,
        render: (row) => {
            const patient = getPatient(row);
            return patient
                ? (
                      <Link id="legalName" to={`${'/patient-profile/'}${patient?.shortId}${'/summary'}`}>
                          {patient.firstName ?? ''} {patient.lastName ?? ''}
                      </Link>
                  ) || 'N/A'
                : 'N/A';
        }
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
        ...CONDITION,
        sortable: true,
        render: (row) => {
            const patient = getPatient(row);
            return (
                <ClassicLink
                    id="condition"
                    url={`/nbs/api/profile/${patient?.personParentUid}/investigation/${row.id}`}>
                    {row.cdDescTxt}
                </ClassicLink>
            );
        }
    },
    {
        ...START_DATE,
        sortable: true,
        render: (row) => {
            return internalizeDate(row.addTime);
        }
    },
    {
        ...JURSIDICTION,
        sortable: true,
        render: (row) => {
            return row.jurisdictionCodeDescTxt;
        }
    },
    {
        ...INVESTIGATOR,
        sortable: true,
        render: (row) => {
            return getInvestigatorName(row);
        }
    },
    {
        ...INVESTIGATION_ID,
        sortable: true,
        render: (row) => {
            return row.id;
        }
    },
    {
        ...STATUS,
        sortable: true,
        render: (row) => {
            return <StatusBadge investigation={row}>{getInvestigationStatusString(row)}</StatusBadge>;
        }
    },
    {
        ...NOTIFICATION,
        sortable: true,
        render: (row) => {
            return row.notificationRecordStatusCd;
        }
    }
];

const preferences: ColumnPreference[] = [
    { ...LEGAL_NAME },
    { ...DATE_OF_BIRTH },
    { ...SEX },
    { ...PATIENT_ID },
    { ...CONDITION, moveable: true, toggleable: true },
    { ...START_DATE, moveable: true, toggleable: true },
    { ...JURSIDICTION, moveable: true, toggleable: true },
    { ...INVESTIGATOR, moveable: true, toggleable: true },
    { ...INVESTIGATION_ID, moveable: true, toggleable: true },
    { ...STATUS, moveable: true, toggleable: true },
    { ...NOTIFICATION, moveable: true, toggleable: true }
];

type Props = {
    results: Investigation[];
};

type StatusBadgeProps = {
    children?: string;
    investigation?: Investigation;
};

const StatusBadge = ({ children, investigation }: StatusBadgeProps) => (
    <span className={`${styles.status} ${investigation?.investigationStatusCd === 'C' && styles.C}`} id="status">
        {children}
    </span>
);

const InvestigationSearchResultsTable = ({ results }: Props) => {
    const { apply, register } = useColumnPreferences();

    useEffect(() => {
        register('Patients', preferences);
    }, []);

    return <DataTable<Investigation> id="patient-search-results" columns={apply(columns)} data={results} />;
};

export { InvestigationSearchResultsTable };
