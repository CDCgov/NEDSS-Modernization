import { permissions, Permitted } from 'libs/permission';
import { Modal } from 'design-system/modal';
import { Message } from 'design-system/message';
import { NavLinkButton } from 'design-system/button';
import { LinkButton } from 'design-system/button';
import { Heading } from 'components/heading';
import { CreatedPatient } from './api';
import { displayName } from 'name';
import { FeatureToggle } from 'feature';
import { maybeMap } from 'utils/mapping';

type Props = {
    created: CreatedPatient;
};

const PatientCreatedPanel = ({ created }: Props) => (
    <Modal
        id={`patient-creation-success`}
        title="Success"
        forceAction
        onClose={() => {}}
        footer={() => (
            <>
                <Permitted permission={permissions.morbidityReport.add}>
                    <LinkButton secondary href={`/nbs/api/profile/${created.id}/report/morbidity`}>
                        Add morbidity report
                    </LinkButton>
                </Permitted>
                <Permitted permission={permissions.labReport.add}>
                    <LinkButton secondary href={`/nbs/api/profile/${created.id}/report/lab`}>
                        Add lab report
                    </LinkButton>
                </Permitted>
                <Permitted permission={permissions.investigation.add}>
                    <LinkButton secondary href={`/nbs/api/profile/${created.id}/investigation`}>
                        Add investigation
                    </LinkButton>
                </Permitted>
                <FeatureToggle
                    guard={(features) => features?.patient?.file.enabled}
                    fallback={
                        <LinkButton target="_self" href={`/nbs/api/patient/${created.id}/file/redirect`}>
                            View patient
                        </LinkButton>
                    }>
                    <NavLinkButton to={`/patient/${created.shortId}`}>View patient</NavLinkButton>
                </FeatureToggle>
            </>
        )}>
        <Message type="success">
            <Heading level={2}>You have successfully added a new patient</Heading>
            <p>
                A patient file for{' '}
                <strong>
                    {resolveName(created)} (Patient ID: {created.shortId})
                </strong>{' '}
                has been added. You can now view the patient, or if you have permissions, add a lab report, morbidity
                report or investigation for this patient using the buttons below.
            </p>
        </Message>
    </Modal>
);

const resolveName = (created: CreatedPatient) => maybeMap(displayName('full'))(created.name);

export { PatientCreatedPanel };
