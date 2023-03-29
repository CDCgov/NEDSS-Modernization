import { useEffect, useState } from 'react';
import { TableBody, TableComponent } from '../../components/Table/Table';
import {
    LabReport,
    OrganizationParticipation,
    useFindDocumentsRequiringReviewForPatientLazyQuery,
    useFindOpenInvestigationsForPatientLazyQuery
} from '../../generated/graphql/schema';
import { TOTAL_TABLE_DATA } from '../../utils/util';
import format from 'date-fns/format';

type SummaryProp = {
    profileData: any;
};

export const Summary = ({ profileData }: SummaryProp) => {
    const [getAllInvestigations, { data: openInvestigationData }] = useFindOpenInvestigationsForPatientLazyQuery();
    const [getDcouments, { data: documentData }] = useFindDocumentsRequiringReviewForPatientLazyQuery();

    const [tableBody, setTableBody] = useState<any>([]);
    const [currentPage, setCurrentPage] = useState<number>(1);
    const [documentCurrentPage, setDocumentCurrentPage] = useState<number>(1);
    const [documentReviewBody, setDocumentReviewBody] = useState<any>([]);
    const [totalInvestigations, setTotalInvestigations] = useState<number | undefined>(0);
    const [totalDocuments, setTotalDocuments] = useState<number | undefined>(0);

    const [investigations, setInvenstigations] = useState<any>(0);

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

    const getDescription = (labReport: LabReport) => {
        // TODO - there could be multiple tests associated with one lab report. How to display them in UI
        const observation = labReport.observations?.find((o) => o?.altCd && o?.displayName && o?.cdDescTxt);
        if (observation) {
            return (
                <>
                    <strong>{observation.cdDescTxt}:</strong>
                    <span>${observation.displayName}</span>
                </>
            );
        } else {
            return '--';
        }
    };

    useEffect(() => {
        if (profileData) {
            getAllInvestigations({
                variables: {
                    patientId: parseInt(profileData.id),
                    page: {
                        pageNumber: currentPage - 1,
                        pageSize: TOTAL_TABLE_DATA
                    }
                }
            });
            getDcouments({
                variables: {
                    patientId: parseInt(profileData.id),
                    page: {
                        pageNumber: currentPage - 1,
                        pageSize: TOTAL_TABLE_DATA
                    }
                }
            });
        }
    }, [profileData, currentPage]);

    const investigationData = (openInvestigationData: any) => {
        const tempArr: TableBody[] = [];
        openInvestigationData?.map((investigation: any) => {
            const investigator = investigation?.personParticipations?.find(
                (person: any) => person?.typeCd === 'InvestgrOfPHC'
            );
            tempArr.push({
                id: investigation?.id,
                checkbox: false,
                tableDetails: [
                    { id: 1, title: format(new Date(investigation?.addTime), 'MM/dd/yyyy') },
                    { id: 2, title: investigation?.cdDescTxt },
                    { id: 3, title: investigation?.recordStatus },
                    { id: 4, title: investigation?.notificationRecordStatusCd },
                    { id: 5, title: investigation?.jurisdictionCodeDescTxt },
                    { id: 6, title: investigator ? investigator?.lastName + ' ' + investigator?.firstName : null },
                    { id: 7, title: investigation?.localId },
                    { id: 8, title: 'COIN1000XX01' }
                ]
            });
            setTableBody(tempArr);
        });
    };

    const getDocumentData = (documentData: any) => {
        const tempArr: TableBody[] = [];
        documentData?.map((document: any, i: number) => {
            tempArr.push({
                id: i + 1,
                checkbox: false,
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
                                {format(new Date(document?.addTime), 'MM/dd/yyyy')} <br />{' '}
                                {format(new Date(document?.addTime), 'hh:mm a')}
                            </>
                        )
                    },
                    {
                        id: 3,
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
                                {/* <strong>Ordering facility:</strong>
                                <br />
                                <span>Dekalb General</span>
                                <br /> */}
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
                    { id: 4, title: i < 2 ? '03 / 24 / 2006' : null },
                    {
                        id: 5,
                        title: <div>{getDescription(document)}</div>
                    },
                    { id: 6, title: 'OBS10001078GA01' }
                ]
            });
            setDocumentReviewBody(tempArr);
        });
    };

    useEffect(() => {
        if (openInvestigationData?.findOpenInvestigationsForPatient) {
            setTotalInvestigations(openInvestigationData?.findOpenInvestigationsForPatient?.total);
            investigationData(openInvestigationData?.findOpenInvestigationsForPatient?.content);
            setInvenstigations(openInvestigationData?.findOpenInvestigationsForPatient?.content);
        }
    }, [openInvestigationData]);

    useEffect(() => {
        if (documentData?.findDocumentsRequiringReviewForPatient) {
            setTotalDocuments(documentData?.findDocumentsRequiringReviewForPatient?.total);
            getDocumentData(documentData?.findDocumentsRequiringReviewForPatient?.content);
        }
    }, [documentData]);

    const sortInvestigationData = (name: string, type: string) => {
        investigationData(
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

    const sortDocumentsData = (name: string, type: string) => {
        investigationData(
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
                investigationData(
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
                investigationData(
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

    const handleDocumentSort = (name: string, type: string) => {
        console.log('name.toLowerCase():', name.toLowerCase());
        console.log('type:', type);
        console.log('sortDocumentsData:', sortDocumentsData);
        // switch (name.toLowerCase()) {
        //     case 'start date':
        //         investigationData(
        //             investigations.slice().sort((a: any, b: any) => {
        //                 const dateA: any = new Date(a.addTime);
        //                 const dateB: any = new Date(b.addTime);
        //                 return type === 'asc' ? dateB - dateA : dateA - dateB;
        //             })
        //         );
        //         break;
        //     case 'condition':
        //         sortInvestigationData('cdDescTxt', type);
        //         break;
        //     case 'jurisdiction':
        //         sortInvestigationData('jurisdictionCodeDescTxt', type);
        //         break;
        //     case 'investigator':
        //         investigationData(
        //             investigations.slice().sort((a: any, b: any) => {
        //                 const firstInv = a?.personParticipations?.find(
        //                     (person: any) => person?.typeCd === 'InvestgrOfPHC'
        //                 ).lastName;
        //                 const secondInv = b?.personParticipations?.find(
        //                     (person: any) => person?.typeCd === 'InvestgrOfPHC'
        //                 ).lastName;
        //                 if (firstInv && secondInv) {
        //                     if (firstInv.toLowerCase() < secondInv.toLowerCase()) {
        //                         return type === 'asc' ? -1 : 1;
        //                     }
        //                     if (firstInv.toLowerCase() > secondInv.toLowerCase()) {
        //                         return type === 'asc' ? 1 : -1;
        //                     }
        //                 }
        //                 return 0;
        //             })
        //         );
        //         break;
        //     case 'case status':
        //         sortInvestigationData('recordStatus', type);
        //         break;
        //     case 'notification':
        //         sortInvestigationData('notificationRecordStatusCd', type);
        // }
    };

    return (
        <>
            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <TableComponent
                    totalResults={totalInvestigations}
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
                    sortData={handleSort}
                />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <TableComponent
                    totalResults={totalDocuments}
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
                    currentPage={documentCurrentPage}
                    handleNext={(e) => setDocumentCurrentPage(e)}
                    sortData={handleDocumentSort}
                />
            </div>
        </>
    );
};
