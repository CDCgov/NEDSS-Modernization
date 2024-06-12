import { View } from 'apps/search';
import { SearchResultsListOptions } from './list/SearchResultsListOptions';

import style from './search-results-options.module.scss';

type Props = {
    view: View;
    disabled?: boolean;
};

const SearchResultsOptionsBar = ({ view, disabled = false }: Props) => {
    return <div className={style.options}>{view === 'list' && <SearchResultsListOptions disabled={disabled} />}</div>;
};

export { SearchResultsOptionsBar };
