import {
    BlockingAttribute,
    MatchingAttribute,
    MatchingAttributeEntry,
    MatchMethod,
    Pass
} from 'apps/deduplication/api/model/Pass';
import { Shown } from 'conditional-render';
import { Button } from 'design-system/button';
import { useEffect, useState } from 'react';
import { FormProvider, useForm, useWatch } from 'react-hook-form';
import { BlockingCriteria } from './blocking-criteria/BlockingCriteria';
import { BlockingCriteriaSidePanel } from './blocking-criteria/panel/BlockingCriteriaSidePanel';
import styles from './pass-form.module.scss';
import { MatchingCriteria } from './matching-criteria/MatchingCriteria';
import { MatchingCriteriaSidePanel } from './matching-criteria/panel/MatchingCriteriaSidePanel';

type Props = {
    initial: Pass;
};
export const PassForm = ({ initial }: Props) => {
    const form = useForm<Pass>({ defaultValues: initial });
    const { blockingCriteria, matchingCriteria } = useWatch(form);
    const [selectedBlockingAttributes, setSelectedBlockingAttributes] = useState<BlockingAttribute[]>([]);
    const [selectedMatchingAttributes, setSelectedMatchingAttributes] = useState<MatchingAttribute[]>([]);
    const [panelState, setPanelState] = useState<{ visible: boolean; content: 'blocking' | 'matching' }>({
        visible: false,
        content: 'blocking'
    });

    useEffect(() => {
        // Reset form when selected pass changes
        form.reset(initial, { keepDefaultValues: false });
        setPanelState({ visible: false, content: 'blocking' });
    }, [initial]);

    useEffect(() => {
        if (panelState.content === 'blocking') {
            setSelectedBlockingAttributes(blockingCriteria ?? []);
        } else if (panelState.content === 'matching') {
            setSelectedMatchingAttributes(
                matchingCriteria?.map((a) => a.attribute).filter((a) => a !== undefined) ?? []
            );
        }
    }, [blockingCriteria]);

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
        form.setValue('blockingCriteria', selectedBlockingAttributes);
        setPanelState({ ...panelState, visible: false });
    };

    const handleCloseBlockingPanel = () => {
        // hide panel
        setPanelState({ ...panelState, visible: false });

        // reset selected blocking criteria
        setSelectedBlockingAttributes(blockingCriteria ?? []);
    };

    const handleSelectMatchingAttributes = () => {
        // Remove entries no longer selected
        let current: MatchingAttributeEntry[] = (matchingCriteria ?? []) as MatchingAttributeEntry[];
        current = current.filter((a) => a.attribute !== undefined && selectedMatchingAttributes.includes(a.attribute));

        // Add newly selected entries
        selectedMatchingAttributes.forEach((selectedEntry) => {
            if (current.findIndex((c) => c.attribute === selectedEntry) === -1) {
                current.push({ attribute: selectedEntry, method: MatchMethod.NONE });
            }
        });

        form.setValue('matchingCriteria', current);
        setPanelState({ ...panelState, visible: false });
    };

    const handleCloseMatchingPanel = () => {
        // hide panel
        setPanelState({ ...panelState, visible: false });

        // reset selected matching criteria
        setSelectedMatchingAttributes(matchingCriteria?.map((a) => a.attribute).filter((a) => a !== undefined) ?? []);
    };

    return (
        <div className={styles.passForm}>
            <FormProvider {...form}>
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
                <div className={styles.formContent}>
                    <BlockingCriteria onAddAttributes={() => togglePanelState('blocking')} />
                    <MatchingCriteria onAddAttributes={() => togglePanelState('matching')} />
                </div>
            </FormProvider>
            <div className={styles.buttonBar}>
                <Button outline>Cancel</Button> <Button disabled>Save pass configuration</Button>
            </div>
        </div>
    );
};
