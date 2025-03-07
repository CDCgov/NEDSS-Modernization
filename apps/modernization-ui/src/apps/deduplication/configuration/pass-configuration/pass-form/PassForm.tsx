import { Pass } from 'apps/deduplication/api/model/Pass';
import { Button } from 'design-system/button';
import { useEffect, useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { BlockingCriteria } from './blocking-criteria/BlockingCriteria';
import styles from './pass-form.module.scss';
import { SidePanel } from './side-panel/SidePanel';

type Props = {
    initial: Pass;
};
export const PassForm = ({ initial }: Props) => {
    const form = useForm<Pass>({ defaultValues: initial });
    const [panelState, setPanelState] = useState<{ visible: boolean; content: 'blocking' | 'matching' }>({
        visible: false,
        content: 'blocking'
    });

    useEffect(() => {
        form.reset(initial, { keepDefaultValues: false });
        setPanelState({ visible: false, content: 'blocking' });
    }, [initial]);

    const togglePanelState = (content: 'blocking' | 'matching') => {
        if (panelState.visible && panelState.content === content) {
            setPanelState({ visible: false, content });
        } else {
            setPanelState({ visible: true, content });
        }
    };

    return (
        <div className={styles.passForm}>
            <FormProvider {...form}>
                <div className={styles.formContent}>
                    <SidePanel state={panelState} onClose={() => togglePanelState(panelState.content)} />
                    <BlockingCriteria onShowAttributes={() => togglePanelState('blocking')} />
                </div>
            </FormProvider>
            <div className={styles.buttonBar}>
                <Button outline>Cancel</Button> <Button disabled>Save pass configuration</Button>
            </div>
        </div>
    );
};
