import { useEffect, useState } from 'react';
import { Icon } from '@trussworks/react-uswds';
import format from 'date-fns/format';
import { FindLabReportsForPatientQuery, useFindLabReportsForPatientLazyQuery } from 'generated/graphql/schema';

import { Headers, PatientLabReport, AssociatedWith, TestResult } from './PatientLabReport';
import { transform } from './PatientLabReportTransformer';
import { sort } from './PatientLabReportSorter';
import { TableBody, TableComponent } from 'components/Table/Table';
import { Direction } from 'sorting';
import { ClassicButton, ClassicLink } from 'classic';

const asTableBody =
    (patient?: string) =>
    (report: PatientLabReport): TableBody => ({
        id: patient,
        tableDetails: [
            {
                id: 1,
                title: report?.receivedOn ? (
                    <ClassicLink url={`/nbs/api/profile/${patient}/report/lab/${report.report}`}>
                        {report?.receivedOn && format(report.receivedOn, 'MM/dd/yyyy')} <br />{' '}
                        {format(new Date(report?.receivedOn), 'hh:mm a')}
                    </ClassicLink>
                ) : null
            },
            {
                id: 2,
                title: (
                    <>
                        {report?.reportingFacility !== null && (
                            <>
                                <strong>Reporting facility:</strong>
                                <br />
                                <p className="margin0">{report.reportingFacility}</p>
                            </>
                        )}

                        {report?.orderingFacility !== null && (
                            <>
                                <strong>Ordering facility:</strong>
                                <br />
                                <p className="margin0">{report.orderingFacility}</p>
                            </>
                        )}

                        {report?.orderingProvider !== null && (
                            <>
                                <strong>Ordering provider:</strong>
                                <br />
                                <p className="margin0">{report.orderingProvider}</p>
                            </>
                        )}
                    </>
                )
            },
            {
                id: 3,
                title: format(report?.collectedOn, 'MM/dd/yyyy') || null
            },
            {
                id: 4,
                title: report?.results.length ? (
                    <>
                        {report.results?.map((result: TestResult) => (
                            <>
                                <br />
                                <strong>{result.test}:</strong>
                                <br />
                                <span>{result.result}</span>
                                <br />
                            </>
                        ))}
                    </>
                ) : null
            },
            {
                id: 5,
                title: report?.associatedWith.length ? (
                    <>
                        {report.associatedWith?.map((investigation: AssociatedWith, index: number) => (
                            <div key={index}>
                                <ClassicLink url={`/nbs/api/profile/${patient}/investigation/${investigation.id}`}>
                                    {investigation?.local}
                                </ClassicLink>
                                <p className="margin-0">{investigation?.condition}</p>
                            </div>
                        ))}
                    </>
                ) : null
            },
            {
                id: 6,
                title: report?.programArea || null
            },
            {
                id: 7,
                title: report?.jurisdiction || null
            },
            {
                id: 8,
                title: report?.event || null
            }
        ]
    });

const asTableBodies = (reports: PatientLabReport[], patient?: string): TableBody[] =>
    reports?.map(asTableBody(patient)) || [];

const headers = [
    { name: Headers.DateReceived, sortable: true },
    { name: Headers.FacilityProvider, sortable: false },
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
    const [items, setItems] = useState<PatientLabReport[]>([]);
    const [bodies, setBodies] = useState<TableBody[]>([]);

    const handleComplete = (data: FindLabReportsForPatientQuery) => {
        setTotal(data?.findLabReportsForPatient?.length || 0);
        const content = transform(data?.findLabReportsForPatient);
        setItems(content);
        const sorted = sort(content, {});
        setBodies(asTableBodies(sorted, patient));
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
