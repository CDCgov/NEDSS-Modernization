import { useAlert } from 'alert';
import { Pass } from 'apps/deduplication/api/model/Pass';
import { useMatchConfiguration } from 'apps/deduplication/api/useMatchConfiguration';
import { DataElements } from 'apps/deduplication/api/model/DataElement';
import { Shown } from 'conditional-render';
import { useEffect, useState } from 'react';
import { FormProvider, useForm, useFormState } from 'react-hook-form';
import { exists } from 'utils';
import { UnsavedChangesConfirmation } from '../confirmation/UnsavedChangesConfirmation';
import { SelectPass } from '../notification-cards/SelectPass';
import styles from './pass-configuration.module.scss';
import { PassForm } from './pass-form/PassForm';
import { SavePassModal } from './pass-form/save-modal/SavePassModal';
import { PassList } from './pass-list/PassList';
import { Loading } from 'components/Spinner';

type Props = {
    dataElements: DataElements;
};
export const PassConfiguration = ({ dataElements }: Props) => {
    const { showSuccess, showError } = useAlert();
    const { passes, selectedPass, error, selectPass, addPass, deletePass, savePass, updatePassName, loading } =
        useMatchConfiguration();
    const form = useForm<Pass>({ mode: 'onBlur' });
    const { isDirty, dirtyFields } = useFormState(form);

    const [confirmationState, setConfirmationState] = useState<{
        visible: boolean;
        onAccept: (() => void) | undefined;
    }>({
        visible: false,
        onAccept: undefined
    });
    const [showSaveModal, setShowSaveModal] = useState<boolean>(false);

    useEffect(() => {
        // Reset form when selected pass changes
        form.reset(
            { lowerBound: undefined, upperBound: undefined, ...selectedPass },
            { keepDefaultValues: false, keepDirty: false }
        );
    }, [selectedPass]);

    useEffect(() => {
        if (error) {
            showError({ message: 'Failed to retrieve Pass configuration' });
        }
    }, [error]);

    const handleAddPassClick = () => {
        // If form is dirty, confirm data loss prior to action
        if (isDirty && exists(dirtyFields) && selectedPass !== undefined) {
            setConfirmationState({ visible: true, onAccept: handleAddPass });
        } else {
            handleAddPass();
        }
    };

    const handleAddPass = () => {
        addPass();
    };

    const handleChangePass = (pass: Pass) => {
        if (pass === selectedPass) {
            // user selected the pass that is already selected
            return;
        }
        // if dirty or new pass, confirm
        if ((isDirty && exists(dirtyFields)) || (selectedPass !== undefined && selectedPass.id === undefined)) {
            setConfirmationState({ visible: true, onAccept: () => selectPass(pass) });
        } else {
            selectPass(pass);
        }
    };

    const handleCancel = () => {
        selectPass();
    };

    const handleDelete = () => {
        if (selectedPass === undefined) {
            return;
        }
        deletePass(selectedPass.id, () => {
            showSuccess({
                message: (
                    <span>
                        You have successfully deleted the <strong>{selectedPass?.name}</strong> configuration.
                    </span>
                )
            });
        });
    };

    const handleSave = () => {
        savePass(form.getValues(), (passName: string) => {
            showSuccess({
                message: (
                    <span>
                        You have successfully updated the <strong>{passName}</strong> pass configuration.
                    </span>
                )
            });
            setShowSaveModal(false);
        });
    };

    const handleRenamePass = (pass: Pass, onSuccess: () => void) => {
        updatePassName(pass, onSuccess);
    };

    return (
        <div className={styles.passConfiguration}>
            <Shown when={!loading} fallback={<Loading center />}>
                <UnsavedChangesConfirmation
                    passName={selectedPass?.name ?? ''}
                    onAccept={() => {
                        confirmationState.onAccept?.();
                        setConfirmationState({ visible: false, onAccept: undefined });
                    }}
                    visible={confirmationState.visible}
                    onCancel={() => setConfirmationState({ visible: false, onAccept: undefined })}
                />
                <PassList
                    passes={passes}
                    onSetSelectedPass={(p) => handleChangePass(p)}
                    onAddPass={handleAddPassClick}
                    onRenamePass={handleRenamePass}
                    selectedPass={selectedPass}
                />

                <Shown when={selectedPass !== undefined} fallback={<SelectPass passCount={passes?.length ?? 0} />}>
                    <FormProvider {...form}>
                        <PassForm
                            dataElements={dataElements}
                            passCount={passes.length}
                            onCancel={handleCancel}
                            onDelete={handleDelete}
                            onSave={() => setShowSaveModal(true)}
                        />
                        <SavePassModal
                            visible={showSaveModal}
                            onAccept={handleSave}
                            onCancel={() => setShowSaveModal(false)}
                        />
                    </FormProvider>
                </Shown>
            </Shown>
        </div>
    );
};
