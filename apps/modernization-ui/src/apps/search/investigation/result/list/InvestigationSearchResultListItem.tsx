import { Investigation } from 'generated/graphql/schema';
import { internalizeDate } from 'date';
import { SelectableResolver } from 'options';
import { displayProfileLink, displayGender } from 'apps/search/basic';
import { Result, ResultItem, ResultItemGroup } from 'apps/search/layout/result/list';
import {
    displayInvestigationLink,
    displayInvestigator,
    displayStatus,
    getPatient
} from 'apps/search/investigation/result';

type Props = {
    result: Investigation;
    notificationStatusResolver: SelectableResolver;
};

const InvestigationSearchResultListItem = ({ result, notificationStatusResolver }: Props) => {
    const patient = getPatient(result);

    return (
        <Result>
            <ResultItemGroup>
                <ResultItem label="Legal name" orientation="vertical">
                    {displayProfileLink(patient)}
                </ResultItem>
                <ResultItem label="Date of birth">{internalizeDate(patient?.birthTime)}</ResultItem>
                <ResultItem label="Current sex">{displayGender(patient)}</ResultItem>
                <ResultItem label="Patient ID">{patient?.shortId}</ResultItem>
            </ResultItemGroup>
            <ResultItemGroup>
                <ResultItem label="Condition" orientation="vertical">
                    {displayInvestigationLink(result)}
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
                    {displayInvestigator(result)}
                </ResultItem>
            </ResultItemGroup>
            <ResultItemGroup>
                <ResultItem label="Status" orientation="vertical">
                    {displayStatus(result)}
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

export { InvestigationSearchResultListItem };
