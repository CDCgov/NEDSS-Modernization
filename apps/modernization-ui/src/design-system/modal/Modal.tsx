import { ReactNode, KeyboardEvent as ReactKeyboardEvent, useRef, useEffect } from 'react';
import { createPortal } from 'react-dom';
import classNames from 'classnames';
import { Button } from 'design-system/button';

import styles from './modal.module.scss';
import { Heading } from 'components/heading';
import { Icon } from 'design-system/icon';

type Close = () => void;

type FooterRenderer = (close: Close) => ReactNode;

type ModalProps = {
    id: string;
    title: string;
    size?: 'small' | 'large';
    /**
     * Whether to force interaction on the modal. A modal with a forced action does not display the
     *  close button nor will it close when the escape button is pressed.
     */
    forceAction?: boolean;
    className?: string;
    ariaDescribedBy?: string;
    children: ReactNode;
    onClose: Close;
    footer?: FooterRenderer;
};

const Modal = (props: ModalProps) => createPortal(<Component {...props} />, document.body);

const Component = ({
    id,
    title,
    size,
    forceAction = false,
    children,
    className,
    ariaDescribedBy,
    onClose,
    footer
}: ModalProps) => {
    const focused = useRef<boolean>(false);
    const element = useRef<HTMLDialogElement>(null);
    const header = `${id}-header`;

    useEffect(() => {
        if (element.current && !focused.current) {
            element.current.focus();
            focused.current = true;
        }
    }, [element.current, focused]);

    const handleKeyDown = (event: ReactKeyboardEvent) => {
        if (event.key === 'Escape') {
            event.preventDefault();
            onClose();
        }
    };

    return (
        <div
            id={`${id}-wrapper`}
            className="usa-modal-wrapper"
            onKeyDown={(!forceAction && handleKeyDown) || undefined}>
            <div className={classNames('usa-modal-overlay', styles.overlay)}>
                <dialog
                    ref={element}
                    id={id}
                    aria-labelledby={header}
                    aria-describedby={ariaDescribedBy}
                    className={classNames('usa-modal', styles.modal, className, {
                        [styles.small]: size === 'small',
                        [styles.large]: size === 'large'
                    })}
                    data-force-action={forceAction}
                    open>
                    <header id={header} className={'usa-modal__heading'}>
                        <Heading level={2}>{title}</Heading>
                        {!forceAction && (
                            <Button
                                className={styles.closer}
                                tertiary
                                aria-label={`Close ${title}`}
                                icon={<Icon name="close" />}
                                onClick={onClose}
                                data-close-modal
                            />
                        )}
                    </header>
                    <div className={'usa-modal__content'}>
                        {footer && <footer className="usa-modal__footer">{footer(onClose)}</footer>}
                        <div className={classNames('usa-modal__main', styles.main)}>{children}</div>
                    </div>
                </dialog>
            </div>
        </div>
    );
};

export { Modal };
