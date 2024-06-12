import { Results, View } from 'apps/search';

import { SearchTerms } from './terms/SearchTerms';
import { SearchResultsOptionsBar } from './options/SearchResultsOptionsBar';

import styles from './search-results-header.module.scss';

type Props = {
    view: View;
    results: Results;
};

const SearchResultsHeader = ({ view, results }: Props) => {
    return (
        <header className={styles.header}>
            <SearchTerms results={results} />
            <SearchResultsOptionsBar view={view} disabled={results.total === 0} />
        </header>
    );
};

export { SearchResultsHeader };
