import { useEffect, useState } from 'react';
import { TableComponent } from '../../components/Table/Table';
import { Button, Icon } from '@trussworks/react-uswds';

export const Events = () => {
    const [tableBody, setTableBody] = useState<any>([]);
    const [tableLabBody, setTableLabBody] = useState<any>([]);
    const [contactRecords, setContactRecords] = useState<any>([]);
    const [currentPage, setCurrentPage] = useState<number>(1);

    useEffect(() => {
        const tempArr = [];
        const labTempArr = [];
        const contactTempArr = [];
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
                labTempArr.push({
                    id: i + 1,
                    checkbox: true,
                    tableDetails: [
                        {
                            id: 1,
                            title: (
                                <>
                                    08 / 30 / 2021 <br /> 10:36 am
                                </>
                            )
                        },
                        {
                            id: 2,
                            title: (
                                <>
                                    <strong>Reporting facility:</strong>
                                    <br />
                                    <span>Lab Corp</span>
                                </>
                            )
                        },
                        { id: 3, title: null },
                        {
                            id: 4,
                            title: (
                                <>
                                    <span className="margin-0">Acid-Fast Stain:</span>
                                    <br />
                                    <span>abnormal</span>
                                </>
                            )
                        },
                        {
                            id: 5,
                            title: (
                                <>
                                    <a href="#" className="margin-0">
                                        CAS10004022ga01
                                    </a>
                                    <br />
                                    <span>Acute flaccid myelitis</span>
                                </>
                            )
                        },
                        { id: 6, title: 'BMIRD' },
                        { id: 7, title: 'Clayton County' },
                        { id: 8, title: 'OBS10003093GA01' }
                    ]
                });
            }

            if (i < 2) {
                contactTempArr.push({
                    id: i + 1,
                    checkbox: true,
                    tableDetails: [
                        {
                            id: 1,
                            title: (
                                <>
                                    08 / 30 / 2021 <br /> 10:36 am
                                </>
                            )
                        },
                        { id: 2, title: 'TEST111, FIRSTMAX1' },
                        { id: 3, title: '10/02/2021' },
                        { id: 4, title: 'HIV Disposition: 2 - Prev. Neg, New Pos' },
                        { id: 5, title: 'CA10004006GA01 HIV' },
                        { id: 6, title: 'COIN10001003GA01 Field Follow-Up (S1)' }
                    ]
                });
            }
        }
        setContactRecords(contactTempArr);
        setTableLabBody(labTempArr);
        setTableBody(tempArr);
    }, []);

    return (
        <>
            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <TableComponent
                    isPagination={true}
                    buttons={
                        <div className="grid-row">
                            <Button disabled type="button" className="grid-row">
                                <Icon.Topic className="margin-right-05" />
                                Compare investigations
                            </Button>
                            <Button type="button" className="grid-row">
                                <Icon.Add className="margin-right-05" />
                                Add investigation
                            </Button>
                        </div>
                    }
                    tableHeader={'Investigations'}
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
                    buttons={
                        <div className="grid-row">
                            <Button type="button" className="grid-row">
                                <Icon.Add className="margin-right-05" />
                                Add lab report
                            </Button>
                        </div>
                    }
                    tableHeader={'Lab reports'}
                    tableHead={[
                        { name: 'Date received', sortable: true },
                        { name: 'Facility / provider', sortable: true },
                        { name: 'Date collected', sortable: true },
                        { name: 'Test results', sortable: true },
                        { name: 'Associated with', sortable: true },
                        { name: 'Program area', sortable: true },
                        { name: 'Jurisdiction #', sortable: false },
                        { name: 'Event #', sortable: false }
                    ]}
                    tableBody={tableLabBody}
                    currentPage={currentPage}
                    handleNext={(e) => setCurrentPage(e)}
                />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <TableComponent
                    isPagination={true}
                    buttons={
                        <div className="grid-row">
                            <Button type="button" className="grid-row">
                                <Icon.Add className="margin-right-05" />
                                Add morbidity report
                            </Button>
                        </div>
                    }
                    tableHeader={'Morbidity reports'}
                    tableHead={[]}
                    tableBody={[]}
                    currentPage={currentPage}
                    handleNext={(e) => setCurrentPage(e)}
                />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
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
                    tableHead={[]}
                    tableBody={[]}
                    currentPage={currentPage}
                    handleNext={(e) => setCurrentPage(e)}
                />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <TableComponent
                    isPagination={true}
                    buttons={
                        <div className="grid-row">
                            <Button type="button" className="grid-row">
                                <Icon.Add className="margin-right-05" />
                                Add document
                            </Button>
                        </div>
                    }
                    tableHeader={'Documents'}
                    tableHead={[]}
                    tableBody={[]}
                    currentPage={currentPage}
                    handleNext={(e) => setCurrentPage(e)}
                />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <TableComponent
                    isPagination={true}
                    buttons={
                        <div className="grid-row">
                            <Button type="button" className="grid-row">
                                <Icon.Add className="margin-right-05" />
                                Add document
                            </Button>
                        </div>
                    }
                    tableHeader={'Contact records'}
                    tableSubHeader={
                        <p className="text-semibold font-sans-md margin-top-1">
                            Contacts named by patient
                            <span className="display-block text-normal margin-top-1">
                                The following contacts were named in John Smith’s investigation of HIV:
                            </span>
                        </p>
                    }
                    tableHead={[
                        { name: 'Date created', sortable: true },
                        { name: 'Named by', sortable: true },
                        { name: 'Date named', sortable: true },
                        { name: 'Description', sortable: true },
                        { name: 'Associated with', sortable: true },
                        { name: 'Event #', sortable: true }
                    ]}
                    tableBody={contactRecords}
                    currentPage={currentPage}
                    handleNext={(e) => setCurrentPage(e)}
                />
            </div>
        </>
    );
};
