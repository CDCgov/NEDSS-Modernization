import { useEffect, useState } from 'react';
import { Icon } from '@trussworks/react-uswds';
import format from 'date-fns/format';
import {
    FindLabReportsForPatientQuery,
    PatientLabReport,
    OrganizationParticipation2,
    useFindLabReportsForPatientLazyQuery
} from 'generated/graphql/schema';

import { SortableTable } from 'components/Table/SortableTable';
import { ClassicButton, ClassicLink } from 'classic';
import { NoData } from 'components/NoData';

type PatientLabReportTableProps = {
    patient?: string;
    pageSize?: number;
    allowAdd?: boolean;
};

export const LabReportTable = ({ patient, pageSize, allowAdd = false }: PatientLabReportTableProps) => {
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

    const handleComplete = (data: FindLabReportsForPatientQuery) => {
        setTotal(data?.findLabReportsForPatient?.length || 0);
        setLabReportData(data.findLabReportsForPatient);
    };

    const [getLabReport, { called, loading }] = useFindLabReportsForPatientLazyQuery({
        onCompleted: handleComplete
    });

    const getOrderingProviderName = (labReport: PatientLabReport): string | undefined => {
        const provider = labReport.personParticipations?.find((p) => p?.typeCd === 'ORD' && p?.personCd === 'PRV');
        if (provider) {
            return `${provider.firstName} ${provider.lastName}`;
        } else {
            return undefined;
        }
    };

    const getReportingFacility = (labReport: PatientLabReport): OrganizationParticipation2 | undefined | null => {
        return labReport.organizationParticipations?.find((o) => o?.typeCd === 'AUT');
    };

    const getOrderingFacility = (labReport: PatientLabReport): OrganizationParticipation2 | undefined | null => {
        return labReport.organizationParticipations?.find((o) => o?.typeCd === 'ORD');
    };

    const getTestedResults = (labReport: PatientLabReport) => {
        return (
            labReport.observations?.map(
                (o: any) =>
                    o?.domainCd === 'Result' && (
                        <div key={o.cdDescTxt}>
                            <strong>{o.cdDescTxt}:</strong>
                            <br />
                            <span>{o.displayName}</span>
                            <br />
                        </div>
                    )
            ) || <NoData />
        );
    };

    const getSortableTestResult = (labReport: PatientLabReport) => {
        if (labReport?.observations?.find((o) => o?.domainCd === 'Result')) {
            return labReport?.observations?.find((o) => o?.domainCd === 'Result')?.cdDescTxt;
        }
    };

    const getSortableAssociatedWith = (labReport: PatientLabReport) => {
        return labReport?.associatedInvestigations?.[0]?.cdDescTxt || '';
    };

    useEffect(() => {
        if (patient) {
            getLabReport({
                variables: {
                    personUid: +patient,
                    page: {
                        pageNumber: currentPage - 1,
                        pageSize: pageSize as number
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
                        const labA: any = getReportingFacility(a)?.name;
                        const labB: any = getReportingFacility(b)?.name;
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
                        const labA: any = getSortableTestResult(a);
                        const labB: any = getSortableTestResult(b);
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
                        const labA: any = getSortableAssociatedWith(a);
                        const labB: any = getSortableAssociatedWith(b);
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
            isLoading={!called || loading}
            isPagination={true}
            buttons={
                allowAdd && (
                    <div className="grid-row">
                        <ClassicButton url={`/nbs/api/profile/${patient}/report/lab`}>
                            <Icon.Add className="margin-right-05" />
                            Add lab report
                        </ClassicButton>
                    </div>
                )
            }
            tableHeader={'Lab reports'}
            tableHead={tableHead}
            tableBody={
                labReportData?.length > 0 &&
                labReportData?.map((report: any, index: number) => {
                    return (
                        <tr key={index}>
                            <td className={`font-sans-md table-data ${tableHead[0].sort !== 'all' && 'sort-td'}`}>
                                {report?.addTime ? (
                                    <ClassicLink
                                        url={`/nbs/api/profile/${patient}/report/lab/${report.observationUid}`}>
                                        {format(new Date(report?.addTime), 'MM/dd/yyyy')} <br />{' '}
                                        {format(new Date(report?.addTime), 'hh:mm a')}
                                    </ClassicLink>
                                ) : (
                                    <NoData />
                                )}
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[1].sort !== 'all' && 'sort-td'}`}>
                                <div>
                                    {getReportingFacility(report) && (
                                        <>
                                            <strong>Reporting facility:</strong>
                                            <br />
                                            <p className="margin-0">{getReportingFacility(report)?.name as any}</p>
                                            <br />
                                        </>
                                    )}
                                    {getOrderingProviderName(report) && (
                                        <>
                                            <strong>Ordering provider:</strong>
                                            <br />
                                            <p className="margin-0">{getOrderingProviderName(report) ?? ''}</p>
                                            <br />
                                        </>
                                    )}
                                    {getOrderingFacility(report) && (
                                        <>
                                            <strong>Ordering facility:</strong>
                                            <br />
                                            <p className="margin-0">{getOrderingFacility(report)?.name as any}</p>
                                        </>
                                    )}
                                </div>
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[2].sort !== 'all' && 'sort-td'}`}>
                                <NoData />
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[3].sort !== 'all' && 'sort-td'}`}>
                                {getTestedResults(report) ? getTestedResults(report) : <NoData />}
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[4].sort !== 'all' && 'sort-td'}`}>
                                {!report.associatedInvestigations ? (
                                    <NoData />
                                ) : (
                                    <>
                                        {report.associatedInvestigations?.map((investigation: any, index: number) => (
                                            <div key={index}>
                                                <ClassicLink
                                                    url={`/nbs/api/profile/${patient}/investigation/${investigation.publicHealthCaseUid}`}>
                                                    {investigation?.localId}
                                                </ClassicLink>
                                                <p className="margin-0">{investigation?.cdDescTxt}</p>
                                            </div>
                                        ))}
                                    </>
                                )}
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[5].sort !== 'all' && 'sort-td'}`}>
                                {report?.programAreaCd ? <span>{report?.programAreaCd}</span> : <NoData />}
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[6].sort !== 'all' && 'sort-td'}`}>
                                {report?.jurisdictionCd ? <span>{report?.jurisdictionCd}</span> : <NoData />}
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[7].sort !== 'all' && 'sort-td'}`}>
                                {report?.localId ? <span>{report?.localId}</span> : <NoData />}
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
