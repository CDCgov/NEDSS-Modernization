import { View } from 'apps/search';
import { useSearchSettings } from 'apps/search/useSearchSettings';
import { ToggleView } from './toggleView/ToggleView';
import { SearchResultsListOptions } from './list/SearchResultsListOptions';
import { SearchResultsTableOptions } from './table/SearchResultsTableOptions';

import style from './search-results-options.module.scss';

type Props = {
    view: View;
    disabled?: boolean;
};

const SearchResultsOptionsBar = ({ view, disabled = false }: Props) => {
    const { settings } = useSearchSettings();

    return (
        <div className={style.options}>
            {settings.allowToggle && <ToggleView />}
            {view === 'list' && <SearchResultsListOptions disabled={disabled} />}
            {view === 'table' && <SearchResultsTableOptions disabled={disabled} />}
        </div>
    );
};

export { SearchResultsOptionsBar };
