import { PatientSearchResult } from 'generated/graphql/schema';
import { displayNameElement, matchesLegalName } from 'name';
import { displayAddress } from 'address/display';

import { ItemGroup } from 'design-system/item';
import { internalizeDate, displayAgeAsOfToday } from 'date';
import { MaybeNoData } from 'components/NoData';

// Displays Other names, that are not the legal name
const displayOtherNames = (result: PatientSearchResult, order: 'normal' | 'reverse' = 'normal'): JSX.Element => {
    const legalName = result.legalName;
    const patientSearchResultNames = order === 'normal' ? result.names : [...result.names].reverse();
    return (
        <div>
            {patientSearchResultNames
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

const displayPhones = (result: PatientSearchResult): JSX.Element => (
    <div>
        <MaybeNoData>
            {result.detailedPhones.map((phone, index) => (
                <div key={index}>
                    <ItemGroup type="phone" label={phone.use ?? phone.type}>
                        {phone.number}
                    </ItemGroup>
                </div>
            ))}
        </MaybeNoData>
    </div>
);
const displayEmails = (result: PatientSearchResult): string => result.emails.join('\n');
const displayPatientName = (result: PatientSearchResult): JSX.Element => (
    <div>
        {result.legalName && displayNameElement(result.legalName)}
        {displayOtherNames(result, 'reverse')}
    </div>
);
const displayPatientAge = (result: PatientSearchResult, variant: 'singleline' | 'multiline'): JSX.Element => (
    <div>
        {internalizeDate(result.birthday)}
        {variant === 'multiline' ? <br /> : ' ('}
        {displayAgeAsOfToday(result.birthday)}
        {variant === 'singleline' && ')'}
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
    displayPatientAge,
    displayOtherNames,
    displayPhones,
    displayAddresses,
    displayEmails,
    displayIdentifications
};
