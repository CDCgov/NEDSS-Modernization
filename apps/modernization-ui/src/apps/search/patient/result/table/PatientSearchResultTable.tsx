import { PatientSearchResult } from 'generated/graphql/schema';
import { Column, DataTable } from 'design-system/table';
import { displayName } from 'name';
import { internalizeDate } from 'date';
import { displayAddress } from 'address/display';

const displayNames = (result: PatientSearchResult): string => {
    const legalName = result.legalName;
    return result.names
        .filter((name) => name?.first != legalName?.first || name?.last != legalName?.last)
        .map(displayName())
        .join('\n');
};

const displayAddresses = (result: PatientSearchResult): string => result.addresses.map(displayAddress).join('\n');

const displayIdentifications = (result: PatientSearchResult): string =>
    result.identification.map((identification) => identification.type + '\n' + identification.value).join('\n');

const displayPhones = (result: PatientSearchResult): string => result.phones.join('\n');
const displayEmails = (result: PatientSearchResult): string => result.emails.join('\n');

type Props = {
    results: PatientSearchResult[];
};

const columns: Column<PatientSearchResult>[] = [
    {
        id: 'lastNm',
        name: 'Legal name',
        fixed: true,
        sortable: true,
        render: (row) => row?.legalName && displayName()(row?.legalName)
    },
    { id: 'birthTime', name: 'Date of birth', sortable: true, render: (result) => internalizeDate(result.birthday) },
    { id: 'sex', name: 'Sex', sortable: true, render: (result) => result.gender },
    { id: 'id', name: 'Patient ID', sortable: true, render: (row) => row.shortId },
    { id: 'address', name: 'Address', sortable: true, render: displayAddresses },
    { id: 'phoneNumber', name: 'Phone', sortable: true, render: displayPhones },
    { id: 'names', name: 'Other names', render: displayNames },
    { id: 'identification', name: 'ID', sortable: true, render: displayIdentifications },
    { id: 'email', name: 'Email', sortable: true, render: displayEmails }
];

const PatientSearchResultTable = ({ results }: Props) => {
    return <DataTable<PatientSearchResult> id="patient-search-results" columns={columns} data={results}></DataTable>;
};

export { PatientSearchResultTable };
