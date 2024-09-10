import classNames from 'classnames';

import { View } from 'apps/search';
import { Term } from 'apps/search/terms';
import { SearchTerms } from './terms/SearchTerms';
import { SearchResultsOptionsBar } from './options/SearchResultsOptionsBar';
import styles from './search-results-header.module.scss';

type Props = {
    view: View;
    className?: string;
    total: number;
    terms: Term[];
};

const SearchResultsHeader = ({ className, view, total, terms }: Props) => {
    return (
        <header className={classNames(styles.header, className)}>
            <SearchTerms total={total} terms={terms} />
            <SearchResultsOptionsBar view={view} disabled={total === 0} />
        </header>
    );
};

export { SearchResultsHeader };
