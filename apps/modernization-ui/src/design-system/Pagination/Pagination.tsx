import { Pagination as PaginationUswds } from '@trussworks/react-uswds';
import styles from './pagination.module.scss';
import { RangeToggle } from 'components/Table/RangeToggle/RangeToggle';
import { usePage } from 'page';

export const Pagination = () => {
    const { request, page } = usePage();

    return page.total ? (
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
    ) : (
        <></>
    );
};
