import { useEffect, useState } from 'react';
import { TableComponent } from '../../components/Table/Table';

export const Summary = () => {
    const [tableBody, setTableBody] = useState<any>([]);
    const [currentPage, setCurrentPage] = useState<number>(1);
    const [documentReviewBody, setDocumentReviewBody] = useState<any>([]);

    useEffect(() => {
        const tempArr = [];
        const tempDocumentArr = [];
        for (let i = 0; i < 10; i++) {
            tempArr.push({
                id: i + 1,
                checkbox: true,
                tableDetails: [
                    { id: 1, title: `12/${i + 1}/2021` },
                    { id: 2, title: 'Acute flaccid myelitis' },
                    { id: 3, title: i === 3 ? 'Confirmed' : i === 7 ? 'Not a case' : null },
                    { id: 4, title: i === 1 ? 'Completed' : null },
                    { id: 5, title: 'Cobb County' },
                    { id: 6, title: i === 4 ? 'John Xerogeanes' : null },
                    { id: 7, title: 'CAS10004022GA01' },
                    { id: 8, title: i === 4 ? 'COIN1000XX01' : null }
                ]
            });

            if (i < 5) {
                tempDocumentArr.push({
                    id: i + 1,
                    checkbox: true,
                    tableDetails: [
                        {
                            id: 1,
                            title: (
                                <>
                                    Lab report <br /> {i % 2 == 0 && 'Electronic'}
                                </>
                            )
                        },
                        {
                            id: 2,
                            title: (
                                <>
                                    08 / 30 / 2021 <br /> 10:36 am
                                </>
                            )
                        },
                        {
                            id: 3,
                            title: (
                                <div>
                                    <strong>Reporting facility:</strong>
                                    <br />
                                    <span>Lab Corp</span>
                                    <br />
                                    <strong>Ordering facility:</strong>
                                    <br />
                                    <span>Dekalb General</span>
                                    <br />
                                    <strong>Ordering provider:</strong>
                                    <br />
                                    <span>Dr. Gene Davis SR</span>
                                </div>
                            )
                        },
                        { id: 4, title: i < 2 ? '03 / 24 / 2006' : null },
                        {
                            id: 5,
                            title: (
                                <div>
                                    <strong>Clostridium Botulinum Toxin: POSITIVE</strong>
                                    <br />
                                    <strong>Reference range: (NEGATIVE) - (Final):</strong>
                                </div>
                            )
                        },
                        { id: 6, title: 'OBS10001078GA01' }
                    ]
                });
            }
        }
        setDocumentReviewBody(tempDocumentArr);
        setTableBody(tempArr);
    }, []);

    return (
        <>
            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <TableComponent
                    isPagination={true}
                    tableHeader={'Open investigations'}
                    tableHead={[
                        { name: 'Start Date', sortable: true },
                        { name: 'Condition', sortable: true },
                        { name: 'Case status', sortable: true },
                        { name: 'Notification', sortable: true },
                        { name: 'Jurisdiction', sortable: true },
                        { name: 'Investigator', sortable: true },
                        { name: 'Investigation #', sortable: false },
                        { name: 'Co-infection #', sortable: false }
                    ]}
                    tableBody={tableBody}
                    currentPage={currentPage}
                    handleNext={(e) => setCurrentPage(e)}
                />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <TableComponent
                    isPagination={true}
                    tableHeader={'Documents requiring review'}
                    tableHead={[
                        { name: 'Document type', sortable: true },
                        { name: 'Date received', sortable: true },
                        { name: 'Reporting facility / provider', sortable: true },
                        { name: 'Event date', sortable: true },
                        { name: 'Description', sortable: true },
                        { name: 'Event #', sortable: true }
                    ]}
                    tableBody={documentReviewBody}
                    currentPage={currentPage}
                    handleNext={(e) => setCurrentPage(e)}
                />
            </div>
        </>
    );
};
