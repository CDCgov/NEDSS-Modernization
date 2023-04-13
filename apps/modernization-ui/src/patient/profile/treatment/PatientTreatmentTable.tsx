import { TableBody, TableComponent } from 'components/Table/Table';
import { useEffect, useState } from 'react';
import { Button, Icon } from '@trussworks/react-uswds';
import format from 'date-fns/format';
import { FindTreatmentsForPatientQuery, useFindTreatmentsForPatientLazyQuery } from 'generated/graphql/schema';

import { TOTAL_TABLE_DATA } from 'utils/util';

export type PatientTreatments = FindTreatmentsForPatientQuery['findTreatmentsForPatient'];

const asTableBody = (treatment: any): TableBody => ({
    id: treatment?.event,
    checkbox: false,
    tableDetails: [
        {
            id: 1,
            title: (
                <>
                    {format(new Date(treatment?.createdOn), 'MM/dd/yyyy')} <br />{' '}
                    {format(new Date(treatment?.createdOn), 'hh:mm a')}
                </>
            ),
            class: 'link',
            link: ''
        },
        {
            id: 2,
            title: treatment?.provider
        },
        { id: 4, title: format(new Date(treatment?.treatedOn), 'MM/dd/yyyy') },
        { id: 7, title: treatment?.description || null },
        {
            id: 5,
            title:
                !treatment || treatment?.associatedWith.condition?.length == 0 ? null : (
                    <>
                        {treatment.associatedWith && treatment.associatedWith.condition.length > 0 && (
                            <div>
                                <p className="margin-0 text-primary text-bold link" style={{ wordBreak: 'break-word' }}>
                                    {treatment.associatedWith?.local}
                                </p>
                                <p className="margin-0">{treatment.associatedWith.condition}</p>
                            </div>
                        )}
                    </>
                )
        },
        { id: 8, title: treatment?.event || null }
    ]
});

const asTableBodies: any = (treatments: any): TableBody[] => treatments?.map(asTableBody) || [];

type PatientTreatmentTableProps = {
    patient?: string;
    pageSize?: number;
};

export const PatientTreatmentTable = ({ patient, pageSize = TOTAL_TABLE_DATA }: PatientTreatmentTableProps) => {
    const [currentPage, setCurrentPage] = useState<number>(1);
    const [total, setTotal] = useState<number>(0);
    const [tableBodies, setTableBodies] = useState<TableBody[]>([]);
    const [treatmentData, setTreatmentData] = useState<any>([]);

    const handleComplete = (data: FindTreatmentsForPatientQuery) => {
        const total = data?.findTreatmentsForPatient?.total || 0;
        setTotal(total);

        const bodies = asTableBodies(data.findTreatmentsForPatient?.content);
        setTableBodies(bodies);
        setTreatmentData(data.findTreatmentsForPatient?.content);
    };

    const [getTreatments] = useFindTreatmentsForPatientLazyQuery({ onCompleted: handleComplete });

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

    const sortData = (name: string, type: string) => {
        const bodies: any = asTableBodies(
            treatmentData?.slice().sort((a: any, b: any) => {
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
        setTableBodies(bodies);
    };

    const handleSort = (name: string, type: string) => {
        switch (name.toLowerCase()) {
            case 'date created':
                setTableBodies(
                    asTableBodies(
                        treatmentData?.slice().sort((a: any, b: any) => {
                            const dateA: any = new Date(a.createdOn);
                            const dateB: any = new Date(b.createdOn);
                            return type === 'asc' ? dateB - dateA : dateA - dateB;
                        })
                    )
                );
                break;
            case 'provider':
                sortData('provider', type);
                break;
            case 'treatment date':
                setTableBodies(
                    asTableBodies(
                        treatmentData?.slice().sort((a: any, b: any) => {
                            const dateA: any = new Date(a.treatedOn);
                            const dateB: any = new Date(b.treatedOn);
                            return type === 'asc' ? dateB - dateA : dateA - dateB;
                        })
                    )
                );
                break;
            case 'treatment':
                sortData('description', type);
                break;
            case 'event #':
                sortData('localId', type);
        }
    };

    return (
        <TableComponent
            isPagination={true}
            buttons={
                <div className="grid-row">
                    <Button type="button" className="grid-row">
                        <Icon.Add className="margin-right-05" />
                        Add treatment
                    </Button>
                </div>
            }
            tableHeader={'Treatments'}
            tableHead={[
                { name: 'Date created', sortable: true },
                { name: 'Provider', sortable: true },
                { name: 'Treatment date', sortable: true },
                { name: 'Treatment', sortable: true },
                { name: 'Associated with', sortable: false },
                { name: 'Event #', sortable: false }
            ]}
            tableBody={tableBodies}
            totalResults={total}
            currentPage={currentPage}
            handleNext={setCurrentPage}
            sortData={handleSort}
        />
    );
};
