import { ReactNode, KeyboardEvent as ReactKeyboardEvent, useRef, useEffect, useId, useCallback } from 'react';
import { createPortal } from 'react-dom';
import classNames from 'classnames';
import { Heading } from 'components/heading';
import { Button } from 'design-system/button';

import styles from './modal.module.scss';

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
    children: ReactNode;
    onClose: Close;
    footer?: FooterRenderer;
};

const Modal = (props: ModalProps) => createPortal(<Component {...props} />, document.body);

const Component = ({ title, size, forceAction = false, children, className, onClose, footer }: ModalProps) => {
    const focused = useRef<boolean>(false);
    const element = useRef<HTMLDialogElement>(null);

    const id = useId();
    const header = useId();
    const content = useId();

    useEffect(() => {
        if (element.current && !focused.current) {
            element.current.focus();
            focused.current = true;
        }
    }, [element.current, focused]);

    const handleKeyDown = useCallback(
        (event: ReactKeyboardEvent) => {
            if (!forceAction && event.key === 'Escape') {
                event.preventDefault();
                onClose();
            }
        },
        [forceAction, onClose]
    );

    return (
        <div className="usa-modal-wrapper" onKeyDown={handleKeyDown}>
            <div className={classNames('usa-modal-overlay', styles.overlay)}>
                <dialog
                    ref={element}
                    id={id}
                    aria-labelledby={header}
                    aria-describedby={content}
                    className={classNames('usa-modal', styles.modal, className, {
                        [styles.small]: size === 'small',
                        [styles.large]: size === 'large'
                    })}
                    data-force-action={forceAction}
                    open>
                    <header id={header} className={classNames('usa-modal__heading', styles.header)}>
                        <Heading level={2}>{title}</Heading>
                        {!forceAction && (
                            <Button
                                className={styles.closer}
                                tertiary
                                aria-label={`Close ${title}`}
                                icon="close"
                                onClick={onClose}
                                data-close-modal
                            />
                        )}
                    </header>
                    <div className={'usa-modal__content'}>
                        {footer && (
                            <footer className={classNames('usa-modal__footer', styles.footer)}>
                                {footer(onClose)}
                            </footer>
                        )}
                        <div id={content} className={classNames('usa-modal__main', styles.main)}>
                            {children}
                        </div>
                    </div>
                </dialog>
            </div>
        </div>
    );
};

export { Modal };
