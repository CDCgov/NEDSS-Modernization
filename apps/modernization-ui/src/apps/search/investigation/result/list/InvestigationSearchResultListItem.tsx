import { Investigation } from 'generated/graphql/schema';

type Props = {
    result: Investigation;
};

const InvestigationSearchResultListItem = ({ result }: Props) => <>{result.localId}</>;

export { InvestigationSearchResultListItem };
