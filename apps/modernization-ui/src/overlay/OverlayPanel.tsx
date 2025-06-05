import classNames from 'classnames';
import React, { ReactNode, useState, KeyboardEvent as ReactKeyboardEvent } from 'react';
import styles from './overlay-panel.module.scss';

type Toggle = () => void;
type Close = () => void;

type ButtonRendererProps = { toggle: Toggle };

type ToggleRenderer = (props: ButtonRendererProps) => JSX.Element;
type PanelRenderer = (close: Close) => JSX.Element;

type OverlayPanelProps = {
    className?: string;
    position?: 'right' | 'left';
    toggle: ToggleRenderer;
    render: PanelRenderer;
    overlayVisible?: boolean;
    openerRef?: React.RefObject<HTMLButtonElement>;
};

const OverlayPanel = ({ className, toggle, render, position, overlayVisible, openerRef }: OverlayPanelProps) => {
    const [visible, setVisible] = useState(false);

    const handleToggle = () => setVisible((existing) => !existing);
    const handleClose = () => {
        setVisible(false);
        if (openerRef && openerRef.current) {
            openerRef.current.focus();
        }
    };

    return (
        <div className={classNames(styles.overlay, className)}>
            {((overlayVisible !== undefined && overlayVisible) || visible) && (
                <Dialog position={position} onClose={handleClose}>
                    {render(handleClose)}
                </Dialog>
            )}
            {toggle({ toggle: handleToggle })}
        </div>
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
