import { Link } from 'react-router';
import { displayName } from 'name';
import { asSelectableGender } from 'options/gender';
import { Mapping, Maybe } from 'utils';
import { SortingSelectable } from 'design-system/sorting/preferences';
import { Direction } from 'sorting';

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

const sorting: SortingSelectable[] = [
    { property: 'relavance', direction: Direction.Descending, name: 'Closest match' },
    { property: 'patientname', direction: Direction.Ascending, name: 'Patient name (A-Z)' },
    { property: 'patientname', direction: Direction.Descending, name: 'Patient name (Z-A)' },
    { property: 'birthday', direction: Direction.Ascending, name: 'Date of birth (Ascending)' },
    { property: 'birthday', direction: Direction.Descending, name: 'Date of birth (Descending)' }
];

export { sorting };
