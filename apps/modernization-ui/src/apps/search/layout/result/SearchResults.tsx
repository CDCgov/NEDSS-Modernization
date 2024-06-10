import { ReactNode } from 'react';
import { useSearch } from 'apps/search/useSearch';

import styles from './search-results.module.scss';

type Renderer = () => ReactNode;

type Props = {
    renderAsList: Renderer;
    renderAsTable?: Renderer;
};

const SearchResults = ({ renderAsList, renderAsTable }: Props) => {
    const { view } = useSearch();

    return (
        <div className={styles.results}>
            <header></header>
            <main>
                {view === 'list' && renderAsList()}
                {view === 'table' && renderAsTable && renderAsTable()}
            </main>
        </div>
    );
};

export { SearchResults };
