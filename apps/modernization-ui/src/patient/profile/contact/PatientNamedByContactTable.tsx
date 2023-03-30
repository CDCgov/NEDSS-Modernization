import { TableBody, TableComponent } from 'components/Table/Table';
import { useEffect, useState } from 'react';
import { TOTAL_TABLE_DATA } from 'utils/util';

import { FindPatientNamedByContactQuery, useFindPatientNamedByContactLazyQuery } from 'generated/graphql/schema';
import { asTableBody, headings } from './PatientContactTableRenderer';

export type Result = FindPatientNamedByContactQuery['findPatientNamedByContact'];

const asTableBodies = (result: Result): TableBody[] => result?.content?.map(asTableBody) || [];

type Props = {
    patient?: string;
    pageSize?: number;
};

export const PatientNamedByContactTable = ({ patient, pageSize = TOTAL_TABLE_DATA }: Props) => {
    const [currentPage, setCurrentPage] = useState<number>(1);
    const [total, setTotal] = useState<number>(0);
    const [tableBodies, setTableBodies] = useState<TableBody[]>([]);

    const handleComplete = (data: FindPatientNamedByContactQuery) => {
        const total = data?.findPatientNamedByContact?.total || 0;
        setTotal(total);

        const bodies = asTableBodies(data.findPatientNamedByContact);
        setTableBodies(bodies);
    };

    const [getContacts] = useFindPatientNamedByContactLazyQuery({ onCompleted: handleComplete });

    useEffect(() => {
        if (patient) {
            getContacts({
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

    return (
        <TableComponent
            isPagination={true}
            tableHeader={'Contact records (patient named by contacts)'}
            tableHead={headings}
            tableBody={tableBodies}
            totalResults={total}
            currentPage={currentPage}
            handleNext={setCurrentPage}
        />
    );
};
