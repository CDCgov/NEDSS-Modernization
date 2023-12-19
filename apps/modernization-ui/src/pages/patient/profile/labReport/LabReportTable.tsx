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

import { Headers, LabReport } from './LabReport';
import { transform } from './LabReportTransformer';
import { sort } from './LabReportSorter';
import { TableBody, TableComponent } from 'components/Table/Table';
import { Direction } from 'sorting';
import { ClassicButton, ClassicLink } from 'classic';

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
                <ClassicLink url={`/nbs/api/profile/${patient}/investigation/${investigation?.publicHealthCaseUid}`}>
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

const asTableBody =
    (patient?: string) =>
    (report: any): TableBody => ({
        id: report.report,
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
    });

const asTableBodies = (reports: LabReport[], patient?: string): TableBody[] => reports?.map(asTableBody(patient)) || [];

const headers = [
    { name: Headers.DateReceived, sortable: true },
    { name: Headers.FacilityProvider, sortable: true },
    { name: Headers.DateCollected, sortable: true },
    { name: Headers.TestResults, sortable: true },
    { name: Headers.AssociatedWith, sortable: true },
    { name: Headers.ProgramArea, sortable: true },
    { name: Headers.Jurisdiction, sortable: true },
    { name: Headers.EventID, sortable: true }
];

type PatientLabReportTableProps = {
    patient?: string;
    pageSize?: number;
    allowAdd?: boolean;
};

export const LabReportTable = ({ patient, pageSize, allowAdd = false }: PatientLabReportTableProps) => {
    const [currentPage, setCurrentPage] = useState<number>(1);
    const [total, setTotal] = useState<number>(0);
    // const [labReportData, setLabReportData] = useState<any>([]);

    const [items, setItems] = useState<LabReport[]>([]);
    const [bodies, setBodies] = useState<TableBody[]>([]);

    const handleComplete = (data: FindLabReportsForPatientQuery) => {
        setTotal(data?.findLabReportsForPatient?.length || 0);
        
        const content = transform(data?.findLabReportsForPatient);
        
        setItems(content);

        setBodies(asTableBodies(content, patient));

    };

    const [getLabReport, { called, loading }] = useFindLabReportsForPatientLazyQuery({
        onCompleted: handleComplete
    });

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

    const handleSort = (name: string, direction: string): void => {
        const criteria = { name: name as Headers, type: direction as Direction };
        const sorted = sort(items, criteria);
        setBodies(asTableBodies(sorted, patient));
    };

    return (
        <TableComponent
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
            isLoading={!called || loading}
            isPagination={true}
            tableHeader={'Lab reports'}
            tableHead={headers}
            tableBody={bodies}
            totalResults={total}
            currentPage={currentPage}
            handleNext={setCurrentPage}
            sortData={handleSort}
        />
    );
};
