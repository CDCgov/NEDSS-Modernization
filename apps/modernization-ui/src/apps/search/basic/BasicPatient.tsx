import { Link } from 'react-router-dom';
import { displayName } from 'name';
import { asSelectableGender } from 'options/gender';
import { Mapping, Maybe } from 'utils';

type BasicPatient = {
    birthTime?: string | null;
    currSexCd?: string | null;
    firstName?: string | null;
    lastName?: string | null;
    personParentUid?: number | null;
    shortId?: number | null;
};

export type { BasicPatient };

const withPatient =
    <I, O>(resolving: Mapping<I, Maybe<BasicPatient>>, mapping: Mapping<Maybe<BasicPatient>, O>) =>
    (containing: I) =>
        mapping(resolving(containing));

const displayProfileLink = (patient: Maybe<BasicPatient>) => (
    <Link to={`/patient-profile/${patient?.shortId}`}>
        {(patient && displayName('short')({ first: patient.firstName, last: patient.lastName })) || 'No Data'}
    </Link>
);

const displayGender = (patient: Maybe<BasicPatient>) => asSelectableGender(patient?.currSexCd)?.name;

export { withPatient, displayProfileLink, displayGender };
