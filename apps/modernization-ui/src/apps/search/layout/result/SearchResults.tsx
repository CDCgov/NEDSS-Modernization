import { ReactNode, useEffect, useRef, useState } from 'react';
import { Term, useSearchResultDisplay } from 'apps/search';
import { SearchResultsHeader } from './header/SearchResultsHeader';
import styles from './search-results.module.scss';
import { usePage } from 'page';
import { Pagination } from 'design-system/Pagination/Pagination';

type Props = {
    children: ReactNode;
    onRemoveTerm: (term: Term) => void;
};

const SearchResults = ({ children, onRemoveTerm }: Props) => {
    const {
        page: { total }
    } = usePage();

    const { view, terms } = useSearchResultDisplay();

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

    useEffect(() => {
        updateContentHeight();
    }, [terms]);

    return (
        <div className={styles.results}>
            <div ref={headerRef}>
                <SearchResultsHeader
                    className={styles.header}
                    onRemoveTerm={onRemoveTerm}
                    view={view}
                    total={total}
                    terms={terms}
                />
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
