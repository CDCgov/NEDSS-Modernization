import { useContext, useEffect, useState } from 'react';
import { Button, Checkbox, Fieldset, Icon } from '@trussworks/react-uswds';
import format from 'date-fns/format';
import { FindInvestigationsByFilterQuery, useFindInvestigationsByFilterLazyQuery } from 'generated/graphql/schema';

import { TOTAL_TABLE_DATA } from 'utils/util';
import { SortableTable } from 'components/Table/SortableTable';
import { RedirectControllerService } from 'generated';
import { UserContext } from 'providers/UserContext';
import { Config } from 'config';

export type PatientInvestigations = FindInvestigationsByFilterQuery['findInvestigationsByFilter'];

type PatientInvestigationTableProps = {
    patient?: string;
    pageSize?: number;
};

export const PatientInvestigationsTable = ({
    patient,
    pageSize = TOTAL_TABLE_DATA
}: PatientInvestigationTableProps) => {
    const { state } = useContext(UserContext);
    const NBS_URL = Config.nbsUrl;

    const [currentPage, setCurrentPage] = useState<number>(1);
    const [total, setTotal] = useState<number>(0);
    const [investigationsData, setInvestigationsData] = useState<any>([]);
    const [tableHead, setTableHead] = useState<{ name: string; sortable: boolean; sort?: string }[]>([
        { name: 'Start Date', sortable: true, sort: 'all' },
        { name: 'Condition', sortable: true, sort: 'all' },
        { name: 'Case status', sortable: true, sort: 'all' },
        { name: 'Notification', sortable: true, sort: 'all' },
        { name: 'Jurisdiction', sortable: true, sort: 'all' },
        { name: 'Investigator', sortable: true, sort: 'all' },
        { name: 'Investigation #', sortable: true, sort: 'all' },
        { name: 'Co-infection #', sortable: true, sort: 'all' }
    ]);

    const handleComplete = (data: FindInvestigationsByFilterQuery) => {
        setTotal(data?.findInvestigationsByFilter?.total || 0);
        setInvestigationsData(data.findInvestigationsByFilter?.content);
    };

    const [getInvestigation] = useFindInvestigationsByFilterLazyQuery({ onCompleted: handleComplete });

    useEffect(() => {
        if (patient) {
            getInvestigation({
                variables: {
                    filter: {
                        patientId: +patient
                    },
                    page: {
                        pageNumber: currentPage - 1,
                        pageSize
                    }
                }
            });
        }
    }, [patient, currentPage]);

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

    const sortData = (name: string, type: string) => {
        setInvestigationsData(
            investigationsData?.slice().sort((a: any, b: any) => {
                if (a[name] && b[name]) {
                    if (a[name] < b[name]) {
                        return type === 'asc' ? -1 : 1;
                    }
                    if (a[name] > b[name]) {
                        return type === 'asc' ? 1 : -1;
                    }
                }
                return 0;
            })
        );
    };

    const handleSort = (name: string, type: string) => {
        tableHeadChanges(name, type);
        switch (name.toLowerCase()) {
            case 'start date':
                setInvestigationsData(
                    investigationsData.slice().sort((a: any, b: any) => {
                        const dateA: any = new Date(a.addTime);
                        const dateB: any = new Date(b.addTime);
                        return type === 'asc' ? dateB - dateA : dateA - dateB;
                    })
                );
                break;
            case 'condition':
                sortData('cdDescTxt', type);
                break;
            case 'jurisdiction':
                sortData('jurisdictionCodeDescTxt', type);
                break;
            case 'investigator':
                setInvestigationsData(
                    investigationsData.slice().sort((a: any, b: any) => {
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
                sortData('recordStatus', type);
                break;
            case 'investigation #':
                sortData('localId', type);
                break;
            case 'co-infection #':
                sortData('localId', type);
                break;
            case 'notification':
                sortData('notificationRecordStatusCd', type);
                break;
        }
    };

    return (
        <SortableTable
            isPagination={true}
            buttons={
                <div className="grid-row">
                    <Button disabled type="button" className="grid-row">
                        <Icon.Topic className="margin-right-05" />
                        Compare investigations
                    </Button>
                    <Button
                        type="button"
                        className="grid-row"
                        onClick={() => {
                            RedirectControllerService.preparePatientDetailsUsingGet({
                                authorization: 'Bearer ' + state.getToken()
                            }).then(() => {
                                window.location.href = `${NBS_URL}/LoadSelectCondition1.do?ContextAction=AddInvestigation`;
                            });
                        }}>
                        <Icon.Add className="margin-right-05" />
                        Add investigation
                    </Button>
                </div>
            }
            tableHeader={'Investigations'}
            tableHead={tableHead}
            tableBody={
                investigationsData?.length > 0 &&
                investigationsData?.map((investigation: any, index: number) => {
                    const investigator = investigation?.personParticipations?.find(
                        (person: any) => person?.typeCd === 'InvestgrOfPHC'
                    );
                    return (
                        <tr key={index}>
                            <td className={`font-sans-md table-data ${tableHead[0].sort !== 'all' && 'sort-td'}`}>
                                <Fieldset>
                                    <Checkbox
                                        key={index}
                                        id={`${investigation?.addTime}`}
                                        name={'tableCheck'}
                                        label=""
                                    />
                                </Fieldset>
                                {investigation?.addTime ? (
                                    <span className="check-title table-span">
                                        {format(new Date(investigation?.addTime), 'MM/dd/yyyy')}
                                    </span>
                                ) : (
                                    <span className="no-data">No data</span>
                                )}
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[1].sort !== 'all' && 'sort-td'}`}>
                                {investigation?.cdDescTxt ? (
                                    <span>{investigation?.cdDescTxt}</span>
                                ) : (
                                    <span className="no-data">No data</span>
                                )}
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[2].sort !== 'all' && 'sort-td'}`}>
                                {investigation?.recordStatus ? (
                                    <span>{investigation?.recordStatus}</span>
                                ) : (
                                    <span className="no-data">No data</span>
                                )}
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[3].sort !== 'all' && 'sort-td'}`}>
                                {investigation?.notificationRecordStatusCd ? (
                                    <span>{investigation?.notificationRecordStatusCd}</span>
                                ) : (
                                    <span className="no-data">No data</span>
                                )}
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[4].sort !== 'all' && 'sort-td'}`}>
                                {investigation?.jurisdictionCodeDescTxt ? (
                                    <span>{investigation?.jurisdictionCodeDescTxt}</span>
                                ) : (
                                    <span className="no-data">No data</span>
                                )}
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[5].sort !== 'all' && 'sort-td'}`}>
                                {investigator ? (
                                    <span>
                                        {investigator ? investigator?.lastName + ' ' + investigator?.firstName : null}
                                    </span>
                                ) : (
                                    <span className="no-data">No data</span>
                                )}
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[6].sort !== 'all' && 'sort-td'}`}>
                                {investigation?.localId ? (
                                    <a href="#">{investigation?.localId}</a>
                                ) : (
                                    <span className="no-data">No data</span>
                                )}
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[7].sort !== 'all' && 'sort-td'}`}>
                                <span className="no-data">No data</span>
                            </td>
                        </tr>
                    );
                })
            }
            totalResults={total}
            currentPage={currentPage}
            handleNext={setCurrentPage}
            sortData={handleSort}
        />
    );
};
