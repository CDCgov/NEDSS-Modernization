import { useEffect, useState } from 'react';
import format from 'date-fns/format';
import { FindTreatmentsForPatientQuery, useFindTreatmentsForPatientLazyQuery } from 'generated/graphql/schema';

import { TOTAL_TABLE_DATA } from 'utils/util';
import { SortableTable } from 'components/Table/SortableTable';
import { ClassicLink } from 'classic';
import { NoData } from 'components/NoData';

export type PatientTreatments = FindTreatmentsForPatientQuery['findTreatmentsForPatient'];

type PatientTreatmentTableProps = {
    patient?: string;
    pageSize?: number;
};

const displayCreatedOn = (patient: string, treatment: any) => {
    const createdOn = new Date(treatment.createdOn);

    return (
        <ClassicLink url={`/nbs/api/profile/${patient}/treatment/${treatment.treatment}`}>
            {format(createdOn, 'MM/dd/yyyy')} <br /> {format(createdOn, 'hh:mm a')}
        </ClassicLink>
    );
};

const displayAssociation = (patient: string, associatedWith: any) => (
    <div>
        <ClassicLink url={`/nbs/api/profile/${patient}/investigation/${associatedWith.id}`}>
            {associatedWith?.local}
        </ClassicLink>
        <p className="margin-0">{associatedWith?.condition}</p>
    </div>
);

export const PatientTreatmentTable = ({ patient, pageSize = TOTAL_TABLE_DATA }: PatientTreatmentTableProps) => {
    const [currentPage, setCurrentPage] = useState<number>(1);
    const [total, setTotal] = useState<number>(0);
    const [treatmentData, setTreatmentData] = useState<any>([]);

    const [tableHead, setTableHead] = useState<{ name: string; sortable: boolean; sort?: string }[]>([
        { name: 'Date created', sortable: true, sort: 'all' },
        { name: 'Provider', sortable: true, sort: 'all' },
        { name: 'Treatment date', sortable: true, sort: 'all' },
        { name: 'Treatment', sortable: true, sort: 'all' },
        { name: 'Associated with', sortable: true, sort: 'all' },
        { name: 'Event #', sortable: true, sort: 'all' }
    ]);

    const handleComplete = (data: FindTreatmentsForPatientQuery) => {
        const total = data?.findTreatmentsForPatient?.total || 0;
        setTotal(total);
        setTreatmentData(data.findTreatmentsForPatient?.content);
    };

    const [getTreatments, { called, loading }] = useFindTreatmentsForPatientLazyQuery({ onCompleted: handleComplete });

    useEffect(() => {
        if (patient) {
            getTreatments({
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
        setTreatmentData(
            name === 'associatedWith'
                ? treatmentData?.slice().sort((a: any, b: any) => {
                      if (a?.associatedWith?.condition && b?.associatedWith?.condition) {
                          if (a?.associatedWith?.condition.toLowerCase() < b?.associatedWith?.condition.toLowerCase()) {
                              return type === 'asc' ? -1 : 1;
                          }
                          if (a?.associatedWith?.condition.toLowerCase() > b?.associatedWith?.condition.toLowerCase()) {
                              return type === 'asc' ? 1 : -1;
                          }
                      }
                      return 0;
                  })
                : treatmentData?.slice().sort((a: any, b: any) => {
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

    const handleSort = (name: string, type: string) => {
        tableHeadChanges(name, type);
        switch (name.toLowerCase()) {
            case 'date created':
                setTreatmentData(
                    treatmentData?.slice().sort((a: any, b: any) => {
                        const dateA: any = new Date(a.createdOn);
                        const dateB: any = new Date(b.createdOn);
                        return type === 'asc' ? dateB - dateA : dateA - dateB;
                    })
                );
                break;
            case 'provider':
                sortData('provider', type);
                break;
            case 'associated with':
                sortData('associatedWith', type);
                break;
            case 'treatment date':
                setTreatmentData(
                    treatmentData?.slice().sort((a: any, b: any) => {
                        const dateA: any = new Date(a.treatedOn);
                        const dateB: any = new Date(b.treatedOn);
                        return type === 'asc' ? dateB - dateA : dateA - dateB;
                    })
                );
                break;
            case 'treatment':
                sortData('description', type);
                break;
            case 'event #':
                sortData('event', type);
        }
    };

    return (
        <SortableTable
            isLoading={!called || loading}
            isPagination={true}
            tableHeader={'Treatments'}
            tableHead={tableHead}
            tableBody={
                treatmentData?.length > 0 &&
                treatmentData?.map((treatment: any, index: number) => {
                    return (
                        <tr key={index}>
                            <td className={`font-sans-md table-data ${tableHead[0].sort !== 'all' && 'sort-td'}`}>
                                {patient && treatment?.createdOn ? displayCreatedOn(patient, treatment) : <NoData />}
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[1].sort !== 'all' && 'sort-td'}`}>
                                {treatment?.provider ? <span>{treatment.provider}</span> : <NoData />}
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[2].sort !== 'all' && 'sort-td'}`}>
                                {treatment?.treatedOn ? (
                                    <span className="table-span">
                                        {format(new Date(treatment?.treatedOn), 'MM/dd/yyyy')} <br />{' '}
                                        {format(new Date(treatment?.treatedOn), 'hh:mm a')}
                                    </span>
                                ) : (
                                    <NoData />
                                )}
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[3].sort !== 'all' && 'sort-td'}`}>
                                {treatment?.description ? <span>{treatment?.description}</span> : <NoData />}
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[4].sort !== 'all' && 'sort-td'}`}>
                                {patient && treatment?.associatedWith ? (
                                    displayAssociation(patient, treatment.associatedWith)
                                ) : (
                                    <NoData />
                                )}
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[5].sort !== 'all' && 'sort-td'}`}>
                                {treatment?.event ? <span>{treatment?.event}</span> : <NoData />}
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
