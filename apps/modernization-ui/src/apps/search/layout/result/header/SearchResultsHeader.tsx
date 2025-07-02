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
    shortcutKeyAnnouncement?: string;
};

const SearchResultsHeader = ({ className, sizing, view, total, terms, shortcutKeyAnnouncement }: Props) => {
    return (
        <header className={classNames(styles.header, className, styles[sizing ?? ''])}>
            <SearchTerms total={total} terms={terms} shortcutKeyAnnouncement={shortcutKeyAnnouncement} />
            <SearchResultsOptionsBar view={view} sizing={sizing} />
        </header>
    );
};

export { SearchResultsHeader };
