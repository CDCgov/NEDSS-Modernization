import { ComponentType, ReactNode } from 'react';
import { useAlert } from 'alert';
import { Button } from 'design-system/button';
import { Hint } from 'design-system/hint';
import { Icon } from 'design-system/icon';
import { Confirmation } from 'design-system/modal';

import { useConditionalRender } from 'conditional-render';
import { useSearchNavigation } from 'apps/search';
import { DeletePatientResponse, useDeletePatient } from './useDeletePatient';
import { Permitted, permissions } from 'libs/permission';
import { usePatient } from '../usePatient';
import { Deletability } from '../patient';
import { displayName } from 'name';

type DeleteActionProps = {
    buttonClassName?: string;
};

/**
 * Represents the delete action for a patient, including the confirmation dialog and handling of the delete operation.
 * @param {Props} props - The props for the component.
 * @return {JSX.Element} The rendered component.
 */
const DeleteAction = ({ buttonClassName }: DeleteActionProps) => {
    const { showSuccess, showError } = useAlert();
    const { go } = useSearchNavigation();
    const { show, hide, render } = useConditionalRender();
    const patient = usePatient();
    const deletability = patient.deletability;

    const handleDeleteComplete = (response: DeletePatientResponse) => {
        if (response.success) {
            const name = patient.name ? displayName('fullLastFirst')(patient.name) : 'patient';
            showSuccess(
                <>
                    You have successfully deleted
                    <strong>{`${name} (Patient ID: ${patient.patientId})`}</strong>.
                </>
            );
            go();
        } else {
            showError('Delete failed. Please try again later.');
        }
    };

    const deletePatient = useDeletePatient(handleDeleteComplete);

    const handleDeletePatient = () => {
        deletePatient(patient.id);
    };

    const HintableButton = withHintable(Button);

    const showHint = deletability === 'Has_Associations' || deletability === 'Is_Inactive';

    return (
        <Permitted permission={permissions.patient.delete}>
            <HintableButton
                className={buttonClassName}
                aria-label="Delete"
                data-tooltip-position="top"
                data-tooltip-offset="center"
                icon={<Icon name="delete" />}
                sizing={'medium'}
                onClick={show}
                showHint={showHint}
                hintId="delete-patient-hint"
                hintContent={<DeletabilityContent deletability={deletability} />}
                secondary
                destructive
                disabled={deletability !== 'Deletable'}
            />
            {render(
                <Confirmation
                    title="Delete patient file"
                    cancelText="Cancel"
                    onCancel={hide}
                    confirmText="Delete"
                    destructive
                    forceAction
                    onConfirm={handleDeletePatient}>
                    You have indicated that you would like to delete this patient file. Select Delete to continue or
                    Cancel to return to the patient file.
                </Confirmation>
            )}
        </Permitted>
    );
};

const DeletabilityContent = ({ deletability }: { deletability: Deletability }) =>
    deletability === 'Has_Associations' ? (
        <div>
            This patient file has associated event records. The file cannot be deleted until all associated event
            records have been deleted. If you are unable to see the associated event records due to your user permission
            settings, please contact your system administrator.
        </div>
    ) : (
        <div>This patient file is inactive and cannot be deleted.</div>
    );

const withHintable = <TProps extends object>(WrappedComponent: ComponentType<TProps>) => {
    type HintableProps = {
        hintId: string;
        hintContent: ReactNode;
        showHint?: boolean;
    };
    const EnhancedComponent = (props: TProps & HintableProps) => {
        const { hintId, hintContent, showHint, ...restProps } = props;
        return showHint ? (
            <Hint
                target={<WrappedComponent {...(restProps as TProps)} aria-describedby={hintId} />}
                id={hintId}
                position="left"
                marginLeft={120}>
                <strong>Delete disabled</strong>
                {hintContent}
            </Hint>
        ) : (
            <WrappedComponent {...(restProps as TProps)} aria-describedby={hintId} />
        );
    };
    return EnhancedComponent;
};

export { DeleteAction };
