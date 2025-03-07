import { Icon } from '@trussworks/react-uswds';
import { Heading } from 'components/heading';
import { Shown } from 'conditional-render';
import { BlockingCriteriaSelection } from '../blocking-criteria/BlockingCriteriaSelection';
import styles from './side-panel.module.scss';
import { useEffect, useState } from 'react';

type Props = {
    state: { visible: boolean; content: 'blocking' | 'matching' };
    onClose: () => void;
};
export const SidePanel = ({ state, onClose }: Props) => {
    const [internalVisible, setInternalVisible] = useState(false);

    useEffect(() => {
        // Immediately shows content while expanding
        if (state.visible) {
            setInternalVisible(true);
        } else {
            // hides content of panel after collapsing
            setTimeout(() => {
                setInternalVisible(false);
            }, 500);
        }
    }, [state.visible]);

    return (
        <div className={styles.sidePanel} style={{ width: state.visible ? '27.5rem' : 0 }}>
            <Shown when={internalVisible}>
                <div className={styles.heading}>
                    <Heading level={2}>Add {state.content} attribute(s)</Heading>
                    <button onClick={onClose}>
                        <Icon.Close size={4} />
                    </button>
                </div>
                <div className={styles.panelContent}>
                    <Shown when={state.content === 'blocking'}>
                        <BlockingCriteriaSelection />
                    </Shown>
                </div>
            </Shown>
        </div>
    );
};
