import { useContext, useEffect, useState } from 'react';
import { TableBody, TableComponent } from '../../components/Table/Table';
import { Button, Icon } from '@trussworks/react-uswds';
import {
    AssociatedInvestigation,
    FindInvestigationsByFilterQuery,
    FindLabReportsByFilterQuery,
    FindMorbidityReportsForPatientQuery,
    LabReport,
    OrganizationParticipation
} from '../../generated/graphql/schema';
import format from 'date-fns/format';
import { RedirectControllerService } from 'generated';
import { UserContext } from 'providers/UserContext';
import { Config } from 'config';
import { PatientTreatmentTable } from 'patient/profile/treatment';
import { PatientNamedByContactTable, ContactNamedByPatientTable } from 'patient/profile/contact';
import { PatientDocumentTable } from 'patient/profile/document';
import { TOTAL_TABLE_DATA } from 'utils/util';
import { PatientInvestigationsTable } from 'patient/profile/investigation';
import { MorbidityTable } from 'patient/profile/morbidity';

type EventTabProp = {
    patient: string | undefined;
    investigationData?: FindInvestigationsByFilterQuery['findInvestigationsByFilter'];
    labReports?: FindLabReportsByFilterQuery['findLabReportsByFilter'] | undefined;
    morbidityData?: FindMorbidityReportsForPatientQuery['findMorbidityReportsForPatient'] | undefined;
    profileData?: any;
};

export const Events = ({ patient, labReports }: EventTabProp) => {
    const { state } = useContext(UserContext);
    const NBS_URL = Config.nbsUrl;

    const [currentPage, setCurrentPage] = useState<number>(1);

    const [totalLabReports, setTotalLabReports] = useState<number | undefined>(0);
    const [labData, setLabData] = useState<any>();
    const [labCurrentPage, setLabCurrentPage] = useState<number>(1);

    const getOrderingProviderName = (labReport: LabReport): string | undefined => {
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
                                {format(new Date(document?.addTime), 'hh:mm a')}
                            </>
                        ),
                        class: 'link',
                        link: ''
                    },
                    {
                        id: 2,
                        title: (
                            <div>
                                {getOrderingProviderName(document) && (
                                    <>
                                        <strong>Reporting facility:</strong>
                                        <br />
                                        <span>{getOrderingProviderName(document) ?? ''}</span>
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
                    { id: 7, title: document?.jurisdictionCd || null },
                    { id: 8, title: document?.localId || null }
                ]
            });
            setLabData(tempArr);
        });
    };

    useEffect(() => {
        if (labReports) {
            setTotalLabReports(labReports?.total);
            getLabReport(labReports?.content);
        }
    }, [labReports]);

    return (
        <>
            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <PatientInvestigationsTable patient={patient} />
            </div>
            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <TableComponent
                    isPagination={true}
                    buttons={
                        <div className="grid-row">
                            <Button
                                type="button"
                                className="grid-row"
                                onClick={() => {
                                    RedirectControllerService.preparePatientDetailsUsingGet({
                                        authorization: 'Bearer ' + state.getToken()
                                    }).then(() => {
                                        // URl should probably be:
                                        // /nbs/LoadAddObservationLab1.do?method=createGenericLoad&ContextAction=AddLab
                                        // But this internally checks for "DSPatientPersonUID" which is stored in some NBSContext.OBJECT_STORE
                                        // for now putting in a temporary one that works, loads the "Add Lab report" page but with the tab "patient"
                                        // in context rather that the tab "report"
                                        window.location.href = `${NBS_URL}/MyTaskList1.do?ContextAction=AddLabDataEntry`;
                                    });
                                }}>
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
                <MorbidityTable patient={patient} />
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
                <PatientTreatmentTable patient={patient} />
            </div>
            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <PatientDocumentTable patient={patient} pageSize={TOTAL_TABLE_DATA} nbsBase={NBS_URL} />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <ContactNamedByPatientTable patient={patient} />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <PatientNamedByContactTable patient={patient} />
            </div>
        </>
    );
};
