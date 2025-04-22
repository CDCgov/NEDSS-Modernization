import { MatchRequiringReview } from 'apps/deduplication/api/model/MatchRequiringReview';
import { useMatchesRequiringReview } from 'apps/deduplication/api/useMatchesRequiringReview';
import { SearchResultPageSizeSelect } from 'apps/search/layout/result/pagination/page-size-select';
import { SearchResultsShowing } from 'apps/search/layout/result/pagination/showing';
import { parseISO, format } from 'date-fns';
import { Button } from 'design-system/button';
import { Pagination } from 'design-system/pagination';
import { Column, DataTable } from 'design-system/table';
import { Status, usePagination } from 'pagination';
import { useEffect } from 'react';
import styles from './matches-requiring-review.module.scss';

export const MatchesRequiringReviewTable = () => {
    const { response, fetchMatchesRequiringReview } = useMatchesRequiringReview();
    const { page, ready, request, firstPage } = usePagination();

    useEffect(() => {
        ready(response.total, Math.max(1, page.current));
    }, [response]);

    useEffect(() => {
        if (page.status === Status.Requested) {
            fetchMatchesRequiringReview(page.current - 1, page.pageSize);
        }
    }, [page.status]);

    const columns: Column<MatchRequiringReview>[] = [
        {
            id: 'patient-id',
            name: 'Patient ID',
            render(match) {
                return <>{match.patientId}</>;
            }
        },
        {
            id: 'person-name',
            name: 'Person name',
            render(match) {
                return <>{match.patientName}</>;
            }
        },
        {
            id: 'date-created',
            name: 'Date created',
            render(match) {
                return <>{format(parseISO(match.createdDate), 'MM/dd/yyyy hh:mm a')}</>;
            }
        },
        {
            id: 'date-identified',
            name: 'Date identified',
            render(match) {
                return <>{format(parseISO(match.identifiedDate), 'MM/dd/yyyy hh:mm a')}</>;
            }
        },
        {
            id: 'count',
            name: 'Number of matching records',
            render(match) {
                return <>{match.numOfMatchingRecords}</>;
            }
        },
        {
            id: 'review',
            name: '',
            render(match) {
                return (
                    <Button
                        sizing="small"
                        className={styles.reviewButton}
                        secondary
                        onClick={() => console.log('clicked review for patient', match.patientId)}>
                        Review
                    </Button>
                );
            }
        }
    ];

    return (
        <div className={styles.table}>
            <DataTable<MatchRequiringReview> id="review-table" columns={columns} data={response.matches} />
            <div className={styles.pagination}>
                <SearchResultPageSizeSelect
                    id={`page-size-select`}
                    value={page.pageSize}
                    selections={[1, 30, 40, 50]}
                    onPageSizeChanged={firstPage}
                />
                <SearchResultsShowing page={page} />
                <Pagination
                    total={Math.ceil(page.total / page.pageSize)}
                    current={page.current}
                    onNext={() => request(page.current + 1)}
                    onPrevious={() => request(page.current - 1)}
                    onSelectPage={(page) => request(page)}
                />
            </div>
        </div>
    );
};
