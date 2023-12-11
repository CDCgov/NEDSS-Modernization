import { useGetPageDetails } from 'apps/page-builder/hooks/api/useGetPageDetails';
import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import styles from './Edit.module.scss';
import { PageStatusBar } from './PageStatusBar';
import { PageHeader } from './PageHeader';

export const Edit = () => {
    const { fetch, pageDetails } = useGetPageDetails();
    const { pageId } = useParams();

    useEffect(() => {
        if (pageId) {
            fetch(+pageId);
        }
    }, [pageId]);

    return (
        <div className={styles.editPage}>
            <PageStatusBar name={pageDetails?.name} pageStatus="EDIT MODE" />
            {pageDetails && <PageHeader pageDetails={pageDetails} />}
        </div>
    );
};
