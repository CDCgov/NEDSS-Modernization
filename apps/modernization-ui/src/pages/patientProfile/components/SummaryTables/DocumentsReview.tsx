import { SortableTable } from 'components/Table/SortableTable';
import { format } from 'date-fns';
import { LabReport, OrganizationParticipation } from 'generated/graphql/schema';
import { useEffect, useState } from 'react';

export const DocumentsReview = ({ documents, totalDocuments }: any) => {
    const [currentPage, setCurrentPage] = useState<number>(1);
    const [data, setData] = useState<any>(documents);

    useEffect(() => {
        setData(documents);
    }, [documents]);

    const getOrderingProvidorName = (labReport: LabReport): string | undefined => {
        const provider = labReport.personParticipations?.find((p) => p?.typeCd === 'ORD' && p?.personCd === 'PRV');
        if (provider) {
            return `${provider.firstName} ${provider.lastName}`;
        } else {
            return undefined;
        }
    };

    const getReportingFacility = (labReport: LabReport): OrganizationParticipation | undefined | null => {
        return labReport.organizationParticipations?.find((o) => o?.typeCd === 'AUT');
    };

    const getOrderingFacility = (labReport: LabReport): OrganizationParticipation | undefined | null => {
        return labReport.organizationParticipations?.find((o) => o?.typeCd === 'ORD');
    };

    const getDescription = (labReport: LabReport) => {
        // TODO - there could be multiple tests associated with one lab report. How to display them in UI
        const observation = labReport.observations?.find((o) => o?.displayName && o?.cdDescTxt);
        if (observation) {
            return (
                <>
                    <strong>{observation.cdDescTxt}:</strong> <br />
                    <span>{observation.displayName}</span>
                </>
            );
        } else {
            return '--';
        }
    };

    const getSortDescription = (labReport: LabReport): string => {
        return labReport.observations?.find((o) => o?.displayName && o?.cdDescTxt)?.cdDescTxt || '';
    };

    const [tableHead, setTableHead] = useState<{ name: string; sortable: boolean; sort?: string }[]>([
        { name: 'Document type', sortable: true, sort: 'all' },
        { name: 'Date received', sortable: true, sort: 'all' },
        { name: 'Reporting facility / provider', sortable: true, sort: 'all' },
        { name: 'Event date', sortable: true, sort: 'all' },
        { name: 'Description', sortable: true, sort: 'all' },
        { name: 'Event #', sortable: true, sort: 'all' }
    ]);

    const tableHeadChanges = (name: string, type: string) => {
        tableHead.map((item) => {
            if (item.name.toLowerCase() === name.toLowerCase()) {
                item.sort = type;
            } else {
                item.sort = 'all';
            }
        });
        setTableHead(tableHead);
    };

    const sortDocumentsData = (name: string, type: string) => {
        setData(
            documents.slice().sort((a: any, b: any) => {
                if (a[name] && b[name]) {
                    if (a[name].toLowerCase() < b[name].toLowerCase()) {
                        return type === 'asc' ? -1 : 1;
                    }
                    if (a[name].toLowerCase() > b[name].toLowerCase()) {
                        return type === 'asc' ? 1 : -1;
                    }
                }
                return 0;
            })
        );
    };

    const handleDocumentSort = (name: string, type: string) => {
        tableHeadChanges(name, type);
        switch (name.toLowerCase()) {
            case 'date received':
                setData(
                    documents.slice().sort((a: any, b: any) => {
                        const dateA: any = new Date(a.addTime);
                        const dateB: any = new Date(b.addTime);
                        return type === 'asc' ? dateB - dateA : dateA - dateB;
                    })
                );
                break;
            case 'event date':
                setData(documents);
                break;
            case 'document type':
                setData(documents.slice().reverse());
                break;
            case 'reporting facility / provider':
                setData(
                    documents.slice().sort((a: any, b: any) => {
                        if (getReportingFacility(a) && getReportingFacility(b)) {
                            if ((getReportingFacility(a)?.name as string) < (getReportingFacility(b)?.name as string)) {
                                return type === 'asc' ? -1 : 1;
                            }
                            if ((getReportingFacility(a)?.name as string) > (getReportingFacility(b)?.name as string)) {
                                return type === 'asc' ? 1 : -1;
                            }
                        }
                        return 0;
                    })
                );
                break;
            case 'description':
                setData(
                    documents.slice().sort((a: any, b: any) => {
                        if (getSortDescription(a) && getSortDescription(b)) {
                            if (getSortDescription(a) < getSortDescription(b)) {
                                return type === 'asc' ? -1 : 1;
                            }
                            if (getSortDescription(a) > getSortDescription(b)) {
                                return type === 'asc' ? 1 : -1;
                            }
                        }
                        return 0;
                    })
                );
                break;
            case 'event #':
                sortDocumentsData('localId', type);
        }
    };

    return (
        <div className="margin-top-6 margin-bottom-2 flex-row common-card">
            <SortableTable
                totalResults={totalDocuments}
                isPagination={true}
                tableHeader={'Open documents'}
                tableHead={tableHead}
                tableBody={
                    data?.length > 0 &&
                    data?.map((document: any, index: number) => {
                        return (
                            <tr key={index}>
                                <td className={`font-sans-md table-data ${tableHead[0].sort !== 'all' && 'sort-td'}`}>
                                    <a href="#">Lab report</a>
                                </td>
                                <td className={`font-sans-md table-data ${tableHead[1].sort !== 'all' && 'sort-td'}`}>
                                    {document?.addTime ? (
                                        <span>
                                            {format(new Date(document?.addTime), 'MM/dd/yyyy')} <br />{' '}
                                            {format(new Date(document?.addTime), 'hh:mm a')}
                                        </span>
                                    ) : (
                                        <span className="no-data">No data</span>
                                    )}
                                </td>
                                <td className={`font-sans-md table-data ${tableHead[2].sort !== 'all' && 'sort-td'}`}>
                                    <div>
                                        {getReportingFacility(document) && (
                                            <>
                                                <strong>Reporting facility:</strong>
                                                <br />
                                                <span>{getReportingFacility(document)?.name ?? ''}</span>
                                            </>
                                        )}
                                        <br />
                                        {getOrderingProvidorName(document) && (
                                            <>
                                                <strong>Ordering provider:</strong>
                                                <br />
                                                <span>{getOrderingProvidorName(document) ?? ''}</span>
                                            </>
                                        )}
                                        <br />
                                        {getOrderingFacility(document) && (
                                            <>
                                                <strong>Ordering facility:</strong>
                                                <br />
                                                <span>{getOrderingFacility(document)?.name ?? ''}</span>
                                            </>
                                        )}
                                    </div>
                                </td>
                                <td className={`font-sans-md table-data ${tableHead[3].sort !== 'all' && 'sort-td'}`}>
                                    <span className="no-data">No data</span>
                                </td>
                                <td className={`font-sans-md table-data ${tableHead[4].sort !== 'all' && 'sort-td'}`}>
                                    {getDescription(document)}
                                </td>
                                <td className={`font-sans-md table-data ${tableHead[5].sort !== 'all' && 'sort-td'}`}>
                                    {document?.localId ? (
                                        <span>{document?.localId}</span>
                                    ) : (
                                        <span className="no-data">No data</span>
                                    )}
                                </td>
                            </tr>
                        );
                    })
                }
                currentPage={currentPage}
                handleNext={(e) => setCurrentPage(e)}
                sortData={handleDocumentSort}
            />
        </div>
    );
};
