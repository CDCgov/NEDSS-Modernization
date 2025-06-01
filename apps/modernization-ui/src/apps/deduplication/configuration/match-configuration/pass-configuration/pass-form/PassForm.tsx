import { DataElements } from 'apps/deduplication/api/model/DataElement';
import { BlockingAttribute, MatchingAttribute, MatchMethod, Pass } from 'apps/deduplication/api/model/Pass';
import { Shown } from 'conditional-render';
import { Button } from 'design-system/button';
import { useEffect, useState } from 'react';
import { useFormContext, useFormState, useWatch } from 'react-hook-form';
import { exists } from 'utils';
import { ActivateToggle } from './activate-toggle/ActivateToggle';
import { BlockingCriteria } from './blocking-criteria/BlockingCriteria';
import { BlockingCriteriaSidePanel } from './blocking-criteria/panel/BlockingCriteriaSidePanel';
import { DeletePassConfirmation } from './confirmation/DeletePassConfirmation';
import { UnsavedChangesConfirmation } from './confirmation/UnsavedChangesConfirmation';
import { MatchingBounds } from './matching-bounds/MatchingBounds';
import { MatchingCriteria } from './matching-criteria/MatchingCriteria';
import { MatchingCriteriaSidePanel } from './matching-criteria/panel/MatchingCriteriaSidePanel';
import styles from './pass-form.module.scss';

type Props = {
    passCount: number;
    dataElements: DataElements;
    onSave: () => void;
    onCancel: () => void;
    onDelete: () => void;
};
export const PassForm = ({ passCount, dataElements, onCancel, onDelete, onSave }: Props) => {
    const form = useFormContext<Pass>();
    const { isDirty, dirtyFields, isValid } = useFormState(form);
    const { id } = useWatch(form);
    const { name } = useWatch(form);
    const { matchingCriteria } = useWatch(form);
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

    const handleSelectMatchingAttributes = (attributes: MatchingAttribute[]) => {
        const newValue = attributes.map((m) => {
            // if attribute was already selected, set method to pre-selected method
            const method = matchingCriteria?.find((x) => x.attribute === m)?.method ?? MatchMethod.NONE;
            return { attribute: m, method: method, threshold: method === MatchMethod.EXACT ? 1 : undefined };
        });
        form.setValue('matchingCriteria', newValue, { shouldDirty: true, shouldValidate: true });
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
                    dataElements={dataElements}
                />
            </Shown>
            <Shown when={panelState.content === 'matching'}>
                <MatchingCriteriaSidePanel
                    onAccept={handleSelectMatchingAttributes}
                    onCancel={closePanel}
                    visible={panelState.visible}
                    dataElements={dataElements}
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
                <MatchingCriteria onAddAttributes={() => togglePanelState('matching')} dataElements={dataElements} />
                <MatchingBounds dataElements={dataElements} />
                <ActivateToggle />
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
                    <Button secondary onClick={handleCancelClick}>
                        Cancel
                    </Button>
                    <Button disabled={!isDirty || !exists(dirtyFields) || !isValid} onClick={onSave}>
                        Save pass configuration
                    </Button>
                </div>
            </div>
        </div>
    );
};
