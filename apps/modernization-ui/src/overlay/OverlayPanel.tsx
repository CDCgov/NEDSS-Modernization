import classNames from 'classnames';
import React, { ReactNode, useState, KeyboardEvent as ReactKeyboardEvent, useRef, useEffect } from 'react';
import styles from './overlay-panel.module.scss';

type Toggle = (element?: React.MouseEvent<HTMLElement>) => void;
type Close = () => void;

type ButtonRendererProps = {
    toggle: Toggle;
};

type ToggleRenderer = (props: ButtonRendererProps) => JSX.Element;
type PanelRenderer = (close: Close) => JSX.Element;

type OverlayPanelProps = {
    className?: string;
    position?: 'right' | 'left';
    toggle: ToggleRenderer;
    render: PanelRenderer;
    overlayVisible?: boolean;
};

const OverlayPanel = ({ className, toggle, render, position, overlayVisible }: OverlayPanelProps) => {
    const [visible, setVisible] = useState(false);
    const [openerElement, setOpenerElement] = useState<HTMLElement | null>(null);

    const handleToggle = (element?: React.MouseEvent<HTMLElement>) => {
        element && setOpenerElement(element.currentTarget);
        setVisible((existing) => !existing);
    };
    const handleClose = () => {
        setVisible(false);
        openerElement?.focus();
        setOpenerElement(null);
    };

    return (
        <div className={classNames(styles.overlay, className)}>
            {toggle({ toggle: handleToggle })}
            {((overlayVisible !== undefined && overlayVisible) || visible) && (
                <Dialog position={position} onClose={handleClose}>
                    {render(handleClose)}
                </Dialog>
            )}
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
    const dialogRef = useRef<HTMLDialogElement>(null);

    useEffect(() => {
        if (dialogRef.current) {
            dialogRef.current.focus();
        }
    }, []);

    const handleKeyDown = (event: ReactKeyboardEvent) => {
        if (event.key === 'Escape') {
            event.preventDefault();
            onClose();
        }
    };

    return (
        <dialog
            ref={dialogRef}
            tabIndex={0}
            aria-label="Overlay modal"
            open
            className={classNames({ [styles.right]: position === 'right', [styles.left]: position === 'left' })}
            onKeyDown={handleKeyDown}>
            {children}
        </dialog>
    );
};
