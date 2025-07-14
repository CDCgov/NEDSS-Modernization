import { PatientSearchResult } from 'generated/graphql/schema';
import { displayName } from 'name';
import { maybeMap } from 'utils/mapping';
import { PatientFileLink } from './PatientFileLink';
import { defaultTo } from 'libs/supplying';

const maybeDisplayName = maybeMap(displayName('fullLastFirst'));

const displayProfileLegalName = (result: PatientSearchResult) => {
    const display = defaultTo('No Data', maybeDisplayName(result.legalName));

    return (
        <PatientFileLink identifier={result.patient} patientId={result.shortId}>
            {display}
        </PatientFileLink>
    );
};

export { displayProfileLegalName };
