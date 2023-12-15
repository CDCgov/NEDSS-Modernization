import { useEffect, useState } from 'react';
import { Icon } from '@trussworks/react-uswds';
import format from 'date-fns/format';
import { Headers, LabReport } from './LabReport';
import { transform } from './LabReportTransformer';
import { sort } from './LabReportSorter';
import { Direction } from 'sorting';
import { Patient } from '../Patient';
import {
    FindLabReportsForPatientQuery,
    PatientLabReport,
    OrganizationParticipation2,
    useFindLabReportsForPatientLazyQuery,
    AssociatedInvestigation2
} from 'generated/graphql/schema';
import { SelectionHandler, SelectionMode, TableBody, TableComponent } from 'components/Table';
import { ClassicButton, ClassicLink } from 'classic';
import { usePatientProfilePermissions } from '../permission';

// type LabReportSelectionHandler = (labReport: LabReport) => SelectionHandler;


// type PatientLabReportTableProps = {
//     patient?: Patient | string;
//     pageSize?: number;
//     allowAdd?: boolean;
// };

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

type Props = {
    patient: string | undefined;
    pageSize: number;
    allowAdd?: boolean;
};

export const LabReportTable = ({ patient, pageSize, allowAdd = false }: Props) => {
    const [currentPage, setCurrentPage] = useState<number>(1);
    const [total, setTotal] = useState<number>(0);
    const [items, setItems] = useState<LabReport[]>([]);
    const [bodies, setBodies] = useState<TableBody[]>([]);
    const [labReportData, setLabReportData] = useState<>([]);
    const [tableHead, setTableHead] = useState<{ name: string; sortable: boolean; sort?: string }[]>(headers);
    const permissions = usePatientProfilePermissions();

    // const handleSelect = (labReport: LabReport) => (mode: SelectionMode) => {
    //     // if (mode === 'select') {
    //     //     select(labReport);
    //     // } else {
    //     //     deselect(labReport);
    //     // }
    // };

    const handleComplete = (data: FindLabReportsForPatientQuery) => {
        setTotal(data?.findLabReportsForPatient?.length || 0);
        setLabReportData(data?.findLabReportsForPatient);
        debugger
        // const content = transform(data?.findLabReportsForPatient);
        // setItems(content);
        // const sorted = sort(content, {});
        // setBodies(asTableBodies(sorted, patient));
    };

    const [getLabReport, { called, loading }] = useFindLabReportsForPatientLazyQuery({
        onCompleted: handleComplete
    });

    // const getOrderingProviderName = (labReport: PatientLabReport): string | undefined => {
    //     const provider = labReport.personParticipations?.find((p) => p?.typeCd === 'ORD' && p?.personCd === 'PRV');
    //     if (provider) {
    //         return `${provider.firstName} ${provider.lastName}`;
    //     } else {
    //         return undefined;
    //     }
    // };

    // const getReportingFacility = (labReport: PatientLabReport): OrganizationParticipation2 | undefined | null => {
    //     return labReport.organizationParticipations?.find((o) => o?.typeCd === 'AUT');
    // };

    // const getOrderingFacility = (labReport: PatientLabReport): OrganizationParticipation2 | undefined | null => {
    //     return labReport.organizationParticipations?.find((o) => o?.typeCd === 'ORD');
    // };

    // const getTestedResults = (labReport: PatientLabReport) => {
    //     return (
    //         labReport.observations?.map(
    //             (o) =>
    //                 o?.domainCd === 'Result' && (
    //                     <div key={o.cdDescTxt}>
    //                         <strong>{o.cdDescTxt}:</strong>
    //                         <br />
    //                         <span>{o.displayName}</span>
    //                         <br />
    //                     </div>
    //                 )
    //         ) || null
    //     );
    // };

    // const getSortableTestResult = (labReport: PatientLabReport) => {
    //     if (labReport?.observations?.find((o) => o?.domainCd === 'Result')) {
    //         return labReport?.observations?.find((o) => o?.domainCd === 'Result')?.cdDescTxt;
    //     }
    // };

    // const getSortableAssociatedWith = (labReport: PatientLabReport) => {
    //     return labReport?.associatedInvestigations?.[0]?.cdDescTxt || '';
    // };

    useEffect(() => {
        debugger
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
        debugger
        const criteria = { name: name as Headers, type: direction as Direction };
        const sorted = sort(items, criteria);
        // setBodies(asTableBodies(sorted, patient));
    };

const asTableBody =
    (report: PatientLabReport, patient: string) =>
    (report: LabReport): TableBody => ({
        id: "hello",
        tableDetails: [
            {
                id: 1,
                title: null
            },
            {
                id: 2,
                title: null
            },
            {
                id: 3,
                title: null
            },
            {
                id: 4,
                title: null
            },
            {
                id: 5,
                title: null
            },
            {
                id: 6,
                title: null
            },
            {
                id: 7,
                title: null
            },
            {
                id: 8,
                title: null
            }
        ]
    });


    // const asTableBody = (report: PatientLabReport, patient: string): TableBody => ({
    //     id: report.id,
    //     tableDetails: [
    //         {
    //             id: 1,
    //             title: report?.addTime ? (
    //                 <ClassicLink url={`/nbs/api/profile/${patient}/report/lab/${report.observationUid}`}>
    //                     {format(new Date(report?.addTime), 'MM/dd/yyyy')} <br />{' '}
    //                     {format(new Date(report?.addTime), 'hh:mm a')}
    //                 </ClassicLink>
    //             ) : null
    //         },
    //         {
    //             id: 2,
    //             title: (
    //                 <>
    //                     {getReportingFacility(report) !== undefined && (
    //                         <>
    //                             <strong>Reporting facility:</strong>
    //                             <br />
    //                             <p className="margin-0">{getReportingFacility(report)?.name}</p>
    //                         </>
    //                     )}
    //                     {getOrderingProviderName(report) !== undefined && (
    //                         <>
    //                             <strong>Reporting facility:</strong>
    //                             <br />
    //                             <p className="margin-0">{getOrderingProviderName(report)}</p>
    //                         </>
    //                     )}

    //                     {getOrderingFacility(report) !== undefined && (
    //                         <>
    //                             <strong>Reporting facility:</strong>
    //                             <br />
    //                             <p className="margin-0">{getOrderingFacility(report)}</p>
    //                         </>
    //                     )}
    //                 </>
    //             )
    //         },
    //         {
    //             id: 3,
    //             title: null
    //         },
    //         {
    //             id: 4,
    //             title: getTestedResults(report) || null
    //         },
    //         {
    //             id: 5,
    //             title: report?.associatedInvestigations
    //                 ? report.associatedInvestigations?.map((investigation: AssociatedInvestigation2, index: number) => (
    //                       <div key={index}>
    //                           <ClassicLink
    //                               url={`/nbs/api/profile/${patient}/investigation/${investigation.publicHealthCaseUid}`}>
    //                               {investigation?.localId}
    //                           </ClassicLink>
    //                           <p className="margin-0">{investigation?.cdDescTxt}</p>
    //                       </div>
    //                   ))
    //                 : null
    //         },
    //         {
    //             id: 6,
    //             title: report?.programAreaCd || null
    //         },
    //         {
    //             id: 7,
    //             title: report?.jurisdictionCd || null
    //         },
    //         {
    //             id: 8,
    //             title: report?.localId || null
    //         }
    //     ]
    // });

    const asTableBodies = (reports: PatientLabReport[], patient: string
        ): TableBody[] => reports?.map((report) => asTableBody(report, patient)) || [];

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
            tableBody={bodies}
            totalResults={total}
            currentPage={currentPage}
            handleNext={setCurrentPage}
            sortData={handleSort}
        />
    );
};
