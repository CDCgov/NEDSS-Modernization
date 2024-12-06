import { PatientSearchResult } from 'generated/graphql/schema';
import { Column, DataTable } from 'design-system/table';
import { ColumnPreference, useColumnPreferences } from 'design-system/table/preferences';
import {
    displayPhones,
    displayPatientName,
    displayPatientAge,
    displayProfileLink,
    displayEmails,
    displayAddresses,
    displayIdentifications
} from 'apps/search/patient/result';
import styles from './patient-search-result-table.module.scss';

// column definitions
const PATIENT_ID = { id: 'patientid', name: 'Patient ID' };
const PATIENT_NAME = { id: 'patientname', name: 'Patient name' };
const DATE_OF_BIRTH = { id: 'birthday', name: 'DOB/Age' };
const SEX = { id: 'sex', name: 'Current sex' };
const ADDRESS = { id: 'address', name: 'Address' };
const PHONE = { id: 'phoneNumber', name: 'Phone' };
const IDENTIFICATIONS = { id: 'identification', name: 'ID' };
const EMAIL = { id: 'email', name: 'Email' };

// table columns
const columns: Column<PatientSearchResult>[] = [
    {
        ...PATIENT_ID,
        fixed: true,
        sortable: true,
        className: styles['col-patientid'],
        render: (result) => displayProfileLink(result.patient, result.shortId)
    },
    {
        ...PATIENT_NAME,
        sortable: true,
        className: styles['col-patientname'],
        render: displayPatientName
    },
    {
        ...DATE_OF_BIRTH,
        sortable: true,
        className: styles['col-dob'],
        render: (result) => result.birthday && displayPatientAge(result, 'multiline')
    },
    { ...SEX, sortable: true, className: styles['col-sex'], render: (result) => result.gender },
    { ...ADDRESS, className: styles['col-address'], render: displayAddresses },
    { ...PHONE, className: styles['col-phone'], render: displayPhones },
    { ...IDENTIFICATIONS, className: styles['col-id'], render: displayIdentifications },
    { ...EMAIL, className: styles['col-email'], render: displayEmails }
];

// column preferences
const preferences: ColumnPreference[] = [
    { ...PATIENT_ID },
    { ...PATIENT_NAME, moveable: true, toggleable: true },
    { ...DATE_OF_BIRTH, moveable: true, toggleable: true },
    { ...SEX, moveable: true, toggleable: true },
    { ...ADDRESS, moveable: true, toggleable: true },
    { ...PHONE, moveable: true, toggleable: true },
    { ...IDENTIFICATIONS, moveable: true, toggleable: true },
    { ...EMAIL, moveable: true, toggleable: true }
];

type Props = {
    results: PatientSearchResult[];
};

const PatientSearchResultTable = ({ results }: Props) => {
    const { apply } = useColumnPreferences();

    return <DataTable<PatientSearchResult> id="patient-search-results" columns={apply(columns)} data={results} />;
};

export { PatientSearchResultTable, preferences };
