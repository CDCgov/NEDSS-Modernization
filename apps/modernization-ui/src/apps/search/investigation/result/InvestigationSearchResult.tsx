import { Investigation, InvestigationPersonParticipation } from 'generated/graphql/schema';
import { ClassicLink } from 'classic';
import { SelectableResolver } from 'options';
import { InvestigationStatus } from './InvestigationStatus';

const getPatient = (investigation: Investigation): InvestigationPersonParticipation | undefined | null => {
    return investigation.personParticipations?.find((p) => p?.typeCd === 'SubjOfPHC');
};

const displayInvestigationLink = (investigation: Investigation) => {
    const patient = getPatient(investigation);
    return (
        <ClassicLink
            id="condition"
            url={`/nbs/api/profile/${patient?.personParentUid}/investigation/${investigation.id}`}>
            {investigation.cdDescTxt}
        </ClassicLink>
    );
};

const displayInvestigator = (investigation: Investigation): string | undefined => {
    const provider = investigation.personParticipations?.find((p) => p?.typeCd === 'InvestgrOfPHC');
    if (provider) {
        return `${provider.firstName} ${provider.lastName}`;
    } else {
        return undefined;
    }
};

const displayStatus = (investigation: Investigation) => {
    return <InvestigationStatus status={investigation.investigationStatusCd === 'C' ? 'closed' : 'open'} />;
};

const displayNotificationStatus = (notificationStatusResolver: SelectableResolver) => (investigation: Investigation) =>
    (investigation.notificationRecordStatusCd &&
        notificationStatusResolver(investigation.notificationRecordStatusCd)?.name) ||
    investigation.notificationRecordStatusCd;

export { getPatient, displayInvestigationLink, displayInvestigator, displayStatus, displayNotificationStatus };
