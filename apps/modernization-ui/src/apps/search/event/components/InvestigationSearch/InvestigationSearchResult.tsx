import { Grid } from '@trussworks/react-uswds';
import { Investigation, InvestigationPersonParticipation } from 'generated/graphql/schema';
import { NoData } from 'components/NoData';
import { ClassicLink } from 'classic';
import { PatientDetails } from 'apps/search/event/components/PatientDetails';
import { internalizeDate } from 'date';

const getPatient = (investigation: Investigation): InvestigationPersonParticipation | undefined | null => {
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
                                {internalizeDate(investigation.addTime) || <NoData />}
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

export { InvestigationSearchResult };
