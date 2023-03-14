import { useEffect, useState } from 'react';
import { TableBody, TableComponent } from '../../components/Table/Table';
import { Button, Icon } from '@trussworks/react-uswds';
import {
    AssociatedInvestigation,
    FindInvestigationsByFilterQuery,
    FindLabReportsByFilterQuery,
    LabReport,
    OrganizationParticipation
} from '../../generated/graphql/schema';
import format from 'date-fns/format';

type EventTabProp = {
    investigationData?: FindInvestigationsByFilterQuery['findInvestigationsByFilter'];
    labReports?: FindLabReportsByFilterQuery['findLabReportsByFilter'] | undefined;
};

export const Events = ({ investigationData, labReports }: EventTabProp) => {
    const [tableBody, setTableBody] = useState<any>([]);
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    const [contactRecords, setContactRecords] = useState<any>([]);
    const [currentPage, setCurrentPage] = useState<number>(1);

    const [totalInvestigations, setTotalInvestigations] = useState<number | undefined>(0);
    const [investigations, setInvenstigations] = useState<any>();

    const [totalLabReports, setTotalLabReports] = useState<number | undefined>(0);
    const [labData, setLabData] = useState<any>();
    const [labCurrentPage, setLabCurrentPage] = useState<number>(1);

    useEffect(() => {
        const contactTempArr = [];
        for (let i = 0; i < 10; i++) {
            // if (i < 5) {
            //     labTempArr.push({
            //         id: i + 1,
            //         checkbox: true,
            //         tableDetails: [
            //             {
            //                 id: 1,
            //                 title: (
            //                     <>
            //                         08 / 30 / 2021 <br /> 10:36 am
            //                     </>
            //                 )
            //             },
            //             {
            //                 id: 2,
            //                 title: (
            //                     <>
            //                         <strong>Reporting facility:</strong>
            //                         <br />
            //                         <span>Lab Corp</span>
            //                     </>
            //                 )
            //             },
            //             { id: 3, title: null },
            //             {
            //                 id: 4,
            //                 title: (
            //                     <>
            //                         <span className="margin-0">Acid-Fast Stain:</span>
            //                         <br />
            //                         <span>abnormal</span>
            //                     </>
            //                 )
            //             },
            //             {
            //                 id: 5,
            //                 title: (
            //                     <>
            //                         <a href="#" className="margin-0">
            //                             CAS10004022ga01
            //                         </a>
            //                         <br />
            //                         <span>Acute flaccid myelitis</span>
            //                     </>
            //                 )
            //             },
            //             { id: 6, title: 'BMIRD' },
            //             { id: 7, title: 'Clayton County' },
            //             { id: 8, title: 'OBS10003093GA01' }
            //         ]
            //     });
            // }

            if (i < 2) {
                contactTempArr.push({
                    id: i + 1,
                    checkbox: false,
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
    }, []);

    const getData = (investigationData: any) => {
        const tempArr: TableBody[] = [];
        investigationData?.map((investigation: any) => {
            const investigator = investigation?.personParticipations?.find(
                (person: any) => person?.typeCd === 'InvestgrOfPHC'
            );
            tempArr.push({
                id: investigation?.id,
                checkbox: true,
                tableDetails: [
                    { id: 1, title: format(new Date(investigation?.addTime), 'MM/dd/yyyy') },
                    { id: 2, title: investigation?.cdDescTxt },
                    { id: 3, title: investigation?.recordStatus },
                    { id: 4, title: investigation?.notificationRecordStatusCd },
                    { id: 5, title: investigation?.jurisdictionCodeDescTxt },
                    { id: 6, title: investigator ? investigator?.lastName + ' ' + investigator?.firstName : null },
                    {
                        id: 7,
                        title: <span onClick={() => console.log('asd')}>{investigation?.localId}</span>,
                        class: 'link',
                        link: ''
                    },
                    { id: 8, title: 'COIN1000XX01' }
                ]
            });
            setTableBody(tempArr);
        });
    };

    const getOrderingProvidorName = (labReport: LabReport): string | undefined => {
        const provider = labReport.personParticipations?.find((p) => p?.typeCd === 'ORD' && p?.personCd === 'PRV');
        if (provider) {
            return `${provider.firstName} ${provider.lastName}`;
        } else {
            return undefined;
        }
    };

    const getReportingFacility = (labReport: LabReport): OrganizationParticipation | undefined | null => {
        return labReport.organizationParticipations?.find((o) => o?.typeCd === 'AUT');
    };

    const getLabReport = (labReportData: any) => {
        const tempArr: TableBody[] = [];
        labReportData?.map((document: any, i: number) => {
            tempArr.push({
                id: i + 1,
                checkbox: false,
                tableDetails: [
                    {
                        id: 1,
                        title: (
                            <>
                                {format(new Date(document?.addTime), 'MM/dd/yyyy')} <br />{' '}
                                {format(new Date(document?.addTime), 'hh:mm b')}
                            </>
                        ),
                        class: 'link',
                        link: ''
                    },
                    {
                        id: 2,
                        title: (
                            <div>
                                {getOrderingProvidorName(document) && (
                                    <>
                                        <strong>Reporting facility:</strong>
                                        <br />
                                        <span>{getOrderingProvidorName(document) ?? ''}</span>
                                        <br />
                                    </>
                                )}
                                {getReportingFacility(document) && (
                                    <>
                                        <strong>Ordering provider:</strong>
                                        <br />
                                        <span>Dr. Gene Davis SR</span>
                                    </>
                                )}
                            </div>
                        )
                    },
                    {
                        id: 3,
                        title: null
                    },
                    { id: 4, title: null },
                    {
                        id: 5,
                        title:
                            !document.associatedInvestigations ||
                            document.associatedInvestigations.length == 0 ? null : (
                                <>
                                    {document.associatedInvestigations &&
                                        document.associatedInvestigations?.length > 0 &&
                                        document.associatedInvestigations?.map(
                                            (i: AssociatedInvestigation, index: number) => (
                                                <div key={index}>
                                                    <p
                                                        className="margin-0 text-primary text-bold link"
                                                        style={{ wordBreak: 'break-word' }}>
                                                        {i?.localId}
                                                    </p>
                                                    <p className="margin-0">{i?.cdDescTxt}</p>
                                                </div>
                                            )
                                        )}
                                </>
                            )
                    },
                    { id: 6, title: document?.programAreaCd || null },
                    { id: 7, title: document?.jurisdictionCodeDescTxt || null },
                    { id: 8, title: document?.localId || null }
                ]
            });
            setLabData(tempArr);
        });
    };

    useEffect(() => {
        if (investigationData) {
            setTotalInvestigations(investigationData.total);
            getData(investigationData?.content);
            setInvenstigations(investigationData?.content);
        }
    }, [investigationData]);

    useEffect(() => {
        if (labReports) {
            setTotalLabReports(labReports?.total);
            getLabReport(labReports?.content);
        }
    }, [labReports]);

    const sortInvestigationData = (name: string, type: string) => {
        getData(
            investigations.slice().sort((a: any, b: any) => {
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
        switch (name.toLowerCase()) {
            case 'start date':
                getData(
                    investigations.slice().sort((a: any, b: any) => {
                        const dateA: any = new Date(a.addTime);
                        const dateB: any = new Date(b.addTime);
                        return type === 'asc' ? dateB - dateA : dateA - dateB;
                    })
                );
                break;
            case 'condition':
                sortInvestigationData('cdDescTxt', type);
                break;
            case 'jurisdiction':
                sortInvestigationData('jurisdictionCodeDescTxt', type);
                break;
            case 'investigator':
                getData(
                    investigations.slice().sort((a: any, b: any) => {
                        const firstInv = a?.personParticipations?.find(
                            (person: any) => person?.typeCd === 'InvestgrOfPHC'
                        ).lastName;
                        const secondInv = b?.personParticipations?.find(
                            (person: any) => person?.typeCd === 'InvestgrOfPHC'
                        ).lastName;
                        if (firstInv && secondInv) {
                            if (firstInv.toLowerCase() < secondInv.toLowerCase()) {
                                return type === 'asc' ? -1 : 1;
                            }
                            if (firstInv.toLowerCase() > secondInv.toLowerCase()) {
                                return type === 'asc' ? 1 : -1;
                            }
                        }
                        return 0;
                    })
                );
                break;
            case 'case status':
                sortInvestigationData('recordStatus', type);
                break;
            case 'notification':
                sortInvestigationData('notificationRecordStatusCd', type);
        }
    };

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
                            <Button type="button" className="grid-row" onClick={() => {}}>
                                <Icon.Add className="margin-right-05" />
                                Add investigation
                            </Button>
                        </div>
                    }
                    totalResults={totalInvestigations}
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
                    sortData={handleSort}
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
                    totalResults={totalLabReports}
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
                    tableBody={labData}
                    currentPage={labCurrentPage}
                    handleNext={(e) => setLabCurrentPage(e)}
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
                                Add vaccination
                            </Button>
                        </div>
                    }
                    tableHeader={'Vaccinations'}
                    tableHead={[
                        { name: 'Date created', sortable: true },
                        { name: 'Provider', sortable: true },
                        { name: 'Date adinistered', sortable: true },
                        { name: 'Vaccine administered', sortable: true },
                        { name: 'Associated with', sortable: true },
                        { name: 'Events', sortable: true }
                    ]}
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
                    tableHead={[
                        { name: 'Date created', sortable: true },
                        { name: 'Named by', sortable: true },
                        { name: 'Date named', sortable: true },
                        { name: 'Description', sortable: true },
                        { name: 'Associated with', sortable: true },
                        { name: 'Event #', sortable: true }
                    ]}
                    tableBody={[]}
                    currentPage={currentPage}
                    handleNext={(e) => setCurrentPage(e)}
                />
            </div>
        </>
    );
};
