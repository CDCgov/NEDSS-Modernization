import { Icon } from 'design-system/icon';
import { ReactNode, RefObject } from 'react';
import { Button } from 'design-system/button';
import styles from './closable-panel.module.scss';

type HeadingLevel = 2 | 3 | 4 | 5;

type CloseHander = () => void;

type Closable = {
    onClose: CloseHander;
};

type FooterRenderer = (closeable: Closable) => ReactNode;

type Props = {
    title: string;
    headingLevel?: HeadingLevel;
    children: ReactNode;
    footer?: FooterRenderer;
    closeButtonRef?: RefObject<HTMLButtonElement>;
} & Closable;

const ClosablePanel = ({ title, headingLevel, children, footer, onClose, closeButtonRef }: Props) => {
    return (
        <div className={styles.panel}>
            <header>{renderHeader(title, headingLevel)}</header>
            {children}
            {footer && <footer>{footer({ onClose })}</footer>}

            <Button
                ref={closeButtonRef}
                icon={<Icon sizing="medium" name="close" />}
                onClick={onClose}
                aria-label={`Close ${title}`}
                tertiary
                className={styles.closer}
            />
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

export { ClosablePanel };
export type { Closable };
