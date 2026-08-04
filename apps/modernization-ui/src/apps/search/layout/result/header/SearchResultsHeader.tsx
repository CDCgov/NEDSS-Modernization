import { View } from 'apps/search';
import { Term } from 'apps/search/terms';
import classNames from 'classnames';
import { Sizing } from 'design-system/field';

import { SearchResultsOptionsBar } from './options/SearchResultsOptionsBar';
import styles from './search-results-header.module.scss';
import { SearchTerms } from './terms/SearchTerms';

type Props = {
    view: View;
    className?: string;
    total: number;
    filteredTotal?: number;
    terms: Term[];
    sizing?: Sizing;
};

/**
 * Renders the header for search results, including search terms and options bar.
 * If filteredTotal is provided, displays "Tf of T results for:".
 * @param {Props} props - The properties for the component.
 * @return {JSX.Element} The rendered header component.
 */
const SearchResultsHeader = ({ className, sizing, view, total, filteredTotal, terms }: Props) => {
    return (
        <header className={classNames(styles.header, className, styles[sizing ?? ''])}>
            <SearchTerms total={total} filteredTotal={filteredTotal} terms={terms} />
            <SearchResultsOptionsBar view={view} sizing={sizing} />
        </header>
    );
};

export { SearchResultsHeader };
