import { PatientSearchResult } from 'generated/graphql/schema';
import { displayName, displayNameElement, matchesLegalName } from 'name';
import { displayAddress } from 'address/display';
import { Link } from 'react-router-dom';

const displayProfileLink = (shortId: number, displayName?: string) => {
    return <Link to={`/patient-profile/${shortId}/summary`}>{displayName || shortId}</Link>;
};

const displayProfileLegalName = (result: PatientSearchResult) => {
    const legalNameDisplay = (result.legalName && displayName('fullLastFirst')(result.legalName)) || 'No Data';
    return displayProfileLink(result.shortId, legalNameDisplay);
};

// Displays Other names, that are not the legal name
const displayOtherNames = (result: PatientSearchResult): JSX.Element => {
    const legalName = result.legalName;
    return (
        <div>
            {result.names
                .filter((name) => !matchesLegalName(name, legalName))
                .map((name, index) => (
                    <div key={index}>{displayNameElement(name)}</div>
                ))}
        </div>
    );
};

// Returns JSX that represents a list of addresses to display
const displayAddresses = (result: PatientSearchResult): JSX.Element => (
    <div>
        {result.addresses.map((address, index) => (
            <div key={index}>{displayAddress(address)}</div>
        ))}
    </div>
);

const displayPhones = (result: PatientSearchResult): string => result.phones.join('\n');
const displayEmails = (result: PatientSearchResult): string => result.emails.join('\n');

export {
    displayOtherNames,
    displayProfileLink,
    displayProfileLegalName,
    displayPhones,
    displayAddresses,
    displayEmails
};
