import { Icon } from 'design-system/icon';
import { ReactNode } from 'react';

import styles from './closeable-panel.module.scss';

type HeadingLevel = 2 | 3 | 4 | 5;

type CloseHander = () => void;

type Closeable = {
    onClose: CloseHander;
};

type FooterRenderer = (closeable: Closeable) => ReactNode;

type Props = {
    title: string;
    headingLevel?: HeadingLevel;
    children: ReactNode;
    footer?: FooterRenderer;
} & Closeable;

const CloseablePanel = ({ title, headingLevel, children, footer, onClose }: Props) => {
    return (
        <div className={styles.panel}>
            <header>
                {renderHeader(title, headingLevel)}
                <Icon
                    name="close"
                    role="button"
                    className={styles.closer}
                    onClick={onClose}
                    aria-label={`Close ${title}`}
                />
            </header>
            {children}
            {footer && <footer>{footer({ onClose })}</footer>}
        </div>
    );
};

const renderHeader = (title: string, headingLevel?: HeadingLevel) => {
    if (headingLevel === 2) {
        return <h2>{title}</h2>;
    } else if (headingLevel === 3) {
        return <h3>{title}</h3>;
    } else if (headingLevel === 4) {
        return <h4>{title}</h4>;
    } else if (headingLevel === 5) {
        return <h5>{title}</h5>;
    } else {
        return title;
    }
};

export { CloseablePanel };
export type { Closeable };
