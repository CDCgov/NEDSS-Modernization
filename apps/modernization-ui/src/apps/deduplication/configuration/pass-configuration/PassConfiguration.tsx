import { Pass } from 'apps/deduplication/api/model/Pass';
import { useMatchConfiguration } from 'apps/deduplication/api/useMatchConfiguration';
import { Shown } from 'conditional-render';
import { useEffect, useState } from 'react';
import { FormProvider, useForm, useFormState } from 'react-hook-form';
import { SelectPass } from '../notification-cards/SelectPass';
import { PassList } from './pass-list/PassList';
import styles from './pass-configuration.module.scss';
import { UnsavedChangesConfirmation } from '../confirmation/UnsavedChangesConfirmation';
import { PassForm } from './pass-form/PassForm';
import { DataElements } from 'apps/deduplication/data-elements/DataElement';

type Props = {
    dataElements: DataElements;
};
export const PassConfiguration = ({ dataElements }: Props) => {
    const { passes, deletePass, savePass } = useMatchConfiguration();
    const form = useForm<Pass>({ mode: 'onBlur' });
    const { isDirty } = useFormState(form);
    const [newPass, setNewPass] = useState<Pass | undefined>();
    const [passList, setPassList] = useState<Pass[]>([]);
    const [selectedPass, setSelectedPass] = useState<Pass | undefined>();
    const [confirmationState, setConfirmationState] = useState<{
        visible: boolean;
        onAccept: (() => void) | undefined;
    }>({
        visible: false,
        onAccept: undefined
    });

    useEffect(() => {
        // Reset form when selected pass changes
        form.reset(
            { ...selectedPass, matchingCriteria: selectedPass?.matchingCriteria ?? [] },
            { keepDefaultValues: false }
        );
    }, [selectedPass]);

    useEffect(() => {
        const passList = [newPass, ...passes].filter((p) => p !== undefined);
        setPassList(passList);
    }, [newPass, passes]);

    const handleEditPassName = () => {
        console.log('edit pass name NYI');
    };

    const handleAddPassClick = () => {
        // If form is dirty, confirm data loss prior to action
        if (isDirty && selectedPass !== undefined) {
            setConfirmationState({ visible: true, onAccept: handleAddPass });
        } else {
            handleAddPass();
        }
    };

    const handleAddPass = () => {
        // Need to confirm data loss if a pass is already selected selected
        const newPass: Pass = {
            name: 'New pass configuration',
            blockingCriteria: [],
            matchingCriteria: [],
            active: false
        };
        setNewPass(newPass);
        setSelectedPass(newPass);
    };

    const handleChangePass = (pass: Pass) => {
        if (pass === selectedPass) {
            // user selected the pass that is already selected
            return;
        }
        // if dirty, confirm
        if (isDirty || (selectedPass === newPass && selectedPass !== undefined)) {
            setConfirmationState({ visible: true, onAccept: () => changeSelectedPass(pass) });
        } else {
            setSelectedPass(pass);
        }
    };

    const changeSelectedPass = (pass: Pass) => {
        setSelectedPass(pass);
        if (selectedPass?.id === undefined) {
            setNewPass(undefined);
        }
    };

    const handleCancel = () => {
        if (selectedPass === newPass) {
            setNewPass(undefined);
        }
        setSelectedPass(undefined);
    };

    const handleDelete = () => {
        if (selectedPass === undefined) {
            return;
        }
        if (selectedPass.id === undefined) {
            setNewPass(undefined);
        } else {
            deletePass(selectedPass.id);
        }
        setSelectedPass(undefined);
    };

    const handleSave = () => {
        savePass(form.getValues());
    };

    return (
        <div className={styles.passConfiguration}>
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
                passes={passList}
                onSetSelectedPass={(p) => handleChangePass(p)}
                onEditPassName={handleEditPassName}
                onAddPass={handleAddPassClick}
                selectedPass={selectedPass}
            />
            <Shown when={selectedPass !== undefined} fallback={<SelectPass passCount={passes.length} />}>
                <FormProvider {...form}>
                    <PassForm
                        dataElements={dataElements}
                        passCount={passes.length}
                        onCancel={handleCancel}
                        onDelete={handleDelete}
                        onSave={handleSave}
                    />
                </FormProvider>
            </Shown>
        </div>
    );
};
