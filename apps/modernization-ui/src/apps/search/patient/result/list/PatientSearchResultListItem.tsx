import { PatientSearchResult } from 'generated/graphql/schema';

import { Result, ResultItem, ResultItemGroup } from 'apps/search/layout/result/list';
import {
    displayPhones,
    displayOtherNames,
    displayEmails,
    displayAddresses,
    displayProfileLegalName,
    displayPatientAge
} from 'apps/search/patient/result';

type Props = {
    result: PatientSearchResult;
};

const PatientSearchResultListItem = ({ result }: Props) => (
    <Result>
        <ResultItemGroup>
            <ResultItem label="Patient name" orientation="vertical">
                {displayProfileLegalName(result)}
            </ResultItem>
            <ResultItem label="DOB/Age">{result.birthday && displayPatientAge(result, 'singleline')}</ResultItem>
            <ResultItem label="Current sex">{result.gender}</ResultItem>
            <ResultItem label="Patient ID">{result.shortId}</ResultItem>
        </ResultItemGroup>
        <ResultItemGroup>
            <ResultItem label="Phone" orientation="vertical">
                {displayPhones(result)}
            </ResultItem>
            <ResultItem label="Other names" orientation="vertical">
                {displayOtherNames(result)}
            </ResultItem>
        </ResultItemGroup>
        <ResultItemGroup>
            <ResultItem label="Email" orientation="vertical">
                {displayEmails(result)}
            </ResultItem>
            <ResultItem label="Address" orientation="vertical">
                {displayAddresses(result)}
            </ResultItem>
        </ResultItemGroup>
        <ResultItemGroup>
            {result.identification.map((id, index) => (
                <ResultItem key={index} label={id.type} orientation="vertical">
                    {id.value}
                </ResultItem>
            ))}
            {!result.identification.length && <ResultItem label="ID Types" orientation="vertical"></ResultItem>}
        </ResultItemGroup>
    </Result>
);

export { PatientSearchResultListItem };
