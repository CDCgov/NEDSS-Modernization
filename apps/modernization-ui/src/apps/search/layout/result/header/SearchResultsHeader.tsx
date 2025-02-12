import classNames from 'classnames';
import { Sizing } from 'design-system/field';
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
    sizing?: Sizing;
};

const SearchResultsHeader = ({ className, sizing, view, total, terms }: Props) => {
    return (
        <header className={classNames(styles.header, className)}>
            <SearchTerms total={total} terms={terms} />
            <SearchResultsOptionsBar view={view} disabled={total === 0} sizing={sizing} />
        </header>
    );
};

export { SearchResultsHeader };
