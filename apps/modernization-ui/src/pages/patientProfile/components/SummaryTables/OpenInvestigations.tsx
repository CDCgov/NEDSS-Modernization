import { SortableTable } from 'components/Table/SortableTable';
import { format } from 'date-fns';
import { useEffect, useState } from 'react';

export const OpenInvestigations = ({ investigations, totalInvestigations }: any) => {
    const [currentPage, setCurrentPage] = useState<number>(1);
    const [data, setData] = useState<any>(investigations);

    useEffect(() => {
        setData(investigations);
    }, [investigations]);

    const sortInvestigationData = (name: string, type: string) => {
        setData(
            investigations.slice().sort((a: any, b: any) => {
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

    const [tableHead, setTableHead] = useState<{ name: string; sortable: boolean; sort?: string }[]>([
        { name: 'Start Date', sortable: true, sort: 'all' },
        { name: 'Condition', sortable: true, sort: 'all' },
        { name: 'Case status', sortable: true, sort: 'all' },
        { name: 'Notification', sortable: true, sort: 'all' },
        { name: 'Jurisdiction', sortable: true, sort: 'all' },
        { name: 'Investigator', sortable: true, sort: 'all' },
        { name: 'Investigation #', sortable: false, sort: 'all' },
        { name: 'Co-infection #', sortable: false, sort: 'all' }
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

    const handleSort = (name: string, type: string) => {
        tableHeadChanges(name, type);
        switch (name.toLowerCase()) {
            case 'start date':
                setData(
                    investigations.slice().sort((a: any, b: any) => {
                        const dateA: any = new Date(a.addTime);
                        const dateB: any = new Date(b.addTime);
                        return type === 'asc' ? dateB - dateA : dateA - dateB;
                    })
                );
                break;
            case 'condition':
                sortInvestigationData('cdDescTxt', type);
                break;
            case 'jurisdiction':
                sortInvestigationData('jurisdictionCodeDescTxt', type);
                break;
            case 'investigator':
                setData(
                    investigations.slice().sort((a: any, b: any) => {
                        const firstInv = a?.personParticipations?.find(
                            (person: any) => person?.typeCd === 'InvestgrOfPHC'
                        ).lastName;
                        const secondInv = b?.personParticipations?.find(
                            (person: any) => person?.typeCd === 'InvestgrOfPHC'
                        ).lastName;
                        if (firstInv && secondInv) {
                            if (firstInv.toLowerCase() < secondInv.toLowerCase()) {
                                return type === 'asc' ? -1 : 1;
                            }
                            if (firstInv.toLowerCase() > secondInv.toLowerCase()) {
                                return type === 'asc' ? 1 : -1;
                            }
                        }
                        return 0;
                    })
                );
                break;
            case 'case status':
                sortInvestigationData('recordStatus', type);
                break;
            case 'notification':
                sortInvestigationData('notificationRecordStatusCd', type);
        }
    };

    return (
        <div className="margin-top-6 margin-bottom-2 flex-row common-card">
            <SortableTable
                totalResults={totalInvestigations}
                isPagination={true}
                tableHeader={'Open investigations'}
                tableHead={tableHead}
                tableBody={
                    data?.length > 0 &&
                    data?.map((investigation: any, index: number) => {
                        const investigator = investigation?.personParticipations?.find(
                            (person: any) => person?.typeCd === 'InvestgrOfPHC'
                        );
                        return (
                            <tr key={index}>
                                <td
                                    style={{ background: tableHead[0].sort !== 'all' ? '#e1f3f8' : 'transparent' }}
                                    className="font-sans-md table-data">
                                    {investigation?.addTime ? (
                                        <span>{format(new Date(investigation?.addTime), 'MM/dd/yyyy')}</span>
                                    ) : (
                                        <span className="no-data">No data</span>
                                    )}
                                </td>
                                <td
                                    style={{ background: tableHead[1].sort !== 'all' ? '#e1f3f8' : 'transparent' }}
                                    className="font-sans-md table-data">
                                    {investigation?.cdDescTxt ? (
                                        <span>{investigation?.cdDescTxt}</span>
                                    ) : (
                                        <span className="no-data">No data</span>
                                    )}
                                </td>
                                <td
                                    style={{ background: tableHead[2].sort !== 'all' ? '#e1f3f8' : 'transparent' }}
                                    className="font-sans-md table-data">
                                    {investigation?.recordStatus ? (
                                        <span>{investigation?.recordStatus}</span>
                                    ) : (
                                        <span className="no-data">No data</span>
                                    )}
                                </td>
                                <td
                                    style={{ background: tableHead[3].sort !== 'all' ? '#e1f3f8' : 'transparent' }}
                                    className="font-sans-md table-data">
                                    {investigation?.notificationRecordStatusCd ? (
                                        <span>{investigation?.notificationRecordStatusCd}</span>
                                    ) : (
                                        <span className="no-data">No data</span>
                                    )}
                                </td>
                                <td
                                    style={{ background: tableHead[4].sort !== 'all' ? '#e1f3f8' : 'transparent' }}
                                    className="font-sans-md table-data">
                                    {investigation?.jurisdictionCodeDescTxt ? (
                                        <span>{investigation?.jurisdictionCodeDescTxt}</span>
                                    ) : (
                                        <span className="no-data">No data</span>
                                    )}
                                </td>
                                <td
                                    style={{ background: tableHead[5].sort !== 'all' ? '#e1f3f8' : 'transparent' }}
                                    className="font-sans-md table-data">
                                    {investigator ? (
                                        <span>
                                            {investigator
                                                ? investigator?.lastName + ' ' + investigator?.firstName
                                                : null}
                                        </span>
                                    ) : (
                                        <span className="no-data">No data</span>
                                    )}
                                </td>
                                <td className="font-sans-md table-data">
                                    {investigation?.localId ? (
                                        <span>{investigation?.localId}</span>
                                    ) : (
                                        <span className="no-data">No data</span>
                                    )}
                                </td>
                                <td className="font-sans-md table-data">COIN1000XX01</td>
                            </tr>
                        );
                    })
                }
                currentPage={currentPage}
                handleNext={(e) => setCurrentPage(e)}
                sortData={handleSort}
            />
        </div>
    );
};
