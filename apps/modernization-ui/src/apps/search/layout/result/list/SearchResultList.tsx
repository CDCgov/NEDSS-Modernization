import { ReactNode } from 'react';

import styles from './search-result-list.module.scss';

type Props<T> = {
    results: T[];
    render: (result: T) => ReactNode;
};

const SearchResultList = <T,>({ results, render }: Props<T>) => {
    return results && results.length > 0 ? (
        results.map((result, index) => (
            <div className={styles.result} key={index}>
                {render(result)}
            </div>
        ))
    ) : (
        <div className="text-center">
            <p>No results found.</p>
            <p>Try refining your search.</p>
        </div>
    );
};

export { SearchResultList };
