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
    const [internalVisible, setInternalVisible] = useState<boolean>(false);
    const divRef = useRef(null);
    const [panelWidth, setPanelWidth] = useState(0);

    useEffect(() => {
        // forces initialization of component at 0 width so transition always happens
        setInternalVisible(visible);
    }, [visible]);

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
        // remove observer when unmounting
        return () => {
            if (divRef.current) {
                observer.unobserve(divRef.current);
            }
        };
    }, []);

    return (
        <div ref={divRef} className={styles.sidePanel} style={{ width: internalVisible ? '27.5rem' : 0 }}>
            <div className={styles.fixedWidth}>
                {/* Hide content when panel is closed */}
                <Shown when={panelWidth > 0 || visible}>
                    <div className={styles.heading}>
                        <Heading level={2}>{heading}</Heading>
                        <button onClick={onClose} aria-label={`Close ${heading}`}>
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
