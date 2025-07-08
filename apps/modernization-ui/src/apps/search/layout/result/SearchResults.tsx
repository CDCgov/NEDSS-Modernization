import { ReactNode, useLayoutEffect, useRef, useState } from 'react';
import { Sizing } from 'design-system/field';
import { LoadingPanel } from 'design-system/loading';
import { View } from 'apps/search';
import { SearchResultsHeader } from './header/SearchResultsHeader';
import { Term } from 'apps/search/terms';
import { SearchResultPagination } from './pagination';

import styles from './search-results.module.scss';

type Props = {
    children: ReactNode;
    view: View;
    total: number;
    filteredTotal?: number;
    terms: Term[];
    loading?: boolean;
    sizing?: Sizing;
};

const SearchResults = ({ children, total, filteredTotal, view, terms, loading = false, sizing }: Props) => {
    const [contentHeight, setContentHeight] = useState<string>('auto');
    const headerRef = useRef<HTMLDivElement>(null);
    const paginationRef = useRef<HTMLDivElement>(null);

    const computeContentHeight = () => {
        const headerHeight = headerRef.current?.clientHeight || 0;
        const paginationHeight = paginationRef.current?.clientHeight || 0;
        const offset = headerHeight + paginationHeight;

        return offset > 0 ? `calc(100% - ${offset}px)` : 'auto';
    };

    const handleResize = () => {
        setContentHeight(total > 0 ? computeContentHeight() : 'auto');
    };

    useLayoutEffect(() => {
        handleResize();
        window.addEventListener('resize', handleResize);

        return () => window.removeEventListener('resize', handleResize);
    }, [total]);

    return (
        <div className={styles.results}>
            <div ref={headerRef}>
                <SearchResultsHeader
                    sizing={sizing}
                    view={view}
                    total={total}
                    filteredTotal={filteredTotal}
                    terms={terms}
                />
            </div>
            <main style={{ height: contentHeight }}>
                <LoadingPanel loading={loading} className={styles.loader}>
                    {children}
                </LoadingPanel>
            </main>
            <SearchResultPagination id="search-result-pagination" elementRef={paginationRef} />
        </div>
    );
};

export { SearchResults };
