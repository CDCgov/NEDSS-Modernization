import { Icon } from '@trussworks/react-uswds';
import { Heading } from 'components/heading';
import { Shown } from 'conditional-render';
import styles from './side-panel.module.scss';
import { ReactNode, useEffect, useState } from 'react';

type Props = {
    heading: string;
    visible: boolean;
    onClose: () => void;
    children: ReactNode;
    buttons?: ReactNode;
};
export const SidePanel = ({ heading, children, buttons, visible, onClose }: Props) => {
    const [internalVisible, setInternalVisible] = useState(false);

    useEffect(() => {
        // Immediately shows content while expanding
        if (visible) {
            setInternalVisible(true);
        } else {
            // hides content of panel after collapsing
            setTimeout(() => {
                setInternalVisible(false);
            }, 500);
        }
    }, [visible]);

    return (
        <div className={styles.sidePanel} style={{ width: visible ? '27.5rem' : 0 }}>
            <Shown when={internalVisible}>
                <div className={styles.heading}>
                    <Heading level={2}>{heading}</Heading>
                    <button onClick={onClose}>
                        <Icon.Close size={4} />
                    </button>
                </div>
                <div className={styles.panelContent}>{children}</div>
                <Shown when={buttons !== undefined}>
                    <div className={styles.buttonBar}>{buttons}</div>
                </Shown>
            </Shown>
        </div>
    );
};
