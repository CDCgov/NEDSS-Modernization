import { useEffect } from 'react';
import { PatientSearchResult, PatientSearchResultIdentification } from 'generated/graphql/schema';
import { Column, DataTable } from 'design-system/table';
import { ColumnPreference, useColumnPreferences } from 'design-system/table/preferences';
import { displayName } from 'name';
import { internalizeDate } from 'date';
import { ExpandableResults } from 'components/Search/ExpandableResults';
import { displayAddress } from 'address/display';

const displayNames = (result: PatientSearchResult) => {
    const legalName = result.legalName;
    const filteredNames = result.names.filter(
        (name) => name?.first != legalName?.first || name?.last != legalName?.last
    );

    return <ExpandableResults results={filteredNames} renderResult={displayName()} />;
};

const displayAddresses = (result: PatientSearchResult) => (
    <ExpandableResults results={result.addresses} renderResult={displayAddress} />
);

const displayIdentifications = (result: PatientSearchResult) => (
    <ExpandableResults results={result.identification} renderResult={displayIdentification} />
);

const displayIdentification = (identification: PatientSearchResultIdentification): string =>
    identification.type + '\n' + identification.value;

const displayPhones = (result: PatientSearchResult) => <ExpandableResults results={result.phones} />;

const displayEmails = (result: PatientSearchResult) => <ExpandableResults results={result.emails} />;

const LEGAL_NAME = {
    id: 'lastNm',
    name: 'Legal name'
};

const DATE_OF_BIRTH = { id: 'birthTime', name: 'Date of birth' };

const SEX = { id: 'sex', name: 'Sex' };

const PATIENT_ID = { id: 'id', name: 'Patient ID' };

const ADDRESS = { id: 'address', name: 'Address' };

const PHONE = { id: 'phoneNumber', name: 'Phone' };

const NAMES = { id: 'names', name: 'Other names' };

const IDENTIFICATIONS = { id: 'identification', name: 'ID' };

const EMAIL = { id: 'email', name: 'Email' };

const columns: Column<PatientSearchResult>[] = [
    {
        ...LEGAL_NAME,
        fixed: true,
        sortable: true,
        render: (row) => row?.legalName && displayName()(row?.legalName)
    },
    {
        ...DATE_OF_BIRTH,
        sortable: true,
        render: (result) => internalizeDate(result.birthday)
    },
    { ...SEX, sortable: true, render: (result) => result.gender },
    { ...PATIENT_ID, sortable: true, render: (row) => row.shortId },
    { ...ADDRESS, render: displayAddresses },
    { ...PHONE, render: displayPhones },
    { ...NAMES, render: displayNames },
    { ...IDENTIFICATIONS, render: displayIdentifications },
    { ...EMAIL, render: displayEmails }
];

const preferences: ColumnPreference[] = [
    { ...LEGAL_NAME },
    { ...DATE_OF_BIRTH },
    { ...SEX },
    { ...PATIENT_ID },
    { ...ADDRESS, moveable: true, toggleable: true },
    { ...PHONE, moveable: true, toggleable: true },
    { ...NAMES, moveable: true, toggleable: true },
    { ...IDENTIFICATIONS, moveable: true, toggleable: true },
    { ...EMAIL, moveable: true, toggleable: true }
];

type Props = {
    results: PatientSearchResult[];
};

const PatientSearchResultTable = ({ results }: Props) => {
    const { apply, register } = useColumnPreferences();

    useEffect(() => {
        register(preferences);
    }, []);

    return <DataTable<PatientSearchResult> id="patient-search-results" columns={apply(columns)} data={results} />;
};

export { PatientSearchResultTable };
