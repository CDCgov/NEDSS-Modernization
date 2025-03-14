import { BlockingAttribute, MatchingAttribute, MatchMethod, Pass } from 'apps/deduplication/api/model/Pass';
import { Shown } from 'conditional-render';
import { Button } from 'design-system/button';
import { useEffect, useState } from 'react';
import { useFormContext, useFormState, useWatch } from 'react-hook-form';
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
    onCancel: () => void;
    onDelete: () => void;
};
export const PassForm = ({ passCount, onCancel, onDelete }: Props) => {
    const form = useFormContext<Pass>();
    const { isDirty, isValid } = useFormState(form);
    const { blockingCriteria, matchingCriteria, name, id } = useWatch(form);
    const [selectedBlockingAttributes, setSelectedBlockingAttributes] = useState<BlockingAttribute[]>([]);
    const [selectedMatchingAttributes, setSelectedMatchingAttributes] = useState<MatchingAttribute[]>([]);
    const [showConfirmation, setShowConfirmation] = useState(false);
    const [showDeleteConfirmation, setShowDeleteConfirmation] = useState(false);
    const [panelState, setPanelState] = useState<{ visible: boolean; content: 'blocking' | 'matching' }>({
        visible: false,
        content: 'blocking'
    });

    useEffect(() => {
        setSelectedBlockingAttributes(blockingCriteria ?? []);
    }, [blockingCriteria]);

    useEffect(() => {
        setSelectedMatchingAttributes(matchingCriteria?.map((a) => a.attribute).filter((a) => a !== undefined) ?? []);
    }, [matchingCriteria]);

    useEffect(() => {
        setPanelState({ ...panelState, visible: false });
    }, [form]);

    const togglePanelState = (content: 'blocking' | 'matching') => {
        if (panelState.visible && panelState.content === content) {
            // Panel is currently visible and we have "toggled" the same attribute select
            if (content === 'blocking') {
                handleCloseBlockingPanel();
            } else {
                handleCloseMatchingPanel();
            }
        } else {
            if (panelState.visible) {
                // Panel is currently visible but we are opening new content
                if (content === 'blocking') {
                    // opening blocking selection, clear matching changes
                    setSelectedMatchingAttributes(
                        matchingCriteria?.map((a) => a.attribute).filter((a) => a !== undefined) ?? []
                    );
                } else {
                    // opening matching selection, clear blocking changes
                    setSelectedBlockingAttributes(blockingCriteria ?? []);
                }
            }
            setPanelState({ visible: true, content });
        }
    };

    const handleSelectBlockingAttributes = () => {
        form.setValue('blockingCriteria', selectedBlockingAttributes, { shouldDirty: true, shouldValidate: true });
        setPanelState({ ...panelState, visible: false });
    };

    const handleCloseBlockingPanel = () => {
        // hide panel
        setPanelState({ ...panelState, visible: false });

        // reset selected blocking criteria
        setSelectedBlockingAttributes(blockingCriteria ?? []);
    };

    const handleSelectMatchingAttributes = () => {
        const newValue = selectedMatchingAttributes.map((m) => {
            // if attribute was already selected, set method to pre-selected method
            const method = matchingCriteria?.find((x) => x.attribute === m)?.method ?? MatchMethod.NONE;
            return { attribute: m, method: method };
        });
        form.setValue('matchingCriteria', newValue, { shouldDirty: true, shouldValidate: true });
        setPanelState({ ...panelState, visible: false });
    };

    const handleCloseMatchingPanel = () => {
        // hide panel
        setPanelState({ ...panelState, visible: false });

        // reset selected matching criteria
        setSelectedMatchingAttributes(matchingCriteria?.map((a) => a.attribute).filter((a) => a !== undefined) ?? []);
    };

    const handleCancelClick = () => {
        if (isDirty) {
            setShowConfirmation(true);
        } else {
            onCancel();
        }
    };

    return (
        <div className={styles.passForm}>
            <Shown when={panelState.content === 'blocking'}>
                <BlockingCriteriaSidePanel
                    selectedAttributes={selectedBlockingAttributes}
                    onAccept={handleSelectBlockingAttributes}
                    onChange={setSelectedBlockingAttributes}
                    onCancel={handleCloseBlockingPanel}
                    visible={panelState.visible}
                />
            </Shown>
            <Shown when={panelState.content === 'matching'}>
                <MatchingCriteriaSidePanel
                    selectedAttributes={selectedMatchingAttributes}
                    onAccept={handleSelectMatchingAttributes}
                    onChange={setSelectedMatchingAttributes}
                    onCancel={handleCloseMatchingPanel}
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
                <MatchingCriteria onAddAttributes={() => togglePanelState('matching')} />
                <MatchingBounds />
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
                    <Button disabled={!isDirty || !isValid}>Save pass configuration</Button>
                </div>
            </div>
        </div>
    );
};
