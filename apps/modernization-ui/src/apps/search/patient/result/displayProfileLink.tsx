import { Link } from 'react-router';
import { FeatureToggle } from 'feature';

const nbs6PatientProfile = (identifier: number, shortId: number, display?: string) => (
    <a href={`/nbs/api/patient/${identifier}/file/redirect`}>{display || shortId}</a>
);

const modernizedPatientProfile = (shortId: number, display?: string) => (
    <Link to={`/patient-profile/${shortId}/summary`}>{display || shortId}</Link>
);

const displayProfileLink = (identifier: number, shortId: number, display?: string) => {
    return (
        <FeatureToggle
            guard={(features) => features?.patient?.profile.enabled}
            fallback={nbs6PatientProfile(identifier, shortId, display)}>
            {modernizedPatientProfile(shortId, display)}
        </FeatureToggle>
    );
};

export { displayProfileLink };
