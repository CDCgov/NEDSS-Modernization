import { useContext, useEffect, useState } from 'react';
import { Button, Icon } from '@trussworks/react-uswds';
import format from 'date-fns/format';
import {
    AssociatedInvestigation,
    FindLabReportsByFilterQuery,
    LabReport,
    OrganizationParticipation,
    useFindLabReportsByFilterLazyQuery
} from 'generated/graphql/schema';

import { TOTAL_TABLE_DATA } from 'utils/util';
import { SortableTable } from 'components/Table/SortableTable';
import { RedirectControllerService } from 'generated';
import { UserContext } from 'providers/UserContext';
import { Config } from 'config';

type PatientLabReportTableProps = {
    patient?: string;
    pageSize?: number;
};

export const LabReportTable = ({ patient, pageSize = TOTAL_TABLE_DATA }: PatientLabReportTableProps) => {
    const { state } = useContext(UserContext);
    const NBS_URL = Config.nbsUrl;

    const [currentPage, setCurrentPage] = useState<number>(1);
    const [total, setTotal] = useState<number>(0);
    const [labReportData, setLabReportData] = useState<any>([]);
    const [tableHead, setTableHead] = useState<{ name: string; sortable: boolean; sort?: string }[]>([
        { name: 'Date received', sortable: true, sort: 'all' },
        { name: 'Facility / provider', sortable: true, sort: 'all' },
        { name: 'Date collected', sortable: true, sort: 'all' },
        { name: 'Test results', sortable: true, sort: 'all' },
        { name: 'Associated with', sortable: true, sort: 'all' },
        { name: 'Program area', sortable: true, sort: 'all' },
        { name: 'Jurisdiction', sortable: true, sort: 'all' },
        { name: 'Event #', sortable: true, sort: 'all' }
    ]);

    const handleComplete = (data: FindLabReportsByFilterQuery) => {
        setTotal(data?.findLabReportsByFilter?.total || 0);
        setLabReportData(data.findLabReportsByFilter?.content);
    };

    const [getLabReport] = useFindLabReportsByFilterLazyQuery({ onCompleted: handleComplete });

    const getOrderingProviderName = (labReport: LabReport): string | undefined => {
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

    const getTestedResults = (labReport: LabReport) => {
        return (
            labReport.observations?.map(
                (o) =>
                    o?.domainCd === 'Result' && (
                        <>
                            <strong>{o.cdDescTxt}:</strong>
                            <br />
                            <span>{o.displayName}</span>
                            <br />
                        </>
                    )
            ) || <span className="no-data">No data</span>
        );
    };

    const getSortableTestResult = (labReport: LabReport) => {
        if (labReport?.observations?.find((o) => o?.domainCd === 'Result')) {
            return labReport?.observations?.find((o) => o?.domainCd === 'Result')?.cdDescTxt;
        }
    };

    const getSortableAssociatedWith = (labReport: LabReport) => {
        return labReport?.associatedInvestigations?.[0]?.cdDescTxt || '';
    };

    useEffect(() => {
        if (patient) {
            getLabReport({
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
        setLabReportData(
            labReportData?.slice().sort((a: any, b: any) => {
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
            case 'date received':
                setLabReportData(
                    labReportData.slice().sort((a: any, b: any) => {
                        const dateA: any = new Date(a.addTime);
                        const dateB: any = new Date(b.addTime);
                        return type === 'asc' ? dateB - dateA : dateA - dateB;
                    })
                );
                break;
            case 'facility / provider':
                setLabReportData(
                    labReportData?.slice().sort((a: any, b: any) => {
                        const labA = getReportingFacility(a)?.name;
                        const labB = getReportingFacility(b)?.name;
                        if (labA && labB) {
                            if (labA.toLowerCase() < labB.toLowerCase()) {
                                return type === 'asc' ? -1 : 1;
                            }
                            if (labA.toLowerCase() > labB.toLowerCase()) {
                                return type === 'asc' ? 1 : -1;
                            }
                        }
                        return 0;
                    })
                );
                break;
            case 'test results':
                setLabReportData(
                    labReportData?.slice().sort((a: any, b: any) => {
                        const labA = getSortableTestResult(a);
                        const labB = getSortableTestResult(b);
                        if (labA && labB) {
                            if (labA.toLowerCase() < labB.toLowerCase()) {
                                return type === 'asc' ? -1 : 1;
                            }
                            if (labA.toLowerCase() > labB.toLowerCase()) {
                                return type === 'asc' ? 1 : -1;
                            }
                        }
                        return 0;
                    })
                );
                break;
            case 'program area':
                sortData('programAreaCd', type);
                break;
            case 'jurisdiction':
                sortData('jurisdictionCd', type);
                break;
            case 'associated with':
                setLabReportData(
                    labReportData?.slice().sort((a: any, b: any) => {
                        const labA = getSortableAssociatedWith(a);
                        const labB = getSortableAssociatedWith(b);
                        if (labA && labB) {
                            if (labA.toLowerCase() < labB.toLowerCase()) {
                                return type === 'asc' ? -1 : 1;
                            }
                            if (labA.toLowerCase() > labB.toLowerCase()) {
                                return type === 'asc' ? 1 : -1;
                            }
                        }
                        return 0;
                    })
                );
                break;
            case 'event #':
                sortData('localId', type);
        }
    };

    return (
        <SortableTable
            isPagination={true}
            buttons={
                <div className="grid-row">
                    <Button
                        type="button"
                        className="grid-row"
                        onClick={() => {
                            RedirectControllerService.preparePatientDetailsUsingGet({
                                authorization: 'Bearer ' + state.getToken()
                            }).then(() => {
                                // URl should probably be:
                                // /nbs/LoadAddObservationLab1.do?method=createGenericLoad&ContextAction=AddLab
                                // But this internally checks for "DSPatientPersonUID" which is stored in some NBSContext.OBJECT_STORE
                                // for now putting in a temporary one that works, loads the "Add Lab report" page but with the tab "patient"
                                // in context rather that the tab "report"
                                window.location.href = `${NBS_URL}/MyTaskList1.do?ContextAction=AddLabDataEntry`;
                            });
                        }}>
                        <Icon.Add className="margin-right-05" />
                        Add lab report
                    </Button>
                </div>
            }
            tableHeader={'Lab reports'}
            tableHead={tableHead}
            tableBody={
                labReportData?.length > 0 &&
                labReportData?.map((report: any, index: number) => {
                    return (
                        <tr key={index}>
                            <td
                                style={{ background: tableHead[0].sort !== 'all' ? '#e1f3f8' : 'transparent' }}
                                className="font-sans-md table-data">
                                {report?.addTime ? (
                                    <span>
                                        {format(new Date(report?.addTime), 'MM/dd/yyyy')} <br />{' '}
                                        {format(new Date(report?.addTime), 'hh:mm a')}
                                    </span>
                                ) : (
                                    <span className="no-data">No data</span>
                                )}
                            </td>
                            <td
                                style={{ background: tableHead[1].sort !== 'all' ? '#e1f3f8' : 'transparent' }}
                                className="font-sans-md table-data">
                                <div>
                                    {getReportingFacility(report) && (
                                        <>
                                            <strong>Reporting facility:</strong>
                                            <br />
                                            <span>{getReportingFacility(report)?.name}</span>
                                            <br />
                                        </>
                                    )}
                                    {getOrderingProviderName(report) && (
                                        <>
                                            <strong>Ordering provider:</strong>
                                            <br />
                                            <span>{getOrderingProviderName(report) ?? ''}</span>
                                            <br />
                                        </>
                                    )}
                                    {getOrderingFacility(report) && (
                                        <>
                                            <strong>Ordering facility:</strong>
                                            <br />
                                            <span>{getOrderingFacility(report)?.name}</span>
                                        </>
                                    )}
                                </div>
                            </td>
                            <td
                                style={{ background: tableHead[2].sort !== 'all' ? '#e1f3f8' : 'transparent' }}
                                className="font-sans-md table-data">
                                <span className="no-data">No data</span>
                            </td>
                            <td
                                style={{ background: tableHead[3].sort !== 'all' ? '#e1f3f8' : 'transparent' }}
                                className="font-sans-md table-data">
                                {getTestedResults(report) ? (
                                    getTestedResults(report)
                                ) : (
                                    <span className="no-data">No data</span>
                                )}
                            </td>
                            <td
                                style={{ background: tableHead[4].sort !== 'all' ? '#e1f3f8' : 'transparent' }}
                                className="font-sans-md table-data">
                                {!report.associatedInvestigations ? (
                                    <span className="no-data">No data</span>
                                ) : (
                                    <>
                                        {report.associatedInvestigations?.map(
                                            (i: AssociatedInvestigation, index: number) => (
                                                <div key={index}>
                                                    <p
                                                        className="margin-0 text-primary text-bold link"
                                                        style={{ wordBreak: 'break-word' }}>
                                                        {i?.localId}
                                                    </p>
                                                    <p className="margin-0">{i?.cdDescTxt}</p>
                                                </div>
                                            )
                                        )}
                                    </>
                                )}
                            </td>
                            <td
                                style={{ background: tableHead[5].sort !== 'all' ? '#e1f3f8' : 'transparent' }}
                                className="font-sans-md table-data">
                                {report?.programAreaCd ? (
                                    <span>{report?.programAreaCd}</span>
                                ) : (
                                    <span className="no-data">No data</span>
                                )}
                            </td>
                            <td
                                className="font-sans-md table-data"
                                style={{ background: tableHead[6].sort !== 'all' ? '#e1f3f8' : 'transparent' }}>
                                {report?.jurisdictionCd ? (
                                    <span>{report?.jurisdictionCd}</span>
                                ) : (
                                    <span className="no-data">No data</span>
                                )}
                            </td>
                            <td
                                className="font-sans-md table-data"
                                style={{ background: tableHead[7].sort !== 'all' ? '#e1f3f8' : 'transparent' }}>
                                {report?.localId ? (
                                    <span>{report?.localId}</span>
                                ) : (
                                    <span className="no-data">No data</span>
                                )}
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
