import { Icon } from '@trussworks/react-uswds';
import { Heading } from 'components/heading';
import { ReactNode } from 'react';
import styles from './side-panel.module.scss';

type Props = {
    heading: string;
    visible: boolean;
    onClose: () => void;
    children: ReactNode;
};
export const SidePanel = ({ heading, children, visible, onClose }: Props) => {
    return (
        <div
            className={styles.sidePanel}
            style={{ transition: visible ? '0.5s' : '0s', width: visible ? '27.5rem' : 0 }}>
            <div className={styles.heading}>
                <Heading level={2}>{heading}</Heading>
                <button onClick={onClose}>
                    <Icon.Close size={4} />
                </button>
            </div>
            <div className={styles.panelContent}>{children}</div>
        </div>
    );
};
