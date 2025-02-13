import { Sizing } from 'design-system/field';
import { View } from 'apps/search';
import { useSearchSettings } from 'apps/search/useSearchSettings';
import { ToggleView } from './toggleView/ToggleView';
import { SearchResultsListOptions } from './list/SearchResultsListOptions';
import { SearchResultsTableOptions } from './table/SearchResultsTableOptions';

import style from './search-results-options.module.scss';

type Props = {
    view: View;
    disabled?: boolean;
    sizing?: Sizing;
};

const SearchResultsOptionsBar = ({ view, disabled = false, sizing }: Props) => {
    const { settings } = useSearchSettings();

    return (
        <div className={style.options}>
            {view === 'list' && <SearchResultsListOptions disabled={disabled} sizing={sizing} />}
            {view === 'table' && <SearchResultsTableOptions disabled={disabled} sizing={sizing} />}
            {settings.allowToggle && <ToggleView sizing={sizing} />}
        </div>
    );
};

export { SearchResultsOptionsBar };
