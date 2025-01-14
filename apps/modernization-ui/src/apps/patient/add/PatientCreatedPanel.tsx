import { Modal } from 'design-system/modal';
import { Message } from 'design-system/message';
import { CreatedPatient } from './api';
import { displayName } from 'name';
import { ClassicButton } from 'classic';
import { NavLinkButton } from 'components/button/nav/NavLinkButton';
import { Heading } from 'components/heading';
import { FeatureToggle } from 'feature';
import { LinkButton } from 'components/button';

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
                <ClassicButton outline url={`/nbs/api/profile/${created.id}/report/lab`}>
                    Add lab report
                </ClassicButton>
                <ClassicButton outline url={`/nbs/api/profile/${created.id}/investigation`}>
                    Add investigation
                </ClassicButton>
                <FeatureToggle
                    guard={(features) => features?.patient?.profile.enabled}
                    fallback={
                        <LinkButton type="solid" target="_self" href={`/nbs/api/patient/${created.id}/file/redirect`}>
                            View patient
                        </LinkButton>
                    }>
                    <NavLinkButton to={`/patient-profile/${created.shortId}`}>View patient</NavLinkButton>
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
                has been added. You can now either view the patient, add a report for this patient or add an
                investigation for this patient using the buttons below.
            </p>
        </Message>
    </Modal>
);

const resolveName = (created: CreatedPatient) => (created.name ? displayName('full')(created.name) : 'the patient');

export { PatientCreatedPanel };
