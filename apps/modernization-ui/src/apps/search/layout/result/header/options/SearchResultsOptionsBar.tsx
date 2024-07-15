import { View } from 'apps/search';
import { SearchResultsListOptions } from './list/SearchResultsListOptions';

import style from './search-results-options.module.scss';
import { ToggleView } from './toggleView/ToggleView';

type Props = {
    view: View;
    disabled?: boolean;
};

const SearchResultsOptionsBar = ({ view, disabled = false }: Props) => {
    return (
        <div className={style.options}>
            <ToggleView />
            {view === 'list' && <SearchResultsListOptions disabled={disabled} />}
        </div>
    );
};

export { SearchResultsOptionsBar };
