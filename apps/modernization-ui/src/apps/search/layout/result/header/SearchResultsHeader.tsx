import classNames from 'classnames';

import { Term, View } from 'apps/search';
import { SearchTerms } from './terms/SearchTerms';
import { SearchResultsOptionsBar } from './options/SearchResultsOptionsBar';

import styles from './search-results-header.module.scss';
import { ToggleView } from './toggleView/ToggleView';

type Props = {
    view: View;
    className?: string;
    total: number;
    terms: Term[];
    setView: (view: View) => void;
};

const SearchResultsHeader = ({ className, view, total, terms, setView }: Props) => {
    return (
        <header className={classNames(styles.header, className)}>
            <SearchTerms total={total} terms={terms} />
            <div className={classNames(styles.flexContainer)}>
                <ToggleView view={view} setView={setView} />
                <SearchResultsOptionsBar view={view} disabled={total === 0} />
            </div>
        </header>
    );
};

export { SearchResultsHeader };
