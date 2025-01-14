import { Button, Icon, Pagination } from '@trussworks/react-uswds';
import {
    PageInformation as InfoType,
    PageControllerService,
    PageHistory,
    PageInformationService
} from 'apps/page-builder/generated';
import { useDownloadPageMetadata } from 'apps/page-builder/hooks/api/useDownloadPageMetadata';
import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { usePageManagement } from '../../usePageManagement';
import styles from './page-information.module.scss';

const PageInformation = () => {
    const [activeTab, setActiveTab] = useState('Details');
    const [totalResults, setTotalResults] = useState(4);
    const [currentPage, setCurrentPage] = useState(1);
    const [pageHistory, setPageHistory] = useState<PageHistory[]>([]);
    const [pageInfo, setPageInfo] = useState<InfoType | undefined>();
    const { pageId } = useParams();
    const pageSize = 10;
    const navigate = useNavigate();
    const { page } = usePageManagement();
    const { downloadMetadata } = useDownloadPageMetadata();
    const fetchPageHistory = async () => {
        PageControllerService.getPageHistory({
            id: Number(pageId),
            page: currentPage - 1,
            size: pageSize
        }).then((rep) => {
            setPageHistory(rep?.content ?? []);
            setTotalResults(rep?.totalElements ?? 0);
        });
    };

    const fetchPageInfo = () => {
        PageInformationService.find({
            page: Number(pageId)
        })
            .then((data: InfoType) => {
                setPageInfo(data);
            })
            .catch((err) => console.error(err));
    };
    useEffect(() => {
        fetchPageInfo();
        fetchPageHistory();
    }, [page]);

    useEffect(() => {
        fetchPageHistory();
    }, [currentPage]);

    const handleNext = (page: number) => {
        setCurrentPage(page);
    };
    const handleDownloadMetadata = async () => {
        downloadMetadata(page.id);
    };

    const handleViewPage = () => {
        navigate(`/page-builder/pages/${pageId}/details`);
    };

    const renderTabs = (
        <ul className={styles.tabs}>
            <li className={activeTab == 'Details' ? styles.active : ''} onClick={() => setActiveTab('Details')}>
                Details
            </li>
            <li
                className={`${activeTab == 'History' ? styles.active : ''} historyTab`}
                data-testid="historyTab"
                onClick={() => setActiveTab('History')}>
                History
            </li>
        </ul>
    );

    const renderBlock = (header: string, desc: string = '-') => (
        <div className={styles.lineBlock}>
            <div className={styles.detailHeader}>{header}</div>
            <div className={styles.smallBodyText}>{desc || '-'}</div>
        </div>
    );

    const isEditable = ['Initial Draft', 'Published with Draft', 'Draft'].includes(page?.status);

    return (
        <section className={styles.information}>
            <header>
                <h3>Page information</h3>
                <Button type="button" outline onClick={handleDownloadMetadata} className={styles.icon}>
                    <Icon.FileDownload />
                    Metadata
                </Button>
            </header>
            <nav>
                <div>{renderTabs}</div>
            </nav>
            {activeTab == 'Details' ? (
                <div className={styles.content}>
                    <div className={styles.detailsContainer}>
                        <div className={styles.informationBlock}>
                            {renderBlock('Event type', pageInfo?.eventType?.name)}
                            {renderBlock('Reporting Mechanism', pageInfo?.messageMappingGuide?.name)}
                        </div>
                        <div className={styles.informationBlock}>
                            {renderBlock('Page name', pageInfo?.name)}
                            {renderBlock('Data mart name', pageInfo?.datamart)}
                        </div>
                        <div className={styles.informationBlock}>
                            <div className=" margin-bottom-1em">
                                <div className={styles.lineBlock}>
                                    {renderBlock('Description', pageInfo?.description)}
                                </div>
                            </div>
                        </div>
                        <div className={styles.informationBlock}>
                            <div className={styles.lineBlock}>
                                <div className={styles.detailHeader}>Related Conditions</div>
                                {pageInfo?.conditions?.map((conditions, index) => (
                                    <div className={styles.smallBodyText} key={index}>
                                        {conditions.name}
                                    </div>
                                ))}
                            </div>
                        </div>
                    </div>
                    <div className={styles.detailsContainer}>
                        <footer>
                            <Button
                                type="button"
                                outline
                                onClick={handleViewPage}
                                className={`${styles.icon} EditViewPageDetails`}
                                data-testid="EditViewPageDetails">
                                {isEditable ? <Icon.Edit /> : <Icon.Visibility />}
                                {isEditable ? 'Edit page details' : 'View page details'}
                            </Button>
                        </footer>
                    </div>
                </div>
            ) : (
                <div className={`${styles.content} historyTabContent`} data-testid="historyTabContent">
                    <div className={styles.historyContent}>
                        {pageHistory?.map((pageData, index) => (
                            <div className={styles.versionBlock} key={index}>
                                <div className={styles.text}>
                                    {`Version ${pageData.publishVersionNbr}`}
                                    <span className={styles.date}>{pageData.lastUpdatedDate}</span>
                                </div>
                                <div className={styles.user}>{pageData.lastUpdatedBy}</div>
                                <div className={styles.notes}>{pageData.notes}</div>
                            </div>
                        ))}
                    </div>
                    {totalResults >= pageSize && (
                        <Pagination
                            className="historyPagination"
                            totalPages={Math.ceil(totalResults / pageSize)}
                            currentPage={currentPage}
                            pathname={'/'}
                            onClickNext={() => handleNext(currentPage + 1)}
                            onClickPrevious={() => handleNext(currentPage - 1)}
                            onClickPageNumber={(_, page) => handleNext(page)}
                        />
                    )}
                </div>
            )}
        </section>
    );
};

export { PageInformation };
