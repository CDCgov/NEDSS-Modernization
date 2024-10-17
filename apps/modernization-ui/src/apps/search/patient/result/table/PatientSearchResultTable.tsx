import { PatientSearchResult } from 'generated/graphql/schema';
import { Column, DataTable } from 'design-system/table';
import { ColumnPreference, useColumnPreferences } from 'design-system/table/preferences';
import { internalizeDate } from 'date';
import {
    displayPhones,
    displayProfileLink,
    displayNames,
    displayEmails,
    displayAddresses
} from 'apps/search/patient/result';
import { displayName } from 'name';

const displayIdentifications = (result: PatientSearchResult): string =>
    result.identification.map((identification) => identification.type + '\n' + identification.value).join('\n');

// column definitions
const PATIENT_ID = { id: 'patientid', name: 'Patient ID' };
const LEGAL_NAME = { id: 'legalName', name: 'Legal name' };
const DATE_OF_BIRTH = { id: 'birthday', name: 'Date of birth' };
const SEX = { id: 'sex', name: 'Current sex' };
const ADDRESS = { id: 'address', name: 'Address' };
const PHONE = { id: 'phoneNumber', name: 'Phone' };
const NAMES = { id: 'names', name: 'Other names' };
const IDENTIFICATIONS = { id: 'identification', name: 'ID' };
const EMAIL = { id: 'email', name: 'Email' };

// table columns
const columns: Column<PatientSearchResult>[] = [
    {
        ...PATIENT_ID,
        fixed: true,
        sortable: true,
        render: (result) => displayProfileLink(result.shortId)
    },
    {
        ...DATE_OF_BIRTH,
        sortable: true,
        render: (result) => internalizeDate(result.birthday)
    },
    { ...SEX, sortable: true, render: (result) => result.gender },
    {
        ...LEGAL_NAME,
        sortable: true,
        render: (result) => result.legalName && displayName('fullLastFirst')(result.legalName)
    },
    { ...ADDRESS, render: displayAddresses },
    { ...PHONE, render: displayPhones },
    { ...NAMES, render: displayNames },
    { ...IDENTIFICATIONS, render: displayIdentifications },
    { ...EMAIL, render: displayEmails }
];

// column preferences
const preferences: ColumnPreference[] = [
    { ...PATIENT_ID },
    { ...LEGAL_NAME, moveable: true, toggleable: true, hidden: true },
    { ...NAMES, moveable: true, toggleable: true, hidden: true },
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
