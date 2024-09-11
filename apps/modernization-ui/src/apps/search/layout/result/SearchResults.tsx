import { ReactNode, useEffect, useRef, useState } from 'react';
import { Pagination } from 'design-system/Pagination';
import { View } from 'apps/search';
import { SearchResultsHeader } from './header/SearchResultsHeader';
import { Term } from 'apps/search/terms';
import styles from './search-results.module.scss';

type Props = {
    children: ReactNode;
    view: View;
    total: number;
    terms: Term[];
};

const SearchResults = ({ children, total, view, terms }: Props) => {
    const headerRef = useRef<HTMLDivElement>(null);
    const paginationRef = useRef<HTMLDivElement>(null);
    const [contentHeight, setContentHeight] = useState<string>('auto');

    const updateContentHeight = () => {
        requestAnimationFrame(() => {
            const headerHeight = headerRef.current?.offsetHeight || 0;
            const paginationHeight = paginationRef.current?.offsetHeight || 0;
            setContentHeight(`calc(100% - ${headerHeight}px - ${paginationHeight}px)`);
        });
    };

    useEffect(() => {
        updateContentHeight();
        window.addEventListener('resize', updateContentHeight);

        return () => window.removeEventListener('resize', updateContentHeight);
    }, []);

    return (
        <div className={styles.results}>
            <div ref={headerRef}>
                <SearchResultsHeader className={styles.header} view={view} total={total} terms={terms} />
            </div>
            <main className={styles.content} style={{ height: contentHeight }}>
                {children}
            </main>
            <div ref={paginationRef} className={styles.pagination}>
                <Pagination />
            </div>
        </div>
    );
};

export { SearchResults };
