import { ReactNode, useLayoutEffect, useRef, useState } from 'react';
import { Pagination } from 'design-system/Pagination';
import { LoadingPanel } from 'design-system/loading';
import { View } from 'apps/search';
import { SearchResultsHeader } from './header/SearchResultsHeader';
import { Term } from 'apps/search/terms';

import styles from './search-results.module.scss';

type Props = {
    children: ReactNode;
    view: View;
    total: number;
    terms: Term[];
    loading?: boolean;
};

const SearchResults = ({ children, total, view, terms, loading = false }: Props) => {
    const [contentHeight, setContentHeight] = useState<string>('auto');
    const headerRef = useRef<HTMLDivElement>(null);
    const paginationRef = useRef<HTMLDivElement>(null);

    const computeContentHeight = () => {
        const headerHeight = headerRef.current?.offsetHeight || 0;
        const paginationHeight = paginationRef.current?.offsetHeight || 0;
        const offset = headerHeight + paginationHeight;

        return offset > 0 ? `calc(100% - ${offset}px)` : 'auto';
    };

    const handleResize = () => {
        setContentHeight(computeContentHeight());
    };

    useLayoutEffect(() => {
        handleResize();
        window.addEventListener('resize', handleResize);

        return () => window.removeEventListener('resize', handleResize);
    }, []);

    return (
        <div className={styles.results}>
            <div ref={headerRef}>
                <SearchResultsHeader className={styles.header} view={view} total={total} terms={terms} />
            </div>
            <main style={{ height: contentHeight }}>
                <LoadingPanel loading={loading} className={styles.loader}>
                    {children}
                </LoadingPanel>
            </main>
            <div ref={paginationRef} className={styles.pagination}>
                <Pagination />
            </div>
        </div>
    );
};

export { SearchResults };
