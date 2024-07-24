import classNames from 'classnames';

import { Term, View } from 'apps/search';
import { SearchTerms } from './terms/SearchTerms';
import { SearchResultsOptionsBar } from './options/SearchResultsOptionsBar';

import styles from './search-results-header.module.scss';

type Props = {
    view: View;
    className?: string;
    total: number;
    terms: Term[];
    onRemoveTerm: (term: Term) => void;
};

const SearchResultsHeader = ({ className, view, total, terms, onRemoveTerm }: Props) => {
    return (
        <header className={classNames(styles.header, className)}>
            <SearchTerms onRemoveTerm={onRemoveTerm} total={total} terms={terms} />
            <SearchResultsOptionsBar view={view} disabled={total === 0} />
        </header>
    );
};

export { SearchResultsHeader };
