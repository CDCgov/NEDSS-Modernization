import { PatientSearchResult } from 'generated/graphql/schema';
import { displayName } from 'name';
import { displayAddress } from 'address/display';
import { Link } from 'react-router-dom';

const displayProfileLink = (result: PatientSearchResult) => (
    <Link to={`/patient-profile/${result.shortId}/summary`}>
        {(result.legalName && displayName('short')(result.legalName)) || 'No Data'}
    </Link>
);

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

export { displayNames, displayProfileLink, displayPhones, displayAddresses, displayEmails };