import { Pagination as PaginationUswds } from '@trussworks/react-uswds';
import styles from './pagination.module.scss';
import { RangeToggle } from 'components/Table/RangeToggle/RangeToggle';
import { useEffect } from 'react';
import { usePage } from 'page';

export const Pagination = () => {
    const { ready, request, page } = usePage();

    useEffect(() => {
        ready(page.total ?? 0, page.current);
    }, [page]);

    return (
        <div className={styles.pagination}>
            <div className={styles.range}>
                Showing <RangeToggle initial={page.pageSize} /> of <span id="totalRowCount">{page.total}</span>
            </div>
            <div className={styles.pages}>
                <PaginationUswds
                    totalPages={Math.ceil(page.total / page.pageSize)}
                    currentPage={page.current}
                    pathname={``}
                    onClickNext={() => request(page.current + 1)}
                    onClickPrevious={() => request(page.current - 1)}
                    onClickPageNumber={(_, page) => request(page)}
                />
            </div>
        </div>
    );
};
