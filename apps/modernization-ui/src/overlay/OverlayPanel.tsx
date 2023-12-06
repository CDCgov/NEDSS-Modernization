import classNames from 'classnames';
import { ReactNode, useState, KeyboardEvent as ReactKeyboardEvent } from 'react';
import styles from './overlay-panel.module.scss';

type Toggle = () => void;
type Close = () => void;

type ButtonRendererProps = { toggle: Toggle };

type ToggleRenderer = (props: ButtonRendererProps) => JSX.Element;
type PanelRenderer = (close: Close) => JSX.Element;

type OverlayPanelProps = {
    position?: 'right' | 'left';
    toggle: ToggleRenderer;
    render: PanelRenderer;
};

const OverlayPanel = ({ toggle, render, position }: OverlayPanelProps) => {
    const [visible, setVisible] = useState(false);

    const handleToggle = () => setVisible((existing) => !existing);
    const handleClose = () => setVisible(false);

    return (
        <>
            <span className={styles.overlay}>
                {visible && (
                    <Dialog position={position} onClose={handleClose}>
                        {render(handleClose)}
                    </Dialog>
                )}
            </span>
            {toggle({ toggle: handleToggle })}
        </>
    );
};

export { OverlayPanel };

type DialogProps = {
    position?: 'right' | 'left';
    onClose: () => void;
    children: ReactNode;
};

const Dialog = ({ position, onClose, children }: DialogProps) => {
    const handleKeyDown = (event: ReactKeyboardEvent) => {
        if (event.key === 'Escape') {
            event.preventDefault();
            onClose();
        }
    };

    return (
        <dialog
            open
            className={classNames({ [styles.right]: position === 'right', [styles.left]: position === 'left' })}
            onKeyDown={handleKeyDown}>
            {children}
        </dialog>
    );
};
