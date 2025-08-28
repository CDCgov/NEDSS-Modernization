import { PatientSearchResult } from 'generated/graphql/schema';
import { displayName } from 'name';
import { mapOr } from 'utils/mapping';
import { PatientFileLink } from './PatientFileLink';

const maybeDisplayName = mapOr(displayName('fullLastFirst'), 'No Data');

const displayProfileLegalName = (result: PatientSearchResult) => {
    const display = maybeDisplayName(result.legalName);

    return (
        <PatientFileLink identifier={result.patient} patientId={result.shortId}>
            {display}
        </PatientFileLink>
    );
};

export { displayProfileLegalName };
