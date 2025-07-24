import { ReactNode } from 'react';
import { Heading, HeadingLevel } from 'components/heading';
import { Button } from 'design-system/button';

import styles from './closable-panel.module.scss';

type CloseHandler = () => void;

type Closable = {
    onClose: CloseHandler;
};

type FooterRenderer = (closeable: Closable) => ReactNode;

type ClosablePanelProps = {
    title: string;
    headingLevel?: HeadingLevel;
    children: ReactNode;
    footer?: FooterRenderer;
} & Closable &
    JSX.IntrinsicElements['div'];

const ClosablePanel = ({ title, headingLevel = 2, children, footer, onClose }: ClosablePanelProps) => {
    return (
        <div className={styles.panel}>
            <header>
                <Heading level={headingLevel}>{title}</Heading>
            </header>
            {children}
            {footer && <footer>{footer({ onClose })}</footer>}
            <Button icon="close" onClick={onClose} aria-label={`Close ${title}`} tertiary className={styles.closer} />
        </div>
    );
};

export { ClosablePanel };
export type { Closable };
