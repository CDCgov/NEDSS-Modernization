import { Button, Icon, Pagination } from '@trussworks/react-uswds';
import { Heading } from 'components/heading';
import { authorization } from 'authorization';
import styles from './page-information.module.scss';
import {
    PageSummaryDownloadControllerService,
    PageInformationService,
    PageInformation as InfoType,
    PageControllerService,
    PageHistory
} from 'apps/page-builder/generated';
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import { usePageManagement } from '../../usePageManagement';

const PageInformation = () => {
    const [activeTab, setActiveTab] = useState('Details');
    const [totalResults, setTotalResults] = useState(4);
    const [currentPage, setCurrentPage] = useState(0);
    const [pageHistory, setPageHistory] = useState<PageHistory[]>([]);
    const [pageInfo, setPageInfo] = useState<InfoType>({});
    const { pageId } = useParams();
    const pageSize = 10;
    const navigate = useNavigate();
    const { page } = usePageManagement();
    const fetchPageHistory = async () => {
        PageControllerService?.getPageHistoryUsingGet?.({
            authorization: authorization(),
            id: Number(pageId),
            page: currentPage,
            size: pageSize
        }).then((rep) => {
            setPageHistory(rep?.content ?? []);
            setTotalResults(rep?.totalElements ?? 0);
        });
    };

    const fetchPageInfo = () => {
        PageInformationService.find({
            authorization: authorization(),
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

    const handleNext = (page: number) => {
        setCurrentPage(page);
        fetchPageHistory();
    };
    const handleDownloadCSV = async () => {
        PageSummaryDownloadControllerService.downloadPageMetadataUsingGet({
            authorization: authorization(),
            id: Number(pageId)
        }).then((data) => {
            const dataIn = data as Blob;
            const newBlob = new Blob([dataIn], { type: '.xlsx' });
            const downloadURL = window.URL.createObjectURL(newBlob);
            const link = document.createElement('a');
            link.href = downloadURL;
            link.download = 'PageMetadata' + '.xlsx';
            link.click();
        });
    };

    const handleViewPage = () => {
        navigate(`/page-builder/pages/${pageId}/details`);
    };

    const renderTabs = (
        <ul className={styles.tabs}>
            <li className={activeTab == 'Details' ? styles.active : ''} onClick={() => setActiveTab('Details')}>
                Details
            </li>
            <li className={activeTab == 'History' ? styles.active : ''} onClick={() => setActiveTab('History')}>
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
                <Heading level={2}>Page information</Heading>
                <Button type="button" outline onClick={handleDownloadCSV} className={styles.icon}>
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
                            {renderBlock('Page name', pageInfo?.name!)}
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
                            <Button type="button" outline onClick={handleViewPage} className={styles.icon}>
                                {isEditable ? <Icon.Edit /> : <Icon.Visibility />}
                                {isEditable ? 'Edit page details' : 'View page details'}
                            </Button>
                        </footer>
                    </div>
                </div>
            ) : (
                <div className={styles.content}>
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
                            className="margin-01 pagination"
                            totalPages={Math.ceil(totalResults / pageSize)}
                            currentPage={currentPage}
                            pathname={'/'}
                            onClickNext={() => handleNext?.(currentPage + 1)}
                            onClickPrevious={() => handleNext?.(currentPage - 1)}
                            onClickPageNumber={(_, page) => handleNext?.(page)}
                        />
                    )}
                </div>
            )}
        </section>
    );
};

export { PageInformation };
