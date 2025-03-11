import { BlockingAttribute, Pass } from 'apps/deduplication/api/model/Pass';
import { Shown } from 'conditional-render';
import { Button } from 'design-system/button';
import { useEffect, useState } from 'react';
import { FormProvider, useForm, useWatch } from 'react-hook-form';
import { BlockingCriteria } from './blocking-criteria/BlockingCriteria';
import { BlockingCriteriaSidePanel } from './blocking-criteria/panel/BlockingCriteriaSidePanel';
import styles from './pass-form.module.scss';

type Props = {
    initial: Pass;
};
export const PassForm = ({ initial }: Props) => {
    const form = useForm<Pass>({ defaultValues: initial });
    const { blockingCriteria } = useWatch(form);
    const [selectedBlockingAttributes, setSelectedBlockingAttributes] = useState<BlockingAttribute[]>([]);
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
        }
    }, [blockingCriteria]);

    const togglePanelState = (content: 'blocking' | 'matching') => {
        if (panelState.visible && panelState.content === content) {
            setPanelState({ visible: false, content });
        } else {
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
                <div className={styles.formContent}>
                    <BlockingCriteria onAddAttributes={() => togglePanelState('blocking')} />
                </div>
            </FormProvider>
            <div className={styles.buttonBar}>
                <Button outline>Cancel</Button> <Button disabled>Save pass configuration</Button>
            </div>
        </div>
    );
};
