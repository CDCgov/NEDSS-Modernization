import { PatientSearchResult } from 'generated/graphql/schema';
import { DataTable } from 'design-system/table';

type Props = {
    results: PatientSearchResult[];
};

const PatientSearchResultTable = ({ results }: Props) => {
    return <DataTable<PatientSearchResult> data={results}></DataTable>;
};

export { PatientSearchResultTable };
