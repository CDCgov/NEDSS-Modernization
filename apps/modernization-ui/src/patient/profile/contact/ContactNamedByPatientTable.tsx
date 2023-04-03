import { TableBody, TableComponent } from 'components/Table/Table';
import { useEffect, useState } from 'react';
import { TOTAL_TABLE_DATA } from 'utils/util';

import { FindContactsNamedByPatientQuery, useFindContactsNamedByPatientLazyQuery } from 'generated/graphql/schema';
import { asTableBody, headings } from './PatientContactTableRenderer';

export type Result = FindContactsNamedByPatientQuery['findContactsNamedByPatient'];

const asTableBodies = (result: Result): TableBody[] => result?.content?.map(asTableBody) || [];

type Props = {
    patient?: string;
    pageSize?: number;
};

export const ContactNamedByPatientTable = ({ patient, pageSize = TOTAL_TABLE_DATA }: Props) => {
    const [currentPage, setCurrentPage] = useState<number>(1);
    const [total, setTotal] = useState<number>(0);
    const [tableBodies, setTableBodies] = useState<TableBody[]>([]);

    const handleComplete = (data: FindContactsNamedByPatientQuery) => {
        const total = data?.findContactsNamedByPatient?.total || 0;
        setTotal(total);

        const bodies = asTableBodies(data.findContactsNamedByPatient);
        setTableBodies(bodies);
    };

    const [getContacts] = useFindContactsNamedByPatientLazyQuery({ onCompleted: handleComplete });

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
            tableHeader={'Contact records (contacts named by patient)'}
            tableHead={headings}
            tableBody={tableBodies}
            totalResults={total}
            currentPage={currentPage}
            handleNext={setCurrentPage}
        />
    );
};
