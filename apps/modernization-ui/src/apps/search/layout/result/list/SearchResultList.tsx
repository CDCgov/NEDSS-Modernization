import { ReactNode } from 'react';

import styles from './search-result-list.module.scss';

type Props<T> = {
    results: T[];
    render: (result: T) => ReactNode;
};

const SearchResultList = <T,>({ results, render }: Props<T>) => {
    return (
        <div className={styles.results}>
            {results.map((result, index) => (
                <div className={styles.result} key={index}>
                    {render(result)}
                </div>
            ))}
        </div>
    );
};

export { SearchResultList };
