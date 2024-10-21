import { PatientSearchResult } from 'generated/graphql/schema';
import { displayName, displayNameElement, matchesLegalName } from 'name';
import { displayAddress } from 'address/display';
import { Link } from 'react-router-dom';
import { ItemGroup } from 'design-system/item';

const displayProfileLink = (shortId: number, displayName?: string) => {
    return <Link to={`/patient-profile/${shortId}/summary`}>{displayName || shortId}</Link>;
};

const displayProfileLegalName = (result: PatientSearchResult) => {
    const legalNameDisplay = (result.legalName && displayName('fullLastFirst')(result.legalName)) || 'No Data';
    return displayProfileLink(result.shortId, legalNameDisplay);
};

// Displays Other names, that are not the legal name
const displayOtherNames = (result: PatientSearchResult, order: 'normal' | 'reverse' = 'normal'): JSX.Element => {
    const legalName = result.legalName;
    const patientSearchResultNames = order === 'normal' ? result.names : [...result.names].reverse();
    return (
        <div>
            {patientSearchResultNames
                .filter((name) => !matchesLegalName(name, legalName))
                .map((name, index) => (
                    <div key={index}>{displayNameElement(name, 'fullLastFirst')}</div>
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
const displayPatientName = (result: PatientSearchResult): JSX.Element => (
    <div>
        {result.legalName && displayNameElement(result.legalName, 'fullLastFirst')}
        {displayOtherNames(result, 'reverse')}
    </div>
);

// Returns JSX that represents a list of IDs to display
const displayIdentifications = (result: PatientSearchResult): JSX.Element => (
    <div>
        {result.identification.map((identification, index) => (
            <div key={index}>
                <ItemGroup type="other" label={identification.type}>
                    {identification.value}
                </ItemGroup>
            </div>
        ))}
    </div>
);

export {
    displayPatientName,
    displayOtherNames,
    displayProfileLink,
    displayProfileLegalName,
    displayPhones,
    displayAddresses,
    displayEmails,
    displayIdentifications
};
