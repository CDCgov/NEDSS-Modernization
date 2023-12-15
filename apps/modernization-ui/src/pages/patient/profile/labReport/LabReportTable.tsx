import { useEffect, useState } from 'react';
import { Icon } from '@trussworks/react-uswds';
import format from 'date-fns/format';
import {
    FindLabReportsForPatientQuery,
    PatientLabReport,
    OrganizationParticipation2,
    useFindLabReportsForPatientLazyQuery,
    AssociatedInvestigation2
} from 'generated/graphql/schema';

import { ClassicButton, ClassicLink } from 'classic';
import { TableBody, TableComponent } from 'components/Table/Table';

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
        { name: 'Date received', sortable: true },
        { name: 'Facility / provider', sortable: true },
        { name: 'Date collected', sortable: true },
        { name: 'Test results', sortable: true },
        { name: 'Associated with', sortable: true },
        { name: 'Program area', sortable: true },
        { name: 'Jurisdiction', sortable: true },
        { name: 'Event #', sortable: true }
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

    const getAssociatedInvestigations = (labReport: any): string | undefined => {
        const num = labReport?.associatedInvestigations?.length;
        if (num) {
            labReport?.associatedInvestigations?.map((investigation: AssociatedInvestigation2, index: number) => (
                <div key={index}>
                    <ClassicLink url={`/nbs/api/profile/${patient}/investigation/${investigation.publicHealthCaseUid}`}>
                        {investigation?.localId}
                    </ClassicLink>
                    <p className="margin-0">{investigation?.cdDescTxt}</p>
                </div>
            ));
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
                (o) =>
                    o?.domainCd === 'Result' && (
                        <div key={o.cdDescTxt}>
                            <strong>{o.cdDescTxt}:</strong>
                            <br />
                            <span>{o.displayName}</span>
                            <br />
                        </div>
                    )
            ) || null
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

    const generateTableRow = (report: PatientLabReport, index: number): TableBody => {
        return {
            id: index,
            tableDetails: [
                {
                    id: 1,
                    title: report?.addTime ? (
                        <ClassicLink url={`/nbs/api/profile/${patient}/report/lab/${report.observationUid}`}>
                            {format(new Date(report?.addTime), 'MM/dd/yyyy')} <br />{' '}
                            {format(new Date(report?.addTime), 'hh:mm a')}
                        </ClassicLink>
                    ) : null
                },
                {
                    id: 2,
                    title: (
                        <>
                            {getReportingFacility(report) !== undefined && (
                                <>
                                    <strong>Reporting facility:</strong>
                                    <br />
                                    <p className="margin-0">{getReportingFacility(report)?.name}</p>
                                </>
                            )}
                            {getOrderingProviderName(report) !== undefined && (
                                <>
                                    <strong>Reporting facility:</strong>
                                    <br />
                                    <p className="margin-0">{getOrderingProviderName(report)}</p>
                                </>
                            )}

                            {getOrderingFacility(report) !== undefined && (
                                <>
                                    <strong>Reporting facility:</strong>
                                    <br />
                                    <p className="margin-0">{getOrderingFacility(report)?.name}</p>
                                </>
                            )}
                        </>
                    )
                },
                {
                    id: 3,
                    title: null
                },
                {
                    id: 4,
                    title: getTestedResults(report) || null
                },
                {
                    id: 5,
                    title: getAssociatedInvestigations(report) || null
                },
                {
                    id: 6,
                    title: report?.programAreaCd || null
                },
                {
                    id: 7,
                    title: report?.jurisdictionCd || null
                },
                {
                    id: 8,
                    title: report?.localId || null
                }
            ]
        };
    };

    const generateTableBody = () => {
        return (labReportData?.length > 0 && labReportData.map(generateTableRow)) || [];
    };

    return (
        <TableComponent
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
            tableBody={generateTableBody()}
            totalResults={total}
            currentPage={currentPage}
            handleNext={setCurrentPage}
            sortData={handleSort}
        />
    );
};
