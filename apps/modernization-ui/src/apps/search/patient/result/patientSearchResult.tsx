import { PatientSearchResult } from 'generated/graphql/schema';
import { displayName } from 'name';
import { displayAddress } from 'address/display';
import { Link } from 'react-router-dom';

const displayProfileLink = (shortId: number, displayName?: string) => {
    return <Link to={`/patient-profile/${shortId}/summary`}>{displayName || shortId}</Link>;
};

const displayProfileLegalName = (result: PatientSearchResult) => {
    const legalNameDisplay = (result.legalName && displayName('fullLastFirst')(result.legalName)) || 'No Data';
    return displayProfileLink(result.shortId, legalNameDisplay);
};

const displayNames = (result: PatientSearchResult): string => {
    const legalName = result.legalName;
    return result.names
        .filter((name) => name?.first != legalName?.first || name?.last != legalName?.last)
        .map(displayName())
        .join('\n');
};

const displayAddresses = (result: PatientSearchResult): string => result.addresses.map(displayAddress).join('\n\n');

const displayPhones = (result: PatientSearchResult): string => result.phones.join('\n');
const displayEmails = (result: PatientSearchResult): string => result.emails.join('\n');

export { displayNames, displayProfileLink, displayProfileLegalName, displayPhones, displayAddresses, displayEmails };
