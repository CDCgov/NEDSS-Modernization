import { PatientSearchResult } from 'generated/graphql/schema';
import { displayName } from 'name';

import { displayProfileLink } from './displayProfileLink';

const displayProfileLegalName = (result: PatientSearchResult) => {
    const legalNameDisplay =
        result.legalName?.first || result.legalName?.last ? displayName('fullLastFirst')(result.legalName) : 'No Data';
    return displayProfileLink(result.patient, result.shortId, legalNameDisplay);
};

export { displayProfileLegalName };
