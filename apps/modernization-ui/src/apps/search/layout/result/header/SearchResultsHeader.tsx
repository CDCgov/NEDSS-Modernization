import classNames from 'classnames';

import { Results, View } from 'apps/search';
import { SearchTerms } from './terms/SearchTerms';
import { SearchResultsOptionsBar } from './options/SearchResultsOptionsBar';

import styles from './search-results-header.module.scss';

type Props = {
    className?: string;
    view: View;
    results: Results;
};

const SearchResultsHeader = ({ className, view, results }: Props) => {
    return (
        <header className={classNames(styles.header, className)}>
            <SearchTerms results={results} />
            <SearchResultsOptionsBar view={view} disabled={results.total === 0} />
        </header>
    );
};

export { SearchResultsHeader };
