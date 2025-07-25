import { PatientSearchResult } from 'generated/graphql/schema';
import { useSorting } from 'libs/sorting';
import { Sizing } from 'design-system/field';
import { Column, DataTable } from 'design-system/table';
import { ColumnPreference, useColumnPreferences } from 'design-system/table/preferences';

import {
    displayPhones,
    displayPatientName,
    displayPatientAge,
    displayEmails,
    displayAddresses,
    displayIdentifications,
    PatientFileLink
} from 'apps/search/patient/result';

import styles from './patient-search-result-table.module.scss';
import { useFilter } from 'design-system/filter';

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
        render: (result) => <PatientFileLink identifier={result.patient} patientId={result.shortId} />,
        filter: { id: 'id', type: 'text' },
        sortIconType: 'numeric'
    },
    {
        ...PATIENT_NAME,
        sortable: true,
        className: styles['col-patientname'],
        render: displayPatientName,
        filter: { id: 'name', type: 'text' },
        sortIconType: 'alpha'
    },
    {
        ...DATE_OF_BIRTH,
        sortable: true,
        className: styles['col-dob'],
        render: (result) => result.birthday && displayPatientAge(result, 'multiline'),
        filter: { id: 'ageOrDateOfBirth', type: 'text' },
        sortIconType: 'numeric'
    },
    {
        ...SEX,
        sortable: true,
        className: styles['col-sex'],
        render: (result) => result.gender,
        filter: { id: 'sex', type: 'text' },
        sortIconType: 'alpha'
    },
    {
        ...ADDRESS,
        className: styles['col-address'],
        render: displayAddresses,
        filter: { id: 'address', type: 'text' },
        sortable: true,
        sortIconType: 'alpha'
    },
    {
        ...PHONE,
        className: styles['col-phone'],
        render: displayPhones,
        filter: { id: 'phone', type: 'text' },
        sortable: true,
        sortIconType: 'numeric'
    },
    {
        ...IDENTIFICATIONS,
        className: styles['col-id'],
        render: displayIdentifications,
        filter: { id: 'identification', type: 'text' },
        sortable: true,
        sortIconType: 'alpha'
    },
    {
        ...EMAIL,
        className: styles['col-email'],
        render: displayEmails,
        sortable: true,
        filter: { id: 'email', type: 'text' },
        sortIconType: 'alpha'
    }
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

type Props = { results: PatientSearchResult[]; sizing?: Sizing };

const PatientSearchResultTable = ({ results, sizing }: Props) => {
    const { apply } = useColumnPreferences();
    const sorting = useSorting();
    const filtering = useFilter();

    return (
        <DataTable<PatientSearchResult>
            id="patient-search-results"
            className={styles.patient_results}
            columns={apply(columns)}
            data={results}
            sizing={sizing}
            features={{ sorting, filtering }}
            onEmpty={() => <></>}
        />
    );
};

export { PatientSearchResultTable, preferences };
