import { PatientSearchResult, InvestigationResults, LabReportResults } from 'generated/graphql/schema';
import { Column, DataTable } from 'design-system/table';
import { displayName } from 'name';
import { internalizeDate } from 'date';
import { displayAddress } from 'address/display';
import { useEffect } from 'react';
import { useColumnContext } from 'apps/search/context/ColumnContextProvider';

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

const PatientSearchResultTable = ({ results }: Props) => {
    const { displayColumns, register } = useColumnContext();

    useEffect(() => {
        register(columns as Column<PatientSearchResult | InvestigationResults | LabReportResults>[]);
    }, []);

    const columns: Column<PatientSearchResult>[] = [
        {
            id: 'lastNm',
            name: 'Legal name',
            fixed: true,
            sortable: true,
            render: (row) => row?.legalName && displayName()(row?.legalName)
        },
        {
            id: 'birthTime',
            name: 'Date of birth',
            sortable: true,
            render: (result) => internalizeDate(result.birthday)
        },
        { id: 'sex', name: 'Sex', sortable: true, render: (result) => result.gender },
        { id: 'id', name: 'Patient ID', render: (row) => row.shortId },
        { id: 'address', name: 'Address', render: displayAddresses },
        { id: 'phoneNumber', name: 'Phone', render: displayPhones },
        { id: 'names', name: 'Other names', render: displayNames },
        { id: 'identification', name: 'ID', render: displayIdentifications },
        { id: 'email', name: 'Email', render: displayEmails }
    ];

    const tableColumns: Column<PatientSearchResult>[] = displayColumns
        .map((orderItem) => columns.find((column) => column.id === orderItem.id && orderItem.visible))
        .filter((item): item is Column<PatientSearchResult> => item !== undefined);

    return (
        <DataTable<PatientSearchResult> id="patient-search-results" columns={tableColumns} data={results}></DataTable>
    );
};

export { PatientSearchResultTable };
