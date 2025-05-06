import { ComponentType, ReactNode, useMemo } from 'react';
import { useAlert } from 'alert';
import { Button } from 'design-system/button';
import { Hint } from 'design-system/hint';
import { Icon } from 'design-system/icon';
import { Confirmation } from 'design-system/modal';

import { useConditionalRender } from 'conditional-render';
import { DeletabilityResult, resolveDeletability } from './resolveDeletability';

import { useSearchNavigation } from 'apps/search';
import { DeletePatientFileResponse, useDeletePatientFile } from './useDeletePatientFile';
import { usePatientFileContext } from '../PatientFileContext';
import { Permitted, permissions } from 'libs/permission';
// import { useProfileContext } from '../ProfileContext';

type DeleteActionProps = {
    buttonClassName?: string;
};

/**
 * Represents the delete action for a patient, including the confirmation dialog and handling of the delete operation.
 * @param {Props} props - The props for the component.
 * @returns {JSX.Element} The rendered component.
 */
const DeleteAction = ({ buttonClassName }: DeleteActionProps) => {
    const { showSuccess, showError } = useAlert();
    const { go } = useSearchNavigation();
    // const { summary } = useProfileContext();
    const { show, hide, render } = useConditionalRender();
    const { patient } = usePatientFileContext();
    const deletability = useMemo(() => resolveDeletability(patient), [patient]);

    console.log('patient', patient);

    const handleDeleteComplete = (response: DeletePatientFileResponse) => {
        if (response.success) {
            showSuccess(`Deleted patient: ${patient?.id}`);
            go();
        } else {
            showError('Delete failed. Please try again later.');
        }
    };

    const deletePatient = useDeletePatientFile(handleDeleteComplete);

    const handleDeletePatient = () => {
        deletePatient(parseInt(patient?.id ?? ''));
    };

    const HintableButton = withHintable(Button);

    const showHint =
        deletability === DeletabilityResult.Has_Associations || deletability === DeletabilityResult.Is_Inactive;
    // const hintText = useMemo(
    //     () =>
    //         deletability === DeletabilityResult.Has_Associations ?
    //             'The file cannot be deleted until all associated event records have been deleted. If you are unable to see the associated event records due to your user permission settings, please contact your system administrator.'
    //             : 'This patient file is inactive and cannot be deleted.',
    //     [deletability]
    // );

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
                hintContent={<DeletabilityContent result={deletability} />}
                secondary
                disabled={!patient?.deletable}
            />
            {render(
                <Confirmation
                    title="Delete patient file"
                    cancelText="Cancel"
                    onCancel={hide}
                    confirmText="Delete"
                    onConfirm={handleDeletePatient}>
                    You have indicated that you would like to delete this patient file. Select Delete to continue or
                    Cancel to return to the patient file.
                </Confirmation>
            )}
        </Permitted>
    );
};

const DeletabilityContent = ({ result }: { result: DeletabilityResult }) =>
    result === DeletabilityResult.Has_Associations ? (
        <div>
            The file cannot be deleted until all associated event records have been deleted. If you are unable to see
            the associated event records due to your user permission settings, please contact your system administrator.
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
            <Hint target={<WrappedComponent {...(restProps as TProps)} />} id={hintId} position="left" marginLeft={120}>
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
