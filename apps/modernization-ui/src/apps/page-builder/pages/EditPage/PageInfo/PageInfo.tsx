import './PageInfo.scss';
import { Button, Icon, Pagination } from '@trussworks/react-uswds';
import React, { useContext, useEffect, useState } from 'react';
import { PageControllerService, PageInformation, PageInformationService } from '../../../generated';
// import { downloadAsCsv } from '../../../../../utils/downloadAsCsv';
import { UserContext } from '../../../../../providers/UserContext';
import { useNavigate } from 'react-router-dom';
// import { saveAs } from 'file-saver';

type Props = {
    page: number;
};

const pageSize = 3;
export const PageInfo = ({ page }: Props) => {
    const { state } = useContext(UserContext);
    const [activeTab, setActiveTab] = useState('Details');
    const [totalResults, setTotalResults] = useState(4);
    const [currentPage, setCurrentPage] = useState(1);
    const [pageInfo, setPageInfo] = useState<PageInformation>({});
    const [pageHistory, setPageHistory] = useState([
        { publishVersionNbr: '', lastUpdatedDate: '', lastUpdatedBy: '', notes: '' }
    ]);
    const navigate = useNavigate();
    const token = `Bearer ${state.getToken()}`;

    const handleDownloadCSV = async () => {
        try {
            PageControllerService.downloadPageMetadataUsingGet({
                authorization: token,
                waTemplateUid: page
            }).then((data) => {
                const fileType = 'application/xlsx'; // 'text/vnd.openxmlformats-officedocument.spreadsheetml.sheet';
                // @ts-ignore
                const blob = new Blob([data], { type: fileType });
                const url = window.URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.href = url;
                a.download = 'PageMetadata.xlsx';
                a.click();
                // downloadAsCsv({
                //     data,
                //     fileName: '1PageMetadata.xlsx',
                //     fileType
                // });
            });
            // @ts-ignore
            // const blob = new Blob([data], { type: fileType });
            // saveAs(blob, 'PageMetadata.xlsx');
        } catch (error) {
            console.log(error);
        }
    };

    const fetchPageHistory = async () => {
        try {
            const rep: any = await PageInformationService.getPageHistory({
                authorization: token,
                page: page
            });
            setPageHistory(rep);
            setTotalResults(rep.length);
        } catch (error) {
            console.log(error);
        }
    };
    const fetchPageInfo = async () => {
        try {
            const data: PageInformation = await PageInformationService.find({
                authorization: token,
                page: page
            });
            setPageInfo(data);
        } catch (error) {
            console.log(error);
        }
    };
    useEffect(() => {
        fetchPageInfo();
        fetchPageHistory();
    }, []);

    const handleNext = (page: number) => {
        setCurrentPage(page);
        fetchPageHistory();
    };
    const handleViewPage = () => {
        navigate(`/page-builder/manage/page-details/${page}`);
    };

    const renderTabs = (
        <ul className="tabs">
            <li className={activeTab == 'Details' ? 'active' : ''} onClick={() => setActiveTab('Details')}>
                Details
            </li>
            <li className={activeTab == 'History' ? 'active' : ''} onClick={() => setActiveTab('History')}>
                History
            </li>
        </ul>
    );

    return (
        <div className={`page-info-container`}>
            <div className="page-info-header">
                <h2 className="title">Page Info</h2>
                <Button type="button" name="Button" onClick={handleDownloadCSV} outline>
                    <Icon.FileDownload />
                    <span>Metadata</span>
                </Button>
            </div>
            <div>{renderTabs}</div>
            {activeTab == 'Details' ? (
                <div className=" tab-content">
                    <div className="details-container">
                        <div className="information-block">
                            <div className="line-block">
                                <div className="detail-header">Event type</div>
                                <div className="small-body-text">{pageInfo.eventType?.name}</div>
                            </div>
                            <div className="line-block">
                                <div className="detail-header">Message mapping guide</div>
                                <div className="small-body-text"> {pageInfo.messageMappingGuide?.name}</div>
                            </div>
                        </div>
                        <div className="information-block">
                            <div className="line-block">
                                <div className="detail-header">Page name</div>
                                <div className="small-body-text"> {pageInfo?.name}</div>
                            </div>
                            <div className="line-block">
                                <div className="detail-header">Data mart name</div>
                                <div className="small-body-text">{pageInfo?.datamart}</div>
                            </div>
                        </div>
                        <div className="information-block">
                            <div className="line-block margin-bottom-1em">
                                <div className="detail-header">Description</div>
                                <div className="small-body-text">{pageInfo?.description!}</div>
                            </div>
                        </div>
                        <div className="information-block">
                            <div className="line-block">
                                <div className="detail-header">Related Conditions</div>
                                {pageInfo.conditions?.map((conditions, index) => (
                                    <div className="small-body-text" key={index}>
                                        {conditions.name}
                                    </div>
                                ))}
                            </div>
                        </div>
                    </div>
                    <Button type="button" name="Button" onClick={handleViewPage} outline>
                        <Icon.Visibility />
                        <span>View Page Details</span>
                    </Button>
                </div>
            ) : (
                <div className="tab-content  remove-padding">
                    <div className="history-content">
                        {pageHistory.map((pageData, index) => (
                            <div className="version-block" key={index}>
                                <div className="version-text">
                                    Version {pageData.publishVersionNbr}
                                    <span className="version-date">{pageData.lastUpdatedDate}</span>
                                </div>
                                <div className="version-user">{pageData.lastUpdatedBy}</div>
                                <div className="version-notes">{pageData.notes}</div>
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
        </div>
    );
};
