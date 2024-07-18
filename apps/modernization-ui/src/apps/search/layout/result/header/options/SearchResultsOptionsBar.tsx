import { View } from 'apps/search';
import { SearchResultsListOptions } from './list/SearchResultsListOptions';

import style from './search-results-options.module.scss';
import { ToggleView } from './toggleView/ToggleView';
import { useSearchSettings } from 'apps/search/useSearchSettings';
import { Columns } from '../columns/Columns';

type Props = {
    view: View;
    disabled?: boolean;
};

const SearchResultsOptionsBar = ({ view, disabled = false }: Props) => {
    const settings = useSearchSettings();

    return (
        <div className={style.options}>
            {settings.allowToggle && <ToggleView />}
            {view === 'list' && <SearchResultsListOptions disabled={disabled} />}
            {view === 'table' ? <Columns /> : null}
        </div>
    );
};

export { SearchResultsOptionsBar };
