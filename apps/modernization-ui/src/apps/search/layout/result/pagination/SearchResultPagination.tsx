import { RefObject } from 'react';
import { usePage } from 'page';
import { Shown } from 'conditional-render';
import { Pagination } from 'design-system/pagination';
import { SearchResultPageSizeSelect } from './page-size-select';
import { SearchResultsShowing } from './showing';

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

    const pageCount = Math.ceil(page.total / page.pageSize);

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
                <div className={styles.pages}>
                    <Pagination
                        total={pageCount}
                        current={page.current}
                        onNext={() => request(page.current + 1)}
                        onPrevious={() => request(page.current - 1)}
                        onSelectPage={request}
                    />
                </div>
            </div>
        </Shown>
    );
};

export { SearchResultPagination };
