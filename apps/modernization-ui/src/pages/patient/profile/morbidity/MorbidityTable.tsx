import { useEffect, useState } from 'react';
import { Icon } from '@trussworks/react-uswds';
import format from 'date-fns/format';
import {
    PatientMorbidity,
    FindMorbidityReportsForPatientQuery,
    useFindMorbidityReportsForPatientLazyQuery
} from 'generated/graphql/schema';

import { ClassicButton, ClassicLink } from 'classic';
import { TableBody, TableComponent } from 'components/Table/Table';
import { internalizeDate } from 'date';

export type PatientMorbidities = FindMorbidityReportsForPatientQuery['findMorbidityReportsForPatient'];

type PatientMoribidityTableProps = {
    patient?: string;
    pageSize: number;
    allowAdd?: boolean;
};

export const MorbidityTable = ({ patient, pageSize, allowAdd = false }: PatientMoribidityTableProps) => {
    const [currentPage, setCurrentPage] = useState<number>(1);
    const [total, setTotal] = useState<number>(0);
    const [morbidityData, setMorbidityData] = useState<PatientMorbidity[]>([]);
    const [tableHead, setTableHead] = useState<{ name: string; sortable: boolean; sort?: string }[]>([
        { name: 'Date received', sortable: true },
        { name: 'Provider', sortable: true },
        { name: 'Report date', sortable: true },
        { name: 'Condition', sortable: true },
        { name: 'Jurisdiction', sortable: true },
        { name: 'Associated with', sortable: true },
        { name: 'Event #', sortable: true }
    ]);

    const handleComplete = (data: FindMorbidityReportsForPatientQuery) => {
        setTotal(data?.findMorbidityReportsForPatient?.total || 0);
        setMorbidityData(data.findMorbidityReportsForPatient?.content as unknown as PatientMorbidity[]);
    };

    const [getmorbidity, { called, loading }] = useFindMorbidityReportsForPatientLazyQuery({
        onCompleted: handleComplete
    });

    useEffect(() => {
        if (patient) {
            getmorbidity({
                variables: {
                    patient: patient,
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
        setMorbidityData(
            name === 'associatedWith'
                ? morbidityData?.slice().sort((a: any, b: any) => {
                      if (a[name] && b[name]) {
                          if (a?.associatedWith?.condition.toLowerCase() < b?.associatedWith?.condition.toLowerCase()) {
                              return type === 'asc' ? -1 : 1;
                          }
                          if (a?.associatedWith?.condition.toLowerCase() > b?.associatedWith?.condition.toLowerCase()) {
                              return type === 'asc' ? 1 : -1;
                          }
                      }
                      return 0;
                  })
                : morbidityData?.slice().sort((a: any, b: any) => {
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

    /**
     * Formats the PatientMorbidity object into TableComponent compatible TableBody object which represents a single row.
     * Each "title" in the tableDetails is a template of each column cell of the row being created.
     * @param {PatientMorbidity} morbidity, each item of the morbidity response data
     * @param {number} index, index of the array item
     * @return {TableBody}
     */
    const generateTableRow = (morbidity: PatientMorbidity, index: number): TableBody => {
        return {
            id: index,
            tableDetails: [
                {
                    id: 1,
                    title: morbidity?.receivedOn ? (
                        <span className={`font-sans-1xs table-data`}>
                            <ClassicLink url={`/nbs/api/profile/${patient}/report/morbidity/${morbidity.morbidity}`}>
                                {internalizeDate(morbidity?.receivedOn)} <br />
                                {format(new Date(morbidity?.receivedOn), 'hh:mm a')}
                            </ClassicLink>
                        </span>
                    ) : null
                },
                {
                    id: 2,
                    title: morbidity?.provider ? (
                        <span className={`font-sans-1xs table-data`}>
                            <strong>Reporting facility:</strong>
                            <br />
                            <span>{morbidity.provider}</span>
                            <br />
                        </span>
                    ) : null
                },
                {
                    id: 3,
                    title: morbidity?.reportedOn ? (
                        <span className={`font-sans-1xs table-data`}>
                            <span className="table-span">
                                {internalizeDate(morbidity?.reportedOn)} <br />
                                {format(new Date(morbidity?.reportedOn), 'hh:mm a')}
                            </span>
                        </span>
                    ) : null
                },
                {
                    id: 4,
                    title: morbidity?.condition ? (
                        <span className={`font-sans-1xs table-data`}>
                            <span>{morbidity?.condition}</span>
                        </span>
                    ) : null
                },
                {
                    id: 5,
                    title: morbidity?.jurisdiction ? (
                        <span className={`font-sans-1xs table-data`}>
                            <span>{morbidity?.jurisdiction}</span>
                        </span>
                    ) : null
                },
                {
                    id: 6,
                    title:
                        morbidity && morbidity?.associatedWith ? (
                            <span className={`font-sans-1xs table-data`}>
                                <div>
                                    <ClassicLink
                                        url={`/nbs/api/profile/${patient}/investigation/${morbidity?.associatedWith.id}`}>
                                        {morbidity?.associatedWith?.local}
                                    </ClassicLink>
                                    <p className="margin-0">{morbidity?.associatedWith?.condition}</p>
                                </div>
                            </span>
                        ) : null
                },
                {
                    id: 7,
                    title: morbidity?.event ? (
                        <span className={`font-sans-1xs table-data`}>
                            <span>{morbidity?.event}</span>
                        </span>
                    ) : null
                }
            ]
        };
    };

    /**
     *
     * @return {TableBody[]} list of TableBody each created from PatientMoribidity
     */
    const generateTableBody = () => {
        return (morbidityData?.length > 0 && morbidityData.map(generateTableRow)) || [];
    };

    const handleSort = (name: string, type: string) => {
        tableHeadChanges(name, type);
        switch (name.toLowerCase()) {
            case 'date received':
                setMorbidityData(
                    morbidityData.slice().sort((a: any, b: any) => {
                        const dateA: any = new Date(a.receivedOn);
                        const dateB: any = new Date(b.receivedOn);
                        return type === 'asc' ? dateB - dateA : dateA - dateB;
                    })
                );
                break;
            case 'report date':
                setMorbidityData(
                    morbidityData.slice().sort((a: any, b: any) => {
                        const dateA: any = new Date(a.reportedOn);
                        const dateB: any = new Date(b.reportedOn);
                        return type === 'asc' ? dateB - dateA : dateA - dateB;
                    })
                );
                break;
            case 'provider':
                sortData('provider', type);
                break;
            case 'condition':
                sortData('condition', type);
                break;
            case 'jurisdiction':
                sortData('jurisdiction', type);
                break;
            case 'associated with':
                sortData('associatedWith', type);
                break;
            case 'event #':
                sortData('event', type);
                break;
        }
    };

    return (
        <TableComponent
            isLoading={!called || loading}
            tableHeader="Morbidity reports"
            tableHead={tableHead}
            tableBody={generateTableBody()}
            isPagination={true}
            pageSize={pageSize}
            currentPage={currentPage}
            totalResults={total}
            handleNext={setCurrentPage}
            sortData={handleSort}
            buttons={
                allowAdd && (
                    <div className="grid-row">
                        <ClassicButton url={`/nbs/api/profile/${patient}/report/morbidity`}>
                            <Icon.Add className="margin-right-05" />
                            Add morbidity report
                        </ClassicButton>
                    </div>
                )
            }
        />
    );
};
