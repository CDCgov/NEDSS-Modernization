import { PatientSearchResult } from 'generated/graphql/schema';

type Props = {
    result: PatientSearchResult;
};

const PatientSearchResultListItem = ({ result }: Props) => <>{result.patient}</>;

export { PatientSearchResultListItem };
