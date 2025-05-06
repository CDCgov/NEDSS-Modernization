import { Modal } from 'design-system/modal';
import { Message } from 'design-system/message';
import { CreatedPatient } from './api';
import { displayName } from 'name';
import { ClassicButton } from 'classic';
import { NavLinkButton } from 'design-system/button';
import { Heading } from 'components/heading';
import { FeatureToggle } from 'feature';
import { LinkButton } from 'components/button';
import { permissions, Permitted } from 'libs/permission';

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
                    <ClassicButton outline url={`/nbs/api/profile/${created.id}/report/morbidity`}>
                        Add morbidity report
                    </ClassicButton>
                </Permitted>
                <Permitted permission={permissions.labReport.add}>
                    <ClassicButton outline url={`/nbs/api/profile/${created.id}/report/lab`}>
                        Add lab report
                    </ClassicButton>
                </Permitted>
                <Permitted permission={permissions.investigation.add}>
                    <ClassicButton outline url={`/nbs/api/profile/${created.id}/investigation`}>
                        Add investigation
                    </ClassicButton>
                </Permitted>
                <FeatureToggle
                    guard={(features) => features?.patient?.file.enabled}
                    fallback={
                        <LinkButton type="solid" target="_self" href={`/nbs/api/patient/${created.id}/file/redirect`}>
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

const resolveName = (created: CreatedPatient) => (created.name ? displayName('full')(created.name) : 'the patient');

export { PatientCreatedPanel };
