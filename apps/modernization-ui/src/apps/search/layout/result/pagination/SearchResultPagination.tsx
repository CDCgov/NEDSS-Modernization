import { RefObject } from 'react';
import { usePage } from 'page';
import { Shown } from 'conditional-render';
import { SearchResultPageSizeSelect } from './page-size-select';
import { SearchResultsShowing } from './showing';
import { SearchResultPageSelect } from './page-select';

import styles from './search-result-pagination.module.scss';

const pageSizes = [20, 30, 50, 100];

type SearchPaginationProps = {
    id: string;
    elementRef?: RefObject<HTMLDivElement>;
};

const SearchResultPagination = ({ id, elementRef }: SearchPaginationProps) => {
    const { request, resize, page } = usePage();

    const minimum = Math.min(...pageSizes);
    const visible = page.total > minimum;

    return (
        <Shown when={visible}>
            <div ref={elementRef} id={id} className={styles.pagination}>
                <SearchResultPageSizeSelect
                    id={`${id}-page-size-select`}
                    value={page.pageSize}
                    selections={pageSizes}
                    onPageSizeChanged={resize}
                />
                <SearchResultsShowing className={styles.showing} page={page} />
                <SearchResultPageSelect className={styles.pages} page={page} onSelected={request} />
            </div>
        </Shown>
    );
};

export { SearchResultPagination };
