import { useEffect, useState } from 'react';
import format from 'date-fns/format';
import { FindTreatmentsForPatientQuery, useFindTreatmentsForPatientLazyQuery } from 'generated/graphql/schema';

import { TOTAL_TABLE_DATA } from 'utils/util';
import { ClassicLink } from 'classic';
import { Column, Treatment } from './treatment';
import { TableBody, TableComponent } from 'components/Table';
import { transform } from './treatmentTransformer';
import { Direction } from 'sorting';
import { sort } from './treatmentSorter';

export type PatientTreatments = FindTreatmentsForPatientQuery['findTreatmentsForPatient'];

const headers = [
    { name: Column.DateCreated, sortable: true },
    { name: Column.Provider, sortable: true },
    { name: Column.TreatmentDate, sortable: true },
    { name: Column.Treatment, sortable: true },
    { name: Column.AssociatedWith, sortable: false },
    { name: Column.EventNumber, sortable: true }
];

type PatientTreatmentTableProps = {
    patient?: string;
    pageSize?: number;
};

const asTableBodies = (treatments: Treatment[], patient?: string): TableBody[] =>
    (patient &&
        treatments?.map((treatment) => ({
            id: treatment.treatment,
            tableDetails: [
                {
                    id: 1,
                    title: treatment?.createdOn ? format(treatment.createdOn, 'MM/dd/yyyy') : null
                },
                { id: 2, title: treatment?.provider },
                {
                    id: 3,
                    title: (
                        <ClassicLink url={`/nbs/api/profile/${patient}/treatment/${treatment?.treatment}`}>
                            {format(new Date(treatment?.treatedOn), 'MM/dd/yyyy')} <br />{' '}
                            {format(new Date(treatment?.treatedOn), 'hh:mm a')}
                        </ClassicLink>
                    )
                },
                { id: 4, title: treatment?.description },
                {
                    id: 5,
                    title: (
                        <div>
                            <ClassicLink
                                url={`/nbs/api/profile/${patient}/investigation/${treatment?.associatedWith.id}`}>
                                {treatment?.associatedWith?.local}
                            </ClassicLink>
                            <p className="margin-0">{treatment?.associatedWith?.condition}</p>
                        </div>
                    )
                },
                { id: 6, title: treatment?.event }
            ]
        }))) ||
    [];

export const PatientTreatmentTable = ({ patient, pageSize = TOTAL_TABLE_DATA }: PatientTreatmentTableProps) => {
    const [currentPage, setCurrentPage] = useState<number>(1);
    const [total, setTotal] = useState<number>(0);
    const [bodies, setBodies] = useState<TableBody[]>([]);
    const [items, setItems] = useState<Treatment[]>([]);

    const handleComplete = (data: FindTreatmentsForPatientQuery) => {
        const total = data?.findTreatmentsForPatient?.total || 0;
        setTotal(total);

        const content = transform(data?.findTreatmentsForPatient);

        setItems(content);

        const sorted = sort(content, {});
        setBodies(asTableBodies(sorted, patient));
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

    const handleSort = (name: string, direction: string): void => {
        const criteria = { name: name as Column, type: direction as Direction };
        const sorted = sort(items, criteria);
        setBodies(asTableBodies(sorted));
    };

    return (
        <TableComponent
            tableHeader={'Treatments'}
            tableHead={headers}
            tableBody={bodies}
            isPagination={true}
            pageSize={TOTAL_TABLE_DATA}
            totalResults={total}
            currentPage={currentPage}
            handleNext={setCurrentPage}
            sortData={handleSort}
        />
    );
};
