import { Link } from 'react-router-dom';
import { Investigation, InvestigationPersonParticipation } from 'generated/graphql/schema';
import { internalizeDate } from 'date';
import { ClassicLink } from 'classic';
import { Result, ResultItem, ResultItemGroup } from 'apps/search/layout/result/list';

import styles from './InvestigationSearchResultListItem.module.scss';
import { SelectableResolver } from 'options';

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

type Props = {
    result: Investigation;
    notificationStatusResolver: SelectableResolver;
};

const InvestigationSearchResultListItem = ({ result, notificationStatusResolver }: Props) => {
    const patient = getPatient(result);
    const firstName = patient?.firstName ?? '';
    const lastName = patient?.lastName ?? '';
    const legalName = firstName && lastName ? `${firstName} ${lastName}` : 'No data';

    const getInvestigationStatusString = (investigation: Investigation): string =>
        investigation.investigationStatusCd === 'C' ? 'CLOSED' : 'OPEN';

    return (
        <Result>
            <ResultItemGroup>
                <ResultItem label="Legal name" orientation="vertical">
                    <Link id="legalName" to={`/patient-profile/${patient?.shortId}/summary`}>
                        {legalName}
                    </Link>
                </ResultItem>
                <ResultItem label="Date of birth">{internalizeDate(patient?.birthTime)}</ResultItem>
                <ResultItem label="Sex">{patient?.currSexCd}</ResultItem>
                <ResultItem label="Patient ID">{patient?.shortId}</ResultItem>
            </ResultItemGroup>
            <ResultItemGroup>
                <ResultItem label="Condition" orientation="vertical">
                    <ClassicLink
                        id="condition"
                        url={`/nbs/api/profile/${patient?.personParentUid}/investigation/${result.id}`}>
                        {result.cdDescTxt}
                    </ClassicLink>
                    {result.localId}
                </ResultItem>
                <ResultItem label="Start date" orientation="vertical">
                    {internalizeDate(result.addTime)}
                </ResultItem>
            </ResultItemGroup>
            <ResultItemGroup>
                <ResultItem label="Jurisdiction" orientation="vertical">
                    {result.jurisdictionCodeDescTxt}
                </ResultItem>
                <ResultItem label="Investigator" orientation="vertical">
                    {getInvestigatorName(result)}
                </ResultItem>
            </ResultItemGroup>
            <ResultItemGroup>
                <ResultItem label="Status" orientation="vertical">
                    <StatusBadge>{getInvestigationStatusString(result)}</StatusBadge>
                </ResultItem>
                <ResultItem label="Notification" orientation="vertical">
                    {(result.notificationRecordStatusCd &&
                        notificationStatusResolver(result.notificationRecordStatusCd)?.name) ||
                        result.notificationRecordStatusCd}
                </ResultItem>
            </ResultItemGroup>
        </Result>
    );
};

type StatusBadgeProps = {
    children?: string;
};

const StatusBadge = ({ children }: StatusBadgeProps) => (
    <span className={styles.status} id="status">
        {children}
    </span>
);

export { InvestigationSearchResultListItem };
