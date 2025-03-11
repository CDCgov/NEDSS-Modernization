import { Icon } from '@trussworks/react-uswds';
import { Heading } from 'components/heading';
import { Shown } from 'conditional-render';
import styles from './side-panel.module.scss';
import { ReactNode, useEffect, useRef, useState } from 'react';

type Props = {
    heading: string;
    visible: boolean;
    onClose: () => void;
    children: ReactNode;
    footer?: ReactNode;
};
export const SidePanel = ({ heading, children, footer, visible, onClose }: Props) => {
    const divRef = useRef(null);
    const [panelWidth, setPanelWidth] = useState(0);

    useEffect(() => {
        // on width changes, update state
        const observer = new ResizeObserver((entries) => {
            const entry = entries[0];
            if (entry) {
                setPanelWidth(entry.contentRect.width);
            }
        });
        // assign ref to observer
        if (divRef.current) {
            observer.observe(divRef.current);
        }
    }, []);

    return (
        <div ref={divRef} className={styles.sidePanel} style={{ width: visible ? '27.5rem' : 0 }}>
            <div className={styles.fixedWidth}>
                {/* Hide content when panel is closed */}
                <Shown when={panelWidth > 0 || visible}>
                    <div className={styles.heading}>
                        <Heading level={2}>{heading}</Heading>
                        <button onClick={onClose}>
                            <Icon.Close size={4} />
                        </button>
                    </div>
                    <div className={styles.panelContent}>{children}</div>
                    <Shown when={footer !== undefined}>
                        <div className={styles.buttonBar}>{footer}</div>
                    </Shown>
                </Shown>
            </div>
        </div>
    );
};
