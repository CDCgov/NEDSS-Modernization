import { Link } from 'react-router';
import { FeatureToggle } from 'feature';
import { Permitted } from 'libs/permission';

type PatientFileLinkProps = {
    identifier: number;
    patientId: number;
    children?: string;
};

const PatientFileLink = ({ identifier, patientId, children }: PatientFileLinkProps) => {
    const display = children ? children : patientId.toString();

    return (
        <Permitted permission="VIEWWORKUP-PATIENT" fallback={display}>
            <FeatureToggle
                guard={(features) => features?.patient?.file.enabled}
                fallback={<NBS6PatientFile identifier={identifier}>{display}</NBS6PatientFile>}>
                <ModernizePatientFile patientId={patientId}>{display}</ModernizePatientFile>
            </FeatureToggle>
        </Permitted>
    );
};

type NBS6PatientFileProps = {
    identifier: number;
    children: string;
};

const NBS6PatientFile = ({ identifier, children }: NBS6PatientFileProps) => (
    <a href={`/nbs/api/patient/${identifier}/file/redirect`}>{children}</a>
);

type ModernizedPatientFileProps = {
    patientId: number;
    children: string;
};

const ModernizePatientFile = ({ patientId, children }: ModernizedPatientFileProps) => (
    <Link to={`/patient/${patientId}`}>{children}</Link>
);

export { PatientFileLink };
