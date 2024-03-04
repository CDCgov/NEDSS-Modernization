import { useNavigate } from 'react-router';
import { Grid } from '@trussworks/react-uswds';
import { Investigation, PersonParticipation } from 'generated/graphql/schema';
import { NoData } from 'components/NoData';
import { ClassicLink } from 'classic';
import { calculateAge } from 'utils/util';
import { formattedName } from 'utils';

const formatDate = (date: Date) => {
    return new Date(date).toLocaleDateString('en-US', {
        timeZone: 'UTC'
    });
};

const getPatient = (investigation: Investigation): PersonParticipation | undefined | null => {
    return investigation.personParticipations?.find((p) => p?.typeCd === 'SubjOfPHC');
};

const getInvestigatorName = (investigation: Investigation): string | undefined => {
    const provider = investigation.personParticipations?.find((p) => p?.typeCd === 'InvestgrOfPHC');
    if (provider) {
        return `${provider.firstName} ${provider.lastName}`;
    } else {
        return undefined;
    }
};

const getInvestigationStatusString = (investigation: Investigation): string => {
    switch (investigation.investigationStatusCd) {
        case 'O':
            return 'OPEN';
        case 'C':
            return 'CLOSED';
        default:
            return investigation.investigationStatusCd ?? 'No Data';
    }
};

type InvestigationSearchResultProps = {
    investigation: Investigation;
};

const InvestigationSearchResult = ({ investigation }: InvestigationSearchResultProps) => {
    const patient = getPatient(investigation);
    const status = getInvestigationStatusString(investigation);

    return (
        <div className="padding-x-3 padding-top-3 padding-bottom-2 margin-bottom-3 bg-white border border-base-light radius-md">
            <Grid row gap={3}>
                <Grid col={3}>
                    <PatientDetails patient={patient} />
                </Grid>
                <Grid col={3}>
                    <Grid row gap={3} className="fill-height">
                        <Grid col={12} className="margin-bottom-2">
                            <p className="margin-0 text-normal text-gray-50 search-result-item-label">CONDITION</p>
                            <ClassicLink
                                className="margin-0 font-sans-md margin-top-05 text-bold text-primary word-break"
                                url={`/nbs/api/profile/${patient?.personParentUid}/investigation/${investigation.id}`}>
                                {investigation.cdDescTxt}
                            </ClassicLink>
                            <br />
                            <span>{investigation.localId}</span>
                        </Grid>
                        <Grid col={12} className="margin-bottom-2">
                            <p className="margin-0 text-normal search-result-item-label text-gray-50 margin-right-1">
                                START DATE
                            </p>
                            <p className="margin-0 font-sans-1xs text-normal">
                                {formatDate(investigation.addTime) || <NoData />}
                            </p>
                        </Grid>
                    </Grid>
                </Grid>
                <Grid col={3}>
                    <Grid row gap={3} className="fill-height">
                        <Grid col={12} className="margin-bottom-2">
                            <p className="margin-0 text-normal text-gray-50 search-result-item-label">JURISDICTION</p>
                            <p className="margin-0 font-sans-1xs text-normal">
                                {investigation.jurisdictionCodeDescTxt || <NoData />}
                            </p>
                        </Grid>
                        <Grid col={12} className="margin-bottom-2">
                            <p className="margin-0 text-normal search-result-item-label text-gray-50 margin-right-1">
                                INVESTIGATOR
                            </p>
                            <p className="margin-0 font-sans-1xs text-normal">
                                {getInvestigatorName(investigation) ?? <NoData />}
                            </p>
                        </Grid>
                    </Grid>
                </Grid>
                <Grid col={2}>
                    <Grid row gap={3} className="fill-height">
                        <Grid col={12} className="margin-bottom-2">
                            <p className="margin-0 text-normal text-gray-50 search-result-item-label">STATUS</p>
                            {status === 'No Data' ? (
                                <NoData />
                            ) : (
                                <p
                                    className="margin-0 font-sans-1xs text-normal status"
                                    style={{ backgroundColor: '#2cb844' }}>
                                    {status}
                                </p>
                            )}
                        </Grid>
                        <Grid col={12} className="margin-bottom-2">
                            <p className="margin-0 text-normal search-result-item-label text-gray-50 margin-right-1">
                                NOTIFICATION
                            </p>
                            {investigation.notificationRecordStatusCd ? (
                                <p className="margin-0 font-sans-1xs text-normal">
                                    {investigation.notificationRecordStatusCd ?? <NoData />}
                                </p>
                            ) : (
                                <NoData />
                            )}
                        </Grid>
                    </Grid>
                </Grid>
            </Grid>
        </div>
    );
};

type PatientDetailsType = {
    patient: PersonParticipation | null | undefined;
};

const PatientDetails = ({ patient }: PatientDetailsType) => {
    const navigate = useNavigate();
    let name = '';
    let birthDate: string | undefined;
    let age: string | undefined;
    let sex: string | undefined;
    if (patient) {
        name =
            !patient.lastName && !patient.firstName
                ? `No Data`
                : formattedName(patient?.lastName ?? '', patient?.firstName ?? '');
        if (patient.birthTime) {
            birthDate = formatDate(patient.birthTime);
            age = calculateAge(new Date(patient.birthTime));
        }
        sex = patient.currSexCd === 'M' ? 'Male' : patient.currSexCd === 'F' ? 'Female' : 'Unknown';
    }

    const redirectPatientProfile = () => {
        navigate(`/patient-profile/${patient?.shortId}`);
    };

    return (
        <Grid row gap={3}>
            <Grid col={12} className="margin-bottom-2">
                <p className="margin-0 text-normal text-gray-50 search-result-item-label">LEGAL NAME</p>
                <a
                    onClick={redirectPatientProfile}
                    className="margin-0 font-sans-md margin-top-05 text-bold text-primary word-break"
                    style={{ wordBreak: 'break-word', cursor: 'pointer' }}>
                    {name}
                </a>
            </Grid>
            <Grid col={12} className="margin-bottom-2">
                <div className="grid-row flex-align-center">
                    <p className="margin-0 text-normal search-result-item-label text-gray-50 margin-right-1">
                        DATE OF BIRTH
                    </p>
                    <p className="margin-0 font-sans-2xs text-normal">
                        <>
                            {birthDate ? birthDate : <NoData />}
                            <span className="font-sans-2xs"> {age ? `(${age})` : ''}</span>
                        </>
                    </p>
                </div>
                <div className="grid-row flex-align-center">
                    <p className="margin-0 text-normal search-result-item-label text-gray-50 margin-right-1">SEX</p>
                    <p className="margin-0 font-sans-2xs text-normal">{sex ? sex : <NoData />}</p>
                </div>
                <div className="grid-row flex-align-center">
                    <p className="margin-0 text-normal search-result-item-label text-gray-50 margin-right-1">
                        PATIENT ID
                    </p>
                    <p className="margin-0 font-sans-2xs text-normal">{patient?.shortId || <NoData />}</p>
                </div>
            </Grid>
        </Grid>
    );
};

export { InvestigationSearchResult };
