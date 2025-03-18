import { BlockingAttribute, Pass } from 'apps/deduplication/api/model/Pass';
import { Shown } from 'conditional-render';
import { Button } from 'design-system/button';
import { useEffect, useState } from 'react';
import { useFormContext, useFormState, useWatch } from 'react-hook-form';
import { BlockingCriteria } from './blocking-criteria/BlockingCriteria';
import { BlockingCriteriaSidePanel } from './blocking-criteria/panel/BlockingCriteriaSidePanel';
import { DeletePassConfirmation } from './confirmation/DeletePassConfirmation';
import { UnsavedChangesConfirmation } from './confirmation/UnsavedChangesConfirmation';
import styles from './pass-form.module.scss';

type Props = {
    passCount: number;
    onSave: () => void;
    onCancel: () => void;
    onDelete: () => void;
};
export const PassForm = ({ passCount, onCancel, onDelete, onSave }: Props) => {
    const form = useFormContext<Pass>();
    const { isDirty, isValid } = useFormState(form);
    const { id } = useWatch(form);
    const { name } = useWatch(form);
    const [showConfirmation, setShowConfirmation] = useState(false);
    const [showDeleteConfirmation, setShowDeleteConfirmation] = useState(false);
    const [panelState, setPanelState] = useState<{ visible: boolean; content: 'blocking' | 'matching' }>({
        visible: false,
        content: 'blocking'
    });

    useEffect(() => {
        setPanelState({ ...panelState, visible: false });
    }, [form]);

    const togglePanelState = (content: 'blocking' | 'matching') => {
        if (panelState.visible && panelState.content === content) {
            closePanel();
        } else {
            setPanelState({ visible: true, content });
        }
    };

    const closePanel = () => {
        setPanelState({ ...panelState, visible: false });
    };

    const handleSelectBlockingAttributes = (attributes: BlockingAttribute[]) => {
        form.setValue('blockingCriteria', attributes, { shouldDirty: true, shouldValidate: true });
        closePanel();
    };

    const handleCancelClick = () => {
        if (isDirty || id === undefined) {
            setShowConfirmation(true);
        } else {
            onCancel();
        }
    };

    return (
        <div className={styles.passForm}>
            <Shown when={panelState.content === 'blocking'}>
                <BlockingCriteriaSidePanel
                    onAccept={handleSelectBlockingAttributes}
                    onCancel={closePanel}
                    visible={panelState.visible}
                />
            </Shown>
            <UnsavedChangesConfirmation
                passName={name ?? ''}
                visible={showConfirmation}
                onAccept={() => {
                    onCancel();
                    setShowConfirmation(false);
                }}
                onCancel={() => setShowConfirmation(false)}
            />
            <DeletePassConfirmation
                passName={name ?? ''}
                visible={showDeleteConfirmation}
                onAccept={() => {
                    onDelete();
                    setShowDeleteConfirmation(false);
                }}
                onCancel={() => setShowDeleteConfirmation(false)}
                isLastPass={passCount === 1}
            />
            <div className={styles.formContent}>
                <BlockingCriteria onAddAttributes={() => togglePanelState('blocking')} />
            </div>
            <div className={styles.buttonBar}>
                <div>
                    <Shown when={id !== undefined}>
                        <Button secondary destructive sizing="small" onClick={() => setShowDeleteConfirmation(true)}>
                            Delete pass configuration
                        </Button>
                    </Shown>
                </div>
                <div>
                    <Button outline onClick={handleCancelClick}>
                        Cancel
                    </Button>
                    <Button disabled={!isDirty || !isValid} onClick={onSave}>
                        Save pass configuration
                    </Button>
                </div>
            </div>
        </div>
    );
};
