import classNames from 'classnames';
import React, { ReactNode, useState, KeyboardEvent as ReactKeyboardEvent, useRef, useEffect } from 'react';
import styles from './overlay-panel.module.scss';

type Toggle = () => void;
type Close = () => void;

type ButtonRendererProps = {
    toggle: Toggle;
    ref: React.RefObject<HTMLButtonElement>;
};

type ToggleRenderer = (props: ButtonRendererProps) => JSX.Element;
type PanelRenderer = (close: Close, closeButtonRef?: React.RefObject<HTMLButtonElement>) => JSX.Element;

type OverlayPanelProps = {
    className?: string;
    position?: 'right' | 'left';
    toggle: ToggleRenderer;
    render: PanelRenderer;
    overlayVisible?: boolean;
};

const OverlayPanel = ({ className, toggle, render, position, overlayVisible }: OverlayPanelProps) => {
    const [visible, setVisible] = useState(false);
    const openerRef = useRef<HTMLButtonElement>(null);
    const closeButtonRef = useRef<HTMLButtonElement>(null);

    const handleToggle = () => setVisible((existing) => !existing);
    const handleClose = () => {
        setVisible(false);
        if (openerRef.current) {
            openerRef.current.focus();
        }
    };

    useEffect(() => {
        if (closeButtonRef.current) {
            closeButtonRef.current.focus();
        }
    }, [visible]);

    return (
        <div className={classNames(styles.overlay, className)}>
            {((overlayVisible !== undefined && overlayVisible) || visible) && (
                <Dialog position={position} onClose={handleClose}>
                    {render(handleClose, closeButtonRef)}
                </Dialog>
            )}
            {toggle({ toggle: handleToggle, ref: openerRef })}
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
